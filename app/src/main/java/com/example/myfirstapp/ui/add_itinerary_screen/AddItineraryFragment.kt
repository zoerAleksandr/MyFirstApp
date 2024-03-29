package com.example.myfirstapp.ui.add_itinerary_screen

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.app
import com.example.myfirstapp.databinding.FragmentAddItineraryBinding
import com.example.myfirstapp.domain.Controller
import com.example.myfirstapp.domain.entity.*
import com.example.myfirstapp.ui.add_loco_screen.AddLocoFragment
import com.example.myfirstapp.ui.add_passenger_screen.AddPassengerFragment
import com.example.myfirstapp.ui.add_train_screen.AddTrainFragment
import com.example.myfirstapp.utils.*
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import java.util.Calendar.*

const val KEY_PARENT_ID = "keyParentID"

class AddItineraryFragment : Fragment(R.layout.fragment_add_itinerary) {
    companion object {
        fun newInstance(): AddItineraryFragment {
            return AddItineraryFragment()
        }
    }

    private val binding: FragmentAddItineraryBinding by viewBinding()
    private val viewModel: AddItineraryViewModel by viewModel()
    private val controller by lazy { activity as Controller }
    private val locoAdapter = LocoAdapter()

    private var dateTurnout: Long = 0
    private val dateAndTimeNow by lazy { getInstance() }
    private var dateAndTimeTurnout: Calendar? = null
    private var dateAndTimeEnding: Calendar? = null
    private var restPointOfTurnover = false
    private val itineraryID: String = generateStringID()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity !is Controller) {
            throw IllegalStateException("Activity должна наследоваться от Controller")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLocoAdapter()

        viewModel.liveData.observe(viewLifecycleOwner) {
            renderListLoco(it)
        }
        viewModel.getListLoco(itineraryID)

        binding.selectorRestPointOfTurnover.apply {
            addTab(
                this.newTab().setText(getString(R.string.text_for_selector_home_rest)),
                0,
                true
            )
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

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }

        binding.btnAddLoco.setOnClickListener {
            val bundle = Bundle().apply {
                putString(KEY_PARENT_ID, itineraryID)
            }
            controller.openScreen(AddLocoFragment.newInstance(bundle))
        }

        binding.btnAddPassenger.setOnClickListener {
            val bundle = Bundle().apply {
                putString(KEY_PARENT_ID, itineraryID)
            }
            controller.openScreen(AddPassengerFragment.newInstance(bundle))
        }

        binding.btnAddTrain.setOnClickListener {
            val bundle = Bundle().apply {
                putString(KEY_PARENT_ID, itineraryID)
            }
            controller.openScreen(AddTrainFragment.newInstance(bundle))
        }

        /** Блок ввода даты и времени явки на работу */
        binding.blockTurnout.setOnClickListener {
            val timePickerTurnout =
                getTimePicker(getString(R.string.text_for_time_picker_turnout), dateAndTimeNow)
                    .also { timePickerTurnout ->
                        timePickerTurnout.addOnPositiveButtonClickListener {
                            dateAndTimeTurnout?.let { calendar ->
                                calendar.set(HOUR_OF_DAY, timePickerTurnout.hour)
                                calendar.set(MINUTE, timePickerTurnout.minute)
                            }
                            binding.apply {
                                timeTurnout.text = setTextTime(timePickerTurnout)
                                timeTurnout.alpha = 1f
                            }
                            verificationWorkTime()
                        }
                    }

            val datePickerTurnout =
                getDatePicker(getString(R.string.text_for_date_picker_turnout), null)
                    .also { datePickerTurnout ->
                        datePickerTurnout.addOnPositiveButtonClickListener { dateInMillis ->
                            dateAndTimeTurnout = getInstance().apply {
                                timeInMillis = dateInMillis
                            }
                            dateTurnout = dateInMillis
                            binding.apply {
                                dateTurnout.text = setTextDate(dateInMillis)
                                dateTurnout.alpha = 1f
                            }
                            timePickerTurnout.show(
                                requireActivity().supportFragmentManager,
                                "TIME_PICKER_TURNOUT"
                            )
                        }
                    }

            datePickerTurnout.show(requireActivity().supportFragmentManager, "DATE_PICKER_TURNOUT")
        }

        /** Блок ввода даты и времени окончания работы.
         * constraintBuilder обеспечивает невозможность ввода даты
         * ранее указанной в Блоке явки */
        binding.blockEnding.setOnClickListener {
            val timePickerEnding =
                getTimePicker(
                    getString(R.string.text_for_time_picker_ending),
                    dateAndTimeNow
                ).also { timePickerEnding ->
                    timePickerEnding.addOnPositiveButtonClickListener {
                        dateAndTimeEnding = getInstance().apply {
                            set(HOUR_OF_DAY, timePickerEnding.hour)
                            set(MINUTE, timePickerEnding.minute)
                        }
                        binding.apply {
                            timeEnding.text = setTextTime(timePickerEnding)
                            timeEnding.alpha = 1f
                        }
                        verificationWorkTime()
                    }

                }

            val constraintBuilder = CalendarConstraints.Builder().apply {
                setValidator(DateValidatorPointForward.from(dateTurnout))
            }

            val datePickerEnding =
                getDatePicker(
                    getString(R.string.text_for_date_picker_ending),
                    constraintBuilder
                ).also { datePickerEnding ->
                    datePickerEnding.addOnPositiveButtonClickListener { date ->
                        dateAndTimeEnding = getInstance().apply {
                            timeInMillis = date
                        }
                        binding.apply {
                            dataEnding.text = setTextDate(date)
                            dataEnding.alpha = 1f
                        }
                        timePickerEnding.show(
                            requireActivity().supportFragmentManager,
                            "TIME_PICKER_ENDING"
                        )
                    }

                }
            datePickerEnding.show(requireActivity().supportFragmentManager, "DATE_PICKER_ENDING")
        }
    }

    private fun renderListLoco(state: ListState) {
        when (state) {
            is ListState.Loading -> {
                //TODO
            }
            is ListState.Empty -> {
                //TODO
            }
            is ListState.Success<*> -> {
                locoAdapter.setData(state.list as List<LocomotiveData>)
            }
            is ListState.Error -> {
                //TODO
            }
        }

    }

    private fun saveItinerary(itinerary: Itinerary) {
        viewModel.saveItinerary(itinerary = itinerary)
        Toast.makeText(requireContext(), "сохранил", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        saveItinerary(getCurrentItinerary())
        super.onDestroyView()
    }

    private fun getCurrentItinerary(): Itinerary {
        return Itinerary(
            itineraryID = itineraryID,
            number = getNumber(),
            appearanceAtWork = getAppearanceAtWork(),
            endOfWork = getEndOfWork(),
            restAtThePointOfTurnover = getRestPointOfTurnover(),
            notes = getNotes()
        )
    }

    private fun getNotes(): String? {
        val value = binding.notesText.text.toString()
        return value.ifBlank { null }
    }

    private fun getRestPointOfTurnover(): Boolean {
        return restPointOfTurnover
    }

    private fun getEndOfWork(): Calendar? {
        return dateAndTimeEnding
    }

    private fun getAppearanceAtWork(): Calendar? {
        return dateAndTimeTurnout
    }

    private fun getNumber(): String? {
        val value = binding.etNumberItinerary.text.toString()
        return value.ifBlank { null }
    }

    private fun initLocoAdapter() {
        binding.locoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.locoRecyclerView.adapter = locoAdapter
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

    private fun createLocomotiveData(
        itineraryID: String,
        locomotiveDataID: String,
        listDieselFuelSectionID: ArrayList<String>,
        listElectricSectionID: ArrayList<String>,
    ) {
        viewModel.addLocomotiveData(
            LocomotiveData(
                locomotiveDataID = locomotiveDataID,
                itineraryID = itineraryID,
                series = null,
                number = null,
                typeOfTraction = TypeOfTraction.DieselLocomotive,
                countSections = CountSections.TwoSection,
                startAcceptance = null,
                endAcceptance = null,
                startDelivery = null,
                endDelivery = null,
                electricSectionList = mutableListOf(
                    ElectricSection(
                        sectionID = listElectricSectionID[0],
                        locomotiveDataId = locomotiveDataID,
                        acceptanceEnergy = null,
                        deliveryEnergy = null,
                        consumptionEnergy = null,
                        acceptanceRecovery = null,
                        deliveryRecovery = null,
                        consumptionRecovery = null
                    ),
                    ElectricSection(
                        sectionID = listElectricSectionID[1],
                        locomotiveDataId = locomotiveDataID,
                        acceptanceEnergy = null,
                        deliveryEnergy = null,
                        consumptionEnergy = null,
                        acceptanceRecovery = null,
                        deliveryRecovery = null,
                        consumptionRecovery = null
                    ),
                    ElectricSection(
                        sectionID = listElectricSectionID[2],
                        locomotiveDataId = locomotiveDataID,
                        acceptanceEnergy = null,
                        deliveryEnergy = null,
                        consumptionEnergy = null,
                        acceptanceRecovery = null,
                        deliveryRecovery = null,
                        consumptionRecovery = null
                    ),
                    ElectricSection(
                        sectionID = listElectricSectionID[3],
                        locomotiveDataId = locomotiveDataID,
                        acceptanceEnergy = null,
                        deliveryEnergy = null,
                        consumptionEnergy = null,
                        acceptanceRecovery = null,
                        deliveryRecovery = null,
                        consumptionRecovery = null
                    ),
                ),
                dieselFuelSectionList = mutableListOf(
                    DieselFuelSection(
                        sectionID = listDieselFuelSectionID[0],
                        locomotiveDataID = locomotiveDataID,
                        accepted = null,
                        delivery = null,
                        supply = null,
                        consumption = null
                    ),

                    DieselFuelSection(
                        sectionID = listDieselFuelSectionID[1],
                        locomotiveDataID = locomotiveDataID,
                        accepted = null,
                        delivery = null,
                        supply = null,
                        consumption = null
                    ),

                    DieselFuelSection(
                        sectionID = listDieselFuelSectionID[2],
                        locomotiveDataID = locomotiveDataID,
                        accepted = null,
                        delivery = null,
                        supply = null,
                        consumption = null
                    ),

                    DieselFuelSection(
                        sectionID = listDieselFuelSectionID[3],
                        locomotiveDataID = locomotiveDataID,
                        accepted = null,
                        delivery = null,
                        supply = null,
                        consumption = null
                    )
                ),
                countBrakeShoes = null,
                countExtinguishers = null
            )
        )
    }

    private fun createTrainData(trainData: TrainData) {
        viewModel.addTrainData(itineraryID, trainData)
    }

    // ОБНОВИТЬ МЕТОД СОХРАНЕНИЯ
    private fun createPassengerData(passenger: Passenger) {
//        viewModel.addPassengerData(passenger)
    }
}