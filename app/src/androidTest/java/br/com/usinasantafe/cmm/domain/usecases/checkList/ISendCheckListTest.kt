package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderCheckListDao
import br.com.usinasantafe.cmm.external.room.dao.variable.ItemRespCheckListDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderCheckListRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.ItemRespCheckListRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.OptionRespCheckList
import br.com.usinasantafe.cmm.lib.StatusSend
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class ISendCheckListTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SendCheckList

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerCheckListDao: HeaderCheckListDao

    @Inject
    lateinit var itemRespCheckListDao: ItemRespCheckListDao

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {
            hiltRule.inject()
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendCheckList -> IConfigRepository.getNumber -> IConfigSharedPreferencesDatasource.getNumber"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_to_send() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendCheckList -> ICheckListRepository.send -> ICheckListRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080"
            )
        }

    @Test
    fun check_return_failure_if_web_service_return_error() =
        runTest {

            val server = MockWebServer()
            server.start(8080)
            server.enqueue(
                MockResponse().setBody("""{"idServ":1,"idEquip":1}""")
            )

            hiltRule.inject()

            initialRegister(2)

            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendCheckList -> ICheckListRepository.send -> ICheckListRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
            )
            server.shutdown()
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            val server = MockWebServer()
            server.start(8080)
            server.enqueue(
                MockResponse().setBody("""[{"idServ":1,"id":1,"respItemList":[{"idServ":1,"id":1}]}]""")
            )

            hiltRule.inject()

            initialRegister(2)

            val headerListBefore = headerCheckListDao.all()
            assertEquals(
                headerListBefore.size,
                1
            )
            val headerModelBefore = headerListBefore[0]
            assertEquals(
                headerModelBefore.id,
                1
            )
            assertEquals(
                headerModelBefore.nroTurn,
                1
            )
            assertEquals(
                headerModelBefore.regOperator,
                1
            )
            assertEquals(
                headerModelBefore.nroEquip,
                1
            )
            assertEquals(
                headerModelBefore.dateHour,
                Date(1760711032)
            )
            assertEquals(
                headerModelBefore.statusSend,
                StatusSend.SEND
            )
            assertEquals(
                headerModelBefore.idServ,
                null
            )
            val respItemListBefore = itemRespCheckListDao.all()
            assertEquals(
                respItemListBefore.size,
                1
            )
            val respItemBefore = respItemListBefore[0]
            assertEquals(
                respItemBefore.id,
                1
            )
            assertEquals(
                respItemBefore.idHeader,
                1
            )
            assertEquals(
                respItemBefore.idItem,
                1
            )
            assertEquals(
                respItemBefore.option,
                OptionRespCheckList.ACCORDING
            )
            assertEquals(
                respItemBefore.idServ,
                null
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            val headerListAfter = headerCheckListDao.all()
            assertEquals(
                headerListAfter.size,
                1
            )
            val headerModelAfter = headerListAfter[0]
            assertEquals(
                headerModelAfter.id,
                1
            )
            assertEquals(
                headerModelAfter.nroTurn,
                1
            )
            assertEquals(
                headerModelAfter.regOperator,
                1
            )
            assertEquals(
                headerModelAfter.nroEquip,
                1
            )
            assertEquals(
                headerModelAfter.dateHour,
                Date(1760711032)
            )
            assertEquals(
                headerModelAfter.statusSend,
                StatusSend.SENT
            )
            assertEquals(
                headerModelAfter.idServ,
                1
            )
            val respItemListAfter = itemRespCheckListDao.all()
            assertEquals(
                respItemListAfter.size,
                1
            )
            val respItemAfter = respItemListAfter[0]
            assertEquals(
                respItemAfter.id,
                1
            )
            assertEquals(
                respItemAfter.idHeader,
                1
            )
            assertEquals(
                respItemAfter.idItem,
                1
            )
            assertEquals(
                respItemAfter.option,
                OptionRespCheckList.ACCORDING
            )
            assertEquals(
                respItemAfter.idServ,
                1
            )
            server.shutdown()
        }

    private suspend fun initialRegister(level: Int) {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                password = "12345",
                app = "PMM",
                version = "1.00",
                checkMotoMec = false,
                idServ = 1,
            )
        )

        if (level == 1) return

        headerCheckListDao.insert(
            HeaderCheckListRoomModel(
                id = 1,
                nroTurn = 1,
                regOperator = 1,
                nroEquip = 1,
                dateHour = Date(1760711032)
            )
        )

        itemRespCheckListDao.insert(
            ItemRespCheckListRoomModel(
                id = 1,
                idHeader = 1,
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
        )

        if (level == 2) return

    }
}