package org.ferhatozcelik.util

import android.app.Activity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class GeneralUtil {

    fun isGooglePlayServicesAvailable(activity: Activity?): Boolean {
        if(activity != null){
            val googleApiAvailability: GoogleApiAvailability = GoogleApiAvailability.getInstance()
            val status: Int = googleApiAvailability.isGooglePlayServicesAvailable(activity)
            if (status != ConnectionResult.SUCCESS) {
                return false
            }
        }
        return true
    }

}