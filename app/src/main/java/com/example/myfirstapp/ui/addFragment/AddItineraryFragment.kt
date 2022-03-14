package com.example.myfirstapp.ui.addFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentAddItineraryBinding
import com.example.myfirstapp.ui.snack
import com.example.myfirstapp.vm.setTextDate
import com.example.myfirstapp.vm.setTextTime
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar.*

class AddItineraryFragment : Fragment(R.layout.fragment_add_itinerary) {

    private val binding: FragmentAddItineraryBinding by viewBinding()
    private var dateTurnout: Long = 0
    private val calendarNow by lazy { getInstance() }
    private val calendarTurnout by lazy { getInstance() }
    private val calendarEnding by lazy { getInstance() }

    private var errorInputCalendar = false
    private var dateTurnoutFixed = false

    private var restPointOfTurnover = false
    private val numberItinerary: String? by lazy {
        if (binding.etNumberItinerary.text.isNullOrEmpty()) null
        else binding.etNumberItinerary.text.toString()
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*Данный селектор выбирает тип отдыха ЛБ*/
        binding.selectorRestPointOfTurnover.apply {
            addTab(this.newTab().setText(getString(R.string.text_for_selector_home_rest)), 0, true)
            addTab(
                this.newTab().setText(getString(R.string.text_for_selector_point_rest)),
                1,
                false
            )
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {
                            restPointOfTurnover = false
                        }
                        1 -> {
                            restPointOfTurnover = true
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    // нечего делать
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // нечего делать
                }

            })
        }

        binding.btnAddLoco.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, AddLocoFragment())?.commit()
        }

        binding.btnAddPassenger.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, AddPassangerFragment())?.commit()
        }

        binding.btnAddTrain.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, AddTrainFragment())?.commit()
        }

/* Блок ввода даты и времени явки на работу */
        binding.blockTurnout.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTitleText(getString(R.string.text_for_date_picker_turnout))
                .build()

            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText(getString(R.string.text_for_time_picker_turnout))
                .setHour(calendarNow.get(HOUR_OF_DAY))
                .setMinute(calendarNow.get(MINUTE))
                .build()

            datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER_TURNOUT")

            datePicker.addOnPositiveButtonClickListener { date ->
                calendarTurnout.timeInMillis = date
                dateTurnout = date
                binding.dateTurnout.text = setTextDate(date)
                binding.dateTurnout.alpha = 1f
                timePicker.show(requireActivity().supportFragmentManager, "TIME_PICKER_TURNOUT")
            }

            timePicker.addOnPositiveButtonClickListener {
                dateTurnoutFixed = true
                calendarTurnout.set(HOUR_OF_DAY, timePicker.hour)
                calendarTurnout.set(MINUTE, timePicker.minute)
                binding.timeTurnout.text = setTextTime(timePicker)
                binding.timeTurnout.alpha = 1f
                verificationWorkTime()
            }
        }

/* Блок ввода даты и времени окончания работы.
* constraintBuilder обеспечивает невозможность ввода даты
* ранее указанной в Блоке явки */
        binding.blockEnding.setOnClickListener {
            val constraintBuilder = CalendarConstraints.Builder()
            constraintBuilder.setValidator(DateValidatorPointForward.from(dateTurnout))
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintBuilder.build())
                .setTitleText(getString(R.string.text_for_date_picker_ending))
                .build()

            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText(getString(R.string.text_for_time_picker_ending))
                .setHour(calendarNow.get(HOUR_OF_DAY))
                .setMinute(calendarNow.get(MINUTE))
                .build()

            datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER_ENDING")

            datePicker.addOnPositiveButtonClickListener { date ->
                calendarEnding.timeInMillis = date
                binding.dataEnding.text = setTextDate(date)
                binding.dataEnding.alpha = 1f
                timePicker.show(requireActivity().supportFragmentManager, "TIME_PICKER_ENDING")
            }

            timePicker.addOnPositiveButtonClickListener {
                calendarEnding.set(HOUR_OF_DAY, timePicker.hour)
                calendarEnding.set(MINUTE, timePicker.minute)
                binding.timeEnding.text = setTextTime(timePicker)
                binding.timeEnding.alpha = 1f
                verificationWorkTime()
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setErrorBackground(view: View) {
        view.background = resources.getDrawable(
            R.drawable.shape_background_data_block_error,
            requireContext().theme
        )
        errorInputCalendar = !errorInputCalendar
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setDefaultBackground(view: View) {
        view.background = resources.getDrawable(
            R.drawable.shape_background_data_block,
            requireContext().theme
        )
        errorInputCalendar = !errorInputCalendar
    }

    /* Метод для определения корректности введенных данных*/
    private fun verificationWorkTime() {
        if (!dateTurnoutFixed) {
            setErrorBackground(binding.blockTurnout)
            binding.root.snack(getString(R.string.text_for_snackbar_error_empty_date_turnout))
        } else setDefaultBackground(binding.blockTurnout)

        if (dateTurnoutFixed && calendarTurnout.timeInMillis > calendarEnding.timeInMillis) {
            setErrorBackground(binding.blockEnding)
            binding.root.snack(getString(R.string.text_for_snackbar_error_ending_time))
        } else setDefaultBackground(binding.blockEnding)
    }
}