package br.com.usinasantafe.cmm.domain.usecases.motomec

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class IGetItemMenuListTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: IListItemMenu

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            val result = usecase("pmm")
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                6
            )
            val entity = list[0]
            assertEquals(
                entity.id,
                1
            )
            assertEquals(
                entity.descr,
                "TRABALHANDO"
            )

        }

}