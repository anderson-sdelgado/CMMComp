package br.com.usinasantafe.cmm.domain.errors

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
) : Exception(removeRepeatedCalls("$context${if (message == null) " -> Unknown Error" else if (message == "-") "" else " -> $message"}"), cause)

fun failure(classAndMethod: String, error: Throwable) : String {
    return removeRepeatedCalls("$classAndMethod -> ${error.message} -> ${error.cause.toString()}")
}

fun removeRepeatedCalls(path: String): String {
    val seen = mutableSetOf<String>()
    val result = mutableListOf<String>()

    path.split(" -> ").forEach { item ->
        if (seen.add(item)) {
            result.add(item)
        }
    }

    return result.joinToString(" -> ")
}