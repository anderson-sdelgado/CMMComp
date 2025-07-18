package br.com.usinasantafe.cmm.utils

fun getClassAndMethodViewModel(): String {
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
            val method = if (methodName == "invoke") "" else ".$methodName"
            "$className$method"
        } ?: "Classe.MétodoDesconhecido"
}

fun getClassAndMethod(): String {
    return Thread.currentThread().stackTrace
        .firstOrNull { element ->
            element.fileName?.endsWith(".kt") == true &&
                    element.methodName != "getClassAndMethod"
        }?.let { element ->
            val className = element.className
                .substringAfterLast('.')
                .substringBefore('$')

            val methodName = element.methodName.substringBefore('-')
            val method = if ((methodName == "invoke") || (methodName == "invokeSuspend")) "" else ".$methodName"
            "$className$method"
        } ?: "Classe.MétodoDesconhecido"
}