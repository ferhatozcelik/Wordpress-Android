package org.ferhatozcelik.ui.activitys.videoplayer

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import org.ferhatozcelik.repository.VideoRepository
import javax.inject.Inject

/**
 *
 * @author ferhatozcelik
 * @since 2023-04-01
 */

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(private val videoRepository: VideoRepository, @ApplicationContext private val context: Context) : ViewModel() {

    fun getVideoById(videoId: String?) = videoRepository.getVideoById(videoId)

    fun getMarkedVideoById(videoId: String?) = videoRepository.getMarkedVideoById(videoId)

}