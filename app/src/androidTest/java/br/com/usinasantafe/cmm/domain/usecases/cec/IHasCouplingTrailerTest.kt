package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.TrailerSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.TrailerSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class IHasCouplingTrailerTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: HasCouplingTrailer

    @Inject
    lateinit var trailerSharedPreferencesDatasource: TrailerSharedPreferencesDatasource

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_false_if_not_have_data() =
        runTest {
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
    fun check_return_true_if_have_data() =
        runTest {
            trailerSharedPreferencesDatasource.add(
                TrailerSharedPreferencesModel(
                    idEquip = 1,
                    pos = 1
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
}