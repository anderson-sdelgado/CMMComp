package br.com.usinasantafe.cmm.utils

import kotlin.text.contains
import kotlin.text.endsWith
import kotlin.text.substringAfter
import kotlin.text.substringAfterLast
import kotlin.text.substringBefore

fun getClassAndMethod(): String {
    val pkg = "br.com.usinasantafe"

    val frame = Throwable().stackTrace
        .firstOrNull { it.className.contains(pkg) &&
                !it.methodName.contains("invokeSuspend") &&
                !it.methodName.contains("access") &&
                !it.methodName.contains("handleFailure") &&
                !it.methodName.contains("default") &&
                !it.methodName.contains("getClassAndMethod")
        }

    if (frame == null) {
        return "Classe.MetodoDesconhecido"
    }

    val cls = frame.className.substringAfterLast('.').substringBefore('$')
    val meth = frame.methodName.substringBefore('-')

    return "$cls.$meth"
}