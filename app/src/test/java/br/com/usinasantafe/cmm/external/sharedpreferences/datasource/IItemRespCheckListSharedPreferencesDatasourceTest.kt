package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.RespItemCheckListSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.OptionRespCheckList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.intArrayOf
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IItemRespCheckListSharedPreferencesDatasourceTest {

    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource: IItemRespCheckListSharedPreferencesDatasource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        datasource = IItemRespCheckListSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `Check add data, get data and clean table`() =
        runTest {
            val model1 = RespItemCheckListSharedPreferencesModel(
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            val model2 = RespItemCheckListSharedPreferencesModel(
                idItem = 2,
                option = OptionRespCheckList.ANALYZE
            )
            val resultAdd1 = datasource.add(model1)
            assertEquals(
                resultAdd1.isSuccess,
                true
            )
            assertEquals(
                resultAdd1.getOrNull()!!,
                true
            )
            val resultAdd2 = datasource.add(model2)
            assertEquals(
                resultAdd2.isSuccess,
                true
            )
            assertEquals(
                resultAdd2.getOrNull()!!,
                true
            )
            val resultList = datasource.list()
            assertEquals(
                resultList.isSuccess,
                true
            )
            val list = resultList.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            val modelResult1 = list[0]
            assertEquals(
                modelResult1.idItem,
                1
            )
            assertEquals(
                modelResult1.option,
                OptionRespCheckList.ACCORDING
            )
            val modelResult2 = list[1]
            assertEquals(
                modelResult2.idItem,
                2
            )
            assertEquals(
                modelResult2.option,
                OptionRespCheckList.ANALYZE
            )
            val resultClean = datasource.clean()
            assertEquals(
                resultClean.isSuccess,
                true
            )
            assertEquals(
                resultClean.getOrNull()!!,
                true
            )
            val resultListAfterClean = datasource.list()
            assertEquals(
                resultListAfterClean.isSuccess,
                true
            )
            val listAfterClean = resultListAfterClean.getOrNull()!!
            assertEquals(
                listAfterClean.size,
                0
            )
        }


    @Test
    fun `Check delete last`() =
        runTest {
            val model1 = RespItemCheckListSharedPreferencesModel(
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            val model2 = RespItemCheckListSharedPreferencesModel(
                idItem = 2,
                option = OptionRespCheckList.ANALYZE
            )
            val resultSave1 = datasource.add(model1)
            assertEquals(
                resultSave1.isSuccess,
                true
            )
            assertEquals(
                resultSave1.getOrNull()!!,
                true
            )
            val resultSave2 = datasource.add(model2)
            assertEquals(
                resultSave2.isSuccess,
                true
            )
            assertEquals(
                resultSave2.getOrNull()!!,
                true
            )
            val resultListBefore = datasource.list()
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
            val resultDeleteLast = datasource.delLast()
            assertEquals(
                resultDeleteLast.isSuccess,
                true
            )
            assertEquals(
                resultDeleteLast.getOrNull()!!,
                true
            )
            val resultListAfter = datasource.list()
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