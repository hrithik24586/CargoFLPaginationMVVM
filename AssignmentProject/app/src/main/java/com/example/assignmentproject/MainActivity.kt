package com.example.assignmentproject

import android.health.connect.datatypes.units.Length
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecyclerListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.assignmentproject.Data.CatRepository
import com.example.assignmentproject.Retrofit.CatApiService
import com.example.assignmentproject.ui.CatAdapter
import com.example.assignmentproject.ui.CatViewModel
import com.example.assignmentproject.ui.CatViewModelFactory
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var catViewModel: CatViewModel
    private lateinit var catAdapter: CatAdapter
    private var nextPage : Int  = 1;
    private var isLoading:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        var progresBar: ProgressBar = findViewById(R.id.progressBar)


        val apiService = CatApiService.create()
        val repository = CatRepository(apiService)

        catViewModel = ViewModelProvider(this, CatViewModelFactory(repository))
            .get(CatViewModel::class.java)

        catAdapter = CatAdapter(emptyList())



        recyclerView.adapter = catAdapter

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        /*val layoutManager  = GridLayoutManager(this,2)
        recyclerView.layoutManager = layoutManager*/
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItems = layoutManager.findLastVisibleItemPositions(null)
                val lastVisibleItem = lastVisibleItems.maxOrNull() ?: 0
                // Load more data when nearing the end and not already loading
                //if (!isLoading && lastVisibleItem == totalItemCount - 1)
                var visibleThreshold: Int = 5
                if (!isLoading && lastVisibleItem + visibleThreshold >= totalItemCount && dy > 0){
                    Log.d("OnScrollListener", "Loading more data. TotalItemCount: $totalItemCount, LastVisibleItem: $lastVisibleItem")
                    isLoading = true
                    progresBar.visibility = View.VISIBLE
                    nextPage++
                    catViewModel.getCatImages(100, nextPage)
                }
            }
        })

        // Observe the LiveData from ViewModel
        catViewModel.catImages.observe(this, Observer {
            isLoading = false
            progresBar.visibility = View.GONE
            catAdapter.updateData(it)

            /*// Check if it's the last page
            if (it.isEmpty()) {
                // It's the last page
                // Set isLastPage = true or handle accordingly
                Toast.makeText(applicationContext,"No Data To Load cause youre on Last Page",Toast.LENGTH_LONG )
            }*/
        })

        // Initial API call
        catViewModel.getCatImages(100, nextPage)
    }
}