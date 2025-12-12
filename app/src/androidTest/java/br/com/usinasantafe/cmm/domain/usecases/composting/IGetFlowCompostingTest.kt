package br.com.usinasantafe.cmm.domain.usecases.composting

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.TypeEquipMain
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class IGetFlowCompostingTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: GetFlowComposting

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

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
                "IGetFlowComposting -> IMotoMecRepository.getFlowCompostingHeader -> IHeaderMotoMecRoomDatasource.getFlowComposting"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'br.com.usinasantafe.cmm.lib.FlowComposting br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getFlowComposting()' on a null object reference"
            )
        }

    @Test
    fun check_return_flow_composting_if_have_data() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    id = 1,
                    regOperator = 19759,
                    idEquip = 20,
                    typeEquipMain = TypeEquipMain.NORMAL,
                    flowComposting = FlowComposting.COMPOUND,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 1000.0,
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
                FlowComposting.COMPOUND
            )
        }

}