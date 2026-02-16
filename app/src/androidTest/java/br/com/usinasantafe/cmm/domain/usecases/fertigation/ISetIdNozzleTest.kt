package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ItemMotoMecSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetIdNozzleTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetIdNozzle

    @Inject
    lateinit var itemMotoMecSharedPreferencesDatasource: ItemMotoMecSharedPreferencesDatasource

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_data_if_data_is_empty() =
        runTest {
            val resultGetBefore = itemMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore,
                ItemMotoMecSharedPreferencesModel()
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            val resultGetAfter = itemMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.idNozzle,
                1
            )
        }


    @Test
    fun check_data_change_if_table_has_data() =
        runTest {
            itemMotoMecSharedPreferencesDatasource.save(
                ItemMotoMecSharedPreferencesModel(
                    idNozzle = 1
                )
            )
            val resultGetBefore = itemMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore,
                ItemMotoMecSharedPreferencesModel(
                    idNozzle = 1
                )
            )
            val result = usecase(2)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            val resultGetAfter = itemMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.idNozzle,
                2
            )
        }
}