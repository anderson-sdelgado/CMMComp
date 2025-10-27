package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Propriedade
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.PropriedadeRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.PropriedadeRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.PropriedadeRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.PropriedadeRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IPropriedadeRepositoryTest {

    private val propriedadeRoomDatasource = mock<PropriedadeRoomDatasource>()
    private val propriedadeRetrofitDatasource = mock<PropriedadeRetrofitDatasource>()
    private val repository = IPropriedadeRepository(
        propriedadeRetrofitDatasource = propriedadeRetrofitDatasource,
        propriedadeRoomDatasource = propriedadeRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                PropriedadeRoomModel(
                    idPropriedade = 1,
                    codPropriedade = 101,
                    descrPropriedade = "FAZENDA SANTA FE"
                )
            )
            val entityList = listOf(
                Propriedade(
                    idPropriedade = 1,
                    codPropriedade = 101,
                    descrPropriedade = "FAZENDA SANTA FE"
                )
            )
            whenever(
                propriedadeRoomDatasource.addAll(roomModelList)
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
                "IPropriedadeRepository.addAll -> Unknown Error"
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
                PropriedadeRoomModel(
                    idPropriedade = 1,
                    codPropriedade = 101,
                    descrPropriedade = "FAZENDA SANTA FE"
                )
            )
            val entityList = listOf(
                Propriedade(
                    idPropriedade = 1,
                    codPropriedade = 101,
                    descrPropriedade = "FAZENDA SANTA FE"
                )
            )
            whenever(
                propriedadeRoomDatasource.addAll(roomModelList)
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
                propriedadeRoomDatasource.deleteAll()
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
                "IPropriedadeRepository.deleteAll -> Unknown Error"
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
                propriedadeRoomDatasource.deleteAll()
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
                propriedadeRetrofitDatasource.listAll("token")
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
                "IPropriedadeRepository.recoverAll -> Unknown Error"
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
                PropriedadeRetrofitModel(
                    idPropriedade = 1,
                    codPropriedade = 101,
                    descrPropriedade = "FAZENDA SANTA FE"
                ),
                PropriedadeRetrofitModel(
                    idPropriedade = 2,
                    codPropriedade = 102,
                    descrPropriedade = "FAZENDA BOA ESPERANCA"
                )
            )
            val entityList = listOf(
                Propriedade(
                    idPropriedade = 1,
                    codPropriedade = 101,
                    descrPropriedade = "FAZENDA SANTA FE"
                ),
                Propriedade(
                    idPropriedade = 2,
                    codPropriedade = 102,
                    descrPropriedade = "FAZENDA BOA ESPERANCA"
                )
            )
            whenever(
                propriedadeRetrofitDatasource.listAll("token")
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
