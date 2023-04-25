package org.ferhatozcelik.ui.fragments.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.ferhatozcelik.R
import org.ferhatozcelik.data.entity.Article
import org.ferhatozcelik.data.entity.Categories
import org.ferhatozcelik.data.model.SectionType
import org.ferhatozcelik.databinding.FragmentArticleBinding
import org.ferhatozcelik.interfaces.ItemClickListener
import org.ferhatozcelik.ui.adapters.ArticleAdapter
import org.ferhatozcelik.ui.adapters.CategoriesAdapter
import org.ferhatozcelik.util.EndlessScrollRecyclListener
import org.ferhatozcelik.util.ProgressDialog

/**
 *
 * @author ferhatozcelik
 * @since 2023-04-01
 */

@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.fragment_article) {

    private val viewModel: ArticleViewModel by viewModels()
    private lateinit var binding: FragmentArticleBinding
    private var progressDialog: ProgressDialog? = null
    private var currentPage = 1

    override fun onResume() {
        super.onResume()
        onScrollRecyclListener.reset()
        viewModel.getArticleData(currentPage, true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        initCategories()
        initArticle()
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

        binding.searchButton.setOnClickListener {
            onScrollRecyclListener.reset()
            viewModel.searchQuery(currentPage, binding.searchEdittext.text.toString())
            binding.articleList.scrollToPosition(0)
        }

        binding.searchEdittext.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onScrollRecyclListener.reset()
                viewModel.searchQuery(currentPage, binding.searchEdittext.text.toString())
                binding.articleList.scrollToPosition(0)
                return@OnEditorActionListener true
            }
            false
        })

    }

    private fun initArticle() {
        val adapter = ArticleAdapter(requireContext(), articleItemClickListener, articleBookmarksItemClickListener)
        binding.articleList.adapter = adapter
         val linearLayoutManager= LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.articleList.layoutManager =linearLayoutManager
        viewModel.getArticle().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.articleList.addOnScrollListener(onScrollRecyclListener)
    }

    private fun initCategories() {
        val adapter = CategoriesAdapter(requireContext(), categoryItemClickListener)
        binding.categoryList.adapter = adapter
        binding.categoryList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        viewModel.getCategories().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private val articleItemClickListener = object : ItemClickListener {
        override fun onItemClick(objects: Any) {
            val item = objects as Article
            if (item.articleId != null) {
                val bundle = Bundle()
                bundle.putString("sectionType", SectionType.LIVE.toString())
                bundle.putInt("articleId", item.articleId!!)
                activity?.findNavController(R.id.navHostFragment)?.navigate(R.id.action_articleFragment_to_articleDetailFragment, bundle)
            }
        }
    }

    private val articleBookmarksItemClickListener = object : ItemClickListener {
        override fun onItemClick(objects: Any) {
            val item = objects as Article
            viewModel.saveBookmarks(item)
        }
    }

    private val categoryItemClickListener = object : ItemClickListener {
        override fun onItemClick(objects: Any) {
            val item = objects as Categories
            onScrollRecyclListener.reset()
            viewModel.categoryQuery(currentPage, item.categoryId)
            binding.articleList.scrollToPosition(0)
        }
    }

    private val onScrollRecyclListener = object : EndlessScrollRecyclListener() {
        override fun onLoadMore(page: Int, totalItemsCount: Int) {
            viewModel.getArticleData(page, false)
        }
    }

}