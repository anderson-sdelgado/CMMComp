package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderCheckListDao
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderCheckListRoomModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class ICheckSendCheckListTestCheckList {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckSendCheckList

    @Inject
    lateinit var headerCheckListDao: HeaderCheckListDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_false_when_list_is_empty() =
        runTest {
            val result = usecase()
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
    fun check_return_true_when_list_is_not_empty() =
        runTest {
            headerCheckListDao.insert(
                HeaderCheckListRoomModel(
                    nroTurn = 1,
                    regOperator = 1,
                    nroEquip = 1,
                    dateHour = Date(1760711032)
                )
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
        }
}