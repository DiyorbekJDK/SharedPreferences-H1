package com.example.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import com.example.homework.model.User
import com.example.homework.userSharedPref.UserSharedPref
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var userInfo: TextView
    private val sharedPref by lazy { UserSharedPref(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userInfo = findViewById(R.id.textView)
        val user2 = sharedPref.getUser()
        user2?.let {
            userInfo.text = "${it.name}\n${it.age}\n${it.IsMail}"
        }


        val btn: MaterialButton = findViewById(R.id.button)
        btn.setOnClickListener {
            val editname: EditText = findViewById(R.id.editTextTextPersonName)
            val editage: EditText = findViewById(R.id.editTextNumber)
            val Editswitcher: SwitchCompat = findViewById(R.id.switch1)
            var isMail: Boolean = true
            if (Editswitcher.isChecked) {
                isMail = false
            }
            val name = editname.text.toString().trim()
            val age = editage.text.toString().trim()
            if (name.isNotEmpty() && age.isNotEmpty()) {
                var genderText = "Gender"
                Toast.makeText(this, "Information is saved", Toast.LENGTH_SHORT).show()
                if (!isMail) {
                    userInfo.text = "$name\n$age\nFemale"
                    genderText = "Female"
                } else {
                    userInfo.text = "$name\n$age\nMale"
                    genderText = "Male"
                }
                sharedPref.saveUser(User(name, age.toInt(), genderText))


            } else {
                Toast.makeText(this, "Enter information!", Toast.LENGTH_SHORT).show()

            }
        }

    }
}