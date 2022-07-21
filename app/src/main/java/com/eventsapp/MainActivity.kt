package com.eventsapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eventsapp.databinding.ActivityMainBinding

@SuppressLint("StaticFieldLeak")
private var mainBinding: ActivityMainBinding? = null
private val binding get()= mainBinding!!

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Hello world!")
        println("111222333")
    }
    }

