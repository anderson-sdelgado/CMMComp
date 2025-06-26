package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.external.room.dao.stable.ActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.OSDao
import br.com.usinasantafe.cmm.external.room.dao.stable.REquipActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ROSActivityDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ROSActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class IListActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ListActivity

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var rEquipActivityDao: REquipActivityDao

    @Inject
    lateinit var activityDao: ActivityDao

    @Inject
    lateinit var osDao: OSDao

    @Inject
    lateinit var rOSActivityDao: ROSActivityDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_table_config() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetActivityList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_table_header_moto_mec() =
        runTest {
            val resultConfigSave = configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1,
                )
            )
            assertEquals(
                resultConfigSave.isSuccess,
                true
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetActivityList -> IHeaderMotoMecRepository.getNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_list_empty_if_not_have_data_in_all_table() =
        runTest {
            val resultConfigSave = configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1,
                )
            )
            assertEquals(
                resultConfigSave.isSuccess,
                true
            )
            val resultHeaderMotoMecSave = headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 1,
                )
            )
            assertEquals(
                resultHeaderMotoMecSave.isSuccess,
                true
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!.size,
                0
            )
        }

    @Test
    fun check_return_list_empty_if_not_have_data_in_table_activity_of_table_r_equip_activity() =
        runTest {
            val resultConfigSave = configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1,
                )
            )
            assertEquals(
                resultConfigSave.isSuccess,
                true
            )
            val resultHeaderMotoMecSave = headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 1,
                )
            )
            assertEquals(
                resultHeaderMotoMecSave.isSuccess,
                true
            )
            rEquipActivityDao.insertAll(
                listOf(
                    REquipActivityRoomModel(
                        idREquipActivity = 1,
                        idEquip = 1,
                        idActivity = 1
                    ),
                    REquipActivityRoomModel(
                        idREquipActivity = 2,
                        idEquip = 1,
                        idActivity = 2
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!.size,
                0
            )
        }

    @Test
    fun check_return_list_if_have_data_in_table_activity_of_table_r_equip_activity() =
        runTest {
            val resultConfigSave = configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1,
                )
            )
            assertEquals(
                resultConfigSave.isSuccess,
                true
            )
            val resultHeaderMotoMecSave = headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 1,
                )
            )
            assertEquals(
                resultHeaderMotoMecSave.isSuccess,
                true
            )
            rEquipActivityDao.insertAll(
                listOf(
                    REquipActivityRoomModel(
                        idREquipActivity = 1,
                        idEquip = 1,
                        idActivity = 1
                    ),
                    REquipActivityRoomModel(
                        idREquipActivity = 2,
                        idEquip = 1,
                        idActivity = 2
                    ),
                    REquipActivityRoomModel(
                        idREquipActivity = 3,
                        idEquip = 2,
                        idActivity = 3
                    )
                )
            )
            activityDao.insertAll(
                listOf(
                    ActivityRoomModel(
                        idActivity = 1,
                        codActivity = 10,
                        descrActivity = "ATIVIDADE 1"
                    ),
                    ActivityRoomModel(
                        idActivity = 2,
                        codActivity = 20,
                        descrActivity = "ATIVIDADE 2"
                    ),
                    ActivityRoomModel(
                        idActivity = 3,
                        codActivity = 30,
                        descrActivity = "ATIVIDADE 2"
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idActivity,
                1
            )
            assertEquals(
                entity1.codActivity,
                10
            )
            assertEquals(
                entity1.descrActivity,
                "ATIVIDADE 1"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idActivity,
                2
            )
            assertEquals(
                entity2.codActivity,
                20
            )
            assertEquals(
                entity2.descrActivity,
                "ATIVIDADE 2"
            )
        }

    @Test
    fun check_return_list_empty_if_have_data_in_table_activity_of_table_r_equip_activity_and_not_have_data_in_table_r_os_activity() =
        runTest {
            val resultConfigSave = configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1,
                )
            )
            assertEquals(
                resultConfigSave.isSuccess,
                true
            )
            val resultHeaderMotoMecSave = headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 123456,
                )
            )
            assertEquals(
                resultHeaderMotoMecSave.isSuccess,
                true
            )
            rEquipActivityDao.insertAll(
                listOf(
                    REquipActivityRoomModel(
                        idREquipActivity = 1,
                        idEquip = 1,
                        idActivity = 1
                    ),
                    REquipActivityRoomModel(
                        idREquipActivity = 2,
                        idEquip = 1,
                        idActivity = 2
                    ),
                    REquipActivityRoomModel(
                        idREquipActivity = 3,
                        idEquip = 2,
                        idActivity = 3
                    )
                )
            )
            activityDao.insertAll(
                listOf(
                    ActivityRoomModel(
                        idActivity = 1,
                        codActivity = 10,
                        descrActivity = "ATIVIDADE 1"
                    ),
                    ActivityRoomModel(
                        idActivity = 2,
                        codActivity = 20,
                        descrActivity = "ATIVIDADE 2"
                    ),
                    ActivityRoomModel(
                        idActivity = 3,
                        codActivity = 30,
                        descrActivity = "ATIVIDADE 2"
                    )
                )
            )
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 123456,
                        idLibOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5,
                        idEquip = 30
                    ),
                    OSRoomModel(
                        idOS = 2,
                        nroOS = 456789,
                        idLibOS = 11,
                        idPropAgr = 21,
                        areaOS = 100.0,
                        idEquip = 31
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!.size,
                0
            )
        }

    @Test
    fun check_return_list_empty_if_have_data_in_table_activity_of_table_r_equip_activity_and_different_data_in_table_r_os_activity() =
        runTest {
            val resultConfigSave = configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1,
                )
            )
            assertEquals(
                resultConfigSave.isSuccess,
                true
            )
            val resultHeaderMotoMecSave = headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 123456,
                )
            )
            assertEquals(
                resultHeaderMotoMecSave.isSuccess,
                true
            )
            rEquipActivityDao.insertAll(
                listOf(
                    REquipActivityRoomModel(
                        idREquipActivity = 1,
                        idEquip = 1,
                        idActivity = 1
                    ),
                    REquipActivityRoomModel(
                        idREquipActivity = 2,
                        idEquip = 1,
                        idActivity = 2
                    ),
                    REquipActivityRoomModel(
                        idREquipActivity = 3,
                        idEquip = 2,
                        idActivity = 3
                    )
                )
            )
            activityDao.insertAll(
                listOf(
                    ActivityRoomModel(
                        idActivity = 1,
                        codActivity = 10,
                        descrActivity = "ATIVIDADE 1"
                    ),
                    ActivityRoomModel(
                        idActivity = 2,
                        codActivity = 20,
                        descrActivity = "ATIVIDADE 2"
                    ),
                    ActivityRoomModel(
                        idActivity = 3,
                        codActivity = 30,
                        descrActivity = "ATIVIDADE 2"
                    )
                )
            )
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 123456,
                        idLibOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5,
                        idEquip = 30
                    ),
                    OSRoomModel(
                        idOS = 2,
                        nroOS = 456789,
                        idLibOS = 11,
                        idPropAgr = 21,
                        areaOS = 100.0,
                        idEquip = 31
                    )
                )
            )
            rOSActivityDao.insertAll(
                listOf(
                    ROSActivityRoomModel(
                        idROSActivity = 1,
                        idOS = 1,
                        idActivity = 4
                    ),
                    ROSActivityRoomModel(
                        idROSActivity = 2,
                        idOS = 1,
                        idActivity = 5
                    ),
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!.size,
                0
            )
        }

    @Test
    fun check_return_list_if_data_is_correct() =
        runTest {
            val resultConfigSave = configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1,
                )
            )
            assertEquals(
                resultConfigSave.isSuccess,
                true
            )
            val resultHeaderMotoMecSave = headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 123456,
                )
            )
            assertEquals(
                resultHeaderMotoMecSave.isSuccess,
                true
            )
            rEquipActivityDao.insertAll(
                listOf(
                    REquipActivityRoomModel(
                        idREquipActivity = 1,
                        idEquip = 1,
                        idActivity = 1
                    ),
                    REquipActivityRoomModel(
                        idREquipActivity = 2,
                        idEquip = 1,
                        idActivity = 2
                    ),
                    REquipActivityRoomModel(
                        idREquipActivity = 3,
                        idEquip = 1,
                        idActivity = 3
                    ),
                    REquipActivityRoomModel(
                        idREquipActivity = 4,
                        idEquip = 4,
                        idActivity = 3
                    ),
                    REquipActivityRoomModel(
                        idREquipActivity = 5,
                        idEquip = 5,
                        idActivity = 3
                    )
                )
            )
            activityDao.insertAll(
                listOf(
                    ActivityRoomModel(
                        idActivity = 1,
                        codActivity = 10,
                        descrActivity = "ATIVIDADE 1"
                    ),
                    ActivityRoomModel(
                        idActivity = 2,
                        codActivity = 20,
                        descrActivity = "ATIVIDADE 2"
                    ),
                    ActivityRoomModel(
                        idActivity = 3,
                        codActivity = 30,
                        descrActivity = "ATIVIDADE 3"
                    )
                )
            )
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 123456,
                        idLibOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5,
                        idEquip = 30
                    ),
                    OSRoomModel(
                        idOS = 2,
                        nroOS = 456789,
                        idLibOS = 11,
                        idPropAgr = 21,
                        areaOS = 100.0,
                        idEquip = 31
                    )
                )
            )
            rOSActivityDao.insertAll(
                listOf(
                    ROSActivityRoomModel(
                        idROSActivity = 1,
                        idOS = 1,
                        idActivity = 2
                    ),
                    ROSActivityRoomModel(
                        idROSActivity = 2,
                        idOS = 1,
                        idActivity = 3
                    ),
                    ROSActivityRoomModel(
                        idROSActivity = 3,
                        idOS = 1,
                        idActivity = 4
                    ),
                    ROSActivityRoomModel(
                        idROSActivity = 4,
                        idOS = 2,
                        idActivity = 3
                    ),
                    ROSActivityRoomModel(
                        idROSActivity = 5,
                        idOS = 3,
                        idActivity = 4
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idActivity,
                2
            )
            assertEquals(
                entity1.codActivity,
                20
            )
            assertEquals(
                entity1.descrActivity,
                "ATIVIDADE 2"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idActivity,
                3
            )
            assertEquals(
                entity2.codActivity,
                30
            )
            assertEquals(
                entity2.descrActivity,
                "ATIVIDADE 3"
            )
        }
}