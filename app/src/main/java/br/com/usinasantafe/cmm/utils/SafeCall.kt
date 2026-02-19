package br.com.usinasantafe.cmm.utils

import kotlinx.coroutines.flow.FlowCollector

suspend fun <T> call (
    context: String,
    block: suspend () -> T
): Result<T> {
    return runCatching {
        block()
    }.fold(
        onSuccess = { Result.success(it) },
        onFailure = { resultFailure(context = context, cause = it) }
    )
}

suspend fun <T> result (
    context: String,
    block: suspend () -> T
): Result<T> {
    return try {
        Result.success(block())
    } catch (e: Exception) {
        resultFailure(
            context = context,
            cause = e
        )
    }
}

suspend fun FlowCollector<UpdateStatusState>.flowCall(
    context: String,
    block: suspend () -> Unit
) {
    try {
        block()
    } catch (e: Exception) {
        val failure = failure(context, e)
        emitFailure(failure)
    }
}

suspend fun <T> tryCatch (
    context: String,
    block: suspend () -> T
): T {
    try {
        return block()
    } catch (e: Exception) {
        throw Exception(context, e)
    }
}
