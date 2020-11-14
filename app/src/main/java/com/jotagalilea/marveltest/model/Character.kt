package com.jotagalilea.marveltest.model

import com.google.gson.annotations.SerializedName
import com.jotagalilea.marveltest.apiclient.CharacterThumbnail
import java.io.Serializable

/**
 * Class to hold a character's relevant data.
 */
data class Character(
	@SerializedName("id")	var id: Int,
	@SerializedName("name") var name: String,
	@SerializedName("description") var description: String,
	@SerializedName("thumbnail") var thumbnail: CharacterThumbnail
): Serializable
