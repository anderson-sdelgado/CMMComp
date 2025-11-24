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
) : Exception("$context${if (message == null) " -> Unknown Error" else if (message == "-") "" else " -> $message"}", cause)

fun failure(classAndMethod: String, error: Throwable) : String {
    return "$classAndMethod -> ${error.message} -> ${error.cause.toString()}"
}

