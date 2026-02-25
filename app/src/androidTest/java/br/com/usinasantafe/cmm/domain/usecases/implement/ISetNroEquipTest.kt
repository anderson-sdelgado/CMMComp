package br.com.usinasantafe.cmm.domain.usecases.implement

import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ImplementSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ImplementSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetNroEquipTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetNroEquip

    @Inject
    lateinit var implementSharedPreferencesDatasource: ImplementSharedPreferencesDatasource

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_value_of_field_is_incorrect() =
        runTest {
            val result = usecase(
                nroEquip = "200a",
                pos = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipImplement -> toLong"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"200a\""
            )
        }

    @Test
    fun check_add_implement_1() =
        runTest {
            val resultListBefore = implementSharedPreferencesDatasource.list()
            assertEquals(
                resultListBefore.isSuccess,
                true
            )
            val listBefore = resultListBefore.getOrNull()!!
            assertEquals(
                listBefore.size,
                0
            )
            val result = usecase(
                nroEquip = "2200",
                pos = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val resultListAfter = implementSharedPreferencesDatasource.list()
            assertEquals(
                resultListAfter.isSuccess,
                true
            )
            val listAfter = resultListAfter.getOrNull()!!
            assertEquals(
                listAfter.size,
                1
            )
            val model = listAfter[0]
            assertEquals(
                model.nroEquip,
                2200
            )
            assertEquals(
                model.pos,
                1
            )
        }

    @Test
    fun check_add_implement_2() =
        runTest {
            implementSharedPreferencesDatasource.add(
                ImplementSharedPreferencesModel(
                    nroEquip = 2200,
                    pos = 1
                )
            )
            val resultListBefore = implementSharedPreferencesDatasource.list()
            assertEquals(
                resultListBefore.isSuccess,
                true
            )
            val listBefore = resultListBefore.getOrNull()!!
            assertEquals(
                listBefore.size,
                1
            )
            val modelBefore1 = listBefore[0]
            assertEquals(
                modelBefore1.nroEquip,
                2200
            )
            assertEquals(
                modelBefore1.pos,
                1
            )
            val result = usecase(
                nroEquip = "1000",
                pos = 2
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val resultListAfter = implementSharedPreferencesDatasource.list()
            assertEquals(
                resultListAfter.isSuccess,
                true
            )
            val listAfter = resultListAfter.getOrNull()!!
            assertEquals(
                listAfter.size,
                2
            )
            val modelAfter1 = listAfter[0]
            assertEquals(
                modelAfter1.nroEquip,
                2200
            )
            assertEquals(
                modelAfter1.pos,
                1
            )
            val modelAfter2 = listAfter[1]
            assertEquals(
                modelAfter2.nroEquip,
                1000
            )
            assertEquals(
                modelAfter2.pos,
                2
            )

        }


    @Test
    fun check_clean_data_in_add_implement_1() =
        runTest {
            implementSharedPreferencesDatasource.add(
                ImplementSharedPreferencesModel(
                    nroEquip = 2200,
                    pos = 1
                )
            )
            implementSharedPreferencesDatasource.add(
                ImplementSharedPreferencesModel(
                    nroEquip = 1000,
                    pos = 2
                )
            )
            val resultListBefore = implementSharedPreferencesDatasource.list()
            assertEquals(
                resultListBefore.isSuccess,
                true
            )
            val listBefore = resultListBefore.getOrNull()!!
            assertEquals(
                listBefore.size,
                2
            )
            val modelBefore1 = listBefore[0]
            assertEquals(
                modelBefore1.nroEquip,
                2200
            )
            assertEquals(
                modelBefore1.pos,
                1
            )
            val modelBefore2 = listBefore[1]
            assertEquals(
                modelBefore2.nroEquip,
                1000
            )
            assertEquals(
                modelBefore2.pos,
                2
            )
            val result = usecase(
                nroEquip = "500",
                pos = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val resultListAfter = implementSharedPreferencesDatasource.list()
            assertEquals(
                resultListAfter.isSuccess,
                true
            )
            val listAfter = resultListAfter.getOrNull()!!
            assertEquals(
                listAfter.size,
                1
            )
            val modelAfter1 = listAfter[0]
            assertEquals(
                modelAfter1.nroEquip,
                500
            )
            assertEquals(
                modelAfter1.pos,
                1
            )
        }

}