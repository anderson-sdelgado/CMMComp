package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
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
class IHasEquipSecondaryTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: HasEquipSecondary

    @Inject
    lateinit var equipDao: EquipDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_false_if_not_have_data() =
    runTest {
        val result = usecase(
            nroEquip = "10",
            typeEquip = TypeEquip.TRANSHIPMENT
        )
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            false
        )
    }

    @Test
    fun check_return_false_if_have_data_but_not_typeEquip_is_different() =
        runTest {
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.REEL_FERT
                    )
                )
            )
            val result = usecase(
                nroEquip = "10",
                typeEquip = TypeEquip.TRANSHIPMENT
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun check_return_false_if_have_data_but_not_nroEquip_is_different() =
        runTest {
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 2200,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.REEL_FERT
                    ),
                    EquipRoomModel(
                        id = 2,
                        nro = 200,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.TRANSHIPMENT
                    )
                )
            )
            val result = usecase(
                nroEquip = "10",
                typeEquip = TypeEquip.TRANSHIPMENT
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun check_return_true_if_have_data_equals_data_field() =
        runTest {
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 2200,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.REEL_FERT
                    ),
                    EquipRoomModel(
                        id = 2,
                        nro = 200,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.TRANSHIPMENT
                    )
                )
            )
            val result = usecase(
                nroEquip = "200",
                typeEquip = TypeEquip.TRANSHIPMENT
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
}