package com.example.fitfoood.view.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.commit
import com.example.fitfoood.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EditAccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tbTitle = view.findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = getString(R.string.account_edit)

        view.findViewById<View>(R.id.toolbar).setOnClickListener {
            activity?.onBackPressed()
        }

        val dateEditText = view.findViewById<TextInputEditText>(R.id.dateEditText)
        dateEditText.setOnClickListener { showDatePicker() }

        val genderRadioGroup = view.findViewById<RadioGroup>(R.id.genderRadioGroup)
        genderRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioMan -> {
                    // Handle 'Man' selection
                }
                R.id.radioWoman -> {
                    // Handle 'Woman' selection
                }
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(calendar.timeInMillis)
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = Date(selection)
            val dateEditText = view?.findViewById<TextInputEditText>(R.id.dateEditText)
            dateEditText?.setText(dateFormat.format(date))
        }

        datePicker.show(parentFragmentManager, "DATE_PICKER")
    }

}