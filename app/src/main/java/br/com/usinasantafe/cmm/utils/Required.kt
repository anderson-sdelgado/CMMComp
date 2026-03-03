package br.com.usinasantafe.cmm.utils

import kotlin.reflect.KProperty0

fun <T> KProperty0<T?>.required(): T =
    get() ?: throw NullPointerException("$name is required")