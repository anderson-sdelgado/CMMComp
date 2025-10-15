package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.MotoMecRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.NoteMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.NoteMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderMotoMecRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.NoteMotoMecRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.roomModelToRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.NoteMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.TypeEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever
import java.util.Date

class IMotoMecRepositoryTest {

    private val headerMotoMecSharedPreferencesDatasource = mock<HeaderMotoMecSharedPreferencesDatasource>()
    private val headerMotoMecRoomDatasource = mock<HeaderMotoMecRoomDatasource>()
    private val noteMotoMecSharedPreferencesDatasource = mock<NoteMotoMecSharedPreferencesDatasource>()
    private val noteMotoMecRoomDatasource = mock<NoteMotoMecRoomDatasource>()
    private val motoMecRetrofitDatasource = mock<MotoMecRetrofitDatasource>()
    private val repository = IMotoMecRepository(
        headerMotoMecSharedPreferencesDatasource = headerMotoMecSharedPreferencesDatasource,
        headerMotoMecRoomDatasource = headerMotoMecRoomDatasource,
        noteMotoMecSharedPreferencesDatasource = noteMotoMecSharedPreferencesDatasource,
        noteMotoMecRoomDatasource = noteMotoMecRoomDatasource,
        motoMecRetrofitDatasource = motoMecRetrofitDatasource
    )

    @Test
    fun `setRegOperator - Check return failure if have error in HeaderMotoMecSharePreferenceDatasource clean`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.clean()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.clean",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.setRegOperatorHeader(12345)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setRegOperatorHeader -> IHeaderMotoMecSharedPreferencesDatasource.clean"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setRegOperator - Check return failure if have error in HeaderMotoMecSharePreferenceDatasource setRegOperator`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerMotoMecSharedPreferencesDatasource.setRegOperator(12345)
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setRegOperator",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.setRegOperatorHeader(12345)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setRegOperatorHeader -> IHeaderMotoMecSharedPreferencesDatasource.setRegOperator"
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
                headerMotoMecSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerMotoMecSharedPreferencesDatasource.setRegOperator(12345)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setRegOperatorHeader(12345)
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
                headerMotoMecSharedPreferencesDatasource.setDataEquip(
                    idEquip = 12345,
                    typeEquip = TypeEquip.NORMAL
                )
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setIdEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.setDataEquipHeader(
                idEquip = 12345,
                typeEquip = TypeEquip.NORMAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setDataEquipHeader -> IHeaderMotoMecSharedPreferencesDatasource.setIdEquip"
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
                headerMotoMecSharedPreferencesDatasource.setDataEquip(
                    idEquip = 12345,
                    typeEquip = TypeEquip.NORMAL
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setDataEquipHeader(
                idEquip = 12345,
                typeEquip = TypeEquip.NORMAL
            )
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
            val result = repository.setIdTurnHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setIdTurnHeader -> IHeaderMotoMecSharedPreferencesDatasource.setIdTurn"
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
            val result = repository.setIdTurnHeader(1)
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
            val result = repository.setNroOSHeader(12345)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setNroOSHeader -> IHeaderMotoMecSharedPreferencesDatasource.setNroOS"
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
            val result = repository.setNroOSHeader(12345)
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
            val result = repository.setIdActivityHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setIdActivityHeader -> IHeaderMotoMecSharedPreferencesDatasource.setIdActivity"
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
            val result = repository.getNroOSHeader()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.getNroOSHeader -> IHeaderMotoMecSharedPreferencesDatasource.getNroOS"
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
            val result = repository.getNroOSHeader()
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
            val result = repository.getIdEquipHeader()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.getIdEquipHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip"
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
            val result = repository.getIdEquipHeader()
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
    fun `setHourMeterInitial - Check return failure if have error in HeaderMotoMecSharedPreferencesDatasource setHourMeterInitial`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setHourMeter(1.0)
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setHourMeterInitial",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.setHourMeterInitialHeader(1.0)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setHourMeterInitialHeader -> IHeaderMotoMecSharedPreferencesDatasource.setHourMeterInitial"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setHourMeterInitial - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setHourMeter(1.0)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setHourMeterInitialHeader(1.0)
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
    fun `saveHeader - Check return failure if have error in HeaderMotoMecSharedPreferencesDatasource get`() =
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
            val result = repository.saveHeader()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.saveHeader -> IHeaderMotoMecSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveHeader - Check return failure if HeaderMotoMecSharedPreferencesModel have field empty`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    HeaderMotoMecSharedPreferencesModel()
                )
            )
            val result = repository.saveHeader()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.saveHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `saveHeader - Check return failure if have error in HeaderMotoMecRoomDatasource save`() =
        runTest {
            val modelSharedPreferences = HeaderMotoMecSharedPreferencesModel(
                regOperator = 12345,
                idEquip = 1,
                typeEquip = TypeEquip.NORMAL,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeter = 1.0
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
            val result = repository.saveHeader()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.saveHeader -> IHeaderMotoMecRoomDatasource.save"
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
                model.hourMeterInitial,
                1.0,
                0.0
            )
        }

    @Test
    fun `saveHeader - Check return correct if function execute successfully`() =
        runTest {
            val modelSharedPreferences = HeaderMotoMecSharedPreferencesModel(
                regOperator = 12345,
                idEquip = 1,
                typeEquip = TypeEquip.NORMAL,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeter = 1.0
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
            val result = repository.saveHeader()
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
                model.hourMeterInitial,
                1.0,
                0.0
            )
        }

    @Test
    fun `checkHeaderOpen - Check return failure if have error in HeaderMotoMecRoomDatasource checkHeaderOpen`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.checkHeaderOpen()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRoomDatasource.checkHeaderOpen",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.checkHeaderOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.checkHeaderOpen -> IHeaderMotoMecRoomDatasource.checkHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `checkHeaderOpen - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.checkHeaderOpen()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkHeaderOpen()
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
    fun `getIdByHeaderOpen - Check return failure if have error in HeaderMotoMecRoomDatasource getIdByHeaderOpen`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.getIdByHeaderOpen()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRoomDatasource.getIdByHeaderOpen",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.getIdByHeaderOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecRoomDatasource.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getIdByHeaderOpen - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getIdByHeaderOpen()
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
    fun `seHourMeterFinish - Check return failure if have error in HeaderMotoMecRoomDatasource setMeasureFinish`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.setHourMeterFinish(1.0)
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRoomDatasource.setHourMeterFinish",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.setHourMeterFinishHeader(1.0)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setHourMeterFinishHeader -> IHeaderMotoMecRoomDatasource.setHourMeterFinish"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setHourMeterFinish - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.setHourMeterFinish(1.0)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setHourMeterFinishHeader(1.0)
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
    fun `close - Check return failure if have error in HeaderMotoMecRoomDatasource close`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.finish()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRoomDatasource.close",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.finishHeader()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.finishHeader -> IHeaderMotoMecRoomDatasource.close"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `close - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.finish()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.finishHeader()
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
    fun `checkHeaderSend - Check return failure if have error in HeaderMotoMecRoomDatasource checkHeaderSend`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.checkHeaderSend()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRoomDatasource.checkHeaderSend",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.checkHeaderSend()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.checkHeaderSend -> IHeaderMotoMecRoomDatasource.checkHeaderSend"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `checkHeaderSend - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.checkHeaderSend()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkHeaderSend()
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
    fun `setNroOSNote - Check return failure if have error in NoteMotoMecSharedPreferencesDatasource clean`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.clean()
            ).thenReturn(
                resultFailure(
                    context = "INoteMotoMecSharedPreferencesDatasource.clean",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.setNroOSNote(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setNroOSNote -> INoteMotoMecSharedPreferencesDatasource.clean"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setNroOSNote - Check return failure if have error in HeaderMotoMecRoomDatasource getStatusConByHeaderOpen`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerMotoMecRoomDatasource.getStatusConByHeaderOpen()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRoomDatasource.getStatusConByHeaderOpen",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.setNroOSNote(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setNroOSNote -> IHeaderMotoMecRoomDatasource.getStatusConByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }
    
    @Test
    fun `setNroOSNote - Check return failure if have error in NoteMotoMecSharedPreferencesDatasource setNroOS`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerMotoMecRoomDatasource.getStatusConByHeaderOpen()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                noteMotoMecSharedPreferencesDatasource.setNroOSAndStatusCon(
                    nroOS = 1,
                    statusCon = true
                )
            ).thenReturn(
                resultFailure(
                    context = "INoteMotoMecSharedPreferencesDatasource.setNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.setNroOSNote(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setNroOSNote -> INoteMotoMecSharedPreferencesDatasource.setNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setNroOSNote - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerMotoMecRoomDatasource.getStatusConByHeaderOpen()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                noteMotoMecSharedPreferencesDatasource.setNroOSAndStatusCon(
                    nroOS = 1,
                    statusCon = true
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setNroOSNote(1)
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
    fun `setIdActivity- Check return failure if have error in NoteMotoMecSharedPreferencesDatasource setIdActivity`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.setIdActivity(1)
            ).thenReturn(
                resultFailure(
                    context = "INoteMotoMecSharedPreferencesDatasource.setIdActivity",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.setIdActivityNote(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setIdActivityNote -> INoteMotoMecSharedPreferencesDatasource.setIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdActivity - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.setIdActivity(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setIdActivityNote(1)
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
    fun `saveNote - Check return failure if have error in NoteMotoMecSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                resultFailure(
                    context = "INoteMotoMecSharedPreferencesDatasource.get",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.saveNote(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.saveNote -> INoteMotoMecSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveNote - Check return failure if NoteMotoMecSharedPreferencesModel have field empty`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    NoteMotoMecSharedPreferencesModel()
                )
            )
            val result = repository.saveNote(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.saveNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `saveNote - Check return failure if have error in NoteMotoMecRoomDatasource save`() =
        runTest {
            val modelSharedPreferences = NoteMotoMecSharedPreferencesModel(
                nroOS = 123456,
                idActivity = 1,
                statusCon = true
            )
            whenever(
                noteMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelCaptor = argumentCaptor<NoteMotoMecRoomModel>().apply {
                whenever(
                    noteMotoMecRoomDatasource.save(capture())
                ).thenReturn(
                    resultFailure(
                        context = "INoteMotoMecRoomDatasource.save",
                        message = "-",
                        cause = Exception()
                    )
                )
            }
            val result = repository.saveNote(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.saveNote -> INoteMotoMecRoomDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.idActivity,
                1
            )
            assertEquals(
                model.idHeader,
                1
            )
        }

    @Test
    fun `saveNote - Check return failure if have error in HeaderMotoMecRoomDatasource setSendHeader`() =
        runTest {
            val modelSharedPreferences = NoteMotoMecSharedPreferencesModel(
                nroOS = 123456,
                idActivity = 1,
                statusCon = true
            )
            whenever(
                noteMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelCaptor = argumentCaptor<NoteMotoMecRoomModel>().apply {
                whenever(
                    noteMotoMecRoomDatasource.save(capture())
                ).thenReturn(
                    Result.success(true)
                )
            }
            whenever(
                headerMotoMecRoomDatasource.setSendHeader(1)
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRoomDatasource.setSendHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.saveNote(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.saveNote -> IHeaderMotoMecRoomDatasource.setSendHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.idActivity,
                1
            )
            assertEquals(
                model.idHeader,
                1
            )
        }

    @Test
    fun `saveNote - Check return correct if function execute successfully`() =
        runTest {
            val modelSharedPreferences = NoteMotoMecSharedPreferencesModel(
                nroOS = 123456,
                idActivity = 1,
                statusCon = true
            )
            whenever(
                noteMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelCaptor = argumentCaptor<NoteMotoMecRoomModel>().apply {
                whenever(
                    noteMotoMecRoomDatasource.save(capture())
                ).thenReturn(
                    Result.success(true)
                )
            }
            whenever(
                headerMotoMecRoomDatasource.setSendHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.saveNote(1)
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
                model.nroOS,
                123456
            )
            assertEquals(
                model.idActivity,
                1
            )
            assertEquals(
                model.idHeader,
                1
            )
        }

    @Test
    fun `getIdActivity - Check return failure if have error in INoteMotoMecSharedPreferencesDatasource getIdActivity`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.getIdActivity()
            ).thenReturn(
                resultFailure(
                    context = "INoteMotoMecSharedPreferencesDatasource.getIdActivity",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.getIdActivity()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.getIdActivity -> INoteMotoMecSharedPreferencesDatasource.getIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getIdActivity - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.getIdActivity()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getIdActivity()
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
    fun `setIdStop - Check return failure if have error in NoteMotoMecSharedPreferencesDatasource setIdStop`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.setIdStop(1)
            ).thenReturn(
                resultFailure(
                    context = "INoteMotoMecSharedPreferencesDatasource.setIdStop",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.setIdStop(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setIdStop -> INoteMotoMecSharedPreferencesDatasource.setIdStop"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdStop - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.setIdStop(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setIdStop(1)
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
    fun `checkHasByIdHeader - Check return failure if have error in noteMotoMecRoomDatasource checkHasNoteById`() =
        runTest {
            whenever(
                noteMotoMecRoomDatasource.checkHasByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    context = "INoteMotoMecRoomDatasource.checkHasByIdHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.checkNoteHasByIdHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.checkNoteHasByIdHeader -> INoteMotoMecRoomDatasource.checkHasByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `checkHasByIdHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                noteMotoMecRoomDatasource.checkHasByIdHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkNoteHasByIdHeader(1)
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
    fun `setStatusConHeader - Check return failure if have error in HeaderMotoMecSharedPreferencesDatasource setStatusConHeader`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setStatusCon(true)
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setStatusConHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.setStatusConHeader(true)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setStatusConHeader -> IHeaderMotoMecSharedPreferencesDatasource.setStatusConHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setStatusConHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setStatusCon(true)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setStatusConHeader(true)
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
    fun `send - Check return failure if have error in HeaderMotoMecRoomDatasource listHeaderSend`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.listHeaderSend()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRoomDatasource.listHeaderSend",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.send(
                number = 16997417840,
                token = "TOKEN"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.send -> IHeaderMotoMecRoomDatasource.listHeaderSend"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return failure if have error in NoteMotoMecRoomDatasource listByIdHeaderAndSend`() =
        runTest {
            val modelRoomList = listOf(
                HeaderMotoMecRoomModel(
                    id = 1,
                    regOperator = 19759,
                    idEquip = 10,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 100000.0,
                    dateHourInitial = Date(1750422691),
                    statusCon = true,
                    statusSend = StatusSend.SEND,
                    status = Status.OPEN
                )
            )
            whenever(
                headerMotoMecRoomDatasource.listHeaderSend()
            ).thenReturn(
                Result.success(modelRoomList)
            )
            whenever(
                noteMotoMecRoomDatasource.listByIdHeaderAndSend(1)
            ).thenReturn(
                resultFailure(
                    context = "INoteMotoMecRoomDatasource.listByIdHeaderAndSend",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.send(
                number = 16997417840,
                token = "TOKEN"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.send -> INoteMotoMecRoomDatasource.listByIdHeaderAndSend"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return failure if have error in MotoMecRetrofitDatasource send`() =
        runTest {
            val headerModelRoomList = listOf(
                HeaderMotoMecRoomModel(
                    id = 1,
                    regOperator = 19759,
                    idEquip = 10,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 100000.0,
                    dateHourInitial = Date(1750422691),
                    statusCon = true,
                    statusSend = StatusSend.SEND,
                    status = Status.OPEN
                )
            )
            val noteModelRoomList = listOf(
                NoteMotoMecRoomModel(
                    id = 1,
                    idHeader = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    idStop = null,
                    dateHour = Date(1750422691),
                    statusSend = StatusSend.SEND,
                    status = Status.CLOSE,
                    statusCon = true
                )
            )
            val modelRetrofitList =
                headerModelRoomList.map {
                    it.roomModelToRetrofitModel(
                        number = 16997417840,
                        noteMotoMecList = noteModelRoomList.map { note -> note.roomModelToRetrofitModel() }
                    )
                }
            whenever(
                headerMotoMecRoomDatasource.listHeaderSend()
            ).thenReturn(
                Result.success(headerModelRoomList)
            )
            whenever(
                noteMotoMecRoomDatasource.listByIdHeaderAndSend(1)
            ).thenReturn(
                Result.success(noteModelRoomList)
            )
            whenever(
                motoMecRetrofitDatasource.send(
                    token = "TOKEN",
                    modelList = modelRetrofitList
                )
            ).thenReturn(
                resultFailure(
                    context = "IMotoMecRetrofitDatasource.send",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.send(
                number = 16997417840,
                token = "TOKEN"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.send -> IMotoMecRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return failure if have error in NoteMotoMecRoomDatasource setSentNote`() =
        runTest {
            val headerModelRoomList = listOf(
                HeaderMotoMecRoomModel(
                    id = 1,
                    regOperator = 19759,
                    idEquip = 10,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 100000.0,
                    dateHourInitial = Date(1750422691),
                    statusCon = true,
                    statusSend = StatusSend.SEND,
                    status = Status.OPEN
                )
            )
            val noteModelRoomList = listOf(
                NoteMotoMecRoomModel(
                    id = 1,
                    idHeader = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    idStop = null,
                    dateHour = Date(1750422691),
                    statusSend = StatusSend.SEND,
                    status = Status.CLOSE,
                    statusCon = true
                )
            )
            val modelRetrofitList =
                headerModelRoomList.map {
                    it.roomModelToRetrofitModel(
                        number = 16997417840,
                        noteMotoMecList = noteModelRoomList.map { note -> note.roomModelToRetrofitModel() }
                    )
                }
            val headerMotoMecRetrofitModelInputList = listOf(
                HeaderMotoMecRetrofitModelInput(
                    id = 1,
                    idBD = 1,
                    noteMotoMecList = listOf(
                        NoteMotoMecRetrofitModelInput(
                            id = 1,
                            idBD = 1,
                        )
                    )
                )
            )
            whenever(
                headerMotoMecRoomDatasource.listHeaderSend()
            ).thenReturn(
                Result.success(headerModelRoomList)
            )
            whenever(
                noteMotoMecRoomDatasource.listByIdHeaderAndSend(1)
            ).thenReturn(
                Result.success(noteModelRoomList)
            )
            whenever(
                motoMecRetrofitDatasource.send(
                    token = "TOKEN",
                    modelList = modelRetrofitList
                )
            ).thenReturn(
                Result.success(headerMotoMecRetrofitModelInputList)
            )
            whenever(
                noteMotoMecRoomDatasource.setSentNote(
                    id = 1,
                    idBD = 1
                )
            ).thenReturn(
                resultFailure(
                    context = "INoteMotoMecRoomDatasource.setSentNote",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.send(
                number = 16997417840,
                token = "TOKEN"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.send -> INoteMotoMecRoomDatasource.setSentNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return failure if have error in HeaderMotoMecRoomDatasource setSentHeader`() =
        runTest {
            val headerModelRoomList = listOf(
                HeaderMotoMecRoomModel(
                    id = 1,
                    regOperator = 19759,
                    idEquip = 10,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 100000.0,
                    dateHourInitial = Date(1750422691),
                    statusCon = true,
                    statusSend = StatusSend.SEND,
                    status = Status.OPEN
                )
            )
            val noteModelRoomList = listOf(
                NoteMotoMecRoomModel(
                    id = 1,
                    idHeader = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    idStop = null,
                    dateHour = Date(1750422691),
                    statusSend = StatusSend.SEND,
                    status = Status.CLOSE,
                    statusCon = true
                )
            )
            val modelRetrofitList =
                headerModelRoomList.map {
                    it.roomModelToRetrofitModel(
                        number = 16997417840,
                        noteMotoMecList = noteModelRoomList.map { note -> note.roomModelToRetrofitModel() }
                    )
                }
            val headerMotoMecRetrofitModelInputList = listOf(
                HeaderMotoMecRetrofitModelInput(
                    id = 1,
                    idBD = 1,
                    noteMotoMecList = listOf(
                        NoteMotoMecRetrofitModelInput(
                            id = 1,
                            idBD = 1,
                        )
                    )
                )
            )
            whenever(
                headerMotoMecRoomDatasource.listHeaderSend()
            ).thenReturn(
                Result.success(headerModelRoomList)
            )
            whenever(
                noteMotoMecRoomDatasource.listByIdHeaderAndSend(1)
            ).thenReturn(
                Result.success(noteModelRoomList)
            )
            whenever(
                motoMecRetrofitDatasource.send(
                    token = "TOKEN",
                    modelList = modelRetrofitList
                )
            ).thenReturn(
                Result.success(headerMotoMecRetrofitModelInputList)
            )
            whenever(
                noteMotoMecRoomDatasource.setSentNote(
                    id = 1,
                    idBD = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerMotoMecRoomDatasource.setSentHeader(
                    id = 1,
                    idBD = 1
                )
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRoomDatasource.setSentHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.send(
                number = 16997417840,
                token = "TOKEN"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.send -> IHeaderMotoMecRoomDatasource.setSentHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return correct if function execute successfully`() =
        runTest {
            val headerModelRoomList = listOf(
                HeaderMotoMecRoomModel(
                    id = 1,
                    regOperator = 19759,
                    idEquip = 10,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 100000.0,
                    dateHourInitial = Date(1750422691),
                    statusCon = true,
                    statusSend = StatusSend.SEND,
                    status = Status.OPEN
                )
            )
            val noteModelRoomList = listOf(
                NoteMotoMecRoomModel(
                    id = 1,
                    idHeader = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    idStop = null,
                    dateHour = Date(1750422691),
                    statusSend = StatusSend.SEND,
                    status = Status.CLOSE,
                    statusCon = true
                )
            )
            val modelRetrofitList =
                headerModelRoomList.map {
                    it.roomModelToRetrofitModel(
                        number = 16997417840,
                        noteMotoMecList = noteModelRoomList.map { note -> note.roomModelToRetrofitModel() }
                    )
                }
            val headerMotoMecRetrofitModelInputList = listOf(
                HeaderMotoMecRetrofitModelInput(
                    id = 1,
                    idBD = 1,
                    noteMotoMecList = listOf(
                        NoteMotoMecRetrofitModelInput(
                            id = 1,
                            idBD = 1,
                        )
                    )
                )
            )
            whenever(
                headerMotoMecRoomDatasource.listHeaderSend()
            ).thenReturn(
                Result.success(headerModelRoomList)
            )
            whenever(
                noteMotoMecRoomDatasource.listByIdHeaderAndSend(1)
            ).thenReturn(
                Result.success(noteModelRoomList)
            )
            whenever(
                motoMecRetrofitDatasource.send(
                    token = "TOKEN",
                    modelList = modelRetrofitList
                )
            ).thenReturn(
                Result.success(headerMotoMecRetrofitModelInputList)
            )
            whenever(
                noteMotoMecRoomDatasource.setSentNote(
                    id = 1,
                    idBD = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerMotoMecRoomDatasource.setSentHeader(
                    id = 1,
                    idBD = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.send(
                number = 16997417840,
                token = "TOKEN"
            )
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
    fun `getIdTurnHeader - Check return failure if have error in HeaderMotoMecRoomDatasource getIdTurnHeader`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.getIdTurnByHeaderOpen()
            ).thenReturn(
                resultFailure(
                    "IHeaderMotoMecRoomDatasource.getIdTurnByHeaderOpen",
                    "-",
                    Exception()
                )
            )
            val result = repository.getIdTurnHeader()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.getIdTurnHeader -> IHeaderMotoMecRoomDatasource.getIdTurnByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception",
            )
        }

    @Test
    fun `getIdTurnHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.getIdTurnByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getIdTurnHeader()
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
    fun `getRegOperator - Check return failure if have error in HeaderMotoMecRoomDatasource getRegOperatorOpen`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.getRegOperatorOpen()
            ).thenReturn(
                resultFailure(
                    "IHeaderMotoMecRoomDatasource.getRegOperatorOpen",
                    "-",
                    Exception()
                )
            )
            val result = repository.getRegOperatorHeader()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.getRegOperator -> IHeaderMotoMecRoomDatasource.getRegOperatorOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getRegOperator - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.getRegOperatorOpen()
            ).thenReturn(
                Result.success(19759)
            )
            val result = repository.getRegOperatorHeader()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                19759
            )
        }


}