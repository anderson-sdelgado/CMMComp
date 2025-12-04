package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMechanicDao
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMechanicRoomModel
import br.com.usinasantafe.cmm.lib.Status
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@HiltAndroidTest
class IFinishNoteMechanicTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: FinishNoteMechanic

    @Inject
    lateinit var noteMechanicDao: NoteMechanicDao

    @Before
    fun init() {
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
                "IFinishNoteMechanic -> IMechanicRepository.setFinishNote -> INoteMechanicRoomDatasource.setFinishNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'void br.com.usinasantafe.cmm.infra.models.room.variable.NoteMechanicRoomModel.setDateHourFinish(java.util.Date)' on a null object reference"
            )
        }

    @Test
    fun check_alter_date() =
        runTest {
            noteMechanicDao.insert(
                NoteMechanicRoomModel(
                    idHeader = 1,
                    os = 123456,
                    item = 1,
                    dateHourFinish = null
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
            val list = noteMechanicDao.all()
            assertEquals(
                list.size,
                1
            )
            val model = list[0]
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.os,
                123456
            )
            assertEquals(
                model.item,
                1
            )
            assertNotNull(
                model.dateHourFinish
            )
            assertEquals(
                model.status,
                Status.FINISH
            )
        }
}