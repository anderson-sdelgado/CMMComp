package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import javax.inject.Inject

class IHeaderMotoMecRepository @Inject constructor(
    private val headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource
): HeaderMotoMecRepository {

    override suspend fun setRegOperator(regOperator: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setRegOperator(regOperator)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IHeaderMotoMecRepository.setRegOperator",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun setIdEquip(idEquip: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setIdEquip(idEquip)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IHeaderMotoMecRepository.setIdEquip",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun setIdTurn(idTurn: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setIdTurn(idTurn)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IHeaderMotoMecRepository.setIdTurn",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun setNroOS(nroOS: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setNroOS(nroOS)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IHeaderMotoMecRepository.setNroOS",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

}