package com.merricklabs.quarantinebot.external.slack

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
        return when (message) {
            is SlackChallenge -> message.challenge
            is EventCallbackMessage -> run {
                if(!message.isBotMessage()){
                    eventHandler.handle(message.event)
                }
                null
            }
            else -> run {
                log.warn("Unhandled Slack message type ${message.type}")
                null
            }
        }
    }
}