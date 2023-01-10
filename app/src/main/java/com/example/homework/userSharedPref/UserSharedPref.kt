package com.example.homework.userSharedPref

import android.content.Context
import android.content.SharedPreferences
import com.example.homework.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserSharedPref(context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("UserPref", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveUser(user: User) {
        val editor = sharedPref.edit()
        editor.putString("user", gson.toJson(user))
        editor.apply()
    }

    fun getUser(): User? {
        val json = sharedPref.getString("user", null)
        val type = object : TypeToken<User>() {}.type
        return gson.fromJson(json, type)
    }
}