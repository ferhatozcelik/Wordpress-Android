package org.ferhatozcelik.ui.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import org.ferhatozcelik.BuildConfig
import org.ferhatozcelik.R

/**
 *
 * @author ferhatozcelik
 * @since 2023-04-01
 */

class AppInfoDialog(var context: Context, var activity: Activity) {

    private companion object{
        val GITHUB_URL = "https://github.com/ferhatozcelik"
        val FERHATOZCELIK_PROFILE = "https://ferhatozcelik.com/"
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    fun createAppInfoDialog() {

        val builder = AlertDialog.Builder(context)
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.dialog_app_info, activity.findViewById<View>(R.id.layoutDialogContainer) as RelativeLayout?
        )
        builder.setView(view)

        val alertDialog = builder.create()

        val version: TextView = (view.findViewById(R.id.version)) as TextView
        val githubCard: LinearLayout = (view.findViewById(R.id.github_card)) as LinearLayout
        val ferhatozcelikCard: LinearLayout = (view.findViewById(R.id.ferhatozcelik_card)) as LinearLayout
        val closeButton: ImageButton = (view.findViewById(R.id.close_button)) as ImageButton

        version.text = context.getString(R.string.version) + " " +  BuildConfig.VERSION_NAME

        githubCard.setOnClickListener {
            UrlOpen(GITHUB_URL)

        }
        ferhatozcelikCard.setOnClickListener {
            UrlOpen(FERHATOZCELIK_PROFILE)
        }

        closeButton.setOnClickListener {
            alertDialog.dismiss()
        }

        if (alertDialog.window != null) {
            alertDialog.window !!.setBackgroundDrawable(ColorDrawable(0))
        }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun UrlOpen(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        context.startActivity(i)
    }
}