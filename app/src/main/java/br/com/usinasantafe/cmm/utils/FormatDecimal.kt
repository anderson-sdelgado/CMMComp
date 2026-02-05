package br.com.usinasantafe.cmm.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

fun stringToDouble(value: String): Double {
    val locale = Locale.Builder().setLanguage("pt").setRegion("BR").build()
    val formatNumber = NumberFormat.getInstance(locale)
    val number = formatNumber.parse(value)!!
    return number.toDouble()
}

fun doubleToString(value: Double): String {
    val locale = Locale.Builder().setLanguage("pt").setRegion("BR").build()
    val symbols = DecimalFormatSymbols(locale)
    val format = DecimalFormat("#,##0.0", symbols)
    return format.format(value)
}