package com.merricklabs.quarantinebot.external.slack

import com.merricklabs.quarantinebot.config.QuarantineBotConfig
import com.merricklabs.quarantinebot.config.SlackConfig
import com.merricklabs.quarantinebot.external.slack.client.SlackClient
import com.merricklabs.quarantinebot.external.slack.messages.ChannelType.CHANNEL
import com.merricklabs.quarantinebot.external.slack.messages.ChannelType.IM
import com.merricklabs.quarantinebot.external.slack.messages.CreateMessagePayload
import com.merricklabs.quarantinebot.external.slack.messages.SlackEvent
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
        when (event.channelType) {
            CHANNEL -> handleChannelEvent(event)
            IM -> handleImEvent(event)
        }
        return null
    }

    private fun handleChannelEvent(event: SlackEvent) {
        log.info("Handling channel event")
        if (!event.text.contains(slackConfig.botName)) {
            return
        }

        postReply(event)
    }

    private fun handleImEvent(event: SlackEvent) {
        log.info("Handling IM event")
        postReply(event)
    }

    private fun postReply(event: SlackEvent) {
        val replyText = getReplyText(event)

        val reply = CreateMessagePayload(
                event.channel,
                replyText
        )

        slackClient.postMessage(reply)
    }

    private fun getReplyText(event: SlackEvent): String {
        return if (event.text.toLowerCase().contains("how long")) {
            val numDays = abs(ChronoUnit.DAYS.between(LocalDate.now(), config.quarantineDate))
            "It's been this many days:\n${OutputFormatter.printFormattedCount(numDays.toInt())}"
        } else {
            "Usage:\n `how long`: Print how long you've been quarantined."
        }
    }
}