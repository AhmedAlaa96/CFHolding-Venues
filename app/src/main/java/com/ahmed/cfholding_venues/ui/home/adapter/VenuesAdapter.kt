package com.ahmed.cfholding_venues.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ahmed.cfholding_venues.data.models.dto.Venue
import com.ahmed.cfholding_venues.databinding.ItemVenueBinding
import com.ahmed.cfholding_venues.ui.base.BaseAdapter
import com.ahmed.cfholding_venues.ui.base.ListItemClickListener

class VenuesAdapter(
    private val mContext: Context,
    private val mMovieItemClickListener: ListItemClickListener<Venue>
) : BaseAdapter<Venue, VenueItemHolder>(itemClickListener = mMovieItemClickListener) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): VenueItemHolder {
        binding = ItemVenueBinding.inflate(
            LayoutInflater.from(mContext),
            viewGroup,
            false
        )
        return VenueItemHolder(
            binding as ItemVenueBinding,
            mMovieItemClickListener,
        )
    }

    override fun clearViewBinding() {
        binding = null
    }
}