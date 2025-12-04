package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.TypeEquip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class ICheckHourMeterTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ICheckHourMeter

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {
            hiltRule.inject()
            val result = usecase("10.000,0")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckMeasureInitial -> IHeaderMotoMecRepository.getIdEquip -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_table_equip() =
        runTest {
            hiltRule.inject()
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    idEquip = 1
                )
            )
            val result = usecase("10.000,0")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckMeasureInitial -> IEquipRepository.getMeasureByIdEquip -> IEquipRoomDatasource.getMeasureByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'double br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getMeasurement()' on a null object reference"
            )
        }

    @Test
    fun check_return_false_if_measure_database_is_bigger_than_measure_input() =
        runTest {
            hiltRule.inject()
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    idEquip = 1
                )
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 0.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
                )
            )
            val result = usecase("10.000,0")
            assertEquals(
                result.isSuccess,
                true
            )
            val entity = result.getOrNull()!!
            assertEquals(
                entity.measureBD,
                "20.000,0",
            )
            assertEquals(
                entity.check,
                false
            )
        }

    @Test
    fun check_return_true_if_measure_database_is_less_than_measure_input() =
        runTest {
            hiltRule.inject()
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    idEquip = 1
                )
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 5000.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
                )
            )
            val result = usecase("10.000,0")
            assertEquals(
                result.isSuccess,
                true
            )
            val entity = result.getOrNull()!!
            assertEquals(
                entity.measureBD,
                "5.000,0",
            )
            assertEquals(
                entity.check,
                true
            )
        }

}