package com.merricklabs.quarantinebot.external.slack.client

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected

@JsonIgnoreProperties(ignoreUnknown = true)
@Introspected
data class SlackResponse(
        @JsonProperty("ok")
        val ok: Boolean,
        @JsonProperty("error")
        val error: String?
)