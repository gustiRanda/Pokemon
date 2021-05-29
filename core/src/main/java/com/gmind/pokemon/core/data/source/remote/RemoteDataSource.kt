package com.gmind.pokemon.core.data.source.remote

import android.util.Log
import com.gmind.pokemon.core.data.source.remote.network.ApiResponse
import com.gmind.pokemon.core.data.source.remote.network.ApiService
import com.gmind.pokemon.core.data.source.remote.response.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllPokemon(): Flow<ApiResponse<List<PokemonResponse>>> {

        //get data from remote api
        return flow {
            try {
                val response = apiService.getList()
                val dataArray = response.pokemon
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.pokemon))
                } else{
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}

