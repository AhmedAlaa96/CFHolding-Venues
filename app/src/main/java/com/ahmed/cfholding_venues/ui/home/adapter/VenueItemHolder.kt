package com.ahmed.cfholding_venues.ui.home.adapter

import com.ahmed.cfholding_venues.R
import com.ahmed.cfholding_venues.data.models.dto.Category
import com.ahmed.cfholding_venues.data.models.dto.Venue
import com.ahmed.cfholding_venues.databinding.ItemVenueBinding
import com.ahmed.cfholding_venues.ui.base.BaseViewHolder
import com.ahmed.cfholding_venues.ui.base.ListItemClickListener
import com.ahmed.cfholding_venues.utils.alternate
import com.ahmed.cfholding_venues.utils.setNetworkImage

class VenueItemHolder(
    private val binding: ItemVenueBinding,
    private val mMatchItemClickListener: ListItemClickListener<Venue>? = null
) : BaseViewHolder<Venue>(binding, mMatchItemClickListener) {
    override fun bind(item: Venue) {
        bindMovieTitle(item.name)
        bindVenueLocation(item.location?.formattedAddress?.joinToString {
            it
        })
        item.categories?.firstOrNull()?.let {
            bindCategoryText(it.name)
            bindMovieIcon(it)
        } ?: with(item.categories) {
            bindCategoryText("-")
            bindMovieIcon(null)
        }

        bindItemClick(item)
    }

    private fun bindItemClick(item: Venue) {
        itemView.setOnClickListener { mMatchItemClickListener?.onItemClick(item, adapterPosition) }
    }

    private fun bindMovieTitle(title: String?) {
        binding.txtName.text = title.alternate()
    }

    private fun bindVenueLocation(location: String?) {
        binding.txtLocation.text = location.alternate()
    }

    private fun bindCategoryText(category: String?) {
        binding.txtCategory.text =
            binding.txtCategory.context.getString(R.string.category, category.alternate())
    }

    private fun bindMovieIcon(category: Category?) {
        if (category == null) {
            binding.imgCategory.background =
                (binding.imgCategory.context.getDrawable(R.drawable.ic_error))
        }else {
            val imageUrl = "${category.icon?.prefix}${category.mapIcon}${category.icon?.suffix}"
            binding.imgCategory.setNetworkImage(imageUrl)
        }
    }
}