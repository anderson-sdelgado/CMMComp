package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.stable.ColabDao
import br.com.usinasantafe.cmm.infra.models.room.stable.ColabRoomModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ICheckRegOperatorTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckRegOperator

    @Inject
    lateinit var colabDao: ColabDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_matric_is_incorrect() =
        runTest {
            val result = usecase("12345a")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckRegOperator"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"12345a\""
            )
        }

    @Test
    fun check_return_false_if_matric_not_have_in_table() =
        runTest {
            val colabList = listOf(
                ColabRoomModel(
                    regColab = 19759,
                    nameColab = "ANDERSON DA SILVA DELGADO"
                )
            )
            colabDao.insertAll(colabList)
            val result = usecase("12345")
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
    fun check_return_true_if_matric_have_in_table() =
        runTest {
            val colabList = listOf(
                ColabRoomModel(
                    regColab = 19759,
                    nameColab = "ANDERSON DA SILVA DELGADO"
                )
            )
            colabDao.insertAll(colabList)
            val result = usecase("19759")
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