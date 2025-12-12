package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ICheckUpdateCheckListTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckUpdateCheckList

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Test
    fun check_return_failure_if_not_have_data_in_config_shared_preferences() =
        runTest {
            hiltRule.inject()
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckUpdateCheckList -> IConfigRepository.getNroEquip -> IConfigSharedPreferencesDatasource.getNroEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }
//
//    @Test
//    fun check_return_failure_if_not_have_data_in_config_to_get_token() =
//        runTest {
//            hiltRule.inject()
//            configSharedPreferencesDatasource.save(
//                ConfigSharedPreferencesModel(
//                    nroEquip = 10L
//                )
//            )
//            val result = usecase()
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "ICheckUpdateCheckList -> IGetToken"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.NullPointerException"
//            )
//        }
//
//    @Test
//    fun check_return_failure_if_not_have_data_without_return_web_service() =
//        runTest {
//            hiltRule.inject()
//            configSharedPreferencesDatasource.save(
//                ConfigSharedPreferencesModel(
//                    nroEquip = 10L,
//                    app = "PMM",
//                    idServ = 1,
//                    number = 1,
//                    version = "1.0",
//                )
//            )
//            val result = usecase()
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "ICheckUpdateCheckList -> IItemCheckListRepository.checkUpdateByNroEquip -> IItemCheckListRetrofitDatasource.checkUpdateByNroEquip"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080"
//            )
//        }
//
//    @Test
//    fun check_return_true_if_return_web_service_is_qtd_is_greater_than_zero() =
//        runTest {
//            val mockWebServer = MockWebServer()
//            mockWebServer.start()
//            mockWebServer.enqueue(
//                MockResponse().setBody("""{"qtd":1}""")
//            )
//            BaseUrlModuleTest.url = mockWebServer.url("/").toString()
//            hiltRule.inject()
//            configSharedPreferencesDatasource.save(
//                ConfigSharedPreferencesModel(
//                    nroEquip = 10L,
//                    app = "PMM",
//                    idServ = 1,
//                    number = 1,
//                    version = "1.0",
//                )
//            )
//            val result = usecase()
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                true
//            )
//        }
//
//    @Test
//    fun check_return_false_if_return_web_service_is_qtd_is_zero() =
//        runTest {
//            val mockWebServer = MockWebServer()
//            mockWebServer.start()
//            mockWebServer.enqueue(
//                MockResponse().setBody("""{"qtd":0}""")
//            )
//            BaseUrlModuleTest.url = mockWebServer.url("/").toString()
//            hiltRule.inject()
//            configSharedPreferencesDatasource.save(
//                ConfigSharedPreferencesModel(
//                    nroEquip = 10L,
//                    app = "PMM",
//                    idServ = 1,
//                    number = 1,
//                    version = "1.0",
//                )
//            )
//            val result = usecase()
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                false
//            )
//        }
}