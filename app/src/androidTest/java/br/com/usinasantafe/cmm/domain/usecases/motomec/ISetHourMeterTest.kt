package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.TypeEquip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetHourMeterTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetHourMeter

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_data_is_empty_header_initial() =
        runTest {
            val modelBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                modelBefore.isSuccess,
                true
            )
            assertEquals(
                modelBefore.getOrNull()!!,
                HeaderMotoMecSharedPreferencesModel()
            )
            val result = usecase("2000,0")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> IHeaderMotoMecRepository.getIdEquip -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
            val modelAfter = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                modelAfter.isSuccess,
                true
            )
            assertEquals(
                modelAfter.getOrNull()!!,
                HeaderMotoMecSharedPreferencesModel(
                    hourMeter = 2000.0
                )
            )
        }

    @Test
    fun check_data_if_execute_successfully_header_initial() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    regOperator = 123456,
                    idEquip = 1,
                    idTurn = 1,
                    nroOS = 1,
                    idActivity = 1,
                )
            )
            val headerModelBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                headerModelBefore.isSuccess,
                true
            )
            assertEquals(
                headerModelBefore.getOrNull()!!,
                HeaderMotoMecSharedPreferencesModel(
                    regOperator = 123456,
                    idEquip = 1,
                    idTurn = 1,
                    nroOS = 1,
                    idActivity = 1,
                )
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 2200,
                        codClass = 1,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 100.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
                )
            )
            val listEquipBefore = equipDao.all()
            assertEquals(
                listEquipBefore.size,
                1
            )
            val equipBefore = listEquipBefore[0]
            assertEquals(
                equipBefore.id,
                1
            )
            assertEquals(
                equipBefore.hourMeter,
                100.0,
                0.0
            )
            val result = usecase("2000,0")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val headerModelAfter = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                headerModelAfter.isSuccess,
                true
            )
            assertEquals(
                headerModelAfter.getOrNull()!!,
                HeaderMotoMecSharedPreferencesModel(
                    regOperator = 123456,
                    idEquip = 1,
                    idTurn = 1,
                    nroOS = 1,
                    idActivity = 1,
                    hourMeter = 2000.0
                )
            )
            val listEquipAfter = equipDao.all()
            assertEquals(
                listEquipAfter.size,
                1
            )
            val equipAfter = listEquipAfter[0]
            assertEquals(
                equipAfter.id,
                1
            )
            assertEquals(
                equipAfter.hourMeter,
                2000.0,
                0.0
            )
            val listHeaderAfter = headerMotoMecDao.all()
            assertEquals(
                listHeaderAfter.size,
                1
            )
            val headerAfter = listHeaderAfter[0]
            assertEquals(
                headerAfter.id,
                1
            )
            assertEquals(
                headerAfter.regOperator,
                123456
            )
            assertEquals(
                headerAfter.idEquip,
                1
            )
            assertEquals(
                headerAfter.hourMeterInitial,
                2000.0,
                0.0
            )
        }

    @Test
    fun check_return_failure_if_number_digit_incorrect_header_initial() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    regOperator = 123456,
                    idEquip = 1,
                    idTurn = 1,
                    nroOS = 1,
                    idActivity = 1,
                )
            )
            val headerModelBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                headerModelBefore.isSuccess,
                true
            )
            assertEquals(
                headerModelBefore.getOrNull()!!,
                HeaderMotoMecSharedPreferencesModel(
                    regOperator = 123456,
                    idEquip = 1,
                    idTurn = 1,
                    nroOS = 1,
                    idActivity = 1,
                )
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 2200,
                        codClass = 1,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 100.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
                )
            )
            val listEquipBefore = equipDao.all()
            assertEquals(
                listEquipBefore.size,
                1
            )
            val equipBefore = listEquipBefore[0]
            assertEquals(
                equipBefore.id,
                1
            )
            assertEquals(
                equipBefore.hourMeter,
                100.0,
                0.0
            )
            val result = usecase("dd20d00as0")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.text.ParseException: Unparseable number: \"dd20d00as0\""
            )
        }

    @Test
    fun check_return_failure_if_data_is_empty_header_finish() =
        runTest {
            val result = usecase(
                hourMeter = "3.000,0",
                flowApp = FlowApp.HEADER_FINISH
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetMeasure -> IHeaderMotoMecRepository.getIdEquip -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_data_alter_if_execution_successfully_header_finish() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123456,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 1,
                    idActivity = 1,
                    hourMeterInitial = 2000.0,
                    statusCon = true
                )
            )
            val listHeaderBefore = headerMotoMecDao.all()
            assertEquals(
                listHeaderBefore.size,
                1
            )
            val headerBefore = listHeaderBefore[0]
            assertEquals(
                headerBefore.id,
                1
            )
            assertEquals(
                headerBefore.regOperator,
                123456
            )
            assertEquals(
                headerBefore.idEquip,
                1
            )
            assertEquals(
                headerBefore.hourMeterInitial,
                2000.0,
                0.0
            )
            assertEquals(
                headerBefore.hourMeterFinish,
                null
            )
            assertEquals(
                headerBefore.status,
                Status.OPEN
            )
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    regOperator = 123456,
                    idEquip = 1,
                    idTurn = 1,
                    nroOS = 1,
                    idActivity = 1,
                )
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 2200,
                        codClass = 1,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 2000.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
                )
            )
            val listEquipBefore = equipDao.all()
            assertEquals(
                listEquipBefore.size,
                1
            )
            val equipBefore = listEquipBefore[0]
            assertEquals(
                equipBefore.id,
                1
            )
            assertEquals(
                equipBefore.hourMeter,
                2000.0,
                0.0
            )
            val result = usecase(
                hourMeter = "3.000,0",
                flowApp = FlowApp.HEADER_FINISH
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val listHeaderAfter = headerMotoMecDao.all()
            assertEquals(
                listHeaderAfter.size,
                1
            )
            val headerAfter = listHeaderAfter[0]
            assertEquals(
                headerAfter.id,
                1
            )
            assertEquals(
                headerAfter.regOperator,
                123456
            )
            assertEquals(
                headerAfter.idEquip,
                1
            )
            assertEquals(
                headerAfter.hourMeterInitial,
                2000.0,
                0.0
            )
            assertEquals(
                headerAfter.hourMeterFinish!!,
                3000.0,
                0.0
            )
            assertEquals(
                headerAfter.status,
                Status.CLOSE
            )
            val listEquipAfter = equipDao.all()
            assertEquals(
                listEquipAfter.size,
                1
            )
            val equipAfter = listEquipAfter[0]
            assertEquals(
                equipAfter.id,
                1
            )
            assertEquals(
                equipAfter.hourMeter,
                3000.0,
                0.0
            )
        }
}