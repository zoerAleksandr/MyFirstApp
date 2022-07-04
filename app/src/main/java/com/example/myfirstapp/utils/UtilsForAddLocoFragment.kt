package com.example.myfirstapp.utils

import com.example.myfirstapp.R

/* Метод для перевода литров в киллограммы
* возвращает результат вычислений в String, при невозможности вычислений возвращает R.string.text_for_empty_data*/
fun setDataDieselFuelInKilograms(
    coefficient: Double,
    data: Double?
): String {
    return if (data != null && data > 0) String.format("%.2f", data * coefficient)
    else ("-")
}