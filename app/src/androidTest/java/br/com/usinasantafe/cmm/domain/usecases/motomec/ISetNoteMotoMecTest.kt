package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.ECM
import br.com.usinasantafe.cmm.utils.ITEM_NORMAL
import br.com.usinasantafe.cmm.utils.TypeEquip
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
class ISetNoteMotoMecTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetNoteMotoMec

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_header_moto_mec_room() =
        runTest {
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNoteMotoMec -> IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecRoomDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Integer br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getId()' on a null object reference"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_moto_mec_shared_preferences() =
        runTest {
            initialRegister(1)
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNoteMotoMec -> IMotoMecRepository.getNroOSHeader -> IHeaderMotoMecSharedPreferencesDatasource.getNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
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
    }

    private suspend fun assets(level: Int){

    }

}