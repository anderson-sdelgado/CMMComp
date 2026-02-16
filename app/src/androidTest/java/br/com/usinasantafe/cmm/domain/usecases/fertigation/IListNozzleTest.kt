package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.entities.stable.Nozzle
import br.com.usinasantafe.cmm.external.room.dao.stable.NozzleDao
import br.com.usinasantafe.cmm.infra.models.room.stable.NozzleRoomModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class IListNozzleTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ListNozzle

    @Inject
    lateinit var nozzleDao: NozzleDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_empty_list_if_table_is_empty() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList()
            )
        }

    @Test
    fun check_return_list_ordered_if_table_have_data() =
        runTest {
            nozzleDao.insertAll(
                listOf(
                    NozzleRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "TESTE",
                    ),
                    NozzleRoomModel(
                        id = 2,
                        cod = 5,
                        descr = "TESTE 2",
                    ),
                    NozzleRoomModel(
                        id = 3,
                        cod = 15,
                        descr = "TESTE 3",
                    ),
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
                    Nozzle(
                        id = 2,
                        cod = 5,
                        descr = "TESTE 2",
                    ),
                    Nozzle(
                        id = 1,
                        cod = 10,
                        descr = "TESTE",
                    ),
                    Nozzle(
                        id = 3,
                        cod = 15,
                        descr = "TESTE 3",
                    ),
                )
            )
        }
}