package com.example.myfirstapp.ui.add_loco_screen

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.BlockDieselFuelBinding
import com.example.myfirstapp.databinding.BlockEnergyBinding
import com.example.myfirstapp.databinding.FragmentAddLocoBinding
import com.example.myfirstapp.domain.entity.*
import com.example.myfirstapp.ui.PREFERENCE
import com.example.myfirstapp.utils.*
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import org.koin.core.component.KoinComponent
import java.util.*
import java.util.Calendar.*
import kotlin.properties.Delegates

private const val LIST_SERIES = "LIST_SERIES"

// для Bundle
//const val KEY_TYPE_OF_TRACTION = "keyTypeOfTraction"
//const val KEY_COUNT_SECTIONS = "keyCountSection"
//const val KEY_COEFFICIENT = "keyCoefficient"
const val KEY_PARENT_ID = "keyParentID"
//const val KEY_LOCOMOTIVE_DATA_ID = "keyLocomotiveDataID"
//const val KEY_LIST_DIESEL_FUEL_SECTION_ID = "keyListDieselFuelSectionID"
//const val KEY_LIST_ELECTRIC_SECTION_ID = "keyListElectricSectionID"

class AddLocoFragment : Fragment(R.layout.fragment_add_loco), KoinComponent {
    companion object {
        fun newInstance(bundle: Bundle): AddLocoFragment {
            val fragment = AddLocoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val binding: FragmentAddLocoBinding by viewBinding()

    private val viewModel: AddLocoViewModel by viewModels()

    private lateinit var itineraryID: String
    private lateinit var locomotiveDataID: String
    private lateinit var listDieselFuelSectionID: ArrayList<String>
    private lateinit var listElectricSectionID: ArrayList<String>

    private val dateAndTimeNow = getInstance()
    private var dateAndTimeStartAcceptance: Calendar? = null
    private var dateAndTimeEndAcceptance: Calendar? = null
    private var dateAndTimeStartDelivery: Calendar? = null
    private var dateAndTimeEndDelivery: Calendar? = null

    // тип тяги
    private lateinit var typeLoco: TypeOfTraction

    // количество секций
    private lateinit var countSection: CountSections

    // для настройки dataPicker
    private var dateStartAcceptance: Long = 0
    private var dateEndAcceptance: Long = 0
    private var dateStartDelivery: Long = 0

    // для проверки заполнения времени
    private var dateAcceptanceFixed = false
    private var dateDeliveryFixed = false

    // Тепловоз
    private var coefficient by Delegates.notNull<Double>()

    // инвентарь
    private var countBrakeShoes: Int = 0
    private var countExtinguishers: Int = 0

    @SuppressLint("MutatingSharedPrefs", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
//            typeLoco =
//                bundle.getParcelable(KEY_TYPE_OF_TRACTION) ?: TypeOfTraction.ElectricLocomotive
//            countSection =
//                bundle.getParcelable(KEY_COUNT_SECTIONS) ?: CountSections.TwoSection
//            coefficient = bundle.getDouble(KEY_COEFFICIENT)
            itineraryID = bundle.getString(KEY_PARENT_ID).toString()
//            locomotiveDataID = bundle.getString(KEY_LOCOMOTIVE_DATA_ID).toString()
//            listDieselFuelSectionID = bundle.getStringArrayList(KEY_LIST_DIESEL_FUEL_SECTION_ID)!!
//            listElectricSectionID = bundle.getStringArrayList(KEY_LIST_ELECTRIC_SECTION_ID)!!
        }
        countSection = CountSections.TwoSection
        typeLoco = TypeOfTraction.ElectricLocomotive
        setCountSection(countSection)
        setTypeOfTraction(typeLoco)

        binding.includeEnergySec1.subtitleBlockEnergyTextView.text =
            resources.getString(R.string.subtitle_block_energy_sec_1)
        binding.includeEnergySec2.subtitleBlockEnergyTextView.text =
            resources.getString(R.string.subtitle_block_energy_sec_2)
        binding.includeEnergySec3.subtitleBlockEnergyTextView.text =
            resources.getString(R.string.subtitle_block_energy_sec_3)
        binding.includeEnergySec4.subtitleBlockEnergyTextView.text =
            resources.getString(R.string.subtitle_block_energy_sec_4)

        binding.includeDieselFuelSec1.subtitleBlockDieselFuelTextView.text =
            resources.getString(R.string.subtitle_block_diesel_fuel_sec_1)
        binding.includeDieselFuelSec2.subtitleBlockDieselFuelTextView.text =
            resources.getString(R.string.subtitle_block_diesel_fuel_sec_2)
        binding.includeDieselFuelSec3.subtitleBlockDieselFuelTextView.text =
            resources.getString(R.string.subtitle_block_diesel_fuel_sec_3)
        binding.includeDieselFuelSec4.subtitleBlockDieselFuelTextView.text =
            resources.getString(R.string.subtitle_block_diesel_fuel_sec_4)

        // SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences(PREFERENCE, MODE_PRIVATE)
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
        binding.etSeriesLoco.addTextChangedListener {
            val data = if (it.isNullOrBlank()) {
                null
            } else {
                it.toString()
            }
        }

        binding.etNumberLoco.addTextChangedListener {
            val data = if (it.isNullOrBlank()) {
                null
            } else {
                it.toString()
            }
        }

/*Селектор выбора типа тяги*/
        binding.typeLocoTabLayout.apply {
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
                        }
                        1 -> {
                            typeLoco = TypeOfTraction.ElectricLocomotive
                        }
                    }
                    setTypeOfTraction(typeLoco)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }

/*Селектор выбора количества секций*/
        binding.countSectionTabLayout.apply {
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
                    setCountSection(countSection)
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }

/*Дата и время начала приемки локомотива*/
        binding.startOfAcceptance.setOnClickListener {
            val timePickerStartAcceptance =
                getTimePicker(
                    getString(R.string.text_for_time_picker_start_acceptance),
                    dateAndTimeNow
                ).also { timePicker ->
                    timePicker.addOnPositiveButtonClickListener {
                        dateAndTimeStartAcceptance?.let { calendar ->
                            calendar.set(HOUR_OF_DAY, timePicker.hour)
                            calendar.set(MINUTE, timePicker.minute)
                        }
                        binding.apply {
                            timeLocoStartAcceptance.text = setTextTime(timePicker)
                            timeLocoStartAcceptance.alpha = 1f
                        }
                    }
                    timePicker.addOnCancelListener {
                        // TODO Укажите время
                    }
                }

            val datePickerStartAcceptance =
                getDatePicker(getString(R.string.text_for_date_picker_start_acceptance), null)
                    .also { datePicker ->
                        datePicker.addOnPositiveButtonClickListener { dateInMillis ->
                            dateAndTimeStartAcceptance = getInstance().apply {
                                timeInMillis = dateInMillis
                            }
                            dateStartAcceptance = dateInMillis
                            binding.apply {
                                dateLocoStartAcceptance.text = setTextDate(dateInMillis)
                                dateLocoStartAcceptance.alpha = 1f
                            }
                            timePickerStartAcceptance.show(
                                requireActivity().supportFragmentManager,
                                "TIME_PICKER_TURNOUT"
                            )
                        }

                    }

            datePickerStartAcceptance.show(
                requireActivity().supportFragmentManager,
                "DATE_PICKER_TURNOUT"
            )
        }

/*Дата и время окончания приемки локомотива*/
        binding.endOfAcceptance.setOnClickListener {
            val timePickerEndAcceptance =
                getTimePicker(
                    getString(R.string.text_for_time_picker_ending),
                    dateAndTimeNow
                ).also { timePicker ->
                    timePicker.addOnPositiveButtonClickListener {
                        dateAndTimeEndAcceptance?.let { calendar ->
                            calendar.set(HOUR_OF_DAY, timePicker.hour)
                            calendar.set(MINUTE, timePicker.minute)
                        }
                        binding.apply {
                            timeLocoEndAcceptance.text = setTextTime(timePicker)
                            timeLocoEndAcceptance.alpha = 1f
                        }
                    }
                    timePicker.addOnCancelListener {
                        // TODO Укажите время
                    }
                }

            val calendarConstraints = CalendarConstraints.Builder()
                .apply {
                    setValidator(DateValidatorPointForward.from(dateStartAcceptance))
                }

            val datePickerEndAcceptance =
                getDatePicker(
                    getString(R.string.text_for_date_picker_end_acceptance),
                    calendarConstraints
                )
                    .also { datePicker ->
                        datePicker.addOnPositiveButtonClickListener { dateInMillis ->
                            dateAndTimeEndAcceptance = getInstance().apply {
                                timeInMillis = dateInMillis
                            }
                            dateEndAcceptance = dateInMillis
                            binding.apply {
                                dateLocoEndAcceptance.text = setTextDate(dateInMillis)
                                dateLocoEndAcceptance.alpha = 1f
                            }
                            timePickerEndAcceptance.show(
                                requireActivity().supportFragmentManager,
                                "TIME_PICKER_TURNOUT"
                            )
                        }

                    }
            datePickerEndAcceptance.show(
                requireActivity().supportFragmentManager,
                "DATE_PICKER_TURNOUT"
            )

        }

/*Дата и время начала сдачи локомотива*/
        binding.startOfDelivery.setOnClickListener {
            val timePicker =
                getTimePicker(
                    getString(R.string.text_for_time_picker_start_delivery),
                    dateAndTimeNow
                ).also { timePicker ->
                    timePicker.addOnPositiveButtonClickListener {
                        dateAndTimeStartDelivery?.let { calendar ->
                            calendar.set(HOUR_OF_DAY, timePicker.hour)
                            calendar.set(MINUTE, timePicker.minute)
                        }
                        binding.apply {
                            timeLocoStartDelivery.text = setTextTime(timePicker)
                            timeLocoStartDelivery.alpha = 1f
                        }
                    }
                    timePicker.addOnCancelListener {
                        // TODO Укажите время
                    }
                }
            val calendarConstraints = CalendarConstraints.Builder()
                .apply {
                    setValidator(DateValidatorPointForward.from(dateEndAcceptance))
                }

            val datePicker =
                getDatePicker(
                    getString(R.string.text_for_date_picker_start_delivery),
                    calendarConstraints
                )
                    .also { datePicker ->
                        datePicker.addOnPositiveButtonClickListener { dateInMillis ->
                            dateAndTimeStartDelivery = getInstance().apply {
                                timeInMillis = dateInMillis
                            }
                            dateStartDelivery = dateInMillis
                            binding.apply {
                                dateLocoStartDelivery.text = setTextDate(dateInMillis)
                                dateLocoStartDelivery.alpha = 1f
                            }
                            timePicker.show(
                                requireActivity().supportFragmentManager,
                                "TIME_PICKER_TURNOUT"
                            )
                        }

                    }
            datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER_TURNOUT")
        }

/*Дата и время окончания сдачи локомотива*/
        binding.endOfDelivery.setOnClickListener {
            val timePicker =
                getTimePicker(
                    getString(R.string.text_for_time_picker_ending),
                    dateAndTimeNow
                ).also { timePicker ->
                    timePicker.addOnPositiveButtonClickListener {
                        dateAndTimeEndDelivery?.let { calendar ->
                            calendar.set(HOUR_OF_DAY, timePicker.hour)
                            calendar.set(MINUTE, timePicker.minute)
                        }
                        binding.apply {
                            timeLocoEndDelivery.text = setTextTime(timePicker)
                            timeLocoEndDelivery.alpha = 1f
                        }
                    }
                    timePicker.addOnCancelListener {
                        // TODO Укажите время
                    }
                }

            val calendarConstraints = CalendarConstraints.Builder()
                .apply {
                    setValidator(DateValidatorPointForward.from(dateStartDelivery))
                }

            val datePicker =
                getDatePicker(
                    getString(R.string.text_for_date_picker_end_delivery),
                    calendarConstraints
                )
                    .also { datePicker ->
                        datePicker.addOnPositiveButtonClickListener { dateInMillis ->
                            dateAndTimeEndDelivery = getInstance().apply {
                                timeInMillis = dateInMillis
                            }
                            binding.apply {
                                dateLocoEndDelivery.text = setTextDate(dateInMillis)
                                dateLocoEndDelivery.alpha = 1f
                            }
                            timePicker.show(
                                requireActivity().supportFragmentManager,
                                "TIME_PICKER_TURNOUT"
                            )
                        }

                    }
            datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER_TURNOUT")
        }

        resultInputDieselFuel(binding.includeDieselFuelSec1, CountSections.OneSection.index)
        resultInputDieselFuel(binding.includeDieselFuelSec2, CountSections.TwoSection.index)
        resultInputDieselFuel(binding.includeDieselFuelSec3, CountSections.ThreeSection.index)
        resultInputDieselFuel(binding.includeDieselFuelSec4, CountSections.FourSection.index)

        resultInputEnergy(binding.includeEnergySec1, CountSections.OneSection.index)
        resultInputEnergy(binding.includeEnergySec2, CountSections.TwoSection.index)
        resultInputEnergy(binding.includeEnergySec3, CountSections.ThreeSection.index)
        resultInputEnergy(binding.includeEnergySec4, CountSections.FourSection.index)

        resultInputRecovery(binding.includeEnergySec1, CountSections.OneSection.index)
        resultInputRecovery(binding.includeEnergySec2, CountSections.TwoSection.index)
        resultInputRecovery(binding.includeEnergySec3, CountSections.ThreeSection.index)
        resultInputRecovery(binding.includeEnergySec4, CountSections.FourSection.index)

        /** Подписался на изменеия расхода топлива на секции*/
        viewModel.getResultDieselSecOne().observe(viewLifecycleOwner) { result ->
            renderDataDieselFuelSecOne(result)
        }
        viewModel.getResultDieselSecTwo().observe(viewLifecycleOwner) { result ->
            renderDataDieselFuelSecTwo(result)
        }
        viewModel.getResultDieselSecThree().observe(viewLifecycleOwner) { result ->
            renderDataDieselFuelSecThree(result)
        }
        viewModel.getResultDieselSecFour().observe(viewLifecycleOwner) { result ->
            renderDataDieselFuelSecFour(result)
        }
        /** Подписался на изменения общего расхода топлива*/
        viewModel.getTotalDieselResult().observe(viewLifecycleOwner) { result ->
            renderDataTotalConsumptionDieselFuel(result)
        }

        /** Подписался на изменеия расхода электроэнергии на секции*/
        viewModel.getResultEnergyElectricSecOne().observe(viewLifecycleOwner) { result ->
            renderDataEnergySecOne(result)
        }
        viewModel.getResultEnergyElectricSecTwo().observe(viewLifecycleOwner) { result ->
            renderDataEnergySecTwo(result)
        }
        viewModel.getResultEnergyElectricSecThree().observe(viewLifecycleOwner) { result ->
            renderDataEnergySecThree(result)
        }
        viewModel.getResultEnergyElectricSecFour().observe(viewLifecycleOwner) { result ->
            renderDataEnergySecFour(result)
        }
        /** Подписался на изменения общего расхода электроэнергии*/
        viewModel.getTotalEnergyElectricResult().observe(viewLifecycleOwner) { result ->
            renderDataTotalConsumptionEnergy(result)
        }

        /** Подписался на изменеия рекуперации на секции*/
        viewModel.getResultRecoveryElectricSecOne().observe(viewLifecycleOwner) { result ->
            renderDataRecoverySecOne(result)
        }
        viewModel.getResultRecoveryElectricSecTwo().observe(viewLifecycleOwner) { result ->
            renderDataRecoverySecTwo(result)
        }
        viewModel.getResultRecoveryElectricSecThree().observe(viewLifecycleOwner) { result ->
            renderDataRecoverySecThree(result)
        }
        viewModel.getResultRecoveryElectricSecFour().observe(viewLifecycleOwner) { result ->
            renderDataRecoverySecFour(result)
        }
        /** Подписался на изменения общего количества рекуперации*/
        viewModel.getTotalRecoveryElectricResult().observe(viewLifecycleOwner) { result ->
            renderDataTotalConsumptionRecovery(result)
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

    private fun renderDataTotalConsumptionDieselFuel(result: StateSection) {
        when (result) {
            is StateSection.EmptyData -> {
                binding.blockResultDieselFuelLayout.visibility = View.GONE
            }
            is StateSection.Success -> {
                binding.blockResultDieselFuelLayout.visibility = View.VISIBLE
                binding.dataTotalConsumptionDieselLiter.text = result.result.toString()
                binding.dataTotalConsumptionDieselKilo.text =
                    String.format("%.2f", result.result * coefficient)
            }
            is StateSection.Error -> {}
        }
    }

    private fun renderDataTotalConsumptionEnergy(result: StateSection) {
        when (result) {
            is StateSection.EmptyData -> {
                binding.blockResultsEnergy.visibility = View.GONE
            }
            is StateSection.Success -> {
                binding.blockResultsEnergy.visibility = View.VISIBLE
                binding.dataTotalConsumptionEnergy.text = result.result.toString()
            }
            is StateSection.Error -> {
                binding.blockResultsEnergy.visibility = View.GONE
            }
        }
    }

    // TODO сделать независимые блоки для электроэнергии и рекуперации
    private fun renderDataTotalConsumptionRecovery(result: StateSection) {
        when (result) {
            is StateSection.EmptyData -> {
                binding.blockResultsEnergy.visibility = View.GONE
            }
            is StateSection.Success -> {
                binding.blockResultsEnergy.visibility = View.VISIBLE
                binding.dataTotalRecovery.text = result.result.toString()
            }
            is StateSection.Error -> {
                binding.blockResultsEnergy.visibility = View.GONE
            }
        }
    }

    /** Установка значения расхода на секцию из ViewModel после вычисления*/
    private fun renderDataDieselFuelSecOne(state: StateSection) {
        when (state) {
            is StateSection.EmptyData -> {
                binding.includeDieselFuelSec1.resultGroup.visibility = View.GONE
            }
            is StateSection.Success -> {
                binding.includeDieselFuelSec1.resultGroup.visibility = View.VISIBLE
                binding.includeDieselFuelSec1.resultDieselFuelLiter.text = state.result.toString()
                binding.includeDieselFuelSec1.resultDieselFuelKilo.text =
                    String.format("%.2f", state.result * coefficient)
            }
            is StateSection.Error -> {
                binding.root.snack(state.message.toString())
                binding.includeDieselFuelSec1.resultGroup.visibility = View.GONE
            }
        }
    }

    private fun renderDataDieselFuelSecTwo(state: StateSection) {
        when (state) {
            is StateSection.EmptyData -> {
                binding.includeDieselFuelSec2.resultGroup.visibility = View.GONE
            }
            is StateSection.Success -> {
                binding.includeDieselFuelSec2.resultGroup.visibility = View.VISIBLE
                binding.includeDieselFuelSec2.resultDieselFuelLiter.text = state.result.toString()
                binding.includeDieselFuelSec2.resultDieselFuelKilo.text =
                    String.format("%.2f", state.result * coefficient)
            }
            is StateSection.Error -> {
                binding.root.snack(state.message.toString())
                binding.includeDieselFuelSec2.resultGroup.visibility = View.GONE
            }
        }
    }

    private fun renderDataDieselFuelSecThree(state: StateSection) {
        when (state) {
            is StateSection.EmptyData -> {
                binding.includeDieselFuelSec3.resultGroup.visibility = View.GONE
            }
            is StateSection.Success -> {
                binding.includeDieselFuelSec3.resultGroup.visibility = View.VISIBLE
                binding.includeDieselFuelSec3.resultDieselFuelLiter.text = state.result.toString()
                binding.includeDieselFuelSec3.resultDieselFuelKilo.text =
                    String.format("%.2f", state.result * coefficient)
            }
            is StateSection.Error -> {
                binding.root.snack(state.message.toString())
                binding.includeDieselFuelSec3.resultGroup.visibility = View.GONE
            }
        }
    }

    private fun renderDataDieselFuelSecFour(state: StateSection) {
        when (state) {
            is StateSection.EmptyData -> {
                binding.includeDieselFuelSec4.resultGroup.visibility = View.GONE
            }
            is StateSection.Success -> {
                binding.includeDieselFuelSec4.resultGroup.visibility = View.VISIBLE
                binding.includeDieselFuelSec4.resultDieselFuelLiter.text = state.result.toString()
                binding.includeDieselFuelSec4.resultDieselFuelKilo.text =
                    String.format("%.2f", state.result * coefficient)
            }
            is StateSection.Error -> {
                binding.root.snack(state.message.toString())
                binding.includeDieselFuelSec4.resultGroup.visibility = View.GONE
            }
        }
    }

    private fun renderDataEnergySecOne(state: StateSection) {
        when (state) {
            is StateSection.EmptyData -> {
                binding.includeEnergySec1.resultEnergyGroup.visibility = View.GONE
            }
            is StateSection.Success -> {
                binding.includeEnergySec1.resultEnergyGroup.visibility = View.VISIBLE
                binding.includeEnergySec1.resultEnergy.text = state.result.toString()
            }
            is StateSection.Error -> {
                binding.root.snack(state.message.toString())
                binding.includeEnergySec1.resultEnergyGroup.visibility = View.GONE
            }
        }
    }

    private fun renderDataEnergySecTwo(state: StateSection) {
        when (state) {
            is StateSection.EmptyData -> {
                binding.includeEnergySec2.resultEnergyGroup.visibility = View.GONE
            }
            is StateSection.Success -> {
                binding.includeEnergySec2.resultEnergyGroup.visibility = View.VISIBLE
                binding.includeEnergySec2.resultEnergy.text = state.result.toString()
            }
            is StateSection.Error -> {
                binding.root.snack(state.message.toString())
                binding.includeEnergySec2.resultEnergyGroup.visibility = View.GONE
            }
        }
    }

    private fun renderDataEnergySecThree(state: StateSection) {
        when (state) {
            is StateSection.EmptyData -> {
                binding.includeEnergySec3.resultEnergyGroup.visibility = View.GONE
            }
            is StateSection.Success -> {
                binding.includeEnergySec3.resultEnergyGroup.visibility = View.VISIBLE
                binding.includeEnergySec3.resultEnergy.text = state.result.toString()
            }
            is StateSection.Error -> {
                binding.root.snack(state.message.toString())
                binding.includeEnergySec3.resultEnergyGroup.visibility = View.GONE
            }
        }
    }

    private fun renderDataEnergySecFour(state: StateSection) {
        when (state) {
            is StateSection.EmptyData -> {
                binding.includeEnergySec4.resultEnergyGroup.visibility = View.GONE
            }
            is StateSection.Success -> {
                binding.includeEnergySec4.resultEnergyGroup.visibility = View.VISIBLE
                binding.includeEnergySec4.resultEnergy.text = state.result.toString()
            }
            is StateSection.Error -> {
                binding.root.snack(state.message.toString())
                binding.includeEnergySec4.resultEnergyGroup.visibility = View.GONE
            }
        }
    }

    private fun renderDataRecoverySecOne(state: StateSection) {
        when (state) {
            is StateSection.EmptyData -> {
                binding.includeEnergySec1.resultRecoveryGroup.visibility = View.GONE
            }
            is StateSection.Success -> {
                binding.includeEnergySec1.resultRecoveryGroup.visibility = View.VISIBLE
                binding.includeEnergySec1.resultRecovery.text = state.result.toString()
            }
            is StateSection.Error -> {
                binding.root.snack(state.message.toString())
                binding.includeEnergySec1.resultRecoveryGroup.visibility = View.GONE
            }
        }
    }

    private fun renderDataRecoverySecTwo(state: StateSection) {
        when (state) {
            is StateSection.EmptyData -> {
                binding.includeEnergySec2.resultRecoveryGroup.visibility = View.GONE
            }
            is StateSection.Success -> {
                binding.includeEnergySec2.resultRecoveryGroup.visibility = View.VISIBLE
                binding.includeEnergySec2.resultRecovery.text = state.result.toString()
            }
            is StateSection.Error -> {
                binding.root.snack(state.message.toString())
                binding.includeEnergySec2.resultRecoveryGroup.visibility = View.GONE
            }
        }
    }

    private fun renderDataRecoverySecThree(state: StateSection) {
        when (state) {
            is StateSection.EmptyData -> {
                binding.includeEnergySec3.resultRecoveryGroup.visibility = View.GONE
            }
            is StateSection.Success -> {
                binding.includeEnergySec3.resultRecoveryGroup.visibility = View.VISIBLE
                binding.includeEnergySec3.resultRecovery.text = state.result.toString()
            }
            is StateSection.Error -> {
                binding.root.snack(state.message.toString())
                binding.includeEnergySec3.resultRecoveryGroup.visibility = View.GONE
            }
        }
    }

    private fun renderDataRecoverySecFour(state: StateSection) {
        when (state) {
            is StateSection.EmptyData -> {
                binding.includeEnergySec4.resultRecoveryGroup.visibility = View.GONE
            }
            is StateSection.Success -> {
                binding.includeEnergySec4.resultRecoveryGroup.visibility = View.VISIBLE
                binding.includeEnergySec4.resultRecovery.text = state.result.toString()
            }
            is StateSection.Error -> {
                binding.root.snack(state.message.toString())
                binding.includeEnergySec4.resultRecoveryGroup.visibility = View.GONE
            }
        }
    }

    /** Ввод данных о количестве топлива */
    private fun resultInputDieselFuel(layout: BlockDieselFuelBinding, sectionIndex: Int) {
        layout.dataDieselFuelAcceptance.addTextChangedListener { data ->
            if (data.isNullOrBlank()) {
                layout.acceptanceKiloGroup.visibility = View.GONE
            } else {
                layout.acceptanceKiloGroup.visibility = View.VISIBLE
                layout.dataDieselFuelKiloAcceptance.text =
                    setDataDieselFuelInKilograms(coefficient, data.toString().toDoubleOrNull())
            }
        }

        layout.dataDieselFuelDelivery.addTextChangedListener { data ->
            if (data.isNullOrBlank()) {
                layout.deliveryKiloGroup.visibility = View.GONE
            } else {
                layout.deliveryKiloGroup.visibility = View.VISIBLE
                layout.dataDieselFuelKiloDelivery.text =
                    setDataDieselFuelInKilograms(coefficient, data.toString().toDoubleOrNull())
            }
        }
    }

    /** Ввод данных счетчиков электроэнергии */
    private fun resultInputEnergy(layout: BlockEnergyBinding, sectionIndex: Int) {
        layout.dataEnergyAcceptance1.addTextChangedListener { data -> }
        layout.dataEnergyDelivery1.addTextChangedListener { data -> }
    }


    /** Ввод данных счетчиков рекуперации */
    private fun resultInputRecovery(layout: BlockEnergyBinding, sectionIndex: Int) {
        layout.dataRecoveryAcceptance.addTextChangedListener { data ->
        }
        layout.dataRecoveryDelivery.addTextChangedListener { data ->
        }
    }

    private fun setCountSection(countSections: CountSections) {
        when (countSections) {
            CountSections.OneSection -> {
                binding.containerSec1.visibility = View.VISIBLE
                binding.containerSec2.visibility = View.GONE
                binding.containerSec3.visibility = View.GONE
                binding.containerSec4.visibility = View.GONE
            }
            CountSections.TwoSection -> {
                binding.containerSec1.visibility = View.VISIBLE
                binding.containerSec2.visibility = View.VISIBLE
                binding.containerSec3.visibility = View.GONE
                binding.containerSec4.visibility = View.GONE
            }
            CountSections.ThreeSection -> {
                binding.containerSec1.visibility = View.VISIBLE
                binding.containerSec2.visibility = View.VISIBLE
                binding.containerSec3.visibility = View.VISIBLE
                binding.containerSec4.visibility = View.GONE
            }
            CountSections.FourSection -> {
                binding.containerSec1.visibility = View.VISIBLE
                binding.containerSec2.visibility = View.VISIBLE
                binding.containerSec3.visibility = View.VISIBLE
                binding.containerSec4.visibility = View.VISIBLE
            }
        }
    }

    private fun setTypeOfTraction(typeOfTraction: TypeOfTraction) {
        when (typeOfTraction) {
            TypeOfTraction.DieselLocomotive -> {
                binding.apply {
                    includeEnergySec1.blockEnergy.visibility = View.GONE
                    includeEnergySec2.blockEnergy.visibility = View.GONE
                    includeEnergySec3.blockEnergy.visibility = View.GONE
                    includeEnergySec4.blockEnergy.visibility = View.GONE

                }
                binding.apply {
                    includeDieselFuelSec1.blockDieselFuel.visibility = View.VISIBLE
                    includeDieselFuelSec2.blockDieselFuel.visibility = View.VISIBLE
                    includeDieselFuelSec3.blockDieselFuel.visibility = View.VISIBLE
                    includeDieselFuelSec4.blockDieselFuel.visibility = View.VISIBLE
                }
            }
            TypeOfTraction.ElectricLocomotive -> {
                binding.apply {
                    includeDieselFuelSec1.blockDieselFuel.visibility = View.GONE
                    includeDieselFuelSec2.blockDieselFuel.visibility = View.GONE
                    includeDieselFuelSec3.blockDieselFuel.visibility = View.GONE
                    includeDieselFuelSec4.blockDieselFuel.visibility = View.GONE
                }
                binding.apply {
                    includeEnergySec1.blockEnergy.visibility = View.VISIBLE
                    includeEnergySec2.blockEnergy.visibility = View.VISIBLE
                    includeEnergySec3.blockEnergy.visibility = View.VISIBLE
                    includeEnergySec4.blockEnergy.visibility = View.VISIBLE
                }
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

        if (dateAcceptanceFixed && dateAndTimeStartAcceptance?.timeInMillis!! >= dateAndTimeEndAcceptance?.timeInMillis!!) {
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

        if (dateDeliveryFixed && dateAndTimeStartDelivery?.timeInMillis!! >= dateAndTimeEndDelivery?.timeInMillis!!) {
            setErrorBackground(
                requireContext(),
                binding.endOfDelivery,
                ColorForBackgroundError.RED
            )
            binding.root.snack(getString(R.string.text_for_snackbar_error_time_end_delivery))
        } else setDefaultBackground(requireContext(), binding.endOfDelivery)
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

    private fun getSeriesLocomotive(): String? {
        val value = binding.etSeriesLoco.text.toString()
        return value.ifBlank { null }
    }

    private fun getNumberLocomotive(): String? {
        val value = binding.etNumberLoco.text.toString()
        return value.ifBlank { null }
    }

    private fun getTypeOfTraction(): TypeOfTraction {
        return typeLoco
    }

    private fun getCountSection(): CountSections {
        return countSection
    }

    private fun getStartAcceptance(): Calendar? {
        return dateAndTimeStartAcceptance
    }

    private fun getEndAcceptance(): Calendar? {
        return dateAndTimeEndAcceptance
    }

    private fun getStartDelivery(): Calendar? {
        return dateAndTimeStartDelivery
    }

    private fun getEndDelivery(): Calendar? {
        return dateAndTimeEndDelivery
    }

    private fun getElectricSectionList(): MutableList<ElectricSection> {
        return mutableListOf()
    }

    private fun getDieselFuelSectionList(): MutableList<DieselFuelSection> {
        return mutableListOf()
    }

    private fun getCountBrakeShoes(): Int {
        return countBrakeShoes
    }

    private fun getCountExtinguishers(): Int {
        return countExtinguishers
    }


    private fun getCurrentLocomotiveData(): LocomotiveData {
        return LocomotiveData(
            locomotiveDataID = generateStringID(),
            itineraryID = itineraryID,
            series = getSeriesLocomotive(),
            number = getNumberLocomotive(),
            typeOfTraction = getTypeOfTraction(),
            countSections = getCountSection(),
            startAcceptance = getStartAcceptance(),
            endAcceptance = getEndAcceptance(),
            startDelivery = getStartDelivery(),
            endDelivery = getEndDelivery(),
            electricSectionList = getElectricSectionList(),
            dieselFuelSectionList = getDieselFuelSectionList(),
            countBrakeShoes = getCountBrakeShoes(),
            countExtinguishers = getCountExtinguishers()
        )
    }

    private fun saveLocomotive() {
        viewModel.addLocomotive(getCurrentLocomotiveData())
    }

    override fun onDestroyView() {
        saveLocomotive()
        super.onDestroyView()
    }
}