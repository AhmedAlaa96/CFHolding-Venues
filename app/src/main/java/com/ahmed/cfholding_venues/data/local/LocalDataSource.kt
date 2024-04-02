package com.ahmed.cfholding_venues.data.local

import com.ahmed.cfholding_venues.data.room.AppDatabase
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val appDatabase: AppDatabase) : ILocalDataSource {


}