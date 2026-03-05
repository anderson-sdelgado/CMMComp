package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderMotoMec
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemDefaultScreenModel
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IListHeaderSecTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val usecase = IListHeaderSec(
        motoMecRepository = motoMecRepository,
        equipRepository = equipRepository
    )

    @Test
    fun `Check return failure if have error in EquipRepository getIdEquipMain`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getIdEquipMain",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListHeaderSec -> IEquipRepository.getIdEquipMain"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdHeaderByIdEquipAndOpen`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdHeaderByIdEquipAndOpen(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdHeaderByIdEquipAndOpen",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListHeaderSec -> IMotoMecRepository.getIdHeaderByIdEquipAndOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository listHeaderSec`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdHeaderByIdEquipAndOpen(1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.listHeaderSec(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.listHeaderSec",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListHeaderSec -> IMotoMecRepository.listHeaderSec"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return empty list if not have data in header secondary`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdHeaderByIdEquipAndOpen(1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.listHeaderSec(1)
            ).thenReturn(
                Result.success(emptyList())
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList()
            )
        }

    @Test
    fun `Check return failure if idEquip is null in HeaderMotoMec`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdHeaderByIdEquipAndOpen(1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.listHeaderSec(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        HeaderMotoMec()
                    )
                )
            )
            whenever(
                equipRepository.getNroById(1)
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getNroById",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListHeaderSec -> idEquip is required"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "null"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getNroById`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdHeaderByIdEquipAndOpen(1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.listHeaderSec(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        HeaderMotoMec(
                            idEquip = 1
                        )
                    )
                )
            )
            whenever(
                equipRepository.getNroById(1)
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getNroById",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListHeaderSec -> IEquipRepository.getNroById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if id is null in HeaderMotoMec`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdHeaderByIdEquipAndOpen(1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.listHeaderSec(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        HeaderMotoMec(
                            idEquip = 1
                        )
                    )
                )
            )
            whenever(
                equipRepository.getNroById(1)
            ).thenReturn(
                Result.success(2200)
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListHeaderSec -> id is required"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "null"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdHeaderByIdEquipAndOpen(1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.listHeaderSec(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        HeaderMotoMec(
                            id = 1,
                            idEquip = 1
                        )
                    )
                )
            )
            whenever(
                equipRepository.getNroById(1)
            ).thenReturn(
                Result.success(2200)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    ItemDefaultScreenModel(
                        id = 1,
                        descr = "2200"
                    )
                )
            )
        }

}