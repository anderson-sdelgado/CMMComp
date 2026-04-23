package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.external.room.dao.stable.OSDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ICheckIdReleaseTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckIdRelease

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var osDao: OSDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_input_is_incorrect() =
        runTest {
            val result = usecase("dfs4512sdfas")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckRelease -> toInt"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"dfs4512sdfas\""
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_shared_preferences() =
        runTest {
            val result = usecase("20000")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckRelease -> IMotoMecRepository.getNroOSHeader -> IHeaderMotoMecSharedPreferencesDatasource.getNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: nroOS is required"
            )
        }
    
    @Test
    fun check_return_false_if_not_have_data_in_os_room() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            val result = usecase("20000")
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
    fun check_return_false_if_nro_is_different() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        id = 1,
                        nro = 456789,
                        idRelease = 20000,
                    ),
                )
            )
            val result = usecase("20000")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                false
            )
        }

    @Test
    fun check_return_false_if_idRelease_is_different() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        id = 1,
                        nro = 456789,
                        idRelease = 20000,
                    ),
                    OSRoomModel(
                        id = 2,
                        nro = 123456,
                        idRelease = 10000,
                    ),
                )
            )
            val result = usecase("20000")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                false
            )
        }

    @Test
    fun check_return_true_if_have_data_research() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        id = 1,
                        nro = 456789,
                        idRelease = 20000,
                    ),
                    OSRoomModel(
                        id = 2,
                        nro = 123456,
                        idRelease = 10000,
                    ),
                    OSRoomModel(
                        id = 3,
                        nro = 123456,
                        idRelease = 20000,
                    ),
                )
            )
            val result = usecase("20000")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                true
            )
        }

}