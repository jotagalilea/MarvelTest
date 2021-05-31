package com.jotagalilea.marveltest.view

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.jotagalilea.marveltest.ImgUtils
import com.jotagalilea.marveltest.R
import com.jotagalilea.marveltest.model.Character
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.lang.StringBuilder

/**
 * Detail screen intended to display a character's info.
 */
class DetailFragment : Fragment() {

	private lateinit var character: Character
	private lateinit var nameText: TextView
	private lateinit var descriptionText: TextView
	private lateinit var image: ImageView
	private lateinit var container: ConstraintLayout

	private val TAG_PICASSO_ERROR = "PICASSO ERROR"
	private val TAG_PICASSO_SUCCESS = "PICASSO SUCCESS"


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		character = arguments?.get("Character") as Character
	}


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupView(view)
	}

	/**
	 * Prepares the view and it is filled with received info.
	 */
	private fun setupView(view: View){
		nameText = view.findViewById(R.id.detail_name)
		descriptionText = view.findViewById(R.id.detail_description)
		image = view.findViewById(R.id.detail_image)
		container = view.findViewById(R.id.detail_container)
		nameText.text = character.name
		descriptionText.text = character.description
		setImage()
	}

	/**
	 * Calls Picasso API to get a large image from url.
	 */
	private fun setImage(){
		val path = StringBuilder().apply {
			append(character.thumbnail.path)
			append(ImgUtils.IMG_LANDSCAPE_XLARGE)
			append('.')
			append(character.thumbnail.extension)
		}.toString()
		Picasso.get().isLoggingEnabled = true
		Picasso.get()
			.load(path)
			.error(R.drawable.ic_error)
			.into(image, object: Callback {
				override fun onSuccess() {
					Log.d(TAG_PICASSO_SUCCESS, "Success retrieving image")
					val dominantColor = Palette.generate(image.drawable.toBitmap()).getDominantColor(resources.getColor(R.color.primaryColor))
					val background = container.background
					background.setTint(dominantColor)
				}
				override fun onError(e: Exception?) {
					image.setImageResource(R.drawable.ic_error)
					Log.e(TAG_PICASSO_ERROR, e?.stackTrace.toString())
				}
			})
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_detail, container, false)
	}

}