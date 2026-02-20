package br.com.usinasantafe.cmm.utils

import br.com.usinasantafe.cmm.lib.ERROR_FIELD_EMPTY
import br.com.usinasantafe.cmm.lib.ERROR_HEADER_WITHOUT_NOTE
import br.com.usinasantafe.cmm.lib.ERROR_NEED_COUPLING_TRAILER
import br.com.usinasantafe.cmm.lib.ERROR_NEED_UNCOUPLING_TRAILER
import br.com.usinasantafe.cmm.lib.ERROR_NOTE_MECHANIC
import br.com.usinasantafe.cmm.lib.ERROR_PRE_CEC_STARTED
import br.com.usinasantafe.cmm.lib.ERROR_TIME_INVALID
import br.com.usinasantafe.cmm.lib.ERROR_WITHOUT_EXIT_MILL_PRE_CEC
import br.com.usinasantafe.cmm.lib.ERROR_WITHOUT_LOADING_COMPOSTING
import br.com.usinasantafe.cmm.lib.ERROR_WITHOUT_LOADING_INPUT
import br.com.usinasantafe.cmm.lib.ERROR_WITHOUT_NOTE
import br.com.usinasantafe.cmm.lib.ERROR_WITHOUT_NOTE_OR_LAST
import br.com.usinasantafe.cmm.lib.ERROR_WITH_FIELD_ARRIVAL_PRE_CEC
import br.com.usinasantafe.cmm.lib.Errors
import timber.log.Timber

fun resultFailure(
    context: String,
    cause: Throwable
): Result<Nothing>  {
    return resultFailure(
        context = context,
        message = cause.message,
        cause = cause.cause
    )
}

fun resultFailure(
    context: String,
    cause: Exception
): Result<Nothing>  {
    return resultFailure(
        context = context,
        message = "-",
        cause = cause
    )
}

fun resultFailure(
    context: String,
    message: String?,
    cause: Throwable? = null
): Result<Nothing>  {
    return Result.failure(
        AppError(
            context = context,
            message = message,
            cause = cause
        )
    )
}

class AppError(
    context: String,
    message: String?,
    cause: Throwable? = null
) : Exception(removeRepeatedCalls("$context${if (message == null) "" else if (message == "-") "" else " -> $message"}"), cause)

fun failure(classAndMethod: String, error: Throwable) : String {
    return removeRepeatedCalls("$classAndMethod -> ${error.message} -> ${error.cause.toString()}")
}

fun removeRepeatedCalls(path: String): String {
    val items = path.split(" -> ")
    val counts = items.groupingBy { it }.eachCount()

    return items
        .filter { counts[it] == 1 }
        .joinToString(" -> ")
}


fun <T> handleFailure(
    failure: String,
    classAndMethod: String,
    block: (String) -> T,
) {
    val fail = "$classAndMethod -> $failure"
    Timber.e(fail)
    block(fail)
}

fun <T> handleFailure(
    error: Throwable,
    classAndMethod: String,
    block: (String) -> T,
) {
    val failure = "${error.message} -> ${error.cause.toString()}"
    handleFailure(failure, classAndMethod, block)
}

fun Result<*>.onFailureHandled(
    classAndMethod: String,
    block: (String) -> Unit
) {
    onFailure { error ->
        handleFailure(error, classAndMethod, block)
    }
}

inline fun handleFailure(
    classAndMethod: String,
    error: Errors = Errors.INVALID,
    crossinline onError: (String, Errors) -> Unit,
    failure: String = ""
) {
    val failure = failure.ifEmpty { failure(error) }
    handleFailure(failure, classAndMethod) {
        onError(it, error)
    }
}

inline fun handleFailure(
    classAndMethod: String,
    failure: String,
    crossinline onError: (String, Errors) -> Unit,
) {
    handleFailure(failure, classAndMethod) {
        onError(it, Errors.INVALID)
    }
}


fun failure(error: Errors): String {
    return when(error){
        Errors.FIELD_EMPTY -> ERROR_FIELD_EMPTY
        Errors.HEADER_EMPTY -> ERROR_HEADER_WITHOUT_NOTE
        Errors.NOTE_MECHANICAL_OPEN -> ERROR_NOTE_MECHANIC
        Errors.WITHOUT_NOTE_TRANSHIPMENT -> ERROR_WITHOUT_NOTE_OR_LAST
        Errors.TIME_INVALID_TRANSHIPMENT -> ERROR_TIME_INVALID
        Errors.LAST_NOTE_WORK -> ERROR_WITHOUT_NOTE
        Errors.PRE_CEC_STARTED -> ERROR_PRE_CEC_STARTED
        Errors.WITHOUT_EXIT_MILL_PRE_CEC -> ERROR_WITHOUT_EXIT_MILL_PRE_CEC
        Errors.WITH_FIELD_ARRIVAL_PRE_CEC -> ERROR_WITH_FIELD_ARRIVAL_PRE_CEC
        Errors.WITHOUT_LOADING_INPUT -> ERROR_WITHOUT_LOADING_INPUT
        Errors.WITHOUT_LOADING_COMPOSTING -> ERROR_WITHOUT_LOADING_COMPOSTING
        Errors.NEED_UNCOUPLING_TRAILER -> ERROR_NEED_UNCOUPLING_TRAILER
        Errors.NEED_COUPLING_TRAILER -> ERROR_NEED_COUPLING_TRAILER
        Errors.WITHOUT_NOTE -> ERROR_WITHOUT_NOTE
        else -> ""
    }
}