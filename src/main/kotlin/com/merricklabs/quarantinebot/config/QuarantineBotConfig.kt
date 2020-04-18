package com.merricklabs.quarantinebot.config

import io.micronaut.context.annotation.ConfigurationProperties

const val PREFIX = "quarantinebot"

@ConfigurationProperties(PREFIX)
class QuarantineBotConfig {
    var quarantineDateString: String? = null
}