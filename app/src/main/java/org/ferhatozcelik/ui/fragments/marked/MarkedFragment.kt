package org.ferhatozcelik.ui.fragments.marked

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.ferhatozcelik.R
import org.ferhatozcelik.data.entity.MarkedArticle
import org.ferhatozcelik.data.entity.MarkedVideo
import org.ferhatozcelik.data.model.SectionType
import org.ferhatozcelik.databinding.FragmentMarkedBinding
import org.ferhatozcelik.interfaces.ItemClickListener
import org.ferhatozcelik.ui.activitys.videoplayer.VideoPlayer
import org.ferhatozcelik.ui.adapters.MarkedArticleAdapter
import org.ferhatozcelik.ui.adapters.MarkedVideosAdapter
import org.ferhatozcelik.util.ProgressDialog

/**
 *
 * @author ferhatozcelik
 * @since 2023-04-01
 */

@AndroidEntryPoint
class MarkedFragment : Fragment(R.layout.fragment_marked) {

    private val viewModel: MarkedViewModel by viewModels()
    private lateinit var binding: FragmentMarkedBinding
    private var progressDialog: ProgressDialog? = null
    private var isMarkedStatus = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMarkedBinding.inflate(inflater, container, false)
        initView()
        chengeView()
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

        binding.markedArticle.setOnClickListener {
            isMarkedStatus = false
            chengeView()
        }

        binding.markedVideo.setOnClickListener {
            isMarkedStatus = true
            chengeView()
        }

    }

    private fun chengeView() {
        if (isMarkedStatus) {
            isMarkedStatus = true
            binding.markedTitle.text = context?.getString(R.string.videos)
            binding.apply {
                markedVideo.background = ContextCompat.getDrawable(requireContext(), R.drawable.view_click_background)
                markedVideo.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                markedArticle.background = ContextCompat.getDrawable(requireContext(), R.drawable.view_background)
                markedArticle.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_primary))
            }
            initMarkedVideo()
        } else {
            isMarkedStatus = false
            binding.markedTitle.text = context?.getString(R.string.article)
            binding.apply {
                markedVideo.background = ContextCompat.getDrawable(requireContext(), R.drawable.view_background)
                markedVideo.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_primary))
                markedArticle.background = ContextCompat.getDrawable(requireContext(), R.drawable.view_click_background)
                markedArticle.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            initMarkedArticle()
        }
    }

    private fun initMarkedArticle() {
        val adapter = MarkedArticleAdapter(requireContext(), articleItemClickListener, articleBookmarksItemClickListener)
        binding.markedList.adapter = adapter
        val linearLayoutManager= LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.markedList.layoutManager =linearLayoutManager
        viewModel.getMarkedArticle().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                binding.noDataView.visibility = View.GONE
                binding.markedList.visibility = View.VISIBLE
                adapter.submitList(it)
            }else{
                binding.markedList.visibility = View.GONE
                binding.noDataView.visibility = View.VISIBLE
            }
        }
    }

    private fun initMarkedVideo() {
        val adapter = MarkedVideosAdapter(requireContext(), videoItemClickListener, videoBookmarksItemClickListener)
        binding.markedList.adapter = adapter
        val linearLayoutManager= LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.markedList.layoutManager =linearLayoutManager
        viewModel.getMarkedVideos().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                binding.noDataView.visibility = View.GONE
                binding.markedList.visibility = View.VISIBLE
                adapter.submitList(it)
            }else{
                binding.markedList.visibility = View.GONE
                binding.noDataView.visibility = View.VISIBLE
            }
        }
    }

    private val articleItemClickListener = object : ItemClickListener {
        override fun onItemClick(objects: Any) {
            val item = objects as MarkedArticle
            if (item.articleId != null) {
                val bundle = Bundle()
                bundle.putString("sectionType", SectionType.MARKED.toString())
                bundle.putInt("articleId", item.articleId!!)
                activity?.findNavController(R.id.navHostFragment)?.navigate(R.id.action_markedFragment_to_articleDetailFragment, bundle)
            }
        }
    }

    private val articleBookmarksItemClickListener = object : ItemClickListener {
        override fun onItemClick(objects: Any) {
            val item = objects as MarkedArticle
            viewModel.removeArticleBookmarks(item)
        }
    }

    private val videoItemClickListener = object : ItemClickListener {
        override fun onItemClick(objects: Any) {
            val item = objects as MarkedVideo
            if (item.videoId != null) {
                val intent = Intent(context, VideoPlayer::class.java)
                intent.putExtra("sectionType", SectionType.MARKED.toString())
                intent.putExtra("videoId", item.videoId!!)
                context?.startActivity(intent)
            }
        }
    }

    private val videoBookmarksItemClickListener = object : ItemClickListener {
        override fun onItemClick(objects: Any) {
            val item = objects as MarkedVideo
            viewModel.removeVideosBookmarks(item)
        }
    }

}