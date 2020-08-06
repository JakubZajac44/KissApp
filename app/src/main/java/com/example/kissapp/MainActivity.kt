package com.example.kissapp

import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.kissapp.view.CharacterListView

class MainActivity : AppCompatActivity() {

    companion object {
        fun showToast(context:Context,message: CharSequence) =
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        if (findViewById<FrameLayout>(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return
            }

            supportFragmentManager.commit {
                add<CharacterListView>(R.id.fragment_container, null, intent.extras)
            }
        }
    }



}
