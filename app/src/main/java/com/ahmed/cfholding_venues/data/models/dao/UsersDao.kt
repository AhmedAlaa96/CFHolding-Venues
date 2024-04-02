package com.ahmed.cfholding_venues.data.models.dao

import androidx.room.*
import com.ahmed.cfholding_venues.data.models.dto.User

@Dao
interface UsersDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>?

    @Query("SELECT * FROM users Where email=:email AND password =:password")
    fun getUserByEmailAndPassword(email: String?, password: String?): User?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

}
