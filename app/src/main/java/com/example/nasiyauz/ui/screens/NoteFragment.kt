package com.example.nasiyauz.ui.screens

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.nasiyauz.R
import com.example.nasiyauz.databinding.FragmentNoteBinding
import com.example.nasiyauz.data.models.UserModel
import com.example.nasiyauz.utils.BaseUtils.getCurrentDataTime
import com.example.nasiyauz.utils.BaseUtils.showSnackToast
import com.example.nasiyauz.viewmodel.UserViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_TEXT
import com.google.android.material.datepicker.SingleDateSelector
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class NoteFragment : Fragment(R.layout.fragment_note) {
    private var _binding: FragmentNoteBinding? = null
    private lateinit var binding: FragmentNoteBinding

    private val viewModel by viewModel<UserViewModel>()
    private val navigation by lazy { findNavController() }
    private val formatDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteBinding.bind(view)
        _binding = binding

        binding.dateTime.text = getCurrentDataTime()

        initClicks()


    }

    private fun initClicks() {
        binding.toolbar.setNavigationOnClickListener {
            backToMainFragment()
        }

        binding.dataPicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setInputMode(INPUT_MODE_TEXT)
                .setNegativeButtonText("Cancel")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())

            datePicker.build().show(childFragmentManager, "show!!!")
//            val datePicker =
//                DatePickerDialog(
//                    requireContext(),
//                    android.R.style.Theme_Holo_Light_Dialog_MinWidth, { _, i, i2, i3 ->
//                        val selectDate = Calendar.getInstance().apply {
//                            set(Calendar.YEAR, i)
//                            set(Calendar.MONTH, i2)
//                            set(Calendar.DAY_OF_MONTH, i3)
//                        }
//
//                        val date = formatDate.format(selectDate.time)
//                        showSnackToast("muddat: $date")
//
//                    },
//                    getDate.get(Calendar.YEAR),
//                    getDate.get(Calendar.MONTH),
//                    getDate.get(Calendar.DAY_OF_MONTH)
//                )
//            datePicker.show()
        }

        binding.colorPicker.setOnClickListener {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .setHour(12)
                .setMinute(10)
                .setTitleText("Vaqtni tanlang :)")
                .build()

            picker.show(childFragmentManager, "tagggg")
        }

        binding.fabDone.setOnClickListener {
            val userName = binding.inputUsersName.text.toString()
            val userNumber = binding.inputUsersNumber.text.toString()
            val date = binding.dateTime.text.toString()
            val description = binding.descriptionText.text.toString()

            if (userName.isNotEmpty() && userNumber.isNotEmpty()) {
                viewModel.insertUser(UserModel(userName, userNumber, date, " ", description))
                backToMainFragment()
            } else {
                showSnackToast(getString(R.string.name_number_error_txt))
            }
        }

    }

    private fun backToMainFragment() {
        val action = NoteFragmentDirections.actionNoteFragmentToBaseFragment()
        navigation.navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}