package com.ahmed.cfholding_venues.data.models.dao

import androidx.room.*
import com.ahmed.cfholding_venues.data.models.dto.User

@Dao
interface UsersDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>?

    @Query("SELECT * FROM users Where id=:id")
    fun getRepoById(id: Int?): User?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: ArrayList<User>)

}
