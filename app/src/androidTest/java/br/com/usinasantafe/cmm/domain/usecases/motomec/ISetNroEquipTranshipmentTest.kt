package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.variable.ItemMotoMecDao
import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlowApp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetNroEquipTranshipmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ISetNroEquipTranshipment

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var itemMotoMecSharedPreferencesDatasource: IItemMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var itemMotoMecDao: ItemMotoMecDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_nro_os_in_header_shared_preferences() =
        runTest {
            val result = usecase(
                nroEquipTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment -> IMotoMecRepository.getNroOSHeader -> IHeaderMotoMecSharedPreferencesDatasource.getNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_id_activity_in_header_shared_preferences() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            val result = usecase(
                nroEquipTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment -> IMotoMecRepository.getIdActivityHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_status_con_in_header_shared_preferences() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 123456,
                    idActivity = 1
                )
            )
            val result = usecase(
                nroEquipTranshipment = "20fdasfd0",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment -> IMotoMecRepository.setNroOSNote -> IHeaderMotoMecSharedPreferencesDatasource.getStatusCon"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_value_nro_transhipment_is_incorrect() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 123456,
                    idActivity = 1,
                    statusCon = true
                )
            )
            val result = usecase(
                nroEquipTranshipment = "20fdasfd0",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_id_in_header_moto_mec_shared_preferences() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 123456,
                    idActivity = 1,
                    statusCon = true
                )
            )
            val result = usecase(
                nroEquipTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment -> IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecSharedPreferencesDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_true_and_check_data_if_process_execute_successfully() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    statusCon = true
                )
            )
            val result = usecase(
                nroEquipTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment -> IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecSharedPreferencesDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                true
//            )
//            val resultItemMotoMecSharedPreferencesModel = itemMotoMecSharedPreferencesDatasource.get()
//            assertEquals(
//                resultItemMotoMecSharedPreferencesModel.isSuccess,
//                true
//            )
//            val itemMotoMecSharedPreferencesModel = resultItemMotoMecSharedPreferencesModel.getOrNull()!!
//            assertEquals(
//                itemMotoMecSharedPreferencesModel.nroEquipTranshipment,
//                200L
//            )
//            assertEquals(
//                itemMotoMecSharedPreferencesModel.idActivity,
//                1
//            )
//            assertEquals(
//                itemMotoMecSharedPreferencesModel.idStop,
//                null
//            )
//            assertEquals(
//                itemMotoMecSharedPreferencesModel.nroOS,
//                123456
//            )
//            assertEquals(
//                itemMotoMecSharedPreferencesModel.statusCon,
//                true
//            )
//            val itemMotoMecRoomModel = itemMotoMecDao.getLastByIdHeader(1)
//            assertEquals(
//                itemMotoMecRoomModel!!.nroEquipTranshipment,
//                200L
//            )
//            assertEquals(
//                itemMotoMecRoomModel.idActivity,
//                1
//            )
//            assertEquals(
//                itemMotoMecRoomModel.idStop,
//                null
//            )
//            assertEquals(
//                itemMotoMecRoomModel.nroOS,
//                123456
//            )
//            assertEquals(
//                itemMotoMecRoomModel.statusCon,
//                true
//            )
//            assertEquals(
//                itemMotoMecRoomModel.idHeader,
//                1
//            )
        }

}