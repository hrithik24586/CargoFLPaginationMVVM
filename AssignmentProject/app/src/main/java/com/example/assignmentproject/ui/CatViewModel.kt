package com.example.assignmentproject.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentproject.Data.CatImage
import com.example.assignmentproject.Data.CatRepository
import kotlinx.coroutines.launch

class CatViewModel(private val repository: CatRepository) : ViewModel()
{
    private val mCatImages = MutableLiveData<List<CatImage>>()
    val catImages: LiveData<List<CatImage>> get() = mCatImages

    fun getCatImages(limit: Int, page: Int) {
        viewModelScope.launch {
            try {
                val catImages = repository.getCatImages(limit, page)
                Log.d("CatViewModel", "Cat Images: $catImages")
                mCatImages.value = catImages
            } catch (e: Exception) {
                Log.e("CatViewModel", "Error fetching cat images: ${e.message}")
            }
        }
    }
}