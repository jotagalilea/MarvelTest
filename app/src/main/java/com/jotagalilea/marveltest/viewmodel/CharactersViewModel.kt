package com.jotagalilea.marveltest.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jotagalilea.marveltest.apiclient.MarvelEndpoints
import com.jotagalilea.marveltest.generateMD5Hash
import com.jotagalilea.marveltest.generateTimestamp
import com.jotagalilea.marveltest.model.Character
import com.jotagalilea.marveltest.apiclient.CharactersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * View model class that holds references to the model objects and the API.
 */
class CharactersViewModel : ViewModel() {

	private val TAG = "Getting characters"
	private var requestOffset: Int = 0
	private var retrofit: Retrofit
	private var api: MarvelEndpoints
	private var charactersResponse: CharactersResponse? = null
	// This object must be assigned to null just in case something goes wrong with the request.
	private var charactersList: MutableLiveData<MutableList<Character>> = MutableLiveData(mutableListOf())


	/**
	 * Retrofit initialization for use with Marvel API.
	 */
	init {
		retrofit = Retrofit.Builder()
			.baseUrl(MarvelEndpoints.BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()

		api = retrofit.create(MarvelEndpoints::class.java)
	}


	/**
	 * It returns the characters list previously obtained.
	 */
	fun getCharactersList(): MutableLiveData<MutableList<Character>>{
		return charactersList
	}


	/**
	 * It makes a request for retrieve some characters from the web service (20 per request),
	 * generating a timestamp and an MD5 hash.
	 */
	fun requestCharactersList(){
		val ts = generateTimestamp()
		val hash = generateMD5Hash(ts + MarvelEndpoints.privateKey + MarvelEndpoints.apikey)
		val call: Call<CharactersResponse> = api.getCharactersList(requestOffset, hash, ts)

		call.enqueue(object : Callback<CharactersResponse> {
			override fun onResponse(
				call: Call<CharactersResponse>,
				response: Response<CharactersResponse>
			) {
				if (response.isSuccessful) {
					charactersResponse = response.body()
					charactersResponse?.charactersAdapter?.charactersList?.let{
						val result = charactersList.value
						result?.addAll(it as MutableList<Character>)
						charactersList.postValue(result)
						Log.d(TAG, charactersResponse.toString())
					}
					requestOffset += 20
				} else {
					/*
					 * Here it is assigned to null just to make the observer react and order the
					 * fragment to show the error message. The same thing has to happen in the
					 * onFailure() method.
					 */
					charactersList.value = null
					requestOffset = 0
					Log.e(TAG, response.message())
				}
			}

			override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
				charactersList.value = null
				requestOffset = 0
				Log.e(TAG, call.toString())
			}
		})
	}


	/**
	 * Method needed to ask this viewModel to remake the liveData and allow
	 * a new observer to be attached. It should only be called from outside this class
	 * in order to set up a new observer in the same class.
	 */
	fun createNewList(){
		charactersList = MutableLiveData(mutableListOf())
	}

}