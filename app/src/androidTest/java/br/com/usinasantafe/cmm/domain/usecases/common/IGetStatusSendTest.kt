package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.StatusSend
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class IGetStatusSendTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: GetStatusSend

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusSend.STARTED
            )
        }

    @Test
    fun check_return_true_and_data_altered() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
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
                StatusSend.SENT
            )
        }
}