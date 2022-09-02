package com.eventsapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.eventsapp.databinding.ChooseMapDialogBinding
import com.eventsapp.databinding.DescriptionFragmentBinding

class ViewInMapsDialog: DialogFragment() {

    lateinit var binding: ChooseMapDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChooseMapDialogBinding.bind(inflater.inflate(R.layout.choose_map_dialog, container))
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancel.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
        binding.inGoogle.setOnClickListener {

            val gmmIntentUri =
                Uri.parse("google.navigation:q=Красная+Площадь")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)

        }
        binding.inYandex.setOnClickListener {

            val uri =
                Uri.parse("yandexmaps://maps.yandex.ru/?rtext=55.745719,37.604337~55.76009,37.648801&rtt=mt")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)

        }
    }



}