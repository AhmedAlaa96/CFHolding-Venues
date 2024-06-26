package com.ahmed.cfholding_venues.data.sharedprefrences

import android.content.Context
import android.content.SharedPreferences
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.utils.Constants
import com.ahmed.cfholding_venues.utils.Constants.SharedPreference.SHARED_PREF_NAME
import com.google.gson.Gson

class PreferencesDataSource(context: Context, private val mGson: Gson) : IPreferencesDataSource {

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = mPrefs.edit()

    private fun removeString(key: String) {
        editor.remove(key)
        editor.commit()
    }

    private fun setString(key: String, value: String) {
        mPrefs.edit().putString(key, value).apply()
    }

    private fun setInt(key: String, value: Int) {
        mPrefs.edit().putInt(key, value).apply()
    }

    private fun getString(key: String, defaultValue: String): String? {
        return mPrefs.getString(key, defaultValue)
    }

    private fun getInt(key: String, defaultValue: Int): Int {
        return mPrefs.getInt(key, defaultValue)
    }

    override fun saveUserData(user: User) {
        setString(Constants.SharedPreference.USER_KEY, mGson.toJson(user))
    }

    override fun getUserData(): User? {
        return mGson.fromJson(getString(Constants.SharedPreference.USER_KEY, ""), User::class.java)
    }

    override fun clearUserData() {
        setString(Constants.SharedPreference.USER_KEY, "")
    }


}