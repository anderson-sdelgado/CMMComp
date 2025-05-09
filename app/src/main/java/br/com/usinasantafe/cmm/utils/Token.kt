package br.com.usinasantafe.cmm.utils

import com.google.common.base.Strings
import java.math.BigInteger

import java.security.MessageDigest
import java.util.Locale

fun token(
    number: Long,
    version: String,
    nroEquip: Long,
    app: String,
    idBD: Int,
): String {
    var token = "$app-$version-$number-$nroEquip-$idBD"
    val messageDigest = MessageDigest.getInstance("MD5")
    messageDigest.update(token.toByteArray(), 0, token.length)
    val bigInteger = BigInteger(1, messageDigest.digest())
    val str = bigInteger.toString(16).uppercase(Locale.getDefault())
    token = Strings.padStart(str, 32, '0')
    return "Bearer $token"
}