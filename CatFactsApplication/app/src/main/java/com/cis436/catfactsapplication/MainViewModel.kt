package com.cis436.catfactsapplication

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    data class CatBreed(
        val name: String,
        val temperament: String,
        val origin: String
    )

    // LiveData to hold the list of cat breeds
    private val _catBreeds = MutableLiveData<List<CatBreed>>()
    val catBreeds: LiveData<List<CatBreed>> = _catBreeds

    init {
        fetchCatData()
    }

    private fun fetchCatData() {
        // Replace the API key with your own
        val apiKey = "live_qeNspEDlyTOdcN2PrX7JaA9D3LKsh0Ec05DND1goauXX9MbVKdO3I1R3WHD0Zxro"
        val catUrl = "https://api.thecatapi.com/v1/breeds?api_key=$apiKey"

        // Create a request queue using the application context
        val queue = Volley.newRequestQueue(getApplication<Application>().applicationContext)

        // Request a string response from the provided URL
        val stringRequest = StringRequest(
            Request.Method.GET, catUrl,
            { response ->
                // Parse the JSON response
                val catBreedsList = parseCatBreeds(response)
                // Update the LiveData with the parsed cat breeds
                _catBreeds.value = catBreedsList
            },
            {
                Log.e("MainViewModel", "Error occurred while fetching cat breeds")
            })

        // Add the request to the RequestQueue
        queue.add(stringRequest)
    }

    private fun parseCatBreeds(response: String): List<CatBreed> {
        val catBreedsList = mutableListOf<CatBreed>()
        try {
            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString("name")
                val temperament = jsonObject.optString("temperament", "")
                val origin = jsonObject.optString("origin", "")
                catBreedsList.add(CatBreed(name, temperament, origin))
            }
        } catch (e: Exception) {
            Log.e("MainViewModel", "Error parsing JSON response: ${e.message}")
        }
        return catBreedsList
    }
}