package com.merricklabs.quarantinebot.slack

import com.merricklabs.quarantinebot.external.slack.messages.EventCallbackMessage
import com.merricklabs.quarantinebot.external.slack.messages.SlackChallenge
import com.merricklabs.quarantinebot.external.slack.messages.SlackMessage
import mu.KotlinLogging
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

@Singleton
class SlackMessageHandler(
        private val eventHandler: SlackEventHandler
) {
    fun handle(message: SlackMessage): String? {
        log.info("Received Slack message of type ${message.type}")
        when (message) {
            is SlackChallenge -> run {
                log.info("Handling Slack challenge message")
                return message.challenge
            }
            is EventCallbackMessage -> run {
                log.info("Handling event callback message")
                if (message.isBotMessage()) {
                    log.info("Skipping bot message")
                    return null
                }
                eventHandler.handle(message.event)
            }
            else -> run {
                log.warn("Unhandled Slack message type ${message.type}")
            }
        }
        return null
    }
}