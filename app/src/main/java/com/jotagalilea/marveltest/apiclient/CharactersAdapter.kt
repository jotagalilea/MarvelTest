package com.jotagalilea.marveltest.apiclient

import com.google.gson.annotations.SerializedName
import com.jotagalilea.marveltest.model.Character

/**
 * Adapter to access the characters list within the json structure.
 */
data class CharactersAdapter (
	@SerializedName("results") var charactersList: List<Character>
)