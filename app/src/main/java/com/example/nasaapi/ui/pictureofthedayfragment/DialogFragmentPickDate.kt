package com.example.nasaapi.ui.pictureofthedayfragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class DialogFragmentPickDate : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val args by navArgs<DialogFragmentPickDateArgs>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = args.date[0]
        val month = args.date[1]
        val day = args.date[2]
        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

    }
}