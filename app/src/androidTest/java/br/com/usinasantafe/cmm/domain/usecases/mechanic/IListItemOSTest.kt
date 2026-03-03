package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.external.room.dao.stable.ComponentDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemOSMechanicDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ServiceDao
import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IMechanicSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.EquipSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ComponentRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemOSMechanicRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ServiceRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.EquipSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.MechanicSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.presenter.model.ItemOSMechanicModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.Int
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class IListItemOSTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ListItemOS

    @Inject
    lateinit var equipSharedPreferencesDatasource: EquipSharedPreferencesDatasource

    @Inject
    lateinit var mechanicSharedPreferencesDatasource: IMechanicSharedPreferencesDatasource

    @Inject
    lateinit var itemOSMechanicDao: ItemOSMechanicDao

    @Inject
    lateinit var serviceDao: ServiceDao

    @Inject
    lateinit var componentDao: ComponentDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_equip_shared_preferences() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemOS -> IEquipRepository.getIdEquipMain -> IEquipSharedPreferencesDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_mechanic_shared_preferences() =
        runTest {
            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 1,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 0.0,
                    classify = 1,
                    flagMechanic = false,
                    flagTire = false
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemOS -> IMechanicRepository.getNroOS -> IMechanicSharedPreferencesDatasource.getNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_empty_list_if_not_have_data_in_item_os_mechanic_repository() =
        runTest {
            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 1,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 0.0,
                    classify = 1,
                    flagMechanic = false,
                    flagTire = false
                )
            )
            mechanicSharedPreferencesDatasource.save(
                MechanicSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList()
            )
        }

    @Test
    fun check_return_list_with_component_and_service_null_if_not_have_data_in_component_room_and_service_room() =
        runTest {
            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 1,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 0.0,
                    classify = 1,
                    flagMechanic = false,
                    flagTire = false
                )
            )
            mechanicSharedPreferencesDatasource.save(
                MechanicSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            itemOSMechanicDao.insertAll(
                listOf(
                    ItemOSMechanicRoomModel(
                        id = 1,
                        idEquip = 1,
                        nroOS = 123456,
                        seqItem = 2,
                        idServ = 1,
                        idComp = 1
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    ItemOSMechanicModel(
                        seq = 2,
                        service = "",
                        component = ""
                    )
                )
            )
        }

    @Test
    fun check_return_list_with_component_null_if_not_have_data_in_component_room() =
        runTest {
            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 1,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 0.0,
                    classify = 1,
                    flagMechanic = false,
                    flagTire = false
                )
            )
            mechanicSharedPreferencesDatasource.save(
                MechanicSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            itemOSMechanicDao.insertAll(
                listOf(
                    ItemOSMechanicRoomModel(
                        id = 1,
                        idEquip = 1,
                        nroOS = 123456,
                        seqItem = 2,
                        idServ = 3,
                        idComp = 1
                    )
                )
            )
            serviceDao.insertAll(
                listOf(
                    ServiceRoomModel(
                        id = 3,
                        cod = 1,
                        descr = "Service 1"
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    ItemOSMechanicModel(
                        seq = 2,
                        service = "Service 1",
                        component = ""
                    )
                )
            )
        }

    @Test
    fun check_return_list_with_all_data_if_have_data_in_all_tables() =
        runTest {
            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 1,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 0.0,
                    classify = 1,
                    flagMechanic = false,
                    flagTire = false
                )
            )
            mechanicSharedPreferencesDatasource.save(
                MechanicSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            itemOSMechanicDao.insertAll(
                listOf(
                    ItemOSMechanicRoomModel(
                        id = 1,
                        idEquip = 1,
                        nroOS = 123456,
                        seqItem = 2,
                        idServ = 3,
                        idComp = 1
                    )
                )
            )
            serviceDao.insertAll(
                listOf(
                    ServiceRoomModel(
                        id = 3,
                        cod = 1,
                        descr = "Service 1"
                    )
                )
            )
            componentDao.insertAll(
                listOf(
                    ComponentRoomModel(
                        id = 1,
                        cod = 1,
                        descr = "Component 1"
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    ItemOSMechanicModel(
                        seq = 2,
                        service = "Service 1",
                        component = "1 - Component 1"
                    )
                )
            )
        }

}