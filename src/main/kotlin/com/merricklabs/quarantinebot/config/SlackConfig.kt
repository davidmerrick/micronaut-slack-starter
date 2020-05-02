package com.merricklabs.quarantinebot.config

import io.micronaut.context.annotation.ConfigurationProperties
import javax.inject.Singleton
import javax.validation.constraints.NotBlank

@Singleton
@ConfigurationProperties("slack")
class SlackConfig {
    @get:NotBlank
    lateinit var token: String

    @get:NotBlank
    lateinit var botName: String
}