package com.example.myfirstapp.ui.add_loco_screen

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentAddLocoBinding
import com.example.myfirstapp.domain.entity.CountSections
import com.example.myfirstapp.domain.entity.TypeOfTraction
import com.example.myfirstapp.utils.*
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*
import java.util.Calendar.getInstance
import kotlin.properties.Delegates

const val PREFERENCES = "preferences"
private const val LIST_SERIES = "LIST_SERIES"

// для Bundle
const val KEY_TYPE_OF_TRACTION = "keyTypeOfTraction"
const val KEY_COUNT_SECTIONS = "keyCountSection"
const val KEY_COEFFICIENT = "keyCoefficient"

class AddLocoFragment : Fragment(R.layout.fragment_add_loco) {
    companion object {
        fun newInstance(bundle: Bundle): AddLocoFragment {
            val fragment = AddLocoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val binding: FragmentAddLocoBinding by viewBinding()

    private val dateAndTimeNow = getInstance()
    private val dateAndTimeStartAcceptance by lazy { getInstance() }
    private val dateAndTimeEndAcceptance by lazy { getInstance() }
    private val dateAndTimeStartDelivery by lazy { getInstance() }
    private val dateAndTimeEndDelivery by lazy { getInstance() }

    // тип тяги
    private lateinit var typeLoco: TypeOfTraction

    // количество секций
    private lateinit var countSection: CountSections

    // для настройки dataPicker
    private var dateStartAcceptance: Long = 0
    private var dateStartDelivery: Long = 0

    // для проверки заполнения времени
    private var dateAcceptanceFixed = false
    private var dateDeliveryFixed = false

    // Тепловоз
    private var coefficient by Delegates.notNull<Double>()

    //  переменные расхода энергоресурсов секции 1
    private var secOneDieselFuelAcceptance: Int? = null
    private var secOneDieselFuelDelivery: Int? = null
    private var secOneResultDieselFuelInLitres: Int = 0
    private var secOneResultDieselFuelInKilo: Int = 0

    //  переменные расхода энергоресурсов секции 2
    private var secTwoDieselFuelAcceptance: Int? = null
    private var secTwoDieselFuelDelivery: Int? = null
    private var secTwoResultDieselFuelInLitres: Int = 0
    private var secTwoResultDieselFuelInKilo: Int = 0

    //  переменные расхода энергоресурсов секции 3
    private var secThreeDieselFuelAcceptance: Int? = null
    private var secThreeDieselFuelDelivery: Int? = null
    private var secThreeResultDieselFuelInLitres: Int = 0
    private var secThreeResultDieselFuelInKilo: Int = 0

    //  переменные расхода энергоресурсов секции 4
    private var secFourDieselFuelAcceptance: Int? = null
    private var secFourDieselFuelDelivery: Int? = null
    private var secFourResultDieselFuelInLitres: Int = 0
    private var secFourResultDieselFuelInKilo: Int = 0

    // Электровоз
    //  переменные расхода энергоресурсов секции 1
    private var secOneEnergyAcceptance: Int? = null
    private var secOneEnergyDelivery: Int? = null
    private var secOneResultEnergy: Int = 0
    private var secOneRecoveryAcceptance: Int? = null
    private var secOneRecoveryDelivery: Int? = null
    private var secOneResultRecovery: Int = 0

    //  переменные расхода энергоресурсов секции 2
    private var secTwoEnergyAcceptance: Int? = null
    private var secTwoEnergyDelivery: Int? = null
    private var secTwoResultEnergy: Int = 0
    private var secTwoRecoveryAcceptance: Int? = null
    private var secTwoRecoveryDelivery: Int? = null
    private var secTwoResultRecovery: Int = 0

    //  переменные расхода энергоресурсов секции 3
    private var secThreeEnergyAcceptance: Int? = null
    private var secThreeEnergyDelivery: Int? = null
    private var secThreeResultEnergy: Int = 0
    private var secThreeRecoveryAcceptance: Int? = null
    private var secThreeRecoveryDelivery: Int? = null
    private var secThreeResultRecovery: Int = 0

    //  переменные расхода энергоресурсов секции 4
    private var secFourEnergyAcceptance: Int? = null
    private var secFourEnergyDelivery: Int? = null
    private var secFourResultEnergy: Int = 0
    private var secFourRecoveryAcceptance: Int? = null
    private var secFourRecoveryDelivery: Int? = null
    private var secFourResultRecovery: Int = 0

    // переменные расхода энергоресурсов общие
    private var totalConsumptionDieselLiter: Int = 0
    private var totalConsumptionDieselKilo: Double = 0.0

    private var totalConsumptionEnergy: Int = 0
    private var totalConsumptionRecovery: Int = 0

    // инвентарь
    private var countBrakeShoes: Int = 0
    private var countExtinguishers: Int = 0

    @SuppressLint("MutatingSharedPrefs", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            typeLoco =
                bundle.getParcelable(KEY_TYPE_OF_TRACTION) ?: TypeOfTraction.ElectricLocomotive
            countSection =
                bundle.getParcelable(KEY_COUNT_SECTIONS) ?: CountSections.TwoSection
            coefficient = bundle.getDouble(KEY_COEFFICIENT)
        }

        setCountSection()

        // SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences(PREFERENCES, MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        // экземпляр настроек полученых из сохраненного состояния
        val set: MutableSet<String>? = sharedPreferences?.getStringSet(LIST_SERIES, mutableSetOf())
        // копия настроек для редактирования
        val setCopy: MutableSet<String> = mutableSetOf<String>().apply {
            addAll(set!!)
        }

        // список для адаптера AutoCompileTextView
        val string: MutableList<String> = mutableListOf<String>().apply {
            addAll(setCopy)
        }
        binding.etSeriesLoco.threshold = 1
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            string
        ).also { arrayAdapter ->
            binding.etSeriesLoco.setAdapter(arrayAdapter)
        }
        // background для DropDown
        binding.etSeriesLoco.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.shape_for_drop_down,
                null
            )
        )
        binding.etSeriesLoco.setOnFocusChangeListener { _, _ ->
            if (binding.etSeriesLoco.text.toString()
                    .isNotBlank() && setCopy.add(binding.etSeriesLoco.text.toString())
            ) {
                editor?.putStringSet(LIST_SERIES, setCopy)?.apply()
            }
        }

/*Селектор выбора типа тяги*/
        binding.tabLayoutLoco.apply {
            addTab(this.newTab().setText("Тепловоз"), 0, false)
            addTab(this.newTab().setText("Электровоз"), 1, false)
            selectTab(
                when (typeLoco) {
                    is TypeOfTraction.DieselLocomotive -> {
                        getTabAt(0)
                    }
                    is TypeOfTraction.ElectricLocomotive -> {
                        getTabAt(1)
                    }
                }
            )

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        0 -> {
                            typeLoco = TypeOfTraction.DieselLocomotive
                            binding.blockEnergySection1.visibility = View.GONE
                            binding.blockEnergySection2.visibility = View.GONE
                            binding.blockEnergySection3.visibility = View.GONE
                            binding.blockEnergySection4.visibility = View.GONE
                            setCountSection()
                        }
                        1 -> {
                            typeLoco = TypeOfTraction.ElectricLocomotive
                            binding.blockDieselFuelSection1.visibility = View.GONE
                            binding.blockDieselFuelSection2.visibility = View.GONE
                            binding.blockDieselFuelSection3.visibility = View.GONE
                            binding.blockDieselFuelSection4.visibility = View.GONE
                            setCountSection()
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    // nothing to do
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // nothing to do
                }
            })
        }

/*Селектор выбора количества секций*/
        binding.tabLayoutLocoSection.apply {
            addTab(this.newTab().setText("1"), 0, false)
            addTab(this.newTab().setText("2"), 1, false)
            addTab(this.newTab().setText("3"), 2, false)
            addTab(this.newTab().setText("4"), 3, false)
            selectTab(
                when (countSection) {
                    CountSections.OneSection -> getTabAt(0)
                    CountSections.TwoSection -> getTabAt(1)
                    CountSections.ThreeSection -> getTabAt(2)
                    CountSections.FourSection -> getTabAt(3)
                }

            )
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        0 -> {
                            countSection = CountSections.OneSection
                        }
                        1 -> {
                            countSection = CountSections.TwoSection
                        }
                        2 -> {
                            countSection = CountSections.ThreeSection
                        }
                        3 -> {
                            countSection = CountSections.FourSection
                        }
                    }
                    setCountSection()
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    // нечего делать
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                    // нечего делать
                }
            })
        }

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

// ТЕПЛОВОЗ
// Секция 1
        // приемка
        binding.dataDieselFuelAcceptance1.addTextChangedListener {
            secOneResultDieselFuelInLitres = calculationOfExpenseDiesel(
                binding.resultDieselFuelLiterSec1,
                binding.resultDieselFuelKiloSec1,
                it.toString().toIntOrNull(),
                binding.dataDieselFuelDelivery1.text.toString().toIntOrNull()
            )
            binding.dataDieselFuelKiloAcceptance1.text = setDataDieselFuelInKilograms(
                coefficient,
                binding.dataDieselFuelAcceptance1.text.toString().toIntOrNull()
            )
            setTotalConsumptionDiesel()
            setBackgroundAcceptance(
                binding.dataDieselFuelAcceptance1.text.toString().toIntOrNull(),
                binding.dataDieselFuelDelivery1.text.toString().toIntOrNull(),
                binding.dieselFuelAcceptance1,
                binding.dieselFuelDelivery1
            )
        }
        // сдача
        binding.dataDieselFuelDelivery1.addTextChangedListener {
            secOneResultDieselFuelInLitres = calculationOfExpenseDiesel(
                binding.resultDieselFuelLiterSec1,
                binding.resultDieselFuelKiloSec1,
                binding.dataDieselFuelAcceptance1.text.toString().toIntOrNull(),
                it.toString().toIntOrNull()
            )
            binding.dataDieselFuelKiloDelivery1.text = setDataDieselFuelInKilograms(
                coefficient,
                binding.dataDieselFuelDelivery1.text.toString().toIntOrNull()
            )
            setTotalConsumptionDiesel()
            setBackgroundDelivery(
                binding.dataDieselFuelAcceptance1.text.toString().toIntOrNull(),
                binding.dataDieselFuelDelivery1.text.toString().toIntOrNull(),
                binding.dieselFuelAcceptance1,
                binding.dieselFuelDelivery1,
            )
        }

// Секция 2
        // приемка
        binding.dataDieselFuelAcceptance2.addTextChangedListener {
            secTwoResultDieselFuelInLitres = calculationOfExpenseDiesel(
                binding.resultDieselFuelLiterSec2,
                binding.resultDieselFuelKiloSec2,
                it.toString().toIntOrNull(),
                binding.dataDieselFuelDelivery2.text.toString().toIntOrNull()
            )
            binding.dataDieselFuelKiloAcceptance2.text = setDataDieselFuelInKilograms(
                coefficient,
                binding.dataDieselFuelAcceptance2.text.toString().toIntOrNull()
            )
            setTotalConsumptionDiesel()
            setBackgroundAcceptance(
                binding.dataDieselFuelAcceptance2.text.toString().toIntOrNull(),
                binding.dataDieselFuelDelivery2.text.toString().toIntOrNull(),
                binding.dieselFuelAcceptance2,
                binding.dieselFuelDelivery2
            )
        }
        // сдача
        binding.dataDieselFuelDelivery2.addTextChangedListener {
            secTwoResultDieselFuelInLitres = calculationOfExpenseDiesel(
                binding.resultDieselFuelLiterSec2,
                binding.resultDieselFuelKiloSec2,
                binding.dataDieselFuelAcceptance2.text.toString().toIntOrNull(),
                it.toString().toIntOrNull()
            )
            binding.dataDieselFuelKiloDelivery2.text = setDataDieselFuelInKilograms(
                coefficient,
                binding.dataDieselFuelDelivery2.text.toString().toIntOrNull()
            )
            setTotalConsumptionDiesel()
            setBackgroundDelivery(
                binding.dataDieselFuelAcceptance2.text.toString().toIntOrNull(),
                binding.dataDieselFuelDelivery2.text.toString().toIntOrNull(),
                binding.dieselFuelAcceptance2,
                binding.dieselFuelDelivery2,
            )
        }

// Секция 3
        // приемка
        binding.dataDieselFuelAcceptance3.addTextChangedListener {
            secThreeResultDieselFuelInLitres = calculationOfExpenseDiesel(
                binding.resultDieselFuelLiterSec3,
                binding.resultDieselFuelKiloSec3,
                it.toString().toIntOrNull(),
                binding.dataDieselFuelDelivery3.text.toString().toIntOrNull()
            )
            binding.dataDieselFuelKiloAcceptance3.text = setDataDieselFuelInKilograms(
                coefficient,
                binding.dataDieselFuelAcceptance3.text.toString().toIntOrNull()
            )
            setTotalConsumptionDiesel()
            setBackgroundAcceptance(
                binding.dataDieselFuelAcceptance3.text.toString().toIntOrNull(),
                binding.dataDieselFuelDelivery3.text.toString().toIntOrNull(),
                binding.dieselFuelAcceptance3,
                binding.dieselFuelDelivery3
            )
        }
        // сдача
        binding.dataDieselFuelDelivery3.addTextChangedListener {
            secThreeResultDieselFuelInLitres = calculationOfExpenseDiesel(
                binding.resultDieselFuelLiterSec3,
                binding.resultDieselFuelKiloSec3,
                binding.dataDieselFuelAcceptance3.text.toString().toIntOrNull(),
                it.toString().toIntOrNull()
            )
            binding.dataDieselFuelKiloDelivery3.text = setDataDieselFuelInKilograms(
                coefficient,
                binding.dataDieselFuelDelivery3.text.toString().toIntOrNull()
            )
            setTotalConsumptionDiesel()
            setBackgroundDelivery(
                binding.dataDieselFuelAcceptance3.text.toString().toIntOrNull(),
                binding.dataDieselFuelDelivery3.text.toString().toIntOrNull(),
                binding.dieselFuelAcceptance3,
                binding.dieselFuelDelivery3,
            )
        }

// Секция 4
        // приемка
        binding.dataDieselFuelAcceptance4.addTextChangedListener {
            secFourResultDieselFuelInLitres = calculationOfExpenseDiesel(
                binding.resultDieselFuelLiterSec4,
                binding.resultDieselFuelKiloSec4,
                it.toString().toIntOrNull(),
                binding.dataDieselFuelDelivery4.text.toString().toIntOrNull()
            )
            binding.dataDieselFuelKiloAcceptance4.text = setDataDieselFuelInKilograms(
                coefficient,
                binding.dataDieselFuelAcceptance4.text.toString().toIntOrNull()
            )
            setTotalConsumptionDiesel()
            setBackgroundAcceptance(
                binding.dataDieselFuelAcceptance4.text.toString().toIntOrNull(),
                binding.dataDieselFuelDelivery4.text.toString().toIntOrNull(),
                binding.dieselFuelAcceptance4,
                binding.dieselFuelDelivery4
            )
        }
        // сдача
        binding.dataDieselFuelDelivery4.addTextChangedListener {
            secFourResultDieselFuelInLitres = calculationOfExpenseDiesel(
                binding.resultDieselFuelLiterSec4,
                binding.resultDieselFuelKiloSec4,
                binding.dataDieselFuelAcceptance4.text.toString().toIntOrNull(),
                it.toString().toIntOrNull()
            )
            binding.dataDieselFuelKiloDelivery4.text = setDataDieselFuelInKilograms(
                coefficient,
                binding.dataDieselFuelDelivery4.text.toString().toIntOrNull()
            )
            setTotalConsumptionDiesel()
            setBackgroundDelivery(
                binding.dataDieselFuelAcceptance4.text.toString().toIntOrNull(),
                binding.dataDieselFuelDelivery4.text.toString().toIntOrNull(),
                binding.dieselFuelAcceptance4,
                binding.dieselFuelDelivery4,
            )
        }

// ЭЛЕКТРОВОЗ
// Секция 1
        // Energy при приемке
        binding.dataEnergyAcceptance1.addTextChangedListener {
            secOneResultEnergy = calculationOfExpense(
                binding.resultEnergySec1,
                it.toString().toIntOrNull(),
                binding.dataEnergyDelivery1.text.toString().toIntOrNull()
            )
            setTotalConsumptionEnergy()
            setBackgroundAcceptance(
                binding.dataEnergyAcceptance1.text.toString().toIntOrNull(),
                binding.dataEnergyDelivery1.text.toString().toIntOrNull(),
                binding.energyAcceptance1,
                binding.energyDelivery1
            )
        }
        // Energy при сдаче
        binding.dataEnergyDelivery1.addTextChangedListener {
            secOneResultEnergy = calculationOfExpense(
                binding.resultEnergySec1,
                binding.dataEnergyAcceptance1.text.toString().toIntOrNull(),
                it.toString().toIntOrNull()
            )
            setTotalConsumptionEnergy()
            setBackgroundDelivery(
                binding.dataEnergyAcceptance1.text.toString().toIntOrNull(),
                binding.dataEnergyDelivery1.text.toString().toIntOrNull(),
                binding.energyAcceptance1,
                binding.energyDelivery1
            )
        }

        // Recovery при приемке
        binding.dataRecoveryAcceptance1.addTextChangedListener {
            secOneResultRecovery = calculationOfExpense(
                binding.resultRecoverySec1,
                it.toString().toIntOrNull(),
                binding.dataRecoveryDelivery1.text.toString().toIntOrNull()
            )
            setTotalConsumptionRecovery()
            setBackgroundAcceptance(
                binding.dataRecoveryAcceptance1.text.toString().toIntOrNull(),
                binding.dataRecoveryDelivery1.text.toString().toIntOrNull(),
                binding.recoveryAcceptance1,
                binding.recoveryDelivery1
            )
        }
        // Recovery при сдаче
        binding.dataRecoveryDelivery1.addTextChangedListener {
            secOneResultRecovery = calculationOfExpense(
                binding.resultRecoverySec1,
                binding.dataRecoveryAcceptance1.text.toString().toIntOrNull(),
                it.toString().toIntOrNull()
            )
            setTotalConsumptionRecovery()
            setBackgroundDelivery(
                binding.dataRecoveryAcceptance1.text.toString().toIntOrNull(),
                binding.dataRecoveryDelivery1.text.toString().toIntOrNull(),
                binding.recoveryAcceptance1,
                binding.recoveryDelivery1
            )
        }

// Секция 2
        // Energy при приемке
        binding.dataEnergyAcceptance2.addTextChangedListener {
            secTwoResultEnergy = calculationOfExpense(
                binding.resultEnergySec2,
                it.toString().toIntOrNull(),
                binding.dataEnergyDelivery2.text.toString().toIntOrNull()
            )
            setTotalConsumptionEnergy()
            setBackgroundAcceptance(
                binding.dataEnergyAcceptance2.text.toString().toIntOrNull(),
                binding.dataEnergyDelivery2.text.toString().toIntOrNull(),
                binding.energyAcceptance2,
                binding.energyDelivery2
            )
        }
        // Energy при сдаче
        binding.dataEnergyDelivery2.addTextChangedListener {
            secTwoResultEnergy = calculationOfExpense(
                binding.resultEnergySec2,
                binding.dataEnergyAcceptance2.text.toString().toIntOrNull(),
                it.toString().toIntOrNull()
            )
            setTotalConsumptionEnergy()
            setBackgroundDelivery(
                binding.dataEnergyAcceptance2.text.toString().toIntOrNull(),
                binding.dataEnergyDelivery2.text.toString().toIntOrNull(),
                binding.energyAcceptance2,
                binding.energyDelivery2
            )
        }

        // Recovery при приемке
        binding.dataRecoveryAcceptance2.addTextChangedListener {
            secTwoResultRecovery = calculationOfExpense(
                binding.resultRecoverySec2,
                it.toString().toIntOrNull(),
                binding.dataRecoveryDelivery2.text.toString().toIntOrNull()
            )
            setTotalConsumptionRecovery()
            setBackgroundAcceptance(
                binding.dataRecoveryAcceptance2.text.toString().toIntOrNull(),
                binding.dataRecoveryDelivery2.text.toString().toIntOrNull(),
                binding.recoveryAcceptance2,
                binding.recoveryDelivery2
            )
        }
        // Recovery при сдаче
        binding.dataRecoveryDelivery2.addTextChangedListener {
            secTwoResultRecovery = calculationOfExpense(
                binding.resultRecoverySec2,
                binding.dataRecoveryAcceptance2.text.toString().toIntOrNull(),
                it.toString().toIntOrNull()
            )
            setTotalConsumptionRecovery()
            setBackgroundDelivery(
                binding.dataRecoveryAcceptance2.text.toString().toIntOrNull(),
                binding.dataRecoveryDelivery2.text.toString().toIntOrNull(),
                binding.recoveryAcceptance2,
                binding.recoveryDelivery2
            )
        }

// Секция 3
        // Energy при приемке
        binding.dataEnergyAcceptance3.addTextChangedListener {
            secThreeResultEnergy = calculationOfExpense(
                binding.resultEnergySec3,
                it.toString().toIntOrNull(),
                binding.dataEnergyDelivery3.text.toString().toIntOrNull()
            )
            setTotalConsumptionEnergy()
            setBackgroundAcceptance(
                binding.dataEnergyAcceptance3.text.toString().toIntOrNull(),
                binding.dataEnergyDelivery3.text.toString().toIntOrNull(),
                binding.energyAcceptance3,
                binding.energyDelivery3
            )
        }
        // Energy при сдаче
        binding.dataEnergyDelivery3.addTextChangedListener {
            secThreeResultEnergy = calculationOfExpense(
                binding.resultEnergySec3,
                binding.dataEnergyAcceptance3.text.toString().toIntOrNull(),
                it.toString().toIntOrNull()
            )
            setTotalConsumptionEnergy()
            setBackgroundDelivery(
                binding.dataEnergyAcceptance3.text.toString().toIntOrNull(),
                binding.dataEnergyDelivery3.text.toString().toIntOrNull(),
                binding.energyAcceptance3,
                binding.energyDelivery3
            )
        }

        // Recovery при приемке
        binding.dataRecoveryAcceptance3.addTextChangedListener {
            secThreeResultRecovery = calculationOfExpense(
                binding.resultRecoverySec3,
                it.toString().toIntOrNull(),
                binding.dataRecoveryDelivery3.text.toString().toIntOrNull()
            )
            setTotalConsumptionRecovery()
            setBackgroundAcceptance(
                binding.dataRecoveryAcceptance3.text.toString().toIntOrNull(),
                binding.dataRecoveryDelivery3.text.toString().toIntOrNull(),
                binding.recoveryAcceptance3,
                binding.recoveryDelivery3
            )
        }
        // Recovery при сдаче
        binding.dataRecoveryDelivery3.addTextChangedListener {
            secThreeResultRecovery = calculationOfExpense(
                binding.resultRecoverySec3,
                binding.dataRecoveryAcceptance3.text.toString().toIntOrNull(),
                it.toString().toIntOrNull()
            )
            setTotalConsumptionRecovery()
            setBackgroundDelivery(
                binding.dataRecoveryAcceptance3.text.toString().toIntOrNull(),
                binding.dataRecoveryDelivery3.text.toString().toIntOrNull(),
                binding.recoveryAcceptance3,
                binding.recoveryDelivery3
            )
        }

// Секция 4
        // Energy при приемке
        binding.dataEnergyAcceptance4.addTextChangedListener {
            secFourResultEnergy = calculationOfExpense(
                binding.resultEnergySec4,
                it.toString().toIntOrNull(),
                binding.dataEnergyDelivery4.text.toString().toIntOrNull()
            )
            setTotalConsumptionEnergy()
            setBackgroundAcceptance(
                binding.dataEnergyAcceptance4.text.toString().toIntOrNull(),
                binding.dataEnergyDelivery4.text.toString().toIntOrNull(),
                binding.energyAcceptance4,
                binding.energyDelivery4
            )
        }
        // Energy при сдаче
        binding.dataEnergyDelivery4.addTextChangedListener {
            secFourResultEnergy = calculationOfExpense(
                binding.resultEnergySec4,
                binding.dataEnergyAcceptance4.text.toString().toIntOrNull(),
                it.toString().toIntOrNull()
            )
            setTotalConsumptionEnergy()
            setBackgroundDelivery(
                binding.dataEnergyAcceptance4.text.toString().toIntOrNull(),
                binding.dataEnergyDelivery4.text.toString().toIntOrNull(),
                binding.energyAcceptance4,
                binding.energyDelivery4
            )
        }

        // Recovery при приемке
        binding.dataRecoveryAcceptance4.addTextChangedListener {
            secFourResultRecovery = calculationOfExpense(
                binding.resultRecoverySec4,
                it.toString().toIntOrNull(),
                binding.dataRecoveryDelivery4.text.toString().toIntOrNull()
            )
            setTotalConsumptionRecovery()
            setBackgroundAcceptance(
                binding.dataRecoveryAcceptance4.text.toString().toIntOrNull(),
                binding.dataRecoveryDelivery4.text.toString().toIntOrNull(),
                binding.recoveryAcceptance4,
                binding.recoveryDelivery4
            )
        }
        // Recovery при сдаче
        binding.dataRecoveryDelivery4.addTextChangedListener {
            secFourResultRecovery = calculationOfExpense(
                binding.resultRecoverySec4,
                binding.dataRecoveryAcceptance4.text.toString().toIntOrNull(),
                it.toString().toIntOrNull()
            )
            setTotalConsumptionRecovery()
            setBackgroundDelivery(
                binding.dataRecoveryAcceptance4.text.toString().toIntOrNull(),
                binding.dataRecoveryDelivery4.text.toString().toIntOrNull(),
                binding.recoveryAcceptance4,
                binding.recoveryDelivery4
            )
        }

// ИНВЕНТАРЬ
        binding.btnMinusBrakeShoes.setOnClickListener {
            if (countBrakeShoes > 0) {
                countBrakeShoes -= 1
                binding.dataBrakeShoes.text = countBrakeShoes.toString()
            } else binding.root.snack(getString(R.string.text_for_snackbar_input_less_zero))
        }
        binding.btnPlusBrakeShoes.setOnClickListener {
            countBrakeShoes += 1
            binding.dataBrakeShoes.text = countBrakeShoes.toString()
        }
        binding.btnMinusExtinguishers.setOnClickListener {
            if (countExtinguishers > 0) {
                countExtinguishers -= 1
                binding.dataExtinguishers.text = countExtinguishers.toString()
            } else binding.root.snack(getString(R.string.text_for_snackbar_input_less_zero))
        }
        binding.btnPlusExtinguishers.setOnClickListener {
            countExtinguishers += 1
            binding.dataExtinguishers.text = countExtinguishers.toString()
        }
    }

    /* Метод для установки видимости блоков расхода энергоресуров в зависимости от выбранного типа тяги и количества секций*/
    private fun setCountSection() {
        when (typeLoco) {
            TypeOfTraction.DieselLocomotive -> {
                when (countSection) {
                    CountSections.OneSection -> {
                        binding.blockDieselFuelSection1.visibility = View.VISIBLE
                        binding.blockDieselFuelSection2.visibility = View.GONE
                        binding.blockDieselFuelSection3.visibility = View.GONE
                        binding.blockDieselFuelSection4.visibility = View.GONE
                        binding.dataDieselFuelAcceptance1.imeOptions = EditorInfo.IME_ACTION_DONE
                        binding.dataDieselFuelDelivery1.imeOptions = EditorInfo.IME_ACTION_DONE
//                        binding.dataDieselFuelDelivery1.setOnEditorActionListener { v, actionId, event ->
//                            if (actionId == EditorInfo.IME_ACTION_DONE){
//                                binding.dataDieselFuelDelivery1.clearFocus()
//                                return@setOnEditorActionListener true
//                            }
//                            false
//                        }
                    }
                    CountSections.TwoSection -> {
                        binding.blockDieselFuelSection1.visibility = View.VISIBLE
                        binding.blockDieselFuelSection2.visibility = View.VISIBLE
                        binding.blockDieselFuelSection3.visibility = View.GONE
                        binding.blockDieselFuelSection4.visibility = View.GONE
                        binding.dataDieselFuelAcceptance1.imeOptions = EditorInfo.IME_ACTION_NONE
                        binding.dataDieselFuelDelivery1.imeOptions = EditorInfo.IME_ACTION_NONE
                        binding.dataDieselFuelAcceptance2.imeOptions = EditorInfo.IME_ACTION_DONE
                        binding.dataDieselFuelDelivery2.imeOptions = EditorInfo.IME_ACTION_DONE
//                        binding.dataDieselFuelDelivery1.setOnEditorActionListener { v, actionId, event ->
//                            var handled = false
//                            if (actionId == EditorInfo.IME_ACTION_DONE){
//                                handled = true
//                                binding.dataDieselFuelDelivery2.clearFocus()
//                            }
//                            handled
//                        }
                    }
                    CountSections.ThreeSection -> {
                        binding.blockDieselFuelSection1.visibility = View.VISIBLE
                        binding.blockDieselFuelSection2.visibility = View.VISIBLE
                        binding.blockDieselFuelSection3.visibility = View.VISIBLE
                        binding.blockDieselFuelSection4.visibility = View.GONE
                        binding.dataDieselFuelAcceptance1.imeOptions = EditorInfo.IME_ACTION_NONE
                        binding.dataDieselFuelDelivery1.imeOptions = EditorInfo.IME_ACTION_NONE
                        binding.dataDieselFuelAcceptance2.imeOptions = EditorInfo.IME_ACTION_NONE
                        binding.dataDieselFuelDelivery2.imeOptions = EditorInfo.IME_ACTION_NONE
                        binding.dataDieselFuelAcceptance3.imeOptions = EditorInfo.IME_ACTION_DONE
                        binding.dataDieselFuelDelivery3.imeOptions = EditorInfo.IME_ACTION_DONE
                    }
                    CountSections.FourSection -> {
                        binding.blockDieselFuelSection1.visibility = View.VISIBLE
                        binding.blockDieselFuelSection2.visibility = View.VISIBLE
                        binding.blockDieselFuelSection3.visibility = View.VISIBLE
                        binding.blockDieselFuelSection4.visibility = View.VISIBLE
                        binding.dataDieselFuelAcceptance1.imeOptions = EditorInfo.IME_ACTION_NONE
                        binding.dataDieselFuelDelivery1.imeOptions = EditorInfo.IME_ACTION_NONE
                        binding.dataDieselFuelAcceptance2.imeOptions = EditorInfo.IME_ACTION_NONE
                        binding.dataDieselFuelDelivery2.imeOptions = EditorInfo.IME_ACTION_NONE
                        binding.dataDieselFuelAcceptance3.imeOptions = EditorInfo.IME_ACTION_NONE
                        binding.dataDieselFuelDelivery3.imeOptions = EditorInfo.IME_ACTION_NONE
                    }
                }
                binding.blockResultDieselFuel.visibility = View.VISIBLE
                binding.blockResultsEnergy.visibility = View.GONE
            }
            TypeOfTraction.ElectricLocomotive -> {
                when (countSection) {
                    CountSections.OneSection -> {
                        binding.blockEnergySection1.visibility = View.VISIBLE
                        binding.blockEnergySection2.visibility = View.GONE
                        binding.blockEnergySection3.visibility = View.GONE
                        binding.blockEnergySection4.visibility = View.GONE
                    }
                    CountSections.TwoSection -> {
                        binding.blockEnergySection1.visibility = View.VISIBLE
                        binding.blockEnergySection2.visibility = View.VISIBLE
                        binding.blockEnergySection3.visibility = View.GONE
                        binding.blockEnergySection4.visibility = View.GONE
                    }
                    CountSections.ThreeSection -> {
                        binding.blockEnergySection1.visibility = View.VISIBLE
                        binding.blockEnergySection2.visibility = View.VISIBLE
                        binding.blockEnergySection3.visibility = View.VISIBLE
                        binding.blockEnergySection4.visibility = View.GONE
                    }
                    CountSections.FourSection -> {
                        binding.blockEnergySection1.visibility = View.VISIBLE
                        binding.blockEnergySection2.visibility = View.VISIBLE
                        binding.blockEnergySection3.visibility = View.VISIBLE
                        binding.blockEnergySection4.visibility = View.VISIBLE
                    }
                }
                binding.blockResultDieselFuel.visibility = View.GONE
                binding.blockResultsEnergy.visibility = View.VISIBLE
            }
        }
    }

    /* Метод для определения корректности введенных данных о времени приемки локомотива*/
    private fun verificationAcceptanceDateAndTime() {
        if (!dateAcceptanceFixed) {
            setErrorBackground(
                requireContext(),
                binding.startOfAcceptance,
                ColorForBackgroundError.RED
            )
            binding.root.snack(getString(R.string.text_for_snackbar_error_empty_date_start_acceptance))
        } else setDefaultBackground(requireContext(), binding.startOfAcceptance)

        if (dateAcceptanceFixed && dateAndTimeStartAcceptance.timeInMillis >= dateAndTimeEndAcceptance.timeInMillis) {
            setErrorBackground(
                requireContext(),
                binding.endOfAcceptance,
                ColorForBackgroundError.RED
            )
            binding.root.snack(getString(R.string.text_for_snackbar_error_time_end_acceptance))
        } else setDefaultBackground(requireContext(), binding.endOfAcceptance)
    }

    /* Метод для определения корректности введенных данных о времени сдачи локомотива*/
    private fun verificationDeliveryDateAndTime() {
        if (!dateDeliveryFixed) {
            setErrorBackground(
                requireContext(),
                binding.startOfDelivery,
                ColorForBackgroundError.RED
            )
            binding.root.snack(getString(R.string.text_for_snackbar_error_empty_date_start_delivery))
        } else setDefaultBackground(requireContext(), binding.startOfDelivery)

        if (dateDeliveryFixed && dateAndTimeStartDelivery.timeInMillis >= dateAndTimeEndDelivery.timeInMillis) {
            setErrorBackground(
                requireContext(),
                binding.endOfDelivery,
                ColorForBackgroundError.RED
            )
            binding.root.snack(getString(R.string.text_for_snackbar_error_time_end_delivery))
        } else setDefaultBackground(requireContext(), binding.endOfDelivery)
    }

    /* Метод для подсчета расхода энергоресурсов и отображения на экране
    * Возвращает 0 если не заполнено одно из полей или первое поле "acceptance" больше чем второе "delivery"
    * переменная view получает значение text от результата вычислений*/
    private fun calculationOfExpense(view: TextView, acceptance: Int?, delivery: Int?): Int {
        var result = 0
        if (acceptance != null && delivery != null && acceptance < delivery) {
            result = Math.subtractExact(delivery, acceptance)
            view.text = result.toString()
        } else view.text = getString(R.string.text_for_empty_data)
        return result
    }

    /* Метод для подсчета расхода топлива и отображения на экране в литрах и килограммах
    * Возвращает 0 если не заполнено одно из полей или первое поле "acceptance" больше чем второе "delivery"
    * переменная viewForLiter получает значение text от результата вычислений в литрах
    * переменная viewForKilo получает значение text от результата вычислений в килограммах*/
    private fun calculationOfExpenseDiesel(
        viewForLiter: TextView,
        viewForKilo: TextView,
        acceptance: Int?,
        delivery: Int?
    ): Int {
        var result = 0
        if (acceptance != null && delivery != null && acceptance < delivery) {
            result = Math.subtractExact(delivery, acceptance)
            viewForLiter.text = result.toString()
            viewForKilo.text = String.format("%.2f", result * coefficient)
        } else {
            viewForLiter.text = getString(R.string.text_for_empty_data)
            viewForKilo.text = getString(R.string.text_for_empty_data)
        }
        return result
    }

    /* Метод для подсчета общего расхода электроэнергии всех секций*/
    private fun setTotalConsumptionEnergy() {
        totalConsumptionEnergy =
            secOneResultEnergy + secTwoResultEnergy + secThreeResultEnergy + secFourResultEnergy
        Log.d("Debug", "$totalConsumptionEnergy")
        if (totalConsumptionEnergy > 0) binding.dataTotalConsumption.text =
            totalConsumptionEnergy.toString()
        else binding.dataTotalConsumption.text = getString(R.string.text_for_empty_data)
    }

    /* Метод для подсчета общего расхода рекуперации всех секций*/
    private fun setTotalConsumptionRecovery() {
        totalConsumptionRecovery =
            secOneResultRecovery + secTwoResultRecovery + secThreeResultRecovery + secFourResultRecovery
        if (totalConsumptionRecovery > 0) binding.dataTotalRecovery.text =
            totalConsumptionRecovery.toString()
        else binding.dataTotalRecovery.text = getString(R.string.text_for_empty_data)
    }

    /* Метод для подсчета общего расхода топлива всех секций в литрах и килограммах*/
    private fun setTotalConsumptionDiesel() {
        // считаем общий расход в литрах
        totalConsumptionDieselLiter =
            secOneResultDieselFuelInLitres + secTwoResultDieselFuelInLitres
        +secThreeResultDieselFuelInLitres + secFourResultDieselFuelInLitres
        // считаем общий расход в килограммах
        totalConsumptionDieselKilo = totalConsumptionDieselLiter * coefficient
        // выводим общий расход на экран
        if (totalConsumptionDieselLiter > 0) {
            binding.dataTotalConsumptionDieselLiter.text =
                totalConsumptionDieselLiter.toString()
            binding.dataTotalConsumptionDieselKilo.text =
                String.format("%.2f", totalConsumptionDieselKilo)
        } else {
            binding.dataTotalConsumptionDieselLiter.text = getString(R.string.text_for_empty_data)
            binding.dataTotalConsumptionDieselKilo.text = getString(R.string.text_for_empty_data)
        }
    }

    /* Метод для перевода литров в киллограммы
    * возвращает результат вычислений в String, при невозможности вычислений возвращает R.string.text_for_empty_data*/
    private fun setDataDieselFuelInKilograms(
        coefficient: Double,
        data: Int?
    ): String {
        return if (data != null && data > 0) String.format("%.2f", data * coefficient)
        else getString(R.string.text_for_empty_data)
    }

    private fun setBackgroundAcceptance(
        acceptance: Int?,
        delivery: Int?,
        acceptanceView: TextInputLayout,
        deliveryView: TextInputLayout
    ) {
        if (acceptance != null && delivery != null) {
            if (acceptance > delivery) acceptanceView.error = "Уменьшите значение"
            else {
                acceptanceView.error = null
                deliveryView.error = null
            }
        }
    }

    private fun setBackgroundDelivery(
        acceptance: Int?,
        delivery: Int?,
        acceptanceView: TextInputLayout,
        deliveryView: TextInputLayout
    ) {
        if (acceptance != null && delivery != null) {
            if (acceptance > delivery) deliveryView.error = "Увеличьте значение"
            else {
                acceptanceView.error = null
                deliveryView.error = null
            }
        }
    }
}