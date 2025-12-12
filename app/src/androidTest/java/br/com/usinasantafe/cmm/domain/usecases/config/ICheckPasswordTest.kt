package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class ICheckPasswordTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckPassword

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_true_if_not_have_data() =
        runTest {
            val result = usecase("12345")
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
    fun check_return_true_and_password_correct() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    app = "PMM",
                    idServ = 1,
                    checkMotoMec = true,
                    version = "1.00",
                    number = 123456,
                    password = "12345"
                )
            )
            val result = usecase("12345")
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
    fun check_return_false_and_password_incorrect() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    app = "PMM",
                    idServ = 1,
                    checkMotoMec = true,
                    version = "1.00",
                    number = 123456,
                    password = "12345"
                )
            )
            val result = usecase("123456")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

}