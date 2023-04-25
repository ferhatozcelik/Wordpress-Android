package org.ferhatozcelik.ui.fragments.articledetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint
import org.ferhatozcelik.R
import org.ferhatozcelik.data.model.SectionType
import org.ferhatozcelik.databinding.FragmentDetailArticleBinding
import org.ferhatozcelik.util.HtmlHelper.htmlInject
import org.ferhatozcelik.util.StringExtension.Companion.capitalize

/**
 *
 * @author ferhatozcelik
 * @since 2023-04-01
 */

@AndroidEntryPoint
class ArticleDetailFragment : Fragment(R.layout.fragment_detail_article) {

    private val viewModel: ArticleDetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailArticleBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailArticleBinding.inflate(inflater, container, false)

        val articleId = arguments?.getInt("articleId")
        val sectionType = arguments?.getString("sectionType")

        if (sectionType != null && articleId != null) {
            if (sectionType == SectionType.LIVE.toString()) {
                viewModel.getArticleById(articleId).observe(viewLifecycleOwner) {
                    if (it != null){
                        if (it.articleId != null){
                            initData(it.articleTitle.toString(), it.authorName.toString(), it.articleImageUrl, it.articleContent.toString())
                        }
                    }
                }

            } else if (sectionType == SectionType.MARKED.toString()) {
                viewModel.getMarkedArticleById(articleId).observe(viewLifecycleOwner) {
                    if (it != null){
                        if (it.articleId != null){
                            initData(it.articleTitle.toString(), it.authorName.toString(), it.articleImageUrl, it.articleContent.toString())
                        }
                    }
                }
            }
        }

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initData(itemArticleTitle: String, itemAuthorName:String, itemArticleImageUrl:String, itemArticleContent:String,) {

        binding.apply {

            backButton.setOnClickListener {
                activity?.findNavController(R.id.navHostFragment)?.popBackStack()
            }

            articleTitle.text = itemArticleTitle.capitalize()
            authorName.text = itemAuthorName.capitalize()

            Glide.with(requireContext()).load(itemArticleImageUrl).centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(imageViewContent)

            val unencodedHtml = itemArticleContent.htmlInject(requireContext())
            val encodedHtml = Base64.encodeToString(unencodedHtml.toByteArray(), Base64.NO_PADDING)

            articleWebview.settings.javaScriptEnabled = true
            articleWebview.loadData(encodedHtml, "text/html", "base64")
            articleWebview.visibility = View.GONE
            notInitImage.visibility = View.VISIBLE
            notInitText.visibility = View.VISIBLE
            articleWebview.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    articleWebview.visibility = View.VISIBLE
                    notInitImage.visibility = View.GONE
                    notInitText.visibility = View.GONE
                }
            }

        }

    }

}