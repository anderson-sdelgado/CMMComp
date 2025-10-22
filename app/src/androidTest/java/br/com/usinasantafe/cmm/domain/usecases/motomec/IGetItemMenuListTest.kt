package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.usecases.common.IListItemMenu
import br.com.usinasantafe.cmm.utils.TypeView
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class IGetItemMenuListTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: IListItemMenu

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            hiltRule.inject()
            val result = usecase()
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
                entity.title,
                "TRABALHANDO"
            )
            assertEquals(
                entity.type,
                TypeView.ITEM
            )
        }

}