package org.ferhatozcelik.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.ferhatozcelik.data.entity.Categories
import org.ferhatozcelik.databinding.ItemCategoriesCardBinding

/**
 *
 * @author ferhatozcelik
 * @since 2023-04-01
 */

class CardCategoriesAdapter(var categoriesList: List<Categories>) : RecyclerView.Adapter<CardCategoriesAdapter.CategoriesViewHolder>() {

    lateinit var itemCategoriesCardBinding: ItemCategoriesCardBinding
    class CategoriesViewHolder(binding: ItemCategoriesCardBinding): RecyclerView.ViewHolder(binding.root) {
        var binding: ItemCategoriesCardBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding = ItemCategoriesCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val categoriesItem = categoriesList[position]
        itemCategoriesCardBinding = holder.binding
        holder.binding.apply {
            if (categoriesItem.categoryName != null){
                categoryName.text = categoriesItem.categoryName
            }
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

}

