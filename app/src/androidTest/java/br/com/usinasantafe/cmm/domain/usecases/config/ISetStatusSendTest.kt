package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.lib.StatusSend
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetStatusSendTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ISetStatusSend

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_add_if_not_have_data() =
        runTest {
            val result = usecase(StatusSend.SEND)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultGet = configSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            assertEquals(
                resultGet.getOrNull()!!.statusSend,
                StatusSend.SEND
            )
        }

}