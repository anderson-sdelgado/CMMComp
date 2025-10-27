package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Frente
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FrenteRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FrenteRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FrenteRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.FrenteRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IFrenteRepositoryTest {

    private val frenteRoomDatasource = mock<FrenteRoomDatasource>()
    private val frenteRetrofitDatasource = mock<FrenteRetrofitDatasource>()
    private val repository = IFrenteRepository(
        frenteRetrofitDatasource = frenteRetrofitDatasource,
        frenteRoomDatasource = frenteRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                FrenteRoomModel(
                    idFrente = 1,
                    codFrente = 111,
                    descrFrente = "FRENTE 1"
                )
            )
            val entityList = listOf(
                Frente(
                    idFrente = 1,
                    codFrente = 111,
                    descrFrente = "FRENTE 1"
                )
            )
            whenever(
                frenteRoomDatasource.addAll(roomModelList)
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
                "IFrenteRepository.addAll -> Unknown Error"
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
                FrenteRoomModel(
                    idFrente = 1,
                    codFrente = 111,
                    descrFrente = "FRENTE 1"
                )
            )
            val entityList = listOf(
                Frente(
                    idFrente = 1,
                    codFrente = 111,
                    descrFrente = "FRENTE 1"
                )
            )
            whenever(
                frenteRoomDatasource.addAll(roomModelList)
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
                frenteRoomDatasource.deleteAll()
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
                "IFrenteRepository.deleteAll -> Unknown Error"
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
                frenteRoomDatasource.deleteAll()
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
                frenteRetrofitDatasource.listAll("token")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.listAll("token")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFrenteRepository.recoverAll -> Unknown Error"
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
                FrenteRetrofitModel(
                    idFrente = 1,
                    codFrente = 111,
                    descrFrente = "FRENTE 1"
                ),
                FrenteRetrofitModel(
                    idFrente = 2,
                    codFrente = 222,
                    descrFrente = "FRENTE 2"
                )
            )
            val entityList = listOf(
                Frente(
                    idFrente = 1,
                    codFrente = 111,
                    descrFrente = "FRENTE 1"
                ),
                Frente(
                    idFrente = 2,
                    codFrente = 222,
                    descrFrente = "FRENTE 2"
                )
            )
            whenever(
                frenteRetrofitDatasource.listAll("token")
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.listAll("token")
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
