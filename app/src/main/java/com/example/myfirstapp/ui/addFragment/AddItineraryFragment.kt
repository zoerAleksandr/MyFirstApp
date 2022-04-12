package com.example.myfirstapp.ui.addFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.data.Itinerary
import com.example.myfirstapp.databinding.FragmentAddItineraryBinding
import com.example.myfirstapp.ui.snack
import com.example.myfirstapp.vm.*
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
    private val dateAndTimeNow by lazy { getInstance() }
    private val dateAndTimeTurnout by lazy { getInstance() }
    private val dateAndTimeEnding by lazy { getInstance() }

    // определят можно ли сохранять маршрут или есть критическая ошибка ввода данных
    private var errorInputCalendar = false
    private var dateTurnoutFixed = false

    private var restPointOfTurnover = false
    private val numberItinerary: String? by lazy {
        if (binding.etNumberItinerary.text.isNullOrEmpty()) null
        else binding.etNumberItinerary.text.toString()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.addItinerary(Itinerary(
            0,
            numberItinerary,
            null,
            null,
            true,
            null,
            mutableListOf(),
            mutableListOf(),
            mutableListOf()
        ))
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
                ?.replace(
                    R.id.container, AddLocoFragment(
                        TypeOfTraction.DieselLocomotive,
                        CountSections.TwoSection,
                        0.83
                    )
                )
                ?.addToBackStack("")
                ?.commit()
        }

        binding.btnAddPassenger.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, AddPassangerFragment())
                ?.addToBackStack("")
                ?.commit()
        }

        binding.btnAddTrain.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, AddTrainFragment())
                ?.addToBackStack("")
                ?.commit()
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
                .setHour(dateAndTimeNow.get(HOUR_OF_DAY))
                .setMinute(dateAndTimeNow.get(MINUTE))
                .build()

            datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER_TURNOUT")

            datePicker.addOnPositiveButtonClickListener { date ->
                dateAndTimeTurnout.timeInMillis = date
                dateTurnout = date
                binding.dateTurnout.text = setTextDate(date)
                binding.dateTurnout.alpha = 1f
                timePicker.show(requireActivity().supportFragmentManager, "TIME_PICKER_TURNOUT")
            }

            timePicker.addOnPositiveButtonClickListener {
                dateTurnoutFixed = true
                dateAndTimeTurnout.set(HOUR_OF_DAY, timePicker.hour)
                dateAndTimeTurnout.set(MINUTE, timePicker.minute)
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
                .setHour(dateAndTimeNow.get(HOUR_OF_DAY))
                .setMinute(dateAndTimeNow.get(MINUTE))
                .build()

            datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER_ENDING")

            datePicker.addOnPositiveButtonClickListener { date ->
                dateAndTimeEnding.timeInMillis = date
                binding.dataEnding.text = setTextDate(date)
                binding.dataEnding.alpha = 1f
                timePicker.show(requireActivity().supportFragmentManager, "TIME_PICKER_ENDING")
            }

            timePicker.addOnPositiveButtonClickListener {
                dateAndTimeEnding.set(HOUR_OF_DAY, timePicker.hour)
                dateAndTimeEnding.set(MINUTE, timePicker.minute)
                binding.timeEnding.text = setTextTime(timePicker)
                binding.timeEnding.alpha = 1f
                verificationWorkTime()
            }
        }
    }


    /* Метод для определения корректности введенных данных*/
    private fun verificationWorkTime() {
        if (!dateTurnoutFixed) {
            setErrorBackground(requireContext(), binding.blockTurnout, ColorForBackgroundError.RED)
            binding.root.snack(getString(R.string.text_for_snackbar_error_empty_date_turnout))
        } else setDefaultBackground(requireContext(), binding.blockTurnout)

        if (dateTurnoutFixed && dateAndTimeTurnout.timeInMillis > dateAndTimeEnding.timeInMillis) {
            setErrorBackground(requireContext(), binding.blockEnding, ColorForBackgroundError.RED)
            binding.root.snack(getString(R.string.text_for_snackbar_error_ending_time))
        } else setDefaultBackground(requireContext(), binding.blockEnding)
    }
}