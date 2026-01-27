package br.com.usinasantafe.cmm.utils

fun <T> T?.required(field: String): T =
    this ?: throw NullPointerException("$field is required")