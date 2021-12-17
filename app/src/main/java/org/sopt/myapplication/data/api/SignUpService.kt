package org.sopt.myapplication.data.api

import org.sopt.myapplication.data.request.RequestSignUpData
import org.sopt.myapplication.data.response.ResponseSignUpData
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("user/signup")
    suspend fun getSignUp(
        @Body body: RequestSignUpData
    ): ResponseSignUpData
}