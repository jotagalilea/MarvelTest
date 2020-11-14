package com.jotagalilea.marveltest.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.jotagalilea.marveltest.ImgUtils
import com.jotagalilea.marveltest.R
import com.jotagalilea.marveltest.model.Character
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.lang.StringBuilder

/**
 * An adapter for the characters recycler view.
 */
class CharactersRecyclerAdapter(private val onItemClickListener: OnItemClickListener)
	: RecyclerView.Adapter<CharactersRecyclerAdapter.CharsRowViewHolder>() {

	private var characters: MutableLiveData<MutableList<Character>> = MutableLiveData(mutableListOf())
	private val TAG_PICASSO_ERROR = "PICASSO ERROR"


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharsRowViewHolder {
		val container = LayoutInflater.from(parent.context)
			.inflate(R.layout.char_item, parent, false) as ConstraintLayout
		return CharsRowViewHolder(container, onItemClickListener)

	}


	override fun onBindViewHolder(holder: CharsRowViewHolder, position: Int) {
		// Thumbnail path building
		val item = characters.value?.get(position)
		val path = StringBuilder().apply {
			append(item?.thumbnail?.path)
			append(ImgUtils.IMG_STANDARD_LARGE)
			append('.')
			append(item?.thumbnail?.extension)
		}.toString()

		// Retrives the image with Picasso.
		Picasso.get().isLoggingEnabled = true
		Picasso.get()
			.load(path)
			.error(R.drawable.ic_error)
			.into(holder.image, object: Callback{
				override fun onSuccess() {
					holder.hideLoader()
				}
				override fun onError(e: Exception?) {
					holder.hideLoader()
					Log.e(TAG_PICASSO_ERROR, e?.stackTrace.toString())
				}
			})

		holder.name.text = item?.name
	}


	/**
	 * Useful to set the first items into the recycler.
	 */
	fun setItems(newItems: MutableList<Character>){
		characters.postValue(newItems)
		notifyDataSetChanged()
	}

	/**
	 * It clears the recycler.
	 */
	fun clearItems(){
		characters.value?.clear()
		notifyDataSetChanged()
	}


	override fun getItemCount(): Int {
		return characters.value?.size ?: 0
	}


	/**
	 * Interface to make easier to attach different onClickListeners to the recycler view holder.
	 */
	interface OnItemClickListener {
		fun onItemClick(character: Character)
	}


	//------------------------- ViewHolder -----------------------------//
	/**
	 * View holder for each character's name, description and thumbnail.
	 */
	inner class CharsRowViewHolder(
		itemView: View,
		private var onItemClickListener: OnItemClickListener
	) : RecyclerView.ViewHolder(itemView), View.OnClickListener  {

		var image: ImageView = itemView.findViewById(R.id.char_thumb_list)
		var name: TextView = itemView.findViewById(R.id.char_name_list)
		private var loader: ProgressBar = itemView.findViewById(R.id.img_loader)

		/**
		 * It attaches this view holder onClick implementation to the item view.
 		 */
		init {
			itemView.setOnClickListener(this)
		}

		/**
		 * It calls the listener wherever it is defined.
		 */
		override fun onClick(v: View?) {
			val selectedItem: Character = characters.value!![adapterPosition]
			onItemClickListener.onItemClick(selectedItem)
		}

		fun hideLoader(){
			loader.visibility = View.GONE
		}

		fun showLoader(){
			loader.visibility = View.VISIBLE
		}
	}
}