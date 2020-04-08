package com.merricklabs

import io.micronaut.function.client.FunctionClient
import io.micronaut.http.annotation.Body
import io.reactivex.Single
import javax.inject.Named

// 1
@FunctionClient
interface AppClient {

    // 2
    @Named("pirate-translator")
    // 3
    fun apply(@Body body : HandlerInput): Single<HandlerOutput>
}