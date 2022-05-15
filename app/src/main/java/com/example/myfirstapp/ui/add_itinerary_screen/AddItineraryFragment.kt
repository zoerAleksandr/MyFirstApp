package com.example.myfirstapp.ui.add_itinerary_screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.app
import com.example.myfirstapp.databinding.FragmentAddItineraryBinding
import com.example.myfirstapp.domain.Controller
import com.example.myfirstapp.domain.entity.CountSections
import com.example.myfirstapp.domain.entity.TypeOfTraction
import com.example.myfirstapp.ui.add_loco_screen.AddLocoFragment
import com.example.myfirstapp.ui.add_loco_screen.KEY_COEFFICIENT
import com.example.myfirstapp.ui.add_loco_screen.KEY_COUNT_SECTIONS
import com.example.myfirstapp.ui.add_loco_screen.KEY_TYPE_OF_TRACTION
import com.example.myfirstapp.ui.add_passenger_screen.AddPassangerFragment
import com.example.myfirstapp.ui.add_train_screen.AddTrainFragment
import com.example.myfirstapp.utils.*
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import java.util.Calendar.*


class AddItineraryFragment : Fragment(R.layout.fragment_add_itinerary) {

    private val binding: FragmentAddItineraryBinding by viewBinding()
    private val viewModel: AddItineraryViewModel by viewModel()

    private var dateTurnout: Long = 0
    private val dateAndTimeNow by lazy { getInstance() }
    private var dateAndTimeTurnout: Calendar? = null
    private var dateAndTimeEnding: Calendar? = null

    // определят можно ли сохранять маршрут или есть критическая ошибка ввода данных
    private var errorInputCalendar = false
    private var dateTurnoutFixed = false

    private var restPointOfTurnover = false

    private val controller by lazy { activity as Controller }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity !is Controller) {
            throw IllegalStateException("Activity должна наследоваться от Controller")
        }
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
            val bundle = Bundle().apply {
                putParcelable(KEY_TYPE_OF_TRACTION, TypeOfTraction.DieselLocomotive)
                putParcelable(KEY_COUNT_SECTIONS, CountSections.TwoSection)
                putDouble(KEY_COEFFICIENT, 0.83)
            }
            controller.openScreen(AddLocoFragment.newInstance(bundle))
        }

        binding.btnAddPassenger.setOnClickListener {
            controller.openScreen(AddPassangerFragment())
        }

        binding.btnAddTrain.setOnClickListener {
            controller.openScreen(AddTrainFragment())
        }

        /** Блок ввода даты и времени явки на работу */
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
                dateAndTimeTurnout = getInstance().apply {
                    timeInMillis = date
                }
                dateTurnout = date
                binding.apply {
                    dateTurnout.text = setTextDate(date)
                    dateTurnout.alpha = 1f
                }
                timePicker.show(requireActivity().supportFragmentManager, "TIME_PICKER_TURNOUT")
            }

            timePicker.addOnPositiveButtonClickListener {
                dateTurnoutFixed = true
                dateAndTimeTurnout?.let { calendar ->
                    calendar.set(HOUR_OF_DAY, timePicker.hour)
                    calendar.set(MINUTE, timePicker.minute)
                }
                binding.apply {
                    timeTurnout.text = setTextTime(timePicker)
                    timeTurnout.alpha = 1f
                }
                verificationWorkTime()
            }
        }

        /** Блок ввода даты и времени окончания работы.
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
                dateAndTimeEnding = getInstance().apply {
                    timeInMillis = date
                }
                binding.apply {
                    dataEnding.text = setTextDate(date)
                    dataEnding.alpha = 1f
                }
                timePicker.show(requireActivity().supportFragmentManager, "TIME_PICKER_ENDING")
            }

            timePicker.addOnPositiveButtonClickListener {
                dateAndTimeEnding = getInstance().apply {
                    set(HOUR_OF_DAY, timePicker.hour)
                    set(MINUTE, timePicker.minute)
                }
                binding.apply {
                    timeEnding.text = setTextTime(timePicker)
                    timeEnding.alpha = 1f
                }
                verificationWorkTime()
            }
        }
    }

    override fun onDestroyView() {
        viewModel.saveItinerary(
            binding.etNumberItinerary.text.toString(),
            dateAndTimeTurnout,
            dateAndTimeEnding,
            restPointOfTurnover,
            binding.notesText.text.toString()
        )
        super.onDestroyView()
    }

    /* Метод для определения корректности введенных данных*/
    private fun verificationWorkTime() {
        if (dateAndTimeTurnout == null) {
            setErrorBackground(requireContext(), binding.blockTurnout, ColorForBackgroundError.RED)
            binding.root.snack(getString(R.string.text_for_snackbar_error_empty_date_turnout))
        } else setDefaultBackground(requireContext(), binding.blockTurnout)

        if (dateAndTimeEnding == null) {
            setErrorBackground(app, binding.blockEnding, ColorForBackgroundError.RED)
        } else {
            setDefaultBackground(app, binding.blockEnding)
        }
        if (dateAndTimeTurnout != null &&
            dateAndTimeEnding != null &&
            dateAndTimeTurnout!!.timeInMillis > dateAndTimeEnding!!.timeInMillis
        ) {
            setErrorBackground(requireContext(), binding.blockEnding, ColorForBackgroundError.RED)
            binding.root.snack(getString(R.string.text_for_snackbar_error_ending_time))
        } else setDefaultBackground(requireContext(), binding.blockEnding)
    }
}