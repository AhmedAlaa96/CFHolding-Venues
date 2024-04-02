package com.ahmed.cfholding_venues.data.models.dto

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
@Keep
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var fName: String? = null,
    var lName: String? = null,
    var age: Int? = null,
    var email: String? = null,
    var password: String? = null,
) : Parcelable
