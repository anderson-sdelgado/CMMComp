package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemRespCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.RespItemCheckListSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.OptionRespCheckList
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class IDelLastRespItemCheckListTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: DelLastRespItemCheckList

    @Inject
    lateinit var itemRespCheckListSharedPreferencesdatasource: ItemRespCheckListSharedPreferencesDatasource

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_delete_last() =
        runTest {
            val model1 = RespItemCheckListSharedPreferencesModel(
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            val model2 = RespItemCheckListSharedPreferencesModel(
                idItem = 2,
                option = OptionRespCheckList.ANALYZE
            )
            val resultSave1 = itemRespCheckListSharedPreferencesdatasource.add(model1)
            assertEquals(
                resultSave1.isSuccess,
                true
            )
            assertEquals(
                resultSave1.getOrNull()!!,
                Unit
            )
            val resultSave2 = itemRespCheckListSharedPreferencesdatasource.add(model2)
            assertEquals(
                resultSave2.isSuccess,
                true
            )
            assertEquals(
                resultSave2.getOrNull()!!,
                Unit
            )
            val resultListBefore = itemRespCheckListSharedPreferencesdatasource.list()
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
                modelBefore1.idItem,
                1
            )
            assertEquals(
                modelBefore1.option,
                OptionRespCheckList.ACCORDING
            )
            val modelBefore2 = listBefore[1]
            assertEquals(
                modelBefore2.idItem,
                2
            )
            assertEquals(
                modelBefore2.option,
                OptionRespCheckList.ANALYZE
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
            val resultListAfter = itemRespCheckListSharedPreferencesdatasource.list()
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
                modelAfter1.idItem,
                1
            )
            assertEquals(
                modelAfter1.option,
                OptionRespCheckList.ACCORDING
            )
        }

}