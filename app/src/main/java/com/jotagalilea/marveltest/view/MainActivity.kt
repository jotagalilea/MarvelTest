package com.jotagalilea.marveltest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.jotagalilea.marveltest.R
import com.jotagalilea.marveltest.model.Character

/**
 * Main activity that contains only a navigation controller, and a navHost in its respective xml file.
 */
class MainActivity : AppCompatActivity() {

	private lateinit var navController: NavController


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_activity)
		navController = findNavController(R.id.nav_host)
	}


	/**
	 * Uses the navigation component calling its controller to navigate between destinations,
	 * although it is very simple for this test app.
	 */
	fun navigateToDetail(character: Character){
		val bundle = bundleOf("Character" to character)
		navController.navigate(R.id.action_main_to_detail, bundle)
	}
}