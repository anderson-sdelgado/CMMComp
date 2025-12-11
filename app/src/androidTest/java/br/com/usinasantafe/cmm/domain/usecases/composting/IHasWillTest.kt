package br.com.usinasantafe.cmm.domain.usecases.composting

import br.com.usinasantafe.cmm.external.room.dao.variable.CompoundCompostingDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.InputCompostingDao
import br.com.usinasantafe.cmm.infra.models.room.variable.CompoundCompostingRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.InputCompostingRoomModel
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TypeEquip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class IHasWillTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: HasWill

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var inputCompostingDao: InputCompostingDao

    @Inject
    lateinit var compoundCompostingDao: CompoundCompostingDao

    @Before
    fun setup() {
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
                "IHasWill -> IMotoMecRepository.getFlowCompostingHeader -> IHeaderMotoMecRoomDatasource.getFlowComposting"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'br.com.usinasantafe.cmm.lib.FlowComposting br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getFlowComposting()' on a null object reference"
            )
        }

    @Test
    fun check_return_false_if_have_data_in_input_composting_room_and_flow_composting_is_input() =
        runTest {
            register(
                FlowComposting.INPUT
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
    fun check_return_false_if_idWill_is_null_and_flow_composting_is_input() =
        runTest {
            register(
                FlowComposting.INPUT
            )
            inputCompostingDao.insert(
                InputCompostingRoomModel(
                    statusSend = StatusSend.SENT
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
    fun check_return_true_if_idWill_is_not_null_and_flow_composting_is_input() =
        runTest {
            register(
                FlowComposting.INPUT
            )
            inputCompostingDao.insert(
                InputCompostingRoomModel(
                    idWill = 2,
                    statusSend = StatusSend.SENT
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

    @Test
    fun check_return_false_if_have_data_in_input_composting_room_and_flow_composting_is_compound() =
        runTest {
            register(
                FlowComposting.COMPOUND
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
    fun check_return_false_if_idWill_is_null_and_flow_composting_is_compound() =
        runTest {
            register(
                FlowComposting.COMPOUND
            )
            compoundCompostingDao.insert(
                CompoundCompostingRoomModel(
                    statusSend = StatusSend.SENT
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
    fun check_return_true_if_idWill_is_not_null_and_flow_composting_is_compound() =
        runTest {
            register(
                FlowComposting.COMPOUND
            )
            compoundCompostingDao.insert(
                CompoundCompostingRoomModel(
                    idWill = 2,
                    statusSend = StatusSend.SENT
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

    private suspend fun register(
        flowComposting: FlowComposting
    ) {
        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                id = 1,
                regOperator = 19759,
                idEquip = 20,
                typeEquip = TypeEquip.NORMAL,
                flowComposting = flowComposting,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 1000.0,
                statusCon = true
            )
        )
    }

}