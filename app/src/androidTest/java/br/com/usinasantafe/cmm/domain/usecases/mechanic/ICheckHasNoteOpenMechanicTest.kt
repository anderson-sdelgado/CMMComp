package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMechanicDao
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMechanicRoomModel
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
class ICheckHasNoteOpenMechanicTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ICheckHasNoteOpenMechanic

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var noteMechanicDao: NoteMechanicDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_header_moto_mec_room() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNoteOpen -> IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecRoomDatasource.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Integer br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getId()' on a null object reference"
            )
        }

    @Test
    fun check_return_false_if_not_have_data_in_table_room() =
        runTest {
            initialRegister(1)
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
    fun check_return_true_if_not_have_data_fielded_in_table_room() =
        runTest {
            initialRegister(2)
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

    private suspend fun initialRegister(level: Int) {

        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                regOperator = 19759,
                idEquip = 10,
                typeEquip = TypeEquip.NORMAL,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 10000.0,
                dateHourInitial = Date(1748359002),
                statusCon = true
            )
        )

        if(level == 1) return

        noteMechanicDao.insert(
            NoteMechanicRoomModel(
                idHeader = 1,
                os = 123456,
                item = 1,
                dateHourFinish = null
            )
        )
        noteMechanicDao.insert(
            NoteMechanicRoomModel(
                idHeader = 2,
                os = 123456,
                item = 1,
                dateHourFinish = null
            )
        )
        noteMechanicDao.insert(
            NoteMechanicRoomModel(
                idHeader = 1,
                os = 123456,
                item = 1,
                dateHourFinish = Date()
            )
        )

        if(level == 2) return

    }

}