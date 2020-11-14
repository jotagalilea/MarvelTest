package com.jotagalilea.marveltest.apiclient

import com.google.gson.annotations.SerializedName

/**
 * Class to save each character's thumbnail. In fact, it saves basic info for getting images of any
 * size, which it must to be appended to the path using the Utils.kt file constants.
 */
data class CharacterThumbnail (
	@SerializedName("path") var path: String,
	@SerializedName("extension") var extension: String
)