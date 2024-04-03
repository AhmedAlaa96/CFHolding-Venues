package com.ahmed.cfholding_venues.ui.base

import androidx.recyclerview.widget.LinearLayoutManager

interface ListItemClickListener<in Item> {
    fun onItemClick(item: Item, position: Int)

}
