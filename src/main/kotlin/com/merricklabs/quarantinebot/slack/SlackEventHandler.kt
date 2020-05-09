package com.merricklabs.quarantinebot.slack

import com.merricklabs.quarantinebot.config.QuarantineBotConfig
import com.merricklabs.quarantinebot.config.SlackConfig
import com.merricklabs.quarantinebot.external.slack.client.SlackClient
import com.merricklabs.quarantinebot.external.slack.messages.ChannelType.CHANNEL
import com.merricklabs.quarantinebot.external.slack.messages.ChannelType.GROUP
import com.merricklabs.quarantinebot.external.slack.messages.ChannelType.IM
import com.merricklabs.quarantinebot.external.slack.messages.CreateMessagePayload
import com.merricklabs.quarantinebot.external.slack.messages.SlackEvent
import com.merricklabs.quarantinebot.external.slack.messages.SlackEventType
import com.merricklabs.quarantinebot.util.OutputFormatter
import mu.KotlinLogging
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Singleton
import kotlin.math.abs

private val log = KotlinLogging.logger {}

@Singleton
class SlackEventHandler(
        private val slackClient: SlackClient,
        private val config: QuarantineBotConfig,
        private val slackConfig: SlackConfig
) {
    fun handle(event: SlackEvent): String? {
        log.info("Handling Slack event")
        when (event.channelType) {
            CHANNEL, GROUP -> handleChannelEvent(event)
            IM -> handleImEvent(event)
            else -> fallbackHandler(event)
        }
        return null
    }

    private fun handleChannelEvent(event: SlackEvent) {
        log.info("Handling channel event")
        if (event.type != SlackEventType.APP_MENTION && !event.text.contains(slackConfig.botName, true)) {
            log.info("Text \"${event.text}\" does not contain ${slackConfig.botName}, skipping")
            return
        }

        postReply(event)
    }

    private fun handleImEvent(event: SlackEvent) {
        log.info("Handling IM event")
        postReply(event)
    }

    private fun fallbackHandler(event: SlackEvent) {
        log.info("Fallback event handler")
        // Handle event in same way as a channel event
        handleChannelEvent(event)
    }

    private fun postReply(event: SlackEvent) {
        val replyText = getReplyText(event)

        log.info("Posting reply: $replyText")
        val reply = CreateMessagePayload(
                event.channel,
                replyText
        )

        slackClient.postMessage(reply)
    }

    private fun getReplyText(event: SlackEvent): String {
        return if (event.text.toLowerCase().contains("how many")) {
            val numDays = abs(ChronoUnit.DAYS.between(LocalDate.now(), config.quarantineDate))
            "It's been this many days:\n${OutputFormatter.printFormattedCount(numDays.toInt())}"
        } else {
            "Usage:\n `how many`: Print how many days you've been quarantined."
        }
    }
}