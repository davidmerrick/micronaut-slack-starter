package com.merricklabs.quarantinebot.external.slack.messages

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected

@JsonIgnoreProperties(ignoreUnknown = true)
@Introspected
data class SlackEvent(
        @JsonProperty("type")
        val type: SlackEventType,
        @JsonProperty("subtype")
        val subtype: String?,
        @JsonProperty("user")
        val user: String,
        @JsonProperty("text")
        val text: String,
        @JsonProperty("channel")
        val channel: String,
        @JsonProperty("channel_type")
        val channelType: ChannelType?,
        @JsonProperty("bot_id")
        val botId: String?
)