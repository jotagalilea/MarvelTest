package com.jotagalilea.marveltest.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jotagalilea.marveltest.R
import com.jotagalilea.marveltest.model.Character
import com.jotagalilea.marveltest.viewmodel.CharactersViewModel

/**
 * Main fragment which contains the view model and objects the recycler needs.
 */
class MainFragment : Fragment(), CharactersRecyclerAdapter.OnItemClickListener {

	private lateinit var viewModel: CharactersViewModel
	private lateinit var recyclerView: RecyclerView
	private lateinit var recyclerAdapter: CharactersRecyclerAdapter
	private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager
	private lateinit var errorImage: ImageView
	private lateinit var errorMsg: TextView
	private lateinit var swipeRefreshLayout: SwipeRefreshLayout


	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View {
		return inflater.inflate(R.layout.main_fragment, container, false)
	}


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		viewModel = ViewModelProvider(this).get(CharactersViewModel::class.java)

		// Prepare the view componentes and the observer.
		setupView(view)
		setupObserver()

		viewModel.requestCharactersList()
		super.onViewCreated(view, savedInstanceState)
	}


	/**
	 * It sets the observer for list refreshing. This allows to display an error message
	 * in case that there is a problem requesting characters data.
	 */
	private fun setupObserver(){
		viewModel.getCharactersList().observe(viewLifecycleOwner,
			Observer<MutableList<Character>> { newList ->
				if (newList != null) {
					recyclerAdapter.setItems(newList)
					hideErrorMsg()
				}
				/*
				 * In case that liveData object needs to be destroyed we need to create a new one
				 * and attach the observer again.
				 */
				else {
					viewModel.createNewList()
					setupObserver()
					showErrorMsg()
				}
			}
		)
	}


	/**
	 * It sets up the views and the scroll listener.
	 */
	private fun setupView(view: View){
		recyclerLayoutManager = LinearLayoutManager(activity)
		recyclerAdapter = CharactersRecyclerAdapter(this)
		recyclerView = view.findViewById<RecyclerView>(R.id.chars_recycler)!!.apply {
			setHasFixedSize(true)
			layoutManager = recyclerLayoutManager
			adapter = recyclerAdapter
		}
		errorImage = view.findViewById(R.id.main_error_image)
		errorMsg = view.findViewById(R.id.main_error_text)
		swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
		/*
		 * This listener is useful here to detect when the user reaches the recycler bottom end in
		 * order to make a new request, taking into account the offset for the web service (which is
		 * equal to the request default limit not to repeat characters into the list).
		 */
		recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
			override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
				super.onScrollStateChanged(recyclerView, newState)
				if (!recyclerView.canScrollVertically(1) && (newState==RecyclerView.SCROLL_STATE_IDLE)){
					try{
						viewModel.requestCharactersList()
					}
					catch (e: Exception){
						Log.e("ERROR LOADING", e.printStackTrace().toString())
						showErrorMsg()
					}
				}
			}
		})

		// It sets up the listener for the swipeRefreshLayout to build new requests swiping down the screen.
		swipeRefreshLayout.setOnRefreshListener {
			viewModel.requestCharactersList()
			swipeRefreshLayout.isRefreshing = false
		}
	}


	/**
	 * It displays the default error message.
	 */
	private fun showErrorMsg(){
		errorImage.visibility = View.VISIBLE
		errorMsg.visibility = View.VISIBLE
	}

	/**
	 * It hides the error message.
	 */
	private fun hideErrorMsg(){
		errorImage.visibility = View.INVISIBLE
		errorMsg.visibility = View.INVISIBLE
	}

	/**
	 * Implementation for the view holder to navigate to the character detail screen.
	 */
	override fun onItemClick(character: Character) {
		(activity as MainActivity).navigateToDetail(character)
	}


}