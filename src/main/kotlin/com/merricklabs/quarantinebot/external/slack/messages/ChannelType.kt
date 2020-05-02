package com.merricklabs.quarantinebot.external.slack.messages

import com.fasterxml.jackson.annotation.JsonProperty

const val CHANNEL_STRING = "channel"
const val IM_STRING = "im"

enum class ChannelType {
    @JsonProperty(CHANNEL_STRING)
    CHANNEL,
    @JsonProperty(IM_STRING)
    IM
}
