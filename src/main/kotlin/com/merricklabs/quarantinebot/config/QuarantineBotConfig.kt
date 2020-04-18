package com.merricklabs.quarantinebot.config

import io.micronaut.context.annotation.ConfigurationProperties

const val PREFIX = "quarantineBot"

@ConfigurationProperties(PREFIX)
class QuarantineBotConfig {
    var quarantineDate: String? = null
}