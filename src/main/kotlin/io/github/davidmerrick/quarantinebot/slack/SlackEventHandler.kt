package io.github.davidmerrick.quarantinebot.slack

import io.github.davidmerrick.quarantinebot.config.QuarantineBotConfig
import io.github.davidmerrick.slakson.messages.SlackEvent
import io.github.davidmerrick.quarantinebot.util.OutputFormatter
import io.github.davidmerrick.slakson.client.SlackClient
import io.github.davidmerrick.slakson.messages.ChannelType.CHANNEL
import io.github.davidmerrick.slakson.messages.ChannelType.GROUP
import io.github.davidmerrick.slakson.messages.ChannelType.IM
import io.github.davidmerrick.slakson.messages.CreateMessagePayload
import io.github.davidmerrick.slakson.messages.SlackEventType
import mu.KotlinLogging
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Singleton
import kotlin.math.abs

private val log = KotlinLogging.logger {}

@Singleton
class SlackEventHandler(
        private val slackClient: SlackClient,
        private val config: QuarantineBotConfig
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
        if (event.type != SlackEventType.APP_MENTION && !event.text.contains(config.botName, true)) {
            log.info("Text does not contain ${config.botName}, skipping")
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
        return if (event.text.contains("how many", true)) {
            val numDays = abs(ChronoUnit.DAYS.between(LocalDate.now(), config.quarantineDate))
            "It's been this many days:\n${OutputFormatter.printFormattedCount(numDays.toInt())}"
        } else {
            "Usage:\n `how many`: Print how many days you've been quarantined."
        }
    }
}