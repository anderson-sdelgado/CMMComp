package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.external.room.dao.stable.RActivityStopDao
import br.com.usinasantafe.cmm.external.room.dao.stable.StopDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.NoteMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.RActivityStopRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.StopRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.NoteMotoMecSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class IGetStopListTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ListStop

    @Inject
    lateinit var noteMotoMecSharedPreferencesDatasource: NoteMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var rActivityStopDao: RActivityStopDao

    @Inject
    lateinit var stopDao: StopDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_have_none_data() =
        runTest {
            val result = usecase()
            Assert.assertEquals(
                result.isFailure,
                true
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetStopList -> INoteMotoMecRepository.getIdActivity -> INoteMotoMecSharedPreferencesDatasource.getIdActivity"
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_empty_list_if_not_have_data_in_table_r_activity_stop() =
        runTest {
            noteMotoMecSharedPreferencesDatasource.save(
                NoteMotoMecSharedPreferencesModel(
                    idActivity = 1
                )
            )
            val result = usecase()
            Assert.assertEquals(
                result.isSuccess,
                true
            )
            Assert.assertEquals(
                result.getOrNull()!!,
                emptyList<NoteMotoMecSharedPreferencesModel>()
            )
        }

    @Test
    fun check_return_empty_list_if_not_have_data_in_table_stop() =
        runTest {
            noteMotoMecSharedPreferencesDatasource.save(
                NoteMotoMecSharedPreferencesModel(
                    idActivity = 1
                )
            )
            rActivityStopDao.insertAll(
                listOf(
                    RActivityStopRoomModel(
                        idRActivityStop = 1,
                        idActivity = 1,
                        idStop = 1
                    ),
                    RActivityStopRoomModel(
                        idRActivityStop = 2,
                        idActivity = 1,
                        idStop = 2
                    ),
                    RActivityStopRoomModel(
                        idRActivityStop = 3,
                        idActivity = 1,
                        idStop = 3
                    ),
                    RActivityStopRoomModel(
                        idRActivityStop = 4,
                        idActivity = 2,
                        idStop = 4
                    )
                )
            )
            val result = usecase()
            Assert.assertEquals(
                result.isSuccess,
                true
            )
            Assert.assertEquals(
                result.getOrNull()!!,
                emptyList<NoteMotoMecSharedPreferencesModel>()
            )
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            noteMotoMecSharedPreferencesDatasource.save(
                NoteMotoMecSharedPreferencesModel(
                    idActivity = 1
                )
            )
            rActivityStopDao.insertAll(
                listOf(
                    RActivityStopRoomModel(
                        idRActivityStop = 1,
                        idActivity = 1,
                        idStop = 1
                    ),
                    RActivityStopRoomModel(
                        idRActivityStop = 2,
                        idActivity = 1,
                        idStop = 2
                    ),
                    RActivityStopRoomModel(
                        idRActivityStop = 3,
                        idActivity = 1,
                        idStop = 3
                    ),
                    RActivityStopRoomModel(
                        idRActivityStop = 4,
                        idActivity = 2,
                        idStop = 4
                    )
                )
            )
            stopDao.insertAll(
                listOf(
                    StopRoomModel(
                        idStop = 1,
                        codStop = 10,
                        descrStop = "PARADA 1"
                    ),
                    StopRoomModel(
                        idStop = 2,
                        codStop = 20,
                        descrStop = "PARADA 2"
                    ),
                    StopRoomModel(
                        idStop = 4,
                        codStop = 40,
                        descrStop = "PARADA 4"
                    ),
                )
            )
            val result = usecase()
            Assert.assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            Assert.assertEquals(
                list.size,
                2
            )
            val entity1 = list[0]
            Assert.assertEquals(
                entity1.id,
                1
            )
            Assert.assertEquals(
                entity1.descr,
                "10 - PARADA 1"
            )
            val entity2 = list[1]
            Assert.assertEquals(
                entity2.id,
                2
            )
            Assert.assertEquals(
                entity2.descr,
                "20 - PARADA 2"
            )

        }
}