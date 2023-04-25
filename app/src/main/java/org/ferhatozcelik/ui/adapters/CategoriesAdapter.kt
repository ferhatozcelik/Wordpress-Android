package org.ferhatozcelik.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.ferhatozcelik.R
import org.ferhatozcelik.data.entity.Categories
import org.ferhatozcelik.databinding.ItemCategoriesBinding
import org.ferhatozcelik.interfaces.ItemClickListener

/**
 *
 * @author ferhatozcelik
 * @since 2023-04-01
 */

class CategoriesAdapter(var context: Context, var itemClickListener: ItemClickListener) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private var categoriesList: List<Categories> = emptyList()
    lateinit var itemCategoriesBinding: ItemCategoriesBinding

    class CategoriesViewHolder(binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemCategoriesBinding

        init {
            this.binding = binding
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(categoriesList: List<Categories>) {
        this.categoriesList = categoriesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding =
            ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val categoriesItem = categoriesList[position]

        itemCategoriesBinding = holder.binding

        itemCategoriesBinding.apply {
            categoryName.text = categoriesItem.categoryName

            if (categoriesItem.categoryName == "All Article") {
                categoryCount.visibility = View.GONE
            } else {
                categoryCount.visibility = View.VISIBLE
                categoryCount.text = categoriesItem.categoryCount.toString()
            }
        }

        if (categoriesItem.isActive) {
            itemCategoriesBinding.apply {
                cardLayout.background =
                    ContextCompat.getDrawable(context, R.drawable.view_click_background)
                categoryCount.background =
                    ContextCompat.getDrawable(context, R.drawable.count_click_background)
                categoryName.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        } else {
            itemCategoriesBinding.apply {
                cardLayout.background =
                    ContextCompat.getDrawable(context, R.drawable.view_background)
                categoryCount.background =
                    ContextCompat.getDrawable(context, R.drawable.count_background)
                categoryName.setTextColor(ContextCompat.getColor(context, R.color.color_primary))
            }
        }

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(categoriesItem)
        }

    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

}

