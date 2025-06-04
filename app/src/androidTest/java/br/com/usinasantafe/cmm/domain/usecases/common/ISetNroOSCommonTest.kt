package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.NoteMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.NoteMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.FlowApp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ISetNroOSCommonTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetNroOSCommon

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var noteMotoMecSharedPreferencesDatasource: NoteMotoMecSharedPreferencesDatasource

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_data_if_data_is_empty_header_default() =
        runTest {
            val resultGetBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore,
                HeaderMotoMecSharedPreferencesModel()
            )
            val result = usecase("123456")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultGetAfter = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.nroOS,
                123456
            )
        }

    @Test
    fun check_data_change_if_table_has_data_header_default() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            val resultGetBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.nroOS,
                123456
            )
            val result = usecase("456789")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultGetAfter = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.nroOS,
                456789
            )
        }

    @Test
    fun check_return_data_if_data_is_empty_note_work() =
        runTest {
            val resultHeaderGetBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultHeaderGetBefore.isSuccess,
                true
            )
            val modelHeaderBefore = resultHeaderGetBefore.getOrNull()!!
            assertEquals(
                modelHeaderBefore,
                HeaderMotoMecSharedPreferencesModel()
            )
            val resultNoteGetBefore = noteMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetBefore.isSuccess,
                true
            )
            val modelNoteBefore = resultNoteGetBefore.getOrNull()!!
            assertEquals(
                modelNoteBefore,
                NoteMotoMecSharedPreferencesModel()
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultHeaderGetAfter = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultHeaderGetAfter.isSuccess,
                true
            )
            val modelHeaderAfter = resultHeaderGetAfter.getOrNull()!!
            assertEquals(
                modelHeaderAfter.nroOS,
                123456
            )
            val resultNoteGetAfter = noteMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetAfter.isSuccess,
                true
            )
            val modelNoteAfter = resultNoteGetAfter.getOrNull()!!
            assertEquals(
                modelNoteAfter.nroOS,
                123456
            )

        }

    @Test
    fun check_data_change_if_table_has_data_note_work() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            val resultHeaderGetBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultHeaderGetBefore.isSuccess,
                true
            )
            val modelHeaderBefore = resultHeaderGetBefore.getOrNull()!!
            assertEquals(
                modelHeaderBefore.nroOS,
                123456
            )
            noteMotoMecSharedPreferencesDatasource.save(
                NoteMotoMecSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            val resultNoteGetBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetBefore.isSuccess,
                true
            )
            val modelNoteBefore = resultNoteGetBefore.getOrNull()!!
            assertEquals(
                modelNoteBefore.nroOS,
                123456
            )
            val result = usecase(
                nroOS = "456789",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultHeaderGetAfter = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultHeaderGetAfter.isSuccess,
                true
            )
            val modelHeaderAfter = resultHeaderGetAfter.getOrNull()!!
            assertEquals(
                modelHeaderAfter.nroOS,
                456789
            )
            val resultNoteGetAfter = noteMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetAfter.isSuccess,
                true
            )
            val modelNoteAfter = resultNoteGetAfter.getOrNull()!!
            assertEquals(
                modelNoteAfter.nroOS,
                456789
            )
        }

}