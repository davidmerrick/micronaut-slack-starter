package com.merricklabs

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

// 1
class AppFunctionTest: Spek({

    describe("app function") {
        // 2
        val server = ApplicationContext.run(EmbeddedServer::class.java)

        // 3
        val client = server.applicationContext.getBean(AppClient::class.java)

        it("should return 'Ahoy!'") {
            val body = HandlerInput("Hello")
            // 4
            assertEquals("Ahoy!", client.apply(body).blockingGet().pirateMessage
            )
        }

        afterGroup {
            server.stop()
        }
    }
})
