package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.entities.stable.Pressure
import br.com.usinasantafe.cmm.external.room.dao.stable.PressureDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.PressureRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ItemMotoMecSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class IListSpeedTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ListSpeed

    @Inject
    lateinit var pressureDao: PressureDao

    @Inject
    lateinit var itemMotoMecSharedPreferencesDatasource: ItemMotoMecSharedPreferencesDatasource

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_item_moto_mec_shared_preferences() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListSpeed -> IFertigationRepository.getIdNozzle -> IItemMotoMecSharedPreferencesDatasource.getIdNozzle"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_value_pressure_in_item_moto_mec_shared_preferences() =
        runTest {
            itemMotoMecSharedPreferencesDatasource.save(
                ItemMotoMecSharedPreferencesModel(
                    idNozzle = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListSpeed -> IFertigationRepository.getValuePressure -> IItemMotoMecSharedPreferencesDatasource.getValuePressure"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_list_empty_if_not_have_data_is_pressure_room() =
        runTest {
            itemMotoMecSharedPreferencesDatasource.save(
                ItemMotoMecSharedPreferencesModel(
                    idNozzle = 1,
                    valuePressure = 10.0
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf()
            )
        }

    @Test
    fun check_return_list_if_have_data_is_pressure_room() =
        runTest {
            itemMotoMecSharedPreferencesDatasource.save(
                ItemMotoMecSharedPreferencesModel(
                    idNozzle = 1,
                    valuePressure = 20.0
                )
            )
            pressureDao.insertAll(
                listOf(
                    PressureRoomModel(
                        id = 1,
                        idNozzle = 2,
                        value = 10.0,
                        speed = 1
                    ),
                    PressureRoomModel(
                        id = 2,
                        idNozzle = 1,
                        value = 10.0,
                        speed = 1
                    ),
                    PressureRoomModel(
                        id = 3,
                        idNozzle = 1,
                        value = 20.0,
                        speed = 15
                    ),
                    PressureRoomModel(
                        id = 4,
                        idNozzle = 1,
                        value = 20.0,
                        speed = 20
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    Pressure(
                        id = 3,
                        idNozzle = 1,
                        value = 20.0,
                        speed = 15
                    ),
                    Pressure(
                        id = 4,
                        idNozzle = 1,
                        value = 20.0,
                        speed = 20
                    )
                )
            )
        }

}