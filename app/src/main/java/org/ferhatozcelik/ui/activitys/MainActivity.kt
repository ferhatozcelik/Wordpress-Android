package org.ferhatozcelik.ui.activitys

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.removeItemAt
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import org.ferhatozcelik.BuildConfig
import org.ferhatozcelik.R
import org.ferhatozcelik.databinding.ActivityMainBinding
import org.ferhatozcelik.util.GeneralUtil


/**
 *
 * @author ferhatozcelik
 * @since 2023-03-29
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        if (GeneralUtil().isGooglePlayServicesAvailable(this)){
            FirebaseApp.initializeApp(applicationContext)
        }
        navController = findNavController(R.id.navHostFragment)
        setupSmoothBottomMenu()
    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_navigation_menu)
        val menu = popupMenu.menu
        activityMainBinding.bottomNavigationView.setupWithNavController(menu, navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
