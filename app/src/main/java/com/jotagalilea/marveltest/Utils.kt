package com.jotagalilea.marveltest

import java.security.MessageDigest

/**
 * Utils class intended for constants and functions that are not appropriate to be defined
 * into other files or classes.
 */

/**
 * Possible sizes of characters images from the API. They must be appended to the path that
 * Picasso API is about to process at each request.
 */
object ImgUtils {
	const val IMG_PORTRAIT_SMALL = "/portrait_small"
	const val IMG_PORTRAIT_MEDIUM = "/portrait_medium"
	const val IMG_PORTRAIT_XLARGE = "/portrait_xlarge"
	const val IMG_PORTRAIT_FANTASTIC = "/portrait_fantastic"
	const val IMG_PORTRAIT_UNCANNY = "/portrait_uncanny"
	const val IMG_PORTRAIT_INCREDIBLE = "/portrait_incredible"

	const val IMG_STANDARD_SMALL = "/standard_small"
	const val IMG_STANDARD_MEDIUM = "/standard_medium"
	const val IMG_STANDARD_LARGE = "/standard_large"
	const val IMG_STANDARD_XLARGE = "/standard_xlarge"
	const val IMG_STANDARD_FANTASTIC = "/standard_fantastic"
	const val IMG_STANDARD_AMAZING = "/standard_amazing"

	const val IMG_LANDSCAPE_SMALL = "/landscape_small"
	const val IMG_LANDSCAPE_MEDIUM = "/landscape_medium"
	const val IMG_LANDSCAPE_LARGE = "/landscape_large"
	const val IMG_LANDSCAPE_XLARGE = "/landscape_xlarge"
	const val IMG_LANDSCAPE_AMAZING = "/landscape_amazing"
	const val IMG_LANDSCAPE_INCREDIBLE = "/landscape_incredible"
}

/**
 * It generates a simple timestamp taking currentTimeMillis().
 */
fun generateTimestamp(): String{
	return System.currentTimeMillis().toString()
}


/**
 * It generates an MD5 hash using MessageDigest.
 */
fun generateMD5Hash(str: String): String{
	return MessageDigest.getInstance("MD5")
		.digest(str.toByteArray(Charsets.UTF_8))
		.joinToString("") { "%02x".format(it)}
}
