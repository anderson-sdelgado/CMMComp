package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ROSAtiv
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ROSAtivRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ROSAtivRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ROSAtivRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ROSAtivRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IROSAtivRepositoryTest {

    private val rosAtivRoomDatasource = mock<ROSAtivRoomDatasource>()
    private val rosAtivRetrofitDatasource = mock<ROSAtivRetrofitDatasource>()
    private val repository = IROSAtivRepository(
        rosAtivRetrofitDatasource = rosAtivRetrofitDatasource,
        rosAtivRoomDatasource = rosAtivRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ROSAtivRoomModel(
                    idROSAtiv = 1,
                    idOS = 10,
                    idAtiv = 20
                )
            )
            val entityList = listOf(
                ROSAtiv(
                    idROSAtiv = 1,
                    idOS = 10,
                    idAtiv = 20
                )
            )
            whenever(
                rosAtivRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IROSAtivRepository.addAll -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "null"
            )
        }

    @Test
    fun `addAll - Check return true if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                ROSAtivRoomModel(
                    idROSAtiv = 1,
                    idOS = 10,
                    idAtiv = 20
                )
            )
            val entityList = listOf(
                ROSAtiv(
                    idROSAtiv = 1,
                    idOS = 10,
                    idAtiv = 20
                )
            )
            whenever(
                rosAtivRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `deleteAll - Check return failure if have error`() =
        runTest {
            whenever(
                rosAtivRoomDatasource.deleteAll()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IROSAtivRepository.deleteAll -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "null"
            )
        }

    @Test
    fun `deleteAll - Check return true if function execute successfully`() =
        runTest {
            whenever(
                rosAtivRoomDatasource.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `recoverAll - Check return failure if have error`() =
        runTest {
            whenever(
                rosAtivRetrofitDatasource.recoverAll("token")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.recoverAll("token")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IROSAtivRepository.recoverAll -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "null"
            )
        }

    @Test
    fun `recoverAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                ROSAtivRetrofitModel(
                    idROSAtiv = 1,
                    idOS = 10,
                    idAtiv = 20
                ),
                ROSAtivRetrofitModel(
                    idROSAtiv = 2,
                    idOS = 11,
                    idAtiv = 21
                )
            )
            val entityList = listOf(
                ROSAtiv(
                    idROSAtiv = 1,
                    idOS = 10,
                    idAtiv = 20
                ),
                ROSAtiv(
                    idROSAtiv = 2,
                    idOS = 11,
                    idAtiv = 21
                )
            )
            whenever(
                rosAtivRetrofitDatasource.recoverAll("token")
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.recoverAll("token")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                entityList
            )
        }

}
