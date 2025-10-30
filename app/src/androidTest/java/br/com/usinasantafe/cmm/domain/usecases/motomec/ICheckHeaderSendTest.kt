package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.TypeEquip
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
class ICheckHeaderSendTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ICheckSendMotoMec

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_false_if_not_have_header_open() =
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
    fun check_return_true_if_have_header_open() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 19759,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    hourMeterFinish = 20.0,
                    dateHourInitial = Date(1748359002),
                    dateHourFinish = Date(1748359002),
                    status = Status.CLOSE,
                    statusSend = StatusSend.SEND,
                    statusCon = true
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
            val qtdAfter = headerMotoMecDao.all().size
            assertEquals(
                qtdAfter,
                1
            )
            val entity = headerMotoMecDao.all()[0]
            assertEquals(
                entity.status,
                Status.CLOSE
            )
            assertEquals(
                entity.statusSend,
                StatusSend.SEND
            )
        }

}