package com.merricklabs.quarantinebot.external.slack.messages

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected

@JsonIgnoreProperties(ignoreUnknown = true)
@Introspected
data class SlackEvent(
        val type: SlackEventType,
        val subtype: String?,
        val user: String,
        val text: String,
        val channel: String,
        @JsonProperty("channel_type")
        val channelType: String,
        @JsonProperty("bot_id")
        val botId: String?
)