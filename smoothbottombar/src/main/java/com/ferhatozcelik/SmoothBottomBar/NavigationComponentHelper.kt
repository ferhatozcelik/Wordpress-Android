package com.ferhatozcelik.SmoothBottomBar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import java.lang.ref.WeakReference

class NavigationComponentHelper {

    companion object {

        fun setupWithNavController(
            menu: Menu, smoothBottomBar: SmoothBottomBar, navController: NavController) {
            smoothBottomBar.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelect(pos: Int): Boolean {
                    return onNavDestinationCustomSelected(menu.getItem(pos), navController)
                }
            }

            val weakReference = WeakReference(smoothBottomBar)
            navController.addOnDestinationChangedListener(object: NavController.OnDestinationChangedListener {
                override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
                    val view = weakReference.get()
                    if (view == null) {
                        navController.removeOnDestinationChangedListener(this)
                        return
                    }

                    for (h in 0 until menu.size()) {
                        val menuItem = menu.getItem(h)
                        if (matchDestination(destination, menuItem.itemId)) {
                            menuItem.isChecked = true
                            smoothBottomBar.itemActiveIndex = h
                        }
                    }
                }
            })
        }

        fun onNavDestinationCustomSelected(item: MenuItem, navController: NavController): Boolean {
            val builder = NavOptions.Builder().setLaunchSingleTop(true).setRestoreState(true)
            if (navController.currentDestination !!.parent !!.findNode(item.itemId) is ActivityNavigator.Destination) {
            /*builder.setEnterAnim(R.anim.slide_in_right)
                    .setExitAnim(R.anim.slide_out_left)
                    .setPopEnterAnim(R.anim.slide_in_left)
                    .setPopExitAnim(R.anim.slide_out_right)*/
            } else {
            /*builder.setEnterAnim(R.anim.slide_in_right)
                    .setExitAnim(R.anim.slide_out_left)
                    .setPopEnterAnim(R.anim.slide_in_left)
                    .setPopExitAnim(R.anim.slide_out_right)*/
            }
            if (item.order and Menu.CATEGORY_SECONDARY == 0) {
                builder.setPopUpTo(
                    navController.graph.findStartDestination().id,
                    inclusive = false,
                    saveState = true
                )
            }
            val options = builder.build()
            return try {
                navController.navigate(item.itemId, null, options)
                navController.currentDestination?.matchDestination(item.itemId) == true
            } catch (e: IllegalArgumentException) {
                false
            }
        }

        @JvmStatic
        internal fun NavDestination.matchDestination(@IdRes destId: Int,): Boolean = hierarchy.any { it.id == destId }

        /**
         * Determines whether the given `destId` matches the NavDestination. This handles
         * both the default case (the destination's id matches the given id) and the nested case where
         * the given id is a parent/grandparent/etc of the destination.
         */
        fun matchDestination(destination: NavDestination, @IdRes destId: Int): Boolean {
            var currentDestination: NavDestination? = destination
            while (currentDestination !!.id != destId && currentDestination.parent != null) {
                currentDestination = currentDestination.parent
            }
            return currentDestination.id == destId
        }
    }
}
