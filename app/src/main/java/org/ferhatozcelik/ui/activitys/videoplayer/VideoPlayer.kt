package org.ferhatozcelik.ui.activitys.videoplayer

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import dagger.hilt.android.AndroidEntryPoint
import org.ferhatozcelik.R
import org.ferhatozcelik.data.model.SectionType
import org.ferhatozcelik.databinding.ActivityVideoPlayerBinding
import org.ferhatozcelik.util.GeneralUtil

/**
 *
 * @author ferhatozcelik
 * @since 2023-04-01
 */

@AndroidEntryPoint
class VideoPlayer : AppCompatActivity() {

    private val viewModel: VideoPlayerViewModel by viewModels()
    private lateinit var binding: ActivityVideoPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (GeneralUtil().isGooglePlayServicesAvailable(this)){
            FirebaseApp.initializeApp(applicationContext)
        }

        binding.backButton.setOnClickListener {
           finish()
        }

        val sectionType = intent.extras?.getString("sectionType")
        val videoId = intent.extras?.getString("videoId")

        if (sectionType != null && videoId != null) {
            if (sectionType == SectionType.LIVE.toString()) {
                viewModel.getVideoById(videoId).observe(this) {
                    if(it != null){
                        if (it.videoId != null){
                            initVideo(it.videoId!!)
                        }
                    }
                }

            } else if (sectionType == SectionType.MARKED.toString()) {
                viewModel.getMarkedVideoById(videoId).observe(this) {
                    if(it != null){
                        if (it.videoId != null){
                            initVideo(it.videoId!!)
                        }
                    }
                }
            }
        }
    }

    private fun initVideo(videoId: String) {

        binding.apply {

            try {
                val iFramePlayerOptions = IFramePlayerOptions.Builder().controls(1).ccLoadPolicy(0).rel(0).fullscreen(0).build()

                youtubePlayerView.addFullscreenListener(object : FullscreenListener {
                    override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                        youtubePlayerView.addView(fullscreenView)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    }

                    override fun onExitFullscreen() {
                        finish()
                    }
                })

                youtubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(videoId, 0f)
                        youTubePlayer.toggleFullscreen()
                    }
                }, iFramePlayerOptions)
                lifecycle.addObserver(youtubePlayerView)
            } catch (e: Exception) {
                Toast.makeText(applicationContext, getString(R.string.alert_error), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerView.release()
    }

}
