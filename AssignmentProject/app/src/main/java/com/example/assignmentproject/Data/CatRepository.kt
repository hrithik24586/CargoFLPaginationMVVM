package com.example.assignmentproject.Data

import android.util.Log
import com.example.assignmentproject.Retrofit.CatApiService
import retrofit2.HttpException

class CatRepository (private val apiService: CatApiService) {
    suspend fun getCatImages(limit: Int, page: Int): List<CatImage> {
        try {
            return apiService.getCatImages(limit, page)
        } catch (e: HttpException) {
            Log.e("CatRepository", "HTTP Error: ${e.message()}")
            throw e
        } catch (e: Exception) {
            Log.e("CatRepository", "Error fetching cat images: ${e.message}")
            throw e
        }
    }

}