package com.eventsapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }



}