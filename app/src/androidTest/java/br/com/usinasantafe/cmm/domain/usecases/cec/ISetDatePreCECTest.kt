package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IPreCECSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.PreCECSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.ECM
import br.com.usinasantafe.cmm.lib.EXIT_FIELD
import br.com.usinasantafe.cmm.lib.EXIT_MILL
import br.com.usinasantafe.cmm.lib.FIELD_ARRIVAL
import br.com.usinasantafe.cmm.lib.StatusPreCEC
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import java.util.Date
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetDatePreCECTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ISetDatePreCEC

    @Inject
    lateinit var preCECSharedPreferencesDatasource: IPreCECSharedPreferencesDatasource

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_StatusPreCEC_EXIT_MILL_if_dateMillExit_is_null_and_type_is_FIELD_ARRIVAL() =
        runTest {
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to FIELD_ARRIVAL,
                    type = 1 to FIELD_ARRIVAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.EXIT_MILL
            )
        }

    @Test
    fun check_return_StatusPreCEC_EXIT_MILL_if_dateMillExit_is_null_and_type_is_EXIT_FIELD() =
        runTest {
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_FIELD,
                    type = 1 to EXIT_FIELD,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.EXIT_MILL
            )
        }

    @Test
    fun check_return_StatusPreCEC_FIELD_ARRIVAL_if_dateFieldArrival_is_null_and_type_is_EXIT_MILL() =
        runTest {
            register(1)
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_MILL,
                    type = 1 to EXIT_MILL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.FIELD_ARRIVAL
            )
        }

    @Test
    fun check_return_StatusPreCEC_FIELD_ARRIVAL_if_dateFieldArrival_is_null_and_type_is_EXIT_FIELD() =
        runTest {
            register(1)
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_FIELD,
                    type = 1 to EXIT_FIELD,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.FIELD_ARRIVAL
            )
        }

    @Test
    fun check_return_StatusPreCEC_EXIT_FIELD_if_dateFieldExit_is_null_and_type_is_EXIT_MILL() =
        runTest {
            register(2)
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_MILL,
                    type = 1 to EXIT_MILL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.EXIT_FIELD
            )
        }

    @Test
    fun check_return_StatusPreCEC_EXIT_FIELD_if_dateFieldExit_is_null_and_type_is_FIELD_ARRIVAL() =
        runTest {
            register(2)
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to FIELD_ARRIVAL,
                    type = 1 to FIELD_ARRIVAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.EXIT_FIELD
            )
        }

    @Test
    fun check_set_dateExitMill() =
        runTest {
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_MILL,
                    type = 1 to EXIT_MILL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.EXIT_MILL
            )
            val resultGet = preCECSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.dateExitMill != null,
                true
            )
            assertEquals(
                model.dateFieldArrival == null,
                true
            )
            assertEquals(
                model.dateExitField == null,
                true
            )
        }

    @Test
    fun check_set_dateFieldArrival() =
        runTest {
            val resultSetDateExitMill = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_MILL,
                    type = 1 to EXIT_MILL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                resultSetDateExitMill.isSuccess,
                true
            )
            assertEquals(
                resultSetDateExitMill.getOrNull()!!,
                StatusPreCEC.EXIT_MILL
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to FIELD_ARRIVAL,
                    type = 1 to FIELD_ARRIVAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.FIELD_ARRIVAL
            )
            val resultGet = preCECSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.dateExitMill != null,
                true
            )
            assertEquals(
                model.dateFieldArrival != null,
                true
            )
            assertEquals(
                model.dateExitField == null,
                true
            )
        }

    @Test
    fun check_set_dateExitField() =
        runTest {
            val resultSetDateExitMill = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_MILL,
                    type = 1 to EXIT_MILL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                resultSetDateExitMill.isSuccess,
                true
            )
            assertEquals(
                resultSetDateExitMill.getOrNull()!!,
                StatusPreCEC.EXIT_MILL
            )
            val resultSetDateFieldArrival = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to FIELD_ARRIVAL,
                    type = 1 to FIELD_ARRIVAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                resultSetDateFieldArrival.isSuccess,
                true
            )
            assertEquals(
                resultSetDateFieldArrival.getOrNull()!!,
                StatusPreCEC.FIELD_ARRIVAL
            )
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to EXIT_FIELD,
                    type = 1 to EXIT_FIELD,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusPreCEC.EXIT_FIELD
            )
            val resultGet = preCECSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.dateExitMill != null,
                true
            )
            assertEquals(
                model.dateFieldArrival != null,
                true
            )
            assertEquals(
                model.dateExitField != null,
                true
            )
        }

    private suspend fun register(level: Int) {

        preCECSharedPreferencesDatasource.save(
            PreCECSharedPreferencesModel(
                dateExitMill = Date()
            )
        )

        if(level == 1) return

        preCECSharedPreferencesDatasource.save(
            PreCECSharedPreferencesModel(
                dateExitMill = Date(),
                dateFieldArrival = Date()
            )
        )

        if(level == 2) return
    }


}