package org.ferhatozcelik.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.ferhatozcelik.R
import org.ferhatozcelik.data.entity.Article
import org.ferhatozcelik.databinding.ItemArticleBinding
import org.ferhatozcelik.interfaces.ItemClickListener
import org.ferhatozcelik.util.StringExtension.Companion.toSimpleDate

/**
 *
 * @author ferhatozcelik
 * @since 2023-04-01
 */

class ArticleAdapter(var context: Context, var itemClickListener: ItemClickListener, var itemBookmarkClickListener: ItemClickListener) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var articleList: List<Article> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(articleList: List<Article>) {
        this.articleList = articleList
        notifyDataSetChanged()
    }

    lateinit var itemArticleBinding: ItemArticleBinding

    class ArticleViewHolder(binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ItemArticleBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val articleItem = articleList[position]

        itemArticleBinding = holder.binding

        itemArticleBinding.apply {
            titleName.text = articleItem.articleTitle ?: ""
            authorName.text = articleItem.authorName ?: ""
            articleDate.text = articleItem.articleCreateAtDate?.toSimpleDate() ?: ""
        }

        Glide.with(context).load(articleItem.articleImageUrl).centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(itemArticleBinding.articleImageView)

        val adapter = CardCategoriesAdapter(articleItem.articleCategory?.toList() ?: emptyList())
        itemArticleBinding.categoryList.adapter = adapter
        itemArticleBinding.categoryList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(articleItem)
        }

        itemArticleBinding.bookmarksCardview.setOnClickListener {
            itemBookmarkClickListener.onItemClick(articleItem)
        }

        if (articleItem.isBookmark == true) {
            itemArticleBinding.apply {
                bookmarksCardview.background = ContextCompat.getDrawable(context, R.drawable.view_click_background)
                bookmarksImageView.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        } else {
            itemArticleBinding.apply {
                bookmarksCardview.background = ContextCompat.getDrawable(context, R.drawable.view_background)
                bookmarksImageView.setColorFilter(ContextCompat.getColor(context, R.color.color_primary), android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }

    }

    override fun getItemCount(): Int {
        return articleList.size
    }

}

