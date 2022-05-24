package com.example.myfirstapp.ui.add_itinerary_screen

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.app
import com.example.myfirstapp.databinding.FragmentAddItineraryBinding
import com.example.myfirstapp.domain.Controller
import com.example.myfirstapp.domain.entity.*
import com.example.myfirstapp.ui.add_loco_screen.*
import com.example.myfirstapp.ui.add_passenger_screen.AddPassangerFragment
import com.example.myfirstapp.ui.add_train_screen.AddTrainFragment
import com.example.myfirstapp.ui.add_train_screen.KEY_TRAIN_DATA_ID
import com.example.myfirstapp.ui.add_train_screen.KEY_TRAIN_DATA_PARENT_ID
import com.example.myfirstapp.utils.*
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import java.util.Calendar.*

const val ITINERARY_ID = "itineraryId"

class AddItineraryFragment : Fragment(R.layout.fragment_add_itinerary) {
    companion object {
        fun newInstance(bundle: Bundle): AddItineraryFragment {
            val fragment = AddItineraryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val binding: FragmentAddItineraryBinding by viewBinding()
    private val viewModel: AddItineraryViewModel by viewModel()
    private val controller by lazy { activity as Controller }

    private var dateTurnout: Long = 0
    private val dateAndTimeNow by lazy { getInstance() }
    private var dateAndTimeTurnout: Calendar? = null
    private var dateAndTimeEnding: Calendar? = null
    private var restPointOfTurnover = false
    private lateinit var itineraryID: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity !is Controller) {
            throw IllegalStateException("Activity должна наследоваться от Controller")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            itineraryID = bundle.getString(ITINERARY_ID).toString()
        }
/*Данный селектор выбирает тип отдыха ЛБ*/
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
            val locomotiveDataID = generateStringID()
            val listDieselFuelSectionID = arrayListOf(
                generateStringID(),
                generateStringID(),
                generateStringID(),
                generateStringID()
            )
            val listElectricSectionID = arrayListOf(
                generateStringID(),
                generateStringID(),
                generateStringID(),
                generateStringID()
            )
            createLocomotiveDataEntity(
                itineraryID,
                locomotiveDataID,
                listDieselFuelSectionID,
                listElectricSectionID
            )
            val bundle = Bundle().apply {
                putParcelable(KEY_TYPE_OF_TRACTION, TypeOfTraction.DieselLocomotive)
                putParcelable(KEY_COUNT_SECTIONS, CountSections.TwoSection)
                putDouble(KEY_COEFFICIENT, 0.83)
                putString(KEY_PARENT_ID, itineraryID)
                putString(KEY_LOCOMOTIVE_DATA_ID, locomotiveDataID)
                putStringArrayList(KEY_LIST_DIESEL_FUEL_SECTION_ID, listDieselFuelSectionID)
                putStringArrayList(KEY_LIST_ELECTRIC_SECTION_ID, listElectricSectionID)
            }
            controller.openScreen(AddLocoFragment.newInstance(bundle))
        }

        binding.btnAddPassenger.setOnClickListener {
            controller.openScreen(AddPassangerFragment())
        }

        binding.btnAddTrain.setOnClickListener {
            val trainDataId = generateStringID()
            val trainData = TrainData(
                trainDataID = trainDataId,
                itineraryID = itineraryID,
                numberOfTrain = null,
                weight = null,
                wheelAxle = null,
                conditionalLength = null,
                stations = mutableListOf()
            )
            createTrainData(trainData)
            val bundle = Bundle().apply {
                putString(KEY_TRAIN_DATA_PARENT_ID, itineraryID)
                putString(KEY_TRAIN_DATA_ID, trainDataId)
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

    override fun onDestroyView() {
        // TODO update Itinerary !! updateLocomotiveDataUseCase
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

    private fun createLocomotiveDataEntity(
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
                    ).apply {
                        viewModel.addElectricSection(this)
                    },
                    ElectricSection(
                        sectionID = listElectricSectionID[1],
                        locomotiveDataId = locomotiveDataID,
                        acceptanceEnergy = null,
                        deliveryEnergy = null,
                        consumptionEnergy = null,
                        acceptanceRecovery = null,
                        deliveryRecovery = null,
                        consumptionRecovery = null
                    ).apply {
                        viewModel.addElectricSection(this)
                    },
                    ElectricSection(
                        sectionID = listElectricSectionID[2],
                        locomotiveDataId = locomotiveDataID,
                        acceptanceEnergy = null,
                        deliveryEnergy = null,
                        consumptionEnergy = null,
                        acceptanceRecovery = null,
                        deliveryRecovery = null,
                        consumptionRecovery = null
                    ).apply {
                        viewModel.addElectricSection(this)
                    },
                    ElectricSection(
                        sectionID = listElectricSectionID[3],
                        locomotiveDataId = locomotiveDataID,
                        acceptanceEnergy = null,
                        deliveryEnergy = null,
                        consumptionEnergy = null,
                        acceptanceRecovery = null,
                        deliveryRecovery = null,
                        consumptionRecovery = null
                    ).apply {
                        viewModel.addElectricSection(this)
                    },
                ),
                dieselFuelSectionList = mutableListOf(
                    DieselFuelSection(
                        sectionID = listDieselFuelSectionID[0],
                        locomotiveDataID = locomotiveDataID,
                        accepted = null,
                        delivery = null,
                        supply = null,
                        consumption = null
                    ).apply {
                        viewModel.addDieselFuelSection(this)
                    },

                    DieselFuelSection(
                        sectionID = listDieselFuelSectionID[1],
                        locomotiveDataID = locomotiveDataID,
                        accepted = null,
                        delivery = null,
                        supply = null,
                        consumption = null
                    ).apply {
                        viewModel.addDieselFuelSection(this)
                    },

                    DieselFuelSection(
                        sectionID = listDieselFuelSectionID[2],
                        locomotiveDataID = locomotiveDataID,
                        accepted = null,
                        delivery = null,
                        supply = null,
                        consumption = null
                    ).apply {
                        viewModel.addDieselFuelSection(this)
                    },

                    DieselFuelSection(
                        sectionID = listDieselFuelSectionID[3],
                        locomotiveDataID = locomotiveDataID,
                        accepted = null,
                        delivery = null,
                        supply = null,
                        consumption = null
                    ).apply {
                        viewModel.addDieselFuelSection(this)
                    }
                ),
                countBrakeShoes = null,
                countExtinguishers = null
            )
        )
    }

    private fun createTrainData(trainData: TrainData) {
        viewModel.addTrainData(trainData)
    }
}