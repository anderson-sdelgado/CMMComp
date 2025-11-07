package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.stable.TurnDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.TypeEquip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class IListTurnTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ListTurn

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var turnDao: TurnDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_table_config() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetTurnList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_table_equip() =
        runTest {
            val resultSave = configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1,
                )
            )
            assertEquals(
                resultSave.isSuccess,
                true
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetTurnList -> IEquipRepository.getCodTurnEquipByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_list_empty_if_not_have_data_in_table_turn() =
        runTest {
            val resultSave = configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1,
                )
            )
            assertEquals(
                resultSave.isSuccess,
                true
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 200,
                        codClass = 1,
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
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList<Turn>()
            )
        }

    @Test
    fun check_return_correct_if_have_data_in_table_turn() =
        runTest {
            val resultSave = configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1,
                )
            )
            assertEquals(
                resultSave.isSuccess,
                true
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 200,
                        codClass = 1,
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
            turnDao.insertAll(
                listOf(
                    TurnRoomModel(
                        idTurn = 1,
                        codTurnEquip = 1,
                        nroTurn = 1,
                        descrTurn = "TURNO 1"
                    ),
                    TurnRoomModel(
                        idTurn = 2,
                        codTurnEquip = 1,
                        nroTurn = 2,
                        descrTurn = "TURNO 2"
                    ),
                    TurnRoomModel(
                        idTurn = 3,
                        codTurnEquip = 2,
                        nroTurn = 3,
                        descrTurn = "TURNO 3"
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idTurn,
                1
            )
            assertEquals(
                entity1.codTurnEquip,
                1
            )
            assertEquals(
                entity1.nroTurn,
                1
            )
            assertEquals(
                entity1.descrTurn,
                "TURNO 1"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idTurn,
                2
            )
            assertEquals(
                entity2.codTurnEquip,
                1
            )
            assertEquals(
                entity2.nroTurn,
                2
            )
            assertEquals(
                entity2.descrTurn,
                "TURNO 2"
            )
        }
}