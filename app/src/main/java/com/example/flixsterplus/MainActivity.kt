package com.example.flixsterplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var rvMovies: RecyclerView
    private val movies = mutableListOf<Movie>()
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMovies = findViewById(R.id.rvMovies)
        adapter = MovieAdapter(movies)
        rvMovies.adapter = adapter
        rvMovies.layoutManager = LinearLayoutManager(this)

        fetchNowPlayingMovies()
    }

    private fun fetchNowPlayingMovies() {
        val url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"

        val client = AsyncHttpClient()
        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                try {
                    val results: JSONArray = json.jsonObject.getJSONArray("results")
                    for (i in 0 until results.length()) {
                        val movieObject = results.getJSONObject(i)
                        val movie = Movie.fromJson(movieObject)
                        movies.add(movie)
                    }
                    adapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Log.e("MainActivity", "JSON parsing error", e)
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e("MainActivity", "Request failed: $statusCode $response", throwable)
            }
        })
    }
}
