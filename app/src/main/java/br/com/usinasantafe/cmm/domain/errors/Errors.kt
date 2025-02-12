package br.com.usinasantafe.cmm.domain.errors

class UsecaseException(
    message: String = "Failure Usecase",
    function: String = "",
    cause: Throwable? = null
) : Exception(
    "$message -> $function",
    cause
)

class RepositoryException(
    message: String = "Failure Repository",
    function: String = "",
    cause: Throwable? = null
) : Exception(
    "$message -> $function",
    cause
)

class DatasourceException(
    message: String = "Failure Datasource",
    function: String = "",
    cause: Throwable? = null
) : Exception(
    "$message -> $function",
    cause
)