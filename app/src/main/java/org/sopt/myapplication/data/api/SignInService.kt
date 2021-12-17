package org.sopt.myapplication.data.api

import org.sopt.myapplication.data.request.RequestSignInData
import org.sopt.myapplication.data.response.ResponseSignUpData
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST("user/login")
    suspend fun getSignIn(
        @Body body : RequestSignInData
    ) : ResponseSignUpData
}