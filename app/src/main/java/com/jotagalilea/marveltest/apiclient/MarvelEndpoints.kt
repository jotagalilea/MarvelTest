package com.jotagalilea.marveltest.apiclient

import retrofit2.Call
import retrofit2.http.*


interface MarvelEndpoints {

	/**
	 * These are variables needed to make requests.
	 */
	companion object{
		val BASE_URL = "https://gateway.marvel.com"
		const val apikey = "5088309b19c5d63e64f5075f293604c1"
		const val privateKey = "6b1823a411fe43b45a4c8e991341bac930b8792d"
		private const val apikey_get = "?apikey=$apikey"
	}

	/**
	 * Request for a list of characters (20 by default) with a given offset.
	 */
	@GET("/v1/public/characters$apikey_get")
	fun getCharactersList(
		@Query("offset") offset: Int,
		@Query("hash") hash: String,
		@Query("ts") timestamp: String
	): Call<CharactersResponse>


	/**
	 * Request for a single character's info. This endpoint is never used because it doesn't bring
	 * any info different from the previous one, but the same data. When navigating to a character's
	 * detail screen the app already has all the info needed.
	 */
	@GET("/v1/public/characters/{id}$apikey_get")
	fun getCharacterDetail(
		@Path("id") id: String,
		@Query("hash") hash: String,
		@Query("ts") timestamp: String
	): Call<CharactersResponse>

}