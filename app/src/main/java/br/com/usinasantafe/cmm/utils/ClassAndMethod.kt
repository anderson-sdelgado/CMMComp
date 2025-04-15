package br.com.usinasantafe.cmm.utils

fun getClassAndMethod(): String {
    return Thread.currentThread().stackTrace
        .firstOrNull { element ->
            element.fileName?.endsWith(".kt") == true &&
                    element.methodName != "getClassAndMethod"
        }?.let { element ->
            val className = element.className
                .substringAfterLast('.')
                .substringBefore('$')
            val methodNameFromClass = element.className
                .substringAfter("$")
                .substringBefore('$')
            val methodName = methodNameFromClass.ifBlank { element.methodName }
            "$className.$methodName"
        } ?: "Classe.MétodoDesconhecido"
}