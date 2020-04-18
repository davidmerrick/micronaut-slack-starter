package com.merricklabs.quarantinebot.external.slack.messages

import io.micronaut.core.annotation.Introspected

@Introspected
data class CreateMessagePayload(
        val channel: String,
        val text: String,
        val threadTs: String? = null
)