package com.ahmed.cfholding_venues.retrofit

import com.ahmed.cfholding_venues.utils.Constants.Headers.ACCEPT
import com.ahmed.cfholding_venues.utils.Constants.Headers.ACCEPT_VALUE
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
            .newBuilder()
            .addHeader(ACCEPT, ACCEPT_VALUE)
            .build()
        return chain.proceed(request)
    }
}