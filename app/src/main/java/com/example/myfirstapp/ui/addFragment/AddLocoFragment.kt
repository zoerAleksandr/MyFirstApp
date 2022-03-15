package com.example.myfirstapp.ui.addFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentAddLocoBinding
import com.example.myfirstapp.ui.snack
import com.example.myfirstapp.vm.*
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*
import java.util.Calendar.getInstance
import com.example.myfirstapp.vm.ColorForBackgroundError

class AddLocoFragment : Fragment(R.layout.fragment_add_loco) {

    private val binding: FragmentAddLocoBinding by viewBinding()

    private val dateAndTimeNow = getInstance()
    private val dateAndTimeStartAcceptance by lazy { getInstance() }
    private val dateAndTimeEndAcceptance by lazy { getInstance() }
    private val dateAndTimeStartDelivery by lazy { getInstance() }
    private val dateAndTimeEndDelivery by lazy { getInstance() }

    private var dateStartAcceptance: Long = 0
    private var dateStartDelivery: Long = 0

    private var dateAcceptanceFixed = false
    private var dateDeliveryFixed = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*Селектор выбора типа тяги*/
        binding.tabLayoutLoco.apply {
            addTab(this.newTab().setText("Тепловоз"), 0, false)
            addTab(this.newTab().setText("Электровоз"), 1, true)
        }

/*Селектор выбора количества секций*/
        binding.tabLayoutLocoSection.apply {
            addTab(this.newTab().setText("1"), 0, false)
            addTab(this.newTab().setText("2"), 1, true)
            addTab(this.newTab().setText("3"), 2, false)
            addTab(this.newTab().setText("4"), 3, false)

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        0 -> {
                            binding.blockEnergySection2.visibility = View.GONE
                            binding.blockEnergySection3.visibility = View.GONE
                            binding.blockEnergySection4.visibility = View.GONE
                        }
                        1 -> {
                            binding.blockEnergySection2.visibility = View.VISIBLE
                            binding.blockEnergySection3.visibility = View.GONE
                            binding.blockEnergySection4.visibility = View.GONE
                        }
                        2 -> {
                            binding.blockEnergySection2.visibility = View.VISIBLE
                            binding.blockEnergySection3.visibility = View.VISIBLE
                            binding.blockEnergySection4.visibility = View.GONE
                        }
                        3 -> {
                            binding.blockEnergySection2.visibility = View.VISIBLE
                            binding.blockEnergySection3.visibility = View.VISIBLE
                            binding.blockEnergySection4.visibility = View.VISIBLE
                        }

                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {

                }

                override fun onTabReselected(tab: TabLayout.Tab) {

                }
            })
        }
// ПРИЕМКА
/*Дата и время начала приемки локомотива*/
        binding.startOfAcceptance.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTitleText(getString(R.string.text_for_date_picker_start_acceptance))
                .build()

            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText(getString(R.string.text_for_time_picker_start_acceptance))
                .setHour(dateAndTimeNow.get(Calendar.HOUR_OF_DAY))
                .setMinute(dateAndTimeNow.get(Calendar.MINUTE))
                .build()

            datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER_TURNOUT")

            datePicker.addOnPositiveButtonClickListener { date ->
                dateAndTimeStartAcceptance.timeInMillis = date
                dateStartAcceptance = date
                binding.dateLocoStartAcceptance.text = setTextDate(date)
                binding.dateLocoStartAcceptance.alpha = 1f
                timePicker.show(requireActivity().supportFragmentManager, "TIME_PICKER_TURNOUT")
            }

            timePicker.addOnPositiveButtonClickListener {
                dateAcceptanceFixed = true
                dateAndTimeStartAcceptance.set(Calendar.HOUR_OF_DAY, timePicker.hour)
                dateAndTimeStartAcceptance.set(Calendar.MINUTE, timePicker.minute)
                binding.timeLocoStartAcceptance.text = setTextTime(timePicker)
                binding.timeLocoStartAcceptance.alpha = 1f
                verificationAcceptanceDateAndTime()
            }
        }

/*Дата и время окончания приемки локомотива*/
        binding.endOfAcceptance.setOnClickListener {
            val constraintBuilder = CalendarConstraints.Builder()
            constraintBuilder.setValidator(DateValidatorPointForward.from(dateStartAcceptance))
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintBuilder.build())
                .setTitleText(getString(R.string.text_for_date_picker_end_acceptance))
                .build()

            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText(getString(R.string.text_for_time_end_acceptance))
                .setHour(dateAndTimeNow.get(Calendar.HOUR_OF_DAY))
                .setMinute(dateAndTimeNow.get(Calendar.MINUTE))
                .build()

            datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER_TURNOUT")

            datePicker.addOnPositiveButtonClickListener { date ->
                dateAndTimeEndAcceptance.timeInMillis = date
                binding.dateLocoEndAcceptance.text = setTextDate(date)
                binding.dateLocoEndAcceptance.alpha = 1f
                timePicker.show(requireActivity().supportFragmentManager, "TIME_PICKER_TURNOUT")
            }

            timePicker.addOnPositiveButtonClickListener {
                dateAndTimeEndAcceptance.set(Calendar.HOUR_OF_DAY, timePicker.hour)
                dateAndTimeEndAcceptance.set(Calendar.MINUTE, timePicker.minute)
                binding.timeLocoEndAcceptance.text = setTextTime(timePicker)
                binding.timeLocoEndAcceptance.alpha = 1f
                verificationAcceptanceDateAndTime()
            }
        }

// СДАЧА
/*Дата и время начала сдачи локомотива*/
        binding.startOfDelivery.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTitleText(getString(R.string.text_for_date_picker_start_delivery))
                .build()

            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText(getString(R.string.text_for_time_picker_start_delivery))
                .setHour(dateAndTimeNow.get(Calendar.HOUR_OF_DAY))
                .setMinute(dateAndTimeNow.get(Calendar.MINUTE))
                .build()

            datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER_TURNOUT")

            datePicker.addOnPositiveButtonClickListener { date ->
                dateAndTimeStartDelivery.timeInMillis = date
                dateStartDelivery = date
                binding.dateLocoStartDelivery.text = setTextDate(date)
                binding.dateLocoStartDelivery.alpha = 1f
                timePicker.show(requireActivity().supportFragmentManager, "TIME_PICKER_TURNOUT")
            }

            timePicker.addOnPositiveButtonClickListener {
                dateDeliveryFixed = true
                dateAndTimeStartDelivery.set(Calendar.HOUR_OF_DAY, timePicker.hour)
                dateAndTimeStartDelivery.set(Calendar.MINUTE, timePicker.minute)
                binding.timeLocoStartDelivery.text = setTextTime(timePicker)
                binding.timeLocoStartDelivery.alpha = 1f
                verificationDeliveryDateAndTime()
            }
        }

/*Дата и время окончания сдачи локомотива*/
        binding.endOfDelivery.setOnClickListener {
            val constraintBuilder = CalendarConstraints.Builder()
            constraintBuilder.setValidator(DateValidatorPointForward.from(dateStartDelivery))
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintBuilder.build())
                .setTitleText(getString(R.string.text_for_date_picker_end_delivery))
                .build()

            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText(getString(R.string.text_for_time_end_delivery))
                .setHour(dateAndTimeNow.get(Calendar.HOUR_OF_DAY))
                .setMinute(dateAndTimeNow.get(Calendar.MINUTE))
                .build()

            datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER_TURNOUT")

            datePicker.addOnPositiveButtonClickListener { date ->
                dateAndTimeEndDelivery.timeInMillis = date
                binding.dateLocoEndDelivery.text = setTextDate(date)
                binding.dateLocoEndDelivery.alpha = 1f
                timePicker.show(requireActivity().supportFragmentManager, "TIME_PICKER_TURNOUT")
            }

            timePicker.addOnPositiveButtonClickListener {
                dateAndTimeEndDelivery.set(Calendar.HOUR_OF_DAY, timePicker.hour)
                dateAndTimeEndDelivery.set(Calendar.MINUTE, timePicker.minute)
                binding.timeLocoEndDelivery.text = setTextTime(timePicker)
                binding.timeLocoEndDelivery.alpha = 1f
                verificationDeliveryDateAndTime()
            }
        }

    }
    /* Метод для определения корректности введенных данных о времени приемки локомотива*/
    private fun verificationAcceptanceDateAndTime() {
        if (!dateAcceptanceFixed) {
            setErrorBackground(requireContext(), binding.startOfAcceptance, ColorForBackgroundError.YELLOW)
            binding.root.snack(getString(R.string.text_for_snackbar_error_empty_date_start_acceptance))
        } else setDefaultBackground(requireContext(), binding.startOfAcceptance)

        if (dateAcceptanceFixed && dateAndTimeStartAcceptance.timeInMillis >= dateAndTimeEndAcceptance.timeInMillis) {
            setErrorBackground(requireContext(), binding.endOfAcceptance, ColorForBackgroundError.YELLOW)
            binding.root.snack(getString(R.string.text_for_snackbar_error_time_end_acceptance))
        } else setDefaultBackground(requireContext(), binding.endOfAcceptance)
    }

    /* Метод для определения корректности введенных данных о времени сдачи локомотива*/
    private fun verificationDeliveryDateAndTime() {
        if (!dateDeliveryFixed) {
            setErrorBackground(requireContext(), binding.startOfDelivery, ColorForBackgroundError.YELLOW)
            binding.root.snack(getString(R.string.text_for_snackbar_error_empty_date_start_delivery))
        } else setDefaultBackground(requireContext(), binding.startOfDelivery)

        if (dateDeliveryFixed && dateAndTimeStartDelivery.timeInMillis >= dateAndTimeEndDelivery.timeInMillis) {
            setErrorBackground(requireContext(), binding.endOfDelivery, ColorForBackgroundError.YELLOW)
            binding.root.snack(getString(R.string.text_for_snackbar_error_time_end_delivery))
        } else setDefaultBackground(requireContext(), binding.endOfDelivery)
    }

}