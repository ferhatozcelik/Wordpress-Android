package org.ferhatozcelik.ui.fragments.contact

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.ferhatozcelik.BuildConfig
import org.ferhatozcelik.BuildConfig.DONATION_URL
import org.ferhatozcelik.BuildConfig.INSTAGRAM_URL
import org.ferhatozcelik.BuildConfig.SPOTIFY_URL
import org.ferhatozcelik.BuildConfig.TWITCH_URL
import org.ferhatozcelik.BuildConfig.TWITTER_URL
import org.ferhatozcelik.BuildConfig.YOUTUBE_URL
import org.ferhatozcelik.R
import org.ferhatozcelik.databinding.FragmentContactBinding
import org.ferhatozcelik.ui.dialogs.AppInfoDialog
import org.ferhatozcelik.util.*

/**
 *
 * @author ferhatozcelik
 * @since 2023-04-01
 */

@AndroidEntryPoint
class ContactFragment : Fragment(R.layout.fragment_contact) {

    private val viewModel: ContactViewModel by viewModels()
    private lateinit var binding: FragmentContactBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentContactBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {

        binding.apply {

            verisonTextview.text = getString(R.string.version) + " " +  BuildConfig.VERSION_NAME
            donationCard.setOnClickListener {
                UrlOpen(DONATION_URL)
            }
            twitchCard.setOnClickListener {
                UrlOpen(TWITCH_URL)
            }
            youtubeCard.setOnClickListener {
                UrlOpen(YOUTUBE_URL)
            }
            twitterCard.setOnClickListener {
                UrlOpen(TWITTER_URL)
            }
            instagramCard.setOnClickListener {
                UrlOpen(INSTAGRAM_URL)
            }
            spotifyCard.setOnClickListener {
                UrlOpen(SPOTIFY_URL)
            }
            starCard.setOnClickListener {
                val detailsUri = Uri.parse("market://details?id=" + (context?.packageName))
                val intent = Intent(Intent.ACTION_VIEW, detailsUri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            developerCard.setOnClickListener {
                AppInfoDialog(requireContext(), requireActivity()).createAppInfoDialog()
            }
        }

    }

    private fun UrlOpen(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

}