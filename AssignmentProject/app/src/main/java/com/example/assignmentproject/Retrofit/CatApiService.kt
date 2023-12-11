package com.example.assignmentproject.Retrofit

import com.example.assignmentproject.Data.CatImage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService
{
    @GET("images/search")
    suspend fun getCatImages(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): List<CatImage>

    companion object {
        private const val BASE_URL = "https://api.thecatapi.com/v1/"

        fun create(): CatApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(CatApiService::class.java)
        }
    }
}
