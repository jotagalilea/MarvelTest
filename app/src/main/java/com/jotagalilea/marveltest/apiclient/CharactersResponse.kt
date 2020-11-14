package com.jotagalilea.marveltest.apiclient

import com.google.gson.annotations.SerializedName

/**
 * Data class to hold relevant data from the response.
 */
data class CharactersResponse(
	@SerializedName("code") var statusCode: Int,
	@SerializedName("status") var status: String,
	@SerializedName("data") var charactersAdapter: CharactersAdapter

)