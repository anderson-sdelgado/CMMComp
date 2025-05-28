package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.capture
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import java.util.Date

class IHeaderMotoMecRepositoryTest {

    private val headerMotoMecSharedPreferencesDatasource = mock<HeaderMotoMecSharedPreferencesDatasource>()
    private val headerMotoMecRoomDatasource = mock<HeaderMotoMecRoomDatasource>()
    private val usecase = IHeaderMotoMecRepository(
        headerMotoMecSharedPreferencesDatasource = headerMotoMecSharedPreferencesDatasource,
        headerMotoMecRoomDatasource = headerMotoMecRoomDatasource
    )

    @Test
    fun `setRegOperator - Check return failure if have error in HeaderMotoMecSharePreferenceDatasource`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setRegOperator(12345)
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setRegOperator",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase.setRegOperator(12345)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRepository.setRegOperator -> IHeaderMotoMecSharedPreferencesDatasource.setRegOperator"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setRegOperator - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setRegOperator(12345)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase.setRegOperator(12345)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                true
            )
        }

    @Test
    fun `setIdEquip - Check return failure if have error in HeaderMotoMecSharePreferenceDatasource setIdEquip`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setIdEquip(12345)
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setIdEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase.setIdEquip(12345)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRepository.setIdEquip -> IHeaderMotoMecSharedPreferencesDatasource.setIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setIdEquip(12345)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase.setIdEquip(12345)
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
    fun `setIdTurn - Check return failure if have error in HeaderMotoMecSharePreferenceDatasource setIdTurn`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setIdTurn(1)
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setIdTurn",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase.setIdTurn(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRepository.setIdTurn -> IHeaderMotoMecSharedPreferencesDatasource.setIdTurn"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdTurn - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setIdTurn(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase.setIdTurn(1)
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
    fun `setNroOS - Check return failure if have error in IHeaderMotoMecRepository setNroOS`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setNroOS(12345)
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase.setNroOS(12345)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRepository.setNroOS -> IHeaderMotoMecSharedPreferencesDatasource.setNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setNroOS - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setNroOS(12345)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase.setNroOS(12345)
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
    fun `setIdActivity - Check return failure if have error in HeaderMotoMecSharePreferenceDatasource setIdActivity`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setIdActivity(1)
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setIdActivity",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase.setIdActivity(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRepository.setIdActivity -> IHeaderMotoMecSharedPreferencesDatasource.setIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getNroOS - Check return failure if have error in HeaderMotoMecSharedPreferencesDatasource getNroOS`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.getNroOS()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.getNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase.getNroOS()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRepository.getNroOS -> IHeaderMotoMecSharedPreferencesDatasource.getNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getNroOS - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.getNroOS()
            ).thenReturn(
                Result.success(123456)
            )
            val result = usecase.getNroOS()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                123456
            )
        }

    @Test
    fun `getIdEquip - Check return failure if have error in HeaderMotoMecSharedPreferencesDatasource getIdEquip`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.getIdEquip()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.getIdEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase.getIdEquip()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRepository.getIdEquip -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getIdEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            val result = usecase.getIdEquip()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1
            )
        }

    @Test
    fun `setMeasureInitial - Check return failure if have error in HeaderMotoMecSharedPreferencesDatasource setMeasureInitial`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setMeasureInitial(1.0)
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setMeasureInitial",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase.setMeasureInitial(1.0)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRepository.setMeasureInitial -> IHeaderMotoMecSharedPreferencesDatasource.setMeasureInitial"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setMeasureInitial - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setMeasureInitial(1.0)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase.setMeasureInitial(1.0)
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
    fun `save - Check return failure if have error in HeaderMotoMecSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.get",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase.save()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRepository.save -> IHeaderMotoMecSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `save - Check return failure if HeaderMotoMecSharedPreferencesModel have field empty`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    HeaderMotoMecSharedPreferencesModel()
                )
            )
            val result = usecase.save()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRepository.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `save - Check return failure if have error in HeaderMotoMecRoomDatasource save`() =
        runTest {
            val modelSharedPreferences = HeaderMotoMecSharedPreferencesModel(
                regOperator = 12345,
                idEquip = 1,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                measureInitial = 1.0
            )
            whenever(
                headerMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelCaptor = argumentCaptor<HeaderMotoMecRoomModel>().apply {
                whenever(
                    headerMotoMecRoomDatasource.save(
                        capture()
                    )
                ).thenReturn(
                    resultFailure(
                        context = "IHeaderMotoMecRoomDatasource.save",
                        message = "-",
                        cause = Exception()
                    )
                )
            }
            val result = usecase.save()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRepository.save -> IHeaderMotoMecRoomDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.regOperator,
                12345
            )
            assertEquals(
                model.idEquip,
                1
            )
            assertEquals(
                model.idTurn,
                1
            )
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.idActivity,
                1
            )
            assertEquals(
                model.measureInitial,
                1.0,
                0.0
            )
        }

    @Test
    fun `save - Check return correct if function execute successfully`() =
        runTest {
            val modelSharedPreferences = HeaderMotoMecSharedPreferencesModel(
                regOperator = 12345,
                idEquip = 1,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                measureInitial = 1.0
            )
            whenever(
                headerMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelCaptor = argumentCaptor<HeaderMotoMecRoomModel>().apply {
                whenever(
                    headerMotoMecRoomDatasource.save(
                        capture()
                    )
                ).thenReturn(
                    Result.success(true)
                )
            }
            val result = usecase.save()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.regOperator,
                12345
            )
            assertEquals(
                model.idEquip,
                1
            )
            assertEquals(
                model.idTurn,
                1
            )
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.idActivity,
                1
            )
            assertEquals(
                model.measureInitial,
                1.0,
                0.0
            )
        }
}