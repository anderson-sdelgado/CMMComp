package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.ItemMotoMec
import br.com.usinasantafe.cmm.domain.entities.variable.Performance
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.MotoMecRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.ItemMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.PerformanceRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.TrailerSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderMotoMecRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.NoteMotoMecRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.roomModelToRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.ItemMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ItemMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TypeEquip
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.assertEquals

class IMotoMecRepositoryTest {

    private val headerMotoMecSharedPreferencesDatasource = mock<HeaderMotoMecSharedPreferencesDatasource>()
    private val headerMotoMecRoomDatasource = mock<HeaderMotoMecRoomDatasource>()
    private val itemMotoMecSharedPreferencesDatasource = mock<ItemMotoMecSharedPreferencesDatasource>()
    private val itemMotoMecRoomDatasource = mock<ItemMotoMecRoomDatasource>()
    private val trailerSharedPreferencesDatasource = mock<TrailerSharedPreferencesDatasource>()
    private val motoMecRetrofitDatasource = mock<MotoMecRetrofitDatasource>()
    private val performanceRoomDatasource = mock<PerformanceRoomDatasource>()
    private val repository = IMotoMecRepository(
        headerMotoMecSharedPreferencesDatasource = headerMotoMecSharedPreferencesDatasource,
        headerMotoMecRoomDatasource = headerMotoMecRoomDatasource,
        itemMotoMecSharedPreferencesDatasource = itemMotoMecSharedPreferencesDatasource,
        itemMotoMecRoomDatasource = itemMotoMecRoomDatasource,
        trailerSharedPreferencesDatasource = trailerSharedPreferencesDatasource,
        motoMecRetrofitDatasource = motoMecRetrofitDatasource,
        performanceRoomDatasource = performanceRoomDatasource
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
                Result.success(Unit)
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
                Result.success(Unit)
            )
            whenever(
                headerMotoMecSharedPreferencesDatasource.setRegOperator(12345)
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.setRegOperatorHeader(12345)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                Unit
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
                Result.success(Unit)
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
                Unit
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
                Result.success(Unit)
            )
            val result = repository.setIdTurnHeader(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
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
                Result.success(Unit)
            )
            val result = repository.setNroOSHeader(12345)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
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
                Result.success(Unit)
            )
            val result = repository.setHourMeterInitialHeader(1.0)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
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
                "IMotoMecRepository.saveHeader -> entityToRoomModel"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: regOperator is required"
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
                hourMeter = 1.0,
                statusCon = true
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
                hourMeter = 1.0,
                statusCon = true
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
                    Result.success(1)
                )
            }
            val result = repository.saveHeader()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
               Unit
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
                headerMotoMecRoomDatasource.checkOpen()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRoomDatasource.checkHeaderOpen",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.hasHeaderOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.hasHeaderOpen -> IHeaderMotoMecRoomDatasource.checkHeaderOpen"
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
                headerMotoMecRoomDatasource.checkOpen()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.hasHeaderOpen()
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
                headerMotoMecSharedPreferencesDatasource.getId()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.getId",
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
                "IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecSharedPreferencesDatasource.getId"
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
                headerMotoMecSharedPreferencesDatasource.getId()
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
                Result.success(Unit)
            )
            val result = repository.setHourMeterFinishHeader(1.0)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
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
                Result.success(Unit)
            )
            val result = repository.finishHeader()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }
    
    @Test
    fun `checkHeaderSend - Check return failure if have error in HeaderMotoMecRoomDatasource checkHeaderSend`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.hasSend()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecRoomDatasource.checkHeaderSend",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.hasHeaderSend()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.hasHeaderSend -> IHeaderMotoMecRoomDatasource.checkHeaderSend"
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
                headerMotoMecRoomDatasource.hasSend()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.hasHeaderSend()
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
                itemMotoMecSharedPreferencesDatasource.clean()
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
                itemMotoMecSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                headerMotoMecSharedPreferencesDatasource.getStatusCon()
            ).thenReturn(
                resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.getStatusCon",
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
                "IMotoMecRepository.setNroOSNote -> IHeaderMotoMecSharedPreferencesDatasource.getStatusCon"
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
                itemMotoMecSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                headerMotoMecSharedPreferencesDatasource.getStatusCon()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                itemMotoMecSharedPreferencesDatasource.setNroOSAndStatusCon(
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
                itemMotoMecSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                headerMotoMecSharedPreferencesDatasource.getStatusCon()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                itemMotoMecSharedPreferencesDatasource.setNroOSAndStatusCon(
                    nroOS = 1,
                    statusCon = true
                )
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.setNroOSNote(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `setIdActivity- Check return failure if have error in NoteMotoMecSharedPreferencesDatasource setIdActivity`() =
        runTest {
            whenever(
                itemMotoMecSharedPreferencesDatasource.setIdActivity(1)
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
                itemMotoMecSharedPreferencesDatasource.setIdActivity(1)
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.setIdActivityNote(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `saveNote - Check return failure if have error in NoteMotoMecSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                itemMotoMecSharedPreferencesDatasource.get()
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
                itemMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    ItemMotoMecSharedPreferencesModel()
                )
            )
            val result = repository.saveNote(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.saveNote -> entityToRoomModel"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: nroOS is required"
            )
        }

    @Test
    fun `saveNote - Check return failure if have error in NoteMotoMecRoomDatasource save`() =
        runTest {
            val modelSharedPreferences = ItemMotoMecSharedPreferencesModel(
                nroOS = 123456,
                idActivity = 1,
                statusCon = true
            )
            whenever(
                itemMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelCaptor = argumentCaptor<ItemMotoMecRoomModel>().apply {
                whenever(
                    itemMotoMecRoomDatasource.save(capture())
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
            val modelSharedPreferences = ItemMotoMecSharedPreferencesModel(
                nroOS = 123456,
                idActivity = 1,
                statusCon = true
            )
            whenever(
                itemMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelCaptor = argumentCaptor<ItemMotoMecRoomModel>().apply {
                whenever(
                    itemMotoMecRoomDatasource.save(capture())
                ).thenReturn(
                    Result.success(Unit)
                )
            }
            whenever(
                headerMotoMecRoomDatasource.setSend(1)
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
            val modelSharedPreferences = ItemMotoMecSharedPreferencesModel(
                nroOS = 123456,
                idActivity = 1,
                statusCon = true
            )
            whenever(
                itemMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelCaptor = argumentCaptor<ItemMotoMecRoomModel>().apply {
                whenever(
                    itemMotoMecRoomDatasource.save(capture())
                ).thenReturn(
                    Result.success(Unit)
                )
            }
            whenever(
                headerMotoMecRoomDatasource.setSend(1)
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.saveNote(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
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
                itemMotoMecSharedPreferencesDatasource.getIdActivity()
            ).thenReturn(
                resultFailure(
                    context = "INoteMotoMecSharedPreferencesDatasource.getIdActivity",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.getIdActivityNote()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.getIdActivityNote -> INoteMotoMecSharedPreferencesDatasource.getIdActivity"
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
                itemMotoMecSharedPreferencesDatasource.getIdActivity()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getIdActivityNote()
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
                itemMotoMecSharedPreferencesDatasource.setIdStop(1)
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
                itemMotoMecSharedPreferencesDatasource.setIdStop(1)
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.setIdStop(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `checkHasByIdHeader - Check return failure if have error in noteMotoMecRoomDatasource checkHasNoteById`() =
        runTest {
            whenever(
                itemMotoMecRoomDatasource.hasByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    context = "INoteMotoMecRoomDatasource.checkHasByIdHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.hasNoteByIdHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.hasNoteByIdHeader -> INoteMotoMecRoomDatasource.checkHasByIdHeader"
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
                itemMotoMecRoomDatasource.hasByIdHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.hasNoteByIdHeader(1)
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
                Result.success(Unit)
            )
            val result = repository.setStatusConHeader(true)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `send - Check return failure if have error in HeaderMotoMecRoomDatasource listHeaderSend`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.listSend()
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
                headerMotoMecRoomDatasource.listSend()
            ).thenReturn(
                Result.success(modelRoomList)
            )
            whenever(
                itemMotoMecRoomDatasource.listByIdHeaderAndSend(1)
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
                ItemMotoMecRoomModel(
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
                headerMotoMecRoomDatasource.listSend()
            ).thenReturn(
                Result.success(headerModelRoomList)
            )
            whenever(
                itemMotoMecRoomDatasource.listByIdHeaderAndSend(1)
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
                ItemMotoMecRoomModel(
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
                    idServ = 1,
                    noteMotoMecList = listOf(
                        NoteMotoMecRetrofitModelInput(
                            id = 1,
                            idServ = 1,
                        )
                    )
                )
            )
            whenever(
                headerMotoMecRoomDatasource.listSend()
            ).thenReturn(
                Result.success(headerModelRoomList)
            )
            whenever(
                itemMotoMecRoomDatasource.listByIdHeaderAndSend(1)
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
                itemMotoMecRoomDatasource.setSentNote(
                    id = 1,
                    idServ = 1
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
                ItemMotoMecRoomModel(
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
                    idServ = 1,
                    noteMotoMecList = listOf(
                        NoteMotoMecRetrofitModelInput(
                            id = 1,
                            idServ = 1,
                        )
                    )
                )
            )
            whenever(
                headerMotoMecRoomDatasource.listSend()
            ).thenReturn(
                Result.success(headerModelRoomList)
            )
            whenever(
                itemMotoMecRoomDatasource.listByIdHeaderAndSend(1)
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
                itemMotoMecRoomDatasource.setSentNote(
                    id = 1,
                    idServ = 1
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                headerMotoMecRoomDatasource.setSent(
                    id = 1,
                    idServ = 1
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
                ItemMotoMecRoomModel(
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
                    idServ = 1,
                    noteMotoMecList = listOf(
                        NoteMotoMecRetrofitModelInput(
                            id = 1,
                            idServ = 1,
                        )
                    )
                )
            )
            whenever(
                headerMotoMecRoomDatasource.listSend()
            ).thenReturn(
                Result.success(headerModelRoomList)
            )
            whenever(
                itemMotoMecRoomDatasource.listByIdHeaderAndSend(1)
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
                itemMotoMecRoomDatasource.setSentNote(
                    id = 1,
                    idServ = 1
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                headerMotoMecRoomDatasource.setSent(
                    id = 1,
                    idServ = 1
                )
            ).thenReturn(
                Result.success(Unit)
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
                Unit
            )
        }

    @Test
    fun `getIdTurnHeader - Check return failure if have error in HeaderMotoMecRoomDatasource getIdTurnHeader`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.getIdTurn()
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
                headerMotoMecRoomDatasource.getIdTurn()
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
                headerMotoMecRoomDatasource.getRegOperator()
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
                "IMotoMecRepository.getRegOperatorHeader -> IHeaderMotoMecRoomDatasource.getRegOperatorOpen"
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
                headerMotoMecRoomDatasource.getRegOperator()
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

    @Test
    fun `noteListByIdHeader - Check return failure if have error in NoteMotoMecRoomDatasource listByIdHeader`() =
        runTest {
            whenever(
                itemMotoMecRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "INoteMotoMecRoomDatasource.listByIdHeader",
                    "-",
                    Exception()
                )
            )
            val result = repository.listNoteByIdHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.noteListByIdHeader -> INoteMotoMecRoomDatasource.listByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `noteListByIdHeader - Check return correct if function execute successfully`() =
        runTest {
            val modelList = listOf(
                ItemMotoMecRoomModel(
                    id = 1,
                    idHeader = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    idStop = null,
                    dateHour = Date(1750422691),
                    statusCon = true
                )
            )
            val entityList = listOf(
                ItemMotoMec(
                    id = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    idStop = null,
                    dateHour = Date(1750422691),
                    statusCon = true
                )
            )
            whenever(
                itemMotoMecRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                Result.success(modelList)
            )
            val result = repository.listNoteByIdHeader(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                entityList
            )
        }

    @Test
    fun `getIdActivityHeader - Check return failure if have error in HeaderMotoMecSharedPreferencesDatasource getIdActivity`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.getIdActivity()
            ).thenReturn(
                resultFailure(
                    "IHeaderMotoMecSharedPreferencesDatasource.getIdActivity",
                    "-",
                    Exception()
                )
            )
            val result = repository.getIdActivityHeader()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.getIdActivityHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getIdActivityHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.getIdActivity()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getIdActivityHeader()
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
    fun `hasNoteByIdStopAndIdHeader - Check return failure if have error in NoteMotoMecRoomDatasource checkHasByIdStop`() =
        runTest {
            whenever(
                itemMotoMecRoomDatasource.hasByIdStopAndIdHeader(
                    idStop = 1,
                    idHeader = 1
                )
            ).thenReturn(
                resultFailure(
                    "INoteMotoMecRoomDatasource.checkHasByIdStop",
                    "-",
                    Exception()
                )
            )
            val result = repository.hasNoteByIdStopAndIdHeader(1, 1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.hasNoteByIdStopAndIdHeader -> INoteMotoMecRoomDatasource.checkHasByIdStop"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `hasNoteByIdStopAndIdHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                itemMotoMecRoomDatasource.hasByIdStopAndIdHeader(
                    idStop = 1,
                    idHeader = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.hasNoteByIdStopAndIdHeader(1, 1)
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
    fun `getFlowCompostingHeader - Check return failure if have error in HeaderMotoMecDatasource getFlowComposting`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.getFlowComposting()
            ).thenReturn(
                resultFailure(
                    "IHeaderMotoMecDatasource.getFlowComposting",
                    "-",
                    Exception()
                )
            )
            val result = repository.getFlowCompostingHeader()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.getFlowCompostingHeader -> IHeaderMotoMecDatasource.getFlowComposting"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getFlowCompostingHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecRoomDatasource.getFlowComposting()
            ).thenReturn(
                Result.success(FlowComposting.INPUT)
            )
            val result = repository.getFlowCompostingHeader()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowComposting.INPUT
            )
        }

    @Test
    fun `getNoteLastByIdHeader - Check return failure if have error in NoteMotoMecDatasource getLastByIdHeader`() =
        runTest {
            whenever(
                itemMotoMecRoomDatasource.getLastByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "INoteMotoMecDatasource.getLastByIdHeader",
                    "-",
                    Exception()
                )
            )
            val result = repository.getNoteLastByIdHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.getNoteLastByIdHeader -> INoteMotoMecDatasource.getLastByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }
    @Test
    fun `getNoteLastByIdHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                itemMotoMecRoomDatasource.getLastByIdHeader(1)
            ).thenReturn(
                Result.success(
                    ItemMotoMecRoomModel(
                        idHeader = 1,
                        nroOS = 123456,
                        idActivity = 1,
                    )
                )
            )
            val result = repository.getNoteLastByIdHeader(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val entity = result.getOrNull()!!
            assertEquals(
                entity.nroOS,
                123456
            )
            assertEquals(
                entity.idActivity,
                1
            )
        }

    @Test
    fun `hasCouplingTrailerImplement - Check return failure if have error in TrailerSharedPreferencesDatasource has`() =
        runTest {
            whenever(
                trailerSharedPreferencesDatasource.has()
            ).thenReturn(
                resultFailure(
                    "ITrailerSharedPreferencesDatasource.has",
                    "-",
                    Exception()
                )
            )
            val result = repository.hasCouplingTrailerImplement()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.hasCouplingTrailerImplement -> ITrailerSharedPreferencesDatasource.has"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `hasCouplingTrailerImplement - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                trailerSharedPreferencesDatasource.has()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.hasCouplingTrailerImplement()
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
    fun `uncouplingTrailerImplement - Check return failure if have error in TrailerSharedPreferencesDatasource clean`() =
        runTest {
            whenever(
                trailerSharedPreferencesDatasource.clean()
            ).thenReturn(
                resultFailure(
                    "ITrailerSharedPreferencesDatasource.clean",
                    "-",
                    Exception()
                )
            )
            val result = repository.uncouplingTrailerImplement()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.uncouplingTrailerImplement -> ITrailerSharedPreferencesDatasource.clean"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `uncouplingTrailerImplement - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                trailerSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.uncouplingTrailerImplement()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `setNroEquipTranshipmentNote - Check return failure if have error in ItemMotoMecDatasource setNroEquipTranshipment`() =
        runTest {
            whenever(
                itemMotoMecSharedPreferencesDatasource.setNroEquipTranshipment(200)
            ).thenReturn(
                resultFailure(
                    "IItemMotoMecDatasource.setNroEquipTranshipment",
                    "-",
                    Exception()
                )
            )
            val result = repository.setNroEquipTranshipmentNote(200)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setNroEquipTranshipmentNote -> IItemMotoMecDatasource.setNroEquipTranshipment"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setNroEquipTranshipmentNote - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                itemMotoMecSharedPreferencesDatasource.setNroEquipTranshipment(200)
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.setNroEquipTranshipmentNote(200)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `getTypeEquipHeader - Check return failure if have error in HeaderMotoMecSharedPreferencesDatasource getTypeEquip`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.getTypeEquip()
            ).thenReturn(
                resultFailure(
                    "IHeaderMotoMecSharedPreferencesDatasource.getTypeEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.getTypeEquipHeader()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.getTypeEquipHeader -> IHeaderMotoMecSharedPreferencesDatasource.getTypeEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getTypeEquipHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.getTypeEquip()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            val result = repository.getTypeEquipHeader()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                TypeEquip.NORMAL
            )
        }

    @Test
    fun `setIdEquipMotorPumpHeader - Check return failure if have error in HeaderMotoMecSharedPreferencesDatasource setIdEquipMotorPump`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setIdEquipMotorPump(10)
            ).thenReturn(
                resultFailure(
                    "IHeaderMotoMecSharedPreferencesDatasource.setIdEquipMotorPump",
                    "-",
                    Exception()
                )
            )
            val result = repository.setIdEquipMotorPumpHeader(10)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.setIdEquipMotorPumpHeader -> IHeaderMotoMecSharedPreferencesDatasource.setIdEquipMotorPump"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdEquipMotorPumpHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.setIdEquipMotorPump(10)
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.setIdEquipMotorPumpHeader(10)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `insertInitialPerformance - Check return failure if have error in HeaderMotoMecSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                resultFailure(
                    "IHeaderMotoMecSharedPreferencesDatasource.get",
                    "-",
                    Exception()
                )
            )
            val result = repository.insertInitialPerformance()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.insertInitialPerformance -> IHeaderMotoMecSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `insertInitialPerformance - Check return failure if have error in PerformanceRoomDatasource insert`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    HeaderMotoMecSharedPreferencesModel(
                        nroOS = 123456,
                        id = 1
                    )
                )
            )
            val modelCaptor = argumentCaptor<PerformanceRoomModel>().apply {
                whenever(
                    performanceRoomDatasource.insert(
                        capture()
                    )
                ).thenReturn(
                    resultFailure(
                        "IPerformanceRoomDatasource.insert",
                        "-",
                        Exception()
                    )
                )
            }
            val result = repository.insertInitialPerformance()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.insertInitialPerformance -> IPerformanceRoomDatasource.insert"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.nroOS,
                123456
            )
        }

    @Test
    fun `insertInitialPerformance - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    HeaderMotoMecSharedPreferencesModel(
                        nroOS = 123456,
                        id = 1
                    )
                )
            )
            val modelCaptor = argumentCaptor<PerformanceRoomModel>().apply {
                whenever(
                    performanceRoomDatasource.insert(
                        capture()
                    )
                ).thenReturn(
                    Result.success(Unit)
                )
            }
            val result = repository.insertInitialPerformance()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.nroOS,
                123456
            )
        }

    @Test
    fun `listPerformanceByIdHeader - Check return failure if have error in PerformanceRoomDatasource listByIdHeader`() =
        runTest {
            whenever(
                performanceRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "IPerformanceRoomDatasource.listByIdHeader",
                    "-",
                    Exception()
                )
            )
            val result = repository.listPerformanceByIdHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.listPerformanceByIdHeader -> IPerformanceRoomDatasource.listByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listPerformanceByIdHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                performanceRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        PerformanceRoomModel(
                            idHeader = 1,
                            nroOS = 1,
                            value = 10.0,
                            dateHour = Date(1750422691)
                        )
                    )
                )
            )
            val result = repository.listPerformanceByIdHeader(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    Performance(
                        id = 1,
                        idHeader = 1,
                        nroOS = 1,
                        value = 10.0,
                        dateHour = Date(1750422691)
                    )
                )
            )
        }

}