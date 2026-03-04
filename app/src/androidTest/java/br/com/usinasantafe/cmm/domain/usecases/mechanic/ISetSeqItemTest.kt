package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.external.room.dao.variable.MechanicDao
import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IMechanicSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.MechanicSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetSeqItemTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetSeqItem

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var mechanicSharedPreferencesDatasource: IMechanicSharedPreferencesDatasource

    @Inject
    lateinit var mechanicDao: MechanicDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_header_shared_preferences() =
        runTest {
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetSeqItem -> IMotoMecRepository.getIdHeaderPointing -> IHeaderMotoMecSharedPreferencesDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_mechanic_shared_preferences() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetSeqItem -> IMechanicRepository.save -> entityToRoomModel"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: nroOS is required"
            )
        }
    
    @Test
    fun check_insert_if_process_execute_successfully() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1
                )
            )
            mechanicSharedPreferencesDatasource.save(
                MechanicSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            val listBefore = mechanicDao.all()
            assertEquals(
                listBefore.size,
                0
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val listAfter = mechanicDao.all()
            assertEquals(
                listAfter.size,
                1
            )
            val model = listAfter[0]
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.seqItem,
                1
            )
        }

}