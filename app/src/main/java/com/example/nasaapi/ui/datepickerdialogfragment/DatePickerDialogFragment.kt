package com.example.nasaapi.ui.datepickerdialogfragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs

class DatePickerDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val args by navArgs<DatePickerDialogFragmentArgs>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = args.date[0]
        val month = args.date[1]
        val day = args.date[2]
        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        setFragmentResult(
            REQUEST_KEY,
            bundleOf(KEY_RESPONSE to intArrayOf(year, month, dayOfMonth))
        )
    }

    companion object {

        @JvmStatic
        val REQUEST_KEY = "DatePickerDialogFragment:defaultRequestKey"

        @JvmStatic
        val KEY_RESPONSE = "RESPONSE"
    }
}