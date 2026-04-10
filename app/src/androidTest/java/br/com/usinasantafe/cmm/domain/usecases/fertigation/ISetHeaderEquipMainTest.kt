package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.EquipSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.EquipSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.TypeEquip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import java.util.Date
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetHeaderEquipMainTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetHeaderEquipMain

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var equipSharedPreferencesDatasource: EquipSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHeaderEquipMain -> IEquipRepository.getIdEquipMain -> IEquipSharedPreferencesDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
            val resultGet = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.idEquip,
                null
            )
        }

    @Test
    fun check_alter_data_if_process_execute_successfully() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 19859,
                    idEquip = 10,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true,
                    status = Status.CLOSE
                )
            )
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 19859,
                    idEquip = 20,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true,
                    status = Status.OPEN
                )
            )
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 19859,
                    idEquip = 30,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true,
                    status = Status.FINISH
                )
            )
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 19859,
                    idEquip = 40,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true,
                    status = Status.OPEN
                )
            )
            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 2,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 10000.0,
                    classify = 20,
                    flagMechanic = false,
                    flagTire = false
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val listAfter = headerMotoMecDao.all()
            assertEquals(
                listAfter.size,
                4
            )
            val modelAfter1 = listAfter[0]
            assertEquals(
                modelAfter1.id,
                1
            )
            assertEquals(
                modelAfter1.idEquip,
                10
            )
            assertEquals(
                modelAfter1.status,
                Status.OPEN
            )
            val modelAfter2 = listAfter[1]
            assertEquals(
                modelAfter2.id,
                2
            )
            assertEquals(
                modelAfter2.idEquip,
                20
            )
            assertEquals(
                modelAfter2.status,
                Status.CLOSE
            )
            val modelAfter3 = listAfter[2]
            assertEquals(
                modelAfter3.id,
                3
            )
            assertEquals(
                modelAfter3.idEquip,
                30
            )
            assertEquals(
                modelAfter3.status,
                Status.FINISH
            )
            val modelAfter4 = listAfter[3]
            assertEquals(
                modelAfter4.id,
                4
            )
            assertEquals(
                modelAfter4.idEquip,
                40
            )
            assertEquals(
                modelAfter4.status,
                Status.CLOSE
            )
            val resultGet = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.id,
                1
            )
            assertEquals(
                model.idEquip,
                10
            )
            assertEquals(
                model.idActivity,
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
                model.regOperator,
                19859
            )
            assertEquals(
                model.typeEquip,
                TypeEquip.NORMAL
            )
        }



}