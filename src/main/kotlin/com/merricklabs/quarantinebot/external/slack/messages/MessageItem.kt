package com.merricklabs.quarantinebot.external.slack.messages

import io.micronaut.core.annotation.Introspected

@Introspected
data class MessageItem(
        val type: String,
        val channel: String,
        val ts: String
)