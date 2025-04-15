package br.com.usinasantafe.cmm.domain.errors

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