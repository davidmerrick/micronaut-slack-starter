package com.merricklabs.quarantinebot.external.slack.messages

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected

@Introspected
enum class ChannelType {
    @JsonProperty("channel")
    CHANNEL,
    @JsonProperty("group")
    GROUP,
    @JsonProperty("im")
    IM
}
