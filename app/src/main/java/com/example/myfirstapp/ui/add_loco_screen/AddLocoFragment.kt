package com.example.myfirstapp.ui.add_loco_screen

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.BlockDieselFuelBinding
import com.example.myfirstapp.databinding.FragmentAddLocoBinding
import com.example.myfirstapp.domain.entity.CountSections
import com.example.myfirstapp.domain.entity.TypeOfTraction
import com.example.myfirstapp.domain.usecase.locomotive.AddLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.*
import com.example.myfirstapp.utils.*
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*
import java.util.Calendar.getInstance
import kotlin.properties.Delegates

const val PREFERENCES = "preferences"
private const val LIST_SERIES = "LIST_SERIES"

// для Bundle
const val KEY_TYPE_OF_TRACTION = "keyTypeOfTraction"
const val KEY_COUNT_SECTIONS = "keyCountSection"
const val KEY_COEFFICIENT = "keyCoefficient"
const val KEY_PARENT_ID = "keyParentID"
const val KEY_LOCOMOTIVE_DATA_ID = "keyLocomotiveDataID"
const val KEY_LIST_DIESEL_FUEL_SECTION_ID = "keyListDieselFuelSectionID"

class AddLocoFragment : Fragment(R.layout.fragment_add_loco), KoinComponent {
    companion object {
        fun newInstance(bundle: Bundle): AddLocoFragment {
            val fragment = AddLocoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val binding: FragmentAddLocoBinding by viewBinding()
    private val getListDieselFuelSectionUseCase: GetListDieselFuelSectionUseCase by inject()
    private val getDieselFuelSectionUseCase: GetDieselFuelSectionUseCase by inject()
    private val updateAcceptedDieselFuelSectionUseCase: UpdateAcceptedDieselFuelSectionUseCase by inject()
    private val updateDeliveryDieselFuelSectionUseCase: UpdateDeliveryDieselFuelSectionUseCase by inject()
    private val updateConsumptionDieselFuelUseCase: UpdateConsumptionDieselFuelUseCase by inject()
    private val addDieselFuelSectionUseCase: AddDieselFuelSectionUseCase by inject()
    private val addLocomotiveDataUseCase: AddLocomotiveDataUseCase by inject()

    private val viewModel: AddLocoViewModel by viewModels {
        AddLocoViewModelFactory(
            locomotiveDataID = arguments?.getString(KEY_LOCOMOTIVE_DATA_ID).toString(),
            getListDieselFuelSectionUseCase,
            getDieselFuelSectionUseCase,
            updateAcceptedDieselFuelSectionUseCase,
            updateDeliveryDieselFuelSectionUseCase,
            updateConsumptionDieselFuelUseCase,
            addDieselFuelSectionUseCase,
            addLocomotiveDataUseCase
        )
    }
    private lateinit var itineraryID: String
    private lateinit var locomotiveDataID: String
    private lateinit var listDieselFuelSectionID: ArrayList<String>

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
            itineraryID = bundle.getString(KEY_PARENT_ID).toString()
            locomotiveDataID = bundle.getString(KEY_LOCOMOTIVE_DATA_ID).toString()
            listDieselFuelSectionID = bundle.getStringArrayList(KEY_LIST_DIESEL_FUEL_SECTION_ID)!!
        }
        setCountSection()
        setTypeOfTraction()

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
                    setTypeOfTraction()
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
                    setCountSection()
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
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

        resultInput(binding.includeDieselFuelSec1, CountSections.OneSection.index)
        resultInput(binding.includeDieselFuelSec2, CountSections.TwoSection.index)
        resultInput(binding.includeDieselFuelSec3, CountSections.ThreeSection.index)
        resultInput(binding.includeDieselFuelSec4, CountSections.FourSection.index)

        /** Подписался на изменеия расхода секции*/
        viewModel.getResultSecOne().observe(viewLifecycleOwner) { result ->
            renderDataDieselFuelSecOne(result)
        }
        viewModel.getResultSecTwo().observe(viewLifecycleOwner) { result ->
            renderDataDieselFuelSecTwo(result)
        }
        viewModel.getResultSecThree().observe(viewLifecycleOwner) { result ->
            renderDataDieselFuelSecThree(result)
        }
        viewModel.getResultSecFour().observe(viewLifecycleOwner) { result ->
            renderDataDieselFuelSecFour(result)
        }
        /** Подписался на изменения общего расхода*/
        viewModel.getTotalResult().observe(viewLifecycleOwner) { result ->
            binding.dataTotalConsumptionDieselLiter.text = result.toString()
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

    /** Установка значения из ViewModel после вычисления*/
    private fun renderDataDieselFuelSecOne(state: StateAddLocoDieselFuel) {
        when (state) {
            is StateAddLocoDieselFuel.EmptyData -> {
                Toast.makeText(requireContext(), "EmptyData", Toast.LENGTH_SHORT).show()
                binding.includeDieselFuelSec1.resultGroup.visibility = View.GONE
            }
            is StateAddLocoDieselFuel.Success -> {
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                binding.includeDieselFuelSec1.resultGroup.visibility = View.VISIBLE
                binding.includeDieselFuelSec1.resultDieselFuelLiter.text = state.result.toString()
                binding.includeDieselFuelSec1.resultDieselFuelKilo.text =
                    String.format("%.2f", state.result * coefficient)
            }
            is StateAddLocoDieselFuel.Error -> {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                binding.root.snack(state.message.toString())
                binding.includeDieselFuelSec1.resultGroup.visibility = View.GONE
            }
        }
    }

    private fun renderDataDieselFuelSecTwo(state: StateAddLocoDieselFuel) {
        when (state) {
            is StateAddLocoDieselFuel.EmptyData -> {
                binding.includeDieselFuelSec2.resultGroup.visibility = View.GONE
            }
            is StateAddLocoDieselFuel.Success -> {
                binding.includeDieselFuelSec2.resultGroup.visibility = View.VISIBLE
                binding.includeDieselFuelSec2.resultDieselFuelLiter.text = state.result.toString()
                binding.includeDieselFuelSec2.resultDieselFuelKilo.text =
                    String.format("%.2f", state.result * coefficient)
            }
            is StateAddLocoDieselFuel.Error -> {
                binding.root.snack(state.message.toString())
                binding.includeDieselFuelSec2.resultGroup.visibility = View.GONE
            }
        }
    }

    private fun renderDataDieselFuelSecThree(state: StateAddLocoDieselFuel) {
        when (state) {
            is StateAddLocoDieselFuel.EmptyData -> {
                binding.includeDieselFuelSec3.resultGroup.visibility = View.GONE
            }
            is StateAddLocoDieselFuel.Success -> {
                binding.includeDieselFuelSec3.resultGroup.visibility = View.VISIBLE
                binding.includeDieselFuelSec3.resultDieselFuelLiter.text = state.result.toString()
                binding.includeDieselFuelSec3.resultDieselFuelKilo.text =
                    String.format("%.2f", state.result * coefficient)
            }
            is StateAddLocoDieselFuel.Error -> {
                binding.root.snack(state.message.toString())
                binding.includeDieselFuelSec3.resultGroup.visibility = View.GONE
            }
        }
    }

    private fun renderDataDieselFuelSecFour(state: StateAddLocoDieselFuel) {
        when (state) {
            is StateAddLocoDieselFuel.EmptyData -> {
                binding.includeDieselFuelSec4.resultGroup.visibility = View.GONE
            }
            is StateAddLocoDieselFuel.Success -> {
                binding.includeDieselFuelSec4.resultGroup.visibility = View.VISIBLE
                binding.includeDieselFuelSec4.resultDieselFuelLiter.text = state.result.toString()
                binding.includeDieselFuelSec4.resultDieselFuelKilo.text =
                    String.format("%.2f", state.result * coefficient)
            }
            is StateAddLocoDieselFuel.Error -> {
                binding.root.snack(state.message.toString())
                binding.includeDieselFuelSec4.resultGroup.visibility = View.GONE
            }
        }
    }

    /** Ввод данных о количестве топлива */
    private fun resultInput(layout: BlockDieselFuelBinding, sectionIndex: Int) {
        layout.dataDieselFuelAcceptance.addTextChangedListener { data ->
            viewModel.saveAcceptedInRoom(
                locomotiveDataID,
                sectionIndex,
                listDieselFuelSectionID[sectionIndex],
                data.toString().toIntOrNull()
            )
//            viewModel.acceptedBySection(sectionIndex, data.toString().toIntOrNull())
            data?.let { inLiters ->
                layout.dataDieselFuelKiloAcceptance.text =
                    setDataDieselFuelInKilograms(coefficient, inLiters.toString().toIntOrNull())
            }
        }

        layout.dataDieselFuelDelivery.addTextChangedListener { data ->
            viewModel.saveDeliveryInRoom(
                locomotiveDataID,
                sectionIndex,
                listDieselFuelSectionID[sectionIndex],
                data.toString().toIntOrNull()
            )
//            viewModel.deliveryBySection(sectionIndex, data.toString().toIntOrNull())
            data?.let { inLiters ->
                layout.dataDieselFuelKiloDelivery.text =
                    setDataDieselFuelInKilograms(coefficient, inLiters.toString().toIntOrNull())
            }
        }
    }

    private fun setCountSection() {
        when (countSection) {
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

    private fun setTypeOfTraction() {
        when (typeLoco) {
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

    override fun onDestroyView() {
        // TODO обновить LocomotiveData в Room
        super.onDestroyView()
    }
}