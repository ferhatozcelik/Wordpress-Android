package org.ferhatozcelik.ui.fragments.videos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.ferhatozcelik.R
import org.ferhatozcelik.data.entity.Video
import org.ferhatozcelik.data.model.SectionType
import org.ferhatozcelik.databinding.FragmentVideosBinding
import org.ferhatozcelik.interfaces.ItemClickListener
import org.ferhatozcelik.ui.activitys.videoplayer.VideoPlayer
import org.ferhatozcelik.ui.adapters.VideosAdapter
import org.ferhatozcelik.util.EndlessScrollRecyclListener
import org.ferhatozcelik.util.ProgressDialog

/**
 *
 * @author ferhatozcelik
 * @since 2023-04-02
 */

@AndroidEntryPoint
class VideosFragment : Fragment(R.layout.fragment_videos) {

    private val viewModel: VideosViewModel by viewModels()
    private lateinit var binding: FragmentVideosBinding
    private var progressDialog: ProgressDialog? = null

    override fun onResume() {
        super.onResume()
        onScrollRecyclListener.reset()
        viewModel.getVideoData(null, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideosBinding.inflate(inflater, container, false)
        initVideos()
        initView()
        return binding.root
    }

    private fun initView() {
        progressDialog = activity?.let { ProgressDialog(it) }
        progressDialog?.createProgressDialog()
        viewModel.isProgressBarStatus.observe(viewLifecycleOwner) {
            if (it){
                progressDialog?.showDialog()
            }else{
                progressDialog?.cancelDelayDialog()
            }
        }

    }

    private fun initVideos() {
        val adapter = VideosAdapter(requireContext(), videoItemClickListener, vidoeBookmarksItemClickListener)
        binding.articleList.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.articleList.layoutManager = linearLayoutManager
        viewModel.getVideo().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.articleList.addOnScrollListener(onScrollRecyclListener)
    }

    private val videoItemClickListener = object : ItemClickListener {
        override fun onItemClick(objects: Any) {
            val item = objects as Video
            if (item.videoId != null) {
                val intent = Intent(context, VideoPlayer::class.java)
                intent.putExtra("sectionType", SectionType.LIVE.toString())
                intent.putExtra("videoId",  item.videoId!!)
                context?.startActivity(intent)
            }
        }
    }

    private val vidoeBookmarksItemClickListener = object : ItemClickListener {
        override fun onItemClick(objects: Any) {
            val item = objects as Video
            viewModel.saveBookmarks(item)


        }
    }

    private val onScrollRecyclListener = object : EndlessScrollRecyclListener() {
        override fun onLoadMore(page: Int, totalItemsCount: Int) {
            viewModel.getVideoData(viewModel.nextPageToken, false)
        }
    }

}