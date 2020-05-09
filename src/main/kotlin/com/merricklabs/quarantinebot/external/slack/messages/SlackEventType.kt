package com.merricklabs.quarantinebot.external.slack.messages

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected

@Introspected
enum class SlackEventType {
    @JsonProperty("app_mention")
    APP_MENTION,
    @JsonProperty("reaction_added")
    REACTION_ADDED,
    @JsonProperty("url_verification")
    URL_VERIFICATION,
    @JsonProperty("message.channels")
    MESSAGE_CHANNELS,
    @JsonProperty("message")
    MESSAGE,
    @JsonProperty("message.groups")
    MESSAGE_GROUPS,
    @JsonProperty("message.im")
    MESSAGE_IM,
    @JsonProperty("message.mpim")
    MESSAGE_MPIM,
    @JsonProperty("message.app_home")
    MESSAGE_APP_HOME,
    @JsonProperty("pin_added")
    PIN_ADDED,
    @JsonProperty("pin_removed")
    PIN_REMOVED
}