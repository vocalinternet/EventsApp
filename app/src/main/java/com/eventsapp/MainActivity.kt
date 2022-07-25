package com.eventsapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.eventsapp.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

@SuppressLint("StaticFieldLeak")
private var mainBinding: ActivityMainBinding? = null
private val binding get()= mainBinding!!

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        with(binding) {
//            button.setOnClickListener {
//                BottomFragment().show(supportFragmentManager, "tag")
//            }
//        }

        var but1 = findViewById<Button>(R.id.button)
        but1.setOnClickListener {
            BottomFragment().show(supportFragmentManager, "tag")
        }
    }
    }

