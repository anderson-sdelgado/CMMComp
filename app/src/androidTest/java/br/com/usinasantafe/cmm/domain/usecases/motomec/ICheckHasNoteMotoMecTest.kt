package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMotoMecDao
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import br.com.usinasantafe.cmm.lib.TypeEquip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import java.util.Date
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ICheckHasNoteMotoMecTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ICheckHasNoteMotoMec

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var noteMotoMecDao: NoteMotoMecDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_header_moto_mec_room() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckHasNoteMotoMec -> IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecRoomDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Integer br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getId()' on a null object reference"
            )
        }

    @Test
    fun check_return_false_if_not_have_data_in_note_moto_mec_room() =
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
                false
            )
        }

    @Test
    fun check_return_true_if_have_data_in_note_moto_mec_room() =
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
                    statusCon = true
                )
            )
            noteMotoMecDao.insert(
                NoteMotoMecRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                    idActivity = 1,
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
        }

}