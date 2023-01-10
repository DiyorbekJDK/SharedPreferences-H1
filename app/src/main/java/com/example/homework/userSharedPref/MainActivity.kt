package com.example.dialogs

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.simple_dialog).setOnClickListener { simpleDialog() }
        findViewById<View>(R.id.single_choice).setOnClickListener { singleChoice() }
        findViewById<View>(R.id.multi_selection).setOnClickListener { view -> multiSelection(view) }
        findViewById<View>(R.id.confrim).setOnClickListener { changeBackground() }
        findViewById<View>(R.id.full_screen).setOnClickListener { fullScreen() }
        findViewById<View>(R.id.progress_dialog).setOnClickListener { progressDialog() }
        findViewById<View>(R.id.custom_dilaog).setOnClickListener { customDialog() }
        findViewById<View>(R.id.simple).setOnClickListener { horizontalDialog() }
    }


    private fun horizontalDialog() {
        val progressDialog = ProgressDialog(this)
        progressDialog.max =100
        progressDialog.setTitle("Horizontal progress dialog")
        progressDialog.setMessage("Loading...")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog.show()

        val handler: Handler = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                progressDialog.incrementProgressBy(5)
            }
        }
        Thread{
            try {
                while (progressDialog.progress <= progressDialog.max){
                    Thread.sleep(100)
                    handler.sendMessage(handler.obtainMessage())
                    if (progressDialog.progress == progressDialog.max){
                        progressDialog.dismiss()
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()

            }
        }.start()
    }

    private fun customDialog() {
        val layoutInflater = LayoutInflater.from(this)
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setView(dialogView)
        val btnYes: MaterialButton = dialogView.findViewById(R.id.btnYes)
        val btnNo: MaterialButton = dialogView.findViewById(R.id.btnNo)
        btnYes.setOnClickListener {
            finish()
        }
        btnNo.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun progressDialog() {
        ProgressDialog(this).apply {
            setTitle("Progress")
            setMessage("This is message")
            show()
        }.create()
    }

    private fun fullScreen() {
        AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
            .apply {
                setTitle("Fullscreen Dialog")
                setMessage("This is a dialog")
                setPositiveButton("Ok") { b, _ ->
                    b.dismiss()
                }
                setNegativeButton("Cancel", null)
                setIcon(R.drawable.ic_launcher_background)
            }.show()
    }

    private fun changeBackground() {
        val body: LinearLayout = findViewById(R.id.mybody)
        AlertDialog.Builder(this).apply {
            setTitle("Background colors")
            setMessage("Choose background color:")
            setNeutralButton("Red") { dialog, _ ->
                body.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.red))
                dialog.dismiss()
            }
            setPositiveButton("Blue") { dialog2, _ ->
                body.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.blue))
                dialog2.dismiss()
            }
            setNegativeButton("Green") { dialog3, _ ->
                body.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.green))
                dialog3.dismiss()
            }

        }.create().show()
    }

    private fun multiSelection(view: View) {
        val array = arrayOf("Real", "Barsa", "Tottenham", "Arsenal", "MCI")
        val choices = booleanArrayOf(false, false, false, false, false)
        AlertDialog.Builder(this).apply {
            setTitle("Football Clubs")
            setMultiChoiceItems(array, choices) { listener, int, _ ->
                listener.dismiss()
                Snackbar.make(view, array[int], Snackbar.LENGTH_LONG).setAction(
                    "Undo"
                ) {}.show()
            }
        }.create().show()
    }

    private fun singleChoice() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Languages")
        alertDialog.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.setNeutralButton("Cancel", null)
        val items = arrayOf("Java", "Kotlin", "Python", "Swift")
        alertDialog.setSingleChoiceItems(items, 4) { _, which ->
            Toast.makeText(this, items[which], Toast.LENGTH_SHORT).show()
        }
        alertDialog.create().show()
    }

    private fun simpleDialog() {

        AlertDialog.Builder(this).apply {
            setTitle("Exit")
            setMessage("Do you want to exit?")
            setPositiveButton("Ok") { _, _ ->
                finish()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()
    }
}