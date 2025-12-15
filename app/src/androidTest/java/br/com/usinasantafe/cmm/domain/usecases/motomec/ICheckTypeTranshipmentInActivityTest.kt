package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionActivityDao
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionActivityRoomModel
import br.com.usinasantafe.cmm.lib.TypeActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ICheckTypeTranshipmentInActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckTypeTranshipmentInActivity

    @Inject
    lateinit var functionActivityDao: FunctionActivityDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_false_if_return_empty_list_in_FunctionActivityRepository_listByIdActivity() =
        runTest {
            val result = usecase(1)
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
    fun check_return_false_if_not_have_TypeActivity_TRANSHIPMENT_in_functionActivity_by_idActivity() =
        runTest {
            functionActivityDao.insertAll(
                listOf(
                    FunctionActivityRoomModel(
                        idFunctionActivity = 1,
                        idActivity = 1,
                        typeActivity = TypeActivity.REEL
                    )
                )
            )
            val result = usecase(1)
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
    fun check_return_true_if_have_TypeActivity_TRANSHIPMENT_in_functionActivity_by_idActivity() =
        runTest {
            functionActivityDao.insertAll(
                listOf(
                    FunctionActivityRoomModel(
                        idFunctionActivity = 1,
                        idActivity = 1,
                        typeActivity = TypeActivity.REEL
                    ),
                    FunctionActivityRoomModel(
                        idFunctionActivity = 2,
                        idActivity = 1,
                        typeActivity = TypeActivity.TRANSHIPMENT
                    )
                )
            )
            val result = usecase(1)
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