package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.TypeEquipMain
import br.com.usinasantafe.cmm.lib.TypeEquipSecondary
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetIdEquipTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetIdEquip

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_table_equip_is_empty() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    app = "CMM",
                    idServ = 1,
                    number = 16997417840,
                    version = "1.0",
                    password = "12345",
                    checkMotoMec = true
                )
            )
            val resultGetBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.idEquip,
                null
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdEquip -> IEquipRepository.getTypeFertByIdEquip -> IEquipRoomDatasource.getTypeFertByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'int br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getTypeFert()' on a null object reference"
            )
        }

    @Test
    fun check_return_true_if_process_execute_successfully() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    app = "CMM",
                    idServ = 1,
                    number = 16997417840,
                    version = "1.0",
                    password = "12345",
                    checkMotoMec = true
                )
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 10,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquipSecondary.REEL
                    ),
                )
            )
            val resultGetBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.idEquip,
                null
            )
            assertEquals(
                modelBefore.typeEquipMain,
                null
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultGetAfter = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.idEquip,
                10
            )
            assertEquals(
                modelAfter.typeEquipMain,
                TypeEquipMain.NORMAL
            )
        }

}