package com.merricklabs.quarantinebot.external.slack.client

import com.merricklabs.quarantinebot.config.SlackConfig
import com.merricklabs.quarantinebot.external.slack.SlackPaths.BASE_API_PATH
import com.merricklabs.quarantinebot.external.slack.SlackPaths.POST_MESSAGE_ENDPOINT
import com.merricklabs.quarantinebot.external.slack.messages.CreateMessagePayload
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import mu.KotlinLogging
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

@Singleton
class SlackClientImpl(
        private val slackConfig: SlackConfig,
        @Client(BASE_API_PATH) private val client: HttpClient
) : SlackClient {
    override fun postMessage(payload: CreateMessagePayload) {
        val request = HttpRequest.POST(
                POST_MESSAGE_ENDPOINT,
                payload
        )
        request.headers.add("Authorization", "Bearer ${slackConfig.token}")
        val response = client.toBlocking()
                .exchange(request, SlackResponse::class.java)
        if (response.status != HttpStatus.OK) {
            log.error("Received bad status from Slack: $response")
        } else {
            val body = response.body.orElseThrow { RuntimeException("Slack didn't return a response") }
            handleResponse(body)
        }
    }

    private fun handleResponse(response: SlackResponse) {
        response.error?.let {
            log.error(it)
            return
        }
        log.info("Success: Posted response to Slack")
    }
}