package com.jotagalilea.marveltest.apiclient

import com.google.gson.annotations.SerializedName

/**
 * Class to save each character's thumbnail.
 */
data class CharacterThumbnail (
	@SerializedName("path") var path: String,
	@SerializedName("extension") var extension: String
)