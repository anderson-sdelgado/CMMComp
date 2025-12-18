package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IHeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.TypeEquip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetIdEquipMotorPumpTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetIdEquipMotorPump

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var headerSharedPreferencesDatasource: IHeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_nroEquip_is_incorrect() =
        runTest {
            val result = usecase("2dfas200")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdEquipMotorPump"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"2dfas200\""
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_equip_room() =
        runTest {
            val result = usecase("2200")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdEquipMotorPump -> IEquipRepository.getIdByNro -> IEquipRoomDatasource.getIdByNro"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'int br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getId()' on a null object reference"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_shared_preferences() =
        runTest {
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 20,
                        nro = 2200,
                        codClass = 1,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.REEL_FERT
                    )
                )
            )
            val result = usecase("2200")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdEquipMotorPump -> IMotoMecRepository.saveHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
            val resultHeader = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultHeader.isSuccess,
                true
            )
            val model = resultHeader.getOrNull()!!
            assertEquals(
                model.idEquipMotorPump,
                20
            )
            assertEquals(
                model.idEquip,
                null
            )
        }

    @Test
    fun check_return_true_and_data_altered_if_process_execute_successfully() =
        runTest {
            headerSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    regOperator = 19759,
                    idEquip = 200,
                    typeEquip = TypeEquip.REEL_FERT,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeter = 1.0,
                    statusCon = true
                )
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 20,
                        nro = 2200,
                        codClass = 1,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.REEL_FERT
                    )
                )
            )
            val result = usecase("2200")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultHeaderSharedPreferences = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultHeaderSharedPreferences.isSuccess,
                true
            )
            val headerSharedPreferencesModel = resultHeaderSharedPreferences.getOrNull()!!
            assertEquals(
                headerSharedPreferencesModel.idEquipMotorPump,
                20
            )
            assertEquals(
                headerSharedPreferencesModel.idEquip,
                200
            )
            assertEquals(
                headerSharedPreferencesModel.typeEquip,
                TypeEquip.REEL_FERT
            )
            assertEquals(
                headerSharedPreferencesModel.regOperator,
                19759
            )
            assertEquals(
                headerSharedPreferencesModel.idTurn,
                1
            )
            assertEquals(
                headerSharedPreferencesModel.nroOS,
                123456
            )
            assertEquals(
                headerSharedPreferencesModel.idActivity,
                1
            )
            assertEquals(
                headerSharedPreferencesModel.hourMeter,
                1.0
            )
            assertEquals(
                headerSharedPreferencesModel.statusCon,
                true
            )
            assertEquals(
                headerSharedPreferencesModel.id,
                1
            )
            val headerRoomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            assertEquals(
                headerRoomModel.idEquipMotorPump,
                20
            )
            assertEquals(
                headerRoomModel.idEquip,
                200
            )
            assertEquals(
                headerRoomModel.typeEquip,
                TypeEquip.REEL_FERT
            )
            assertEquals(
                headerRoomModel.regOperator,
                19759
            )
            assertEquals(
                headerRoomModel.idTurn,
                1
            )
            assertEquals(
                headerRoomModel.nroOS,
                123456
            )
            assertEquals(
                headerRoomModel.idActivity,
                1
            )
            assertEquals(
                headerRoomModel.hourMeterInitial,
                1.0
            )
            assertEquals(
                headerRoomModel.statusCon,
                true
            )
            assertEquals(
                headerRoomModel.id,
                1
            )
        }

}