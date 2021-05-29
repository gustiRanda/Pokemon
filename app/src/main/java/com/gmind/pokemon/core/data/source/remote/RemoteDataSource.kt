package com.gmind.pokemon.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmind.pokemon.core.data.source.remote.network.ApiResponse
import com.gmind.pokemon.core.data.source.remote.network.ApiService
import com.gmind.pokemon.core.data.source.remote.response.ListPokemonResponse
import com.gmind.pokemon.core.data.source.remote.response.PokemonResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    fun getAllPokemon(): LiveData<ApiResponse<List<PokemonResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<PokemonResponse>>>()

        //get data from remote api
        val client = apiService.getList()

        client.enqueue(object : Callback<ListPokemonResponse> {
            override fun onResponse(
                call: Call<ListPokemonResponse>,
                response: Response<ListPokemonResponse>
            ) {
                val dataArray = response.body()?.pokemon
                resultData.value = if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<ListPokemonResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }

        })

        return resultData
    }
}

