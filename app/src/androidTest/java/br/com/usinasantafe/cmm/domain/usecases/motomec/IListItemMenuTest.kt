package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionStopDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemMenuDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionStopRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.TypeActivity
import br.com.usinasantafe.cmm.utils.TypeEquip
import br.com.usinasantafe.cmm.utils.TypeStop
import br.com.usinasantafe.cmm.utils.TypeView
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class IListItemMenuTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ListItemMenu

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var itemMenuDao: ItemMenuDao

    @Inject
    lateinit var functionActivityDao: FunctionActivityDao

    @Inject
    lateinit var functionStopDao: FunctionStopDao

    @Inject
    lateinit var noteMotoMecDao: NoteMotoMecDao

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_config_shared_preferences() =
        runTest {
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IConfigRepository.getIdEquip -> IConfigSharedPreferencesDatasource.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_moto_mec_shared_preferences() =
        runTest {
            initialRegister(1)
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IMotoMecRepository.getIdEquipHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_equip_room() =
        runTest {
            initialRegister(2)
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IEquipRepository.getTypeEquipByIdEquip -> IEquipRoomDatasource.getTypeEquipByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'br.com.usinasantafe.cmm.utils.TypeEquip br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getTypeEquip()' on a null object reference"
            )
        }

    @Test
    fun check_return_failure_if_not_have_id_activity_in_config_shared_preferences() =
        runTest {
            initialRegister(3)
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IMotoMecRepository.getIdActivityHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_moto_mec_room() =
        runTest {
            initialRegister(4)
            initialRegisterSec(1)
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IMotoMecRepository.checkNoteHasByIdStop -> IHeaderMotoMecRoomDatasource.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Integer br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getId()' on a null object reference"
            )
        }

    @Test
    fun check_return_empty_list_if_not_have_data_in_item_menu_pmm_room() =
        runTest(
            timeout = 2.minutes
        ) {
            initialRegister(4)
            val result = usecase("pmm")
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
    fun check_return_list_if_menu_basic() =
        runTest(
            timeout = 2.minutes
        ) {
            initialRegister(5)
            val result = usecase("pmm")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 2,
                        descr = "PARADO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 12,
                        descr = "FINALIZAR BOLETIM",
                        type = TypeView.BUTTON
                    )
                )
            )
        }

    @Test
    fun check_return_list_if_menu_is_all_and_equip_normal() =
        runTest(
            timeout = 2.minutes
        ) {
            initialRegister(
                level = 5,
                flagMechanic = true,
                flagTire = true
            )
            initialRegisterSec(2)
            val result = usecase("pmm")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 2,
                        descr = "PARADO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 3,
                        descr = "RENDIMENTO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 4,
                        descr = "NOVO TRANSBORDO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 5,
                        descr = "TROCAR IMPLEMENTO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 7,
                        descr = "APONTAR MANUTENÇÃO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 8,
                        descr = "FINALIZAR MANUTENÇÃO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 9,
                        descr = "CALIBRAGEM DE PNEU",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 10,
                        descr = "TROCA DE PNEU",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 11,
                        descr = "APONTAR CARRETEL",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 12,
                        descr = "FINALIZAR BOLETIM",
                        type = TypeView.BUTTON
                    )
                )
            )
        }

    @Test
    fun check_return_list_if_menu_is_all_and_equip_fert() =
        runTest(
            timeout = 2.minutes
        ) {
            initialRegister(
                level = 5,
                flagMechanic = true,
                flagTire = true,
                typeEquip = TypeEquip.FERT
            )
            initialRegisterSec(2)
            val result = usecase("pmm")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 2,
                        descr = "PARADO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 6,
                        descr = "RECOLHIMENTO MANGUEIRA",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 7,
                        descr = "APONTAR MANUTENÇÃO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 8,
                        descr = "FINALIZAR MANUTENÇÃO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 9,
                        descr = "CALIBRAGEM DE PNEU",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 10,
                        descr = "TROCA DE PNEU",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 11,
                        descr = "APONTAR CARRETEL",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 12,
                        descr = "FINALIZAR BOLETIM",
                        type = TypeView.BUTTON
                    )
                )
            )
        }

    @Test
    fun check_return_list_if_menu_is_all_and_equip_fert_and_return_list() =
        runTest(
            timeout = 2.minutes
        ) {
            initialRegister(
                level = 5,
                flagMechanic = true,
                flagTire = true,
                typeEquip = TypeEquip.FERT,
                idEquip = 10
            )
            initialRegisterSec(2)
            val result = usecase("pmm")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 2,
                        descr = "PARADO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 6,
                        descr = "RECOLHIMENTO MANGUEIRA",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 7,
                        descr = "APONTAR MANUTENÇÃO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 8,
                        descr = "FINALIZAR MANUTENÇÃO",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 9,
                        descr = "CALIBRAGEM DE PNEU",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 10,
                        descr = "TROCA DE PNEU",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 11,
                        descr = "APONTAR CARRETEL",
                        type = TypeView.ITEM
                    ),
                    ItemMenuModel(
                        id = 13,
                        descr = "RETORNAR PRA LISTA",
                        type = TypeView.BUTTON
                    )
                )
            )
        }

    private suspend fun initialRegister(
        level: Int,
        typeEquip: TypeEquip = TypeEquip.NORMAL,
        flagMechanic: Boolean = false,
        flagTire: Boolean = false,
        idEquip: Int = 20,
    ) {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                password = "12345",
                nroEquip = 310,
                app = "PMM",
                version = "1.00",
                checkMotoMec = false,
                idServ = 1,
                idEquip = idEquip
            )
        )

        if (level == 1) return

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                idEquip = 20,
            )
        )

        if (level == 2) return

        equipDao.insertAll(
            listOf(
                EquipRoomModel(
                    id = 20,
                    nro = 2200L,
                    codClass = 1,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = typeEquip,
                    hourMeter = 1000.0,
                    classify = 1,
                    flagMechanic = flagMechanic,
                    flagTire = flagTire,
                )
            )
        )

        if (level == 3) return

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                idEquip = 20,
                idActivity = 1
            )
        )

        if (level == 4) return

        itemMenuDao.insertAll(
            listOf(
                ItemMenuRoomModel(
                    id = 1,
                    descr = "TRABALHANDO",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 2,
                    descr = "PARADO",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 3,
                    descr = "RENDIMENTO",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 4,
                    descr = "NOVO TRANSBORDO",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 5,
                    descr = "TROCAR IMPLEMENTO",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 6,
                    descr = "RECOLHIMENTO MANGUEIRA",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 7,
                    descr = "APONTAR MANUTENÇÃO",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 8,
                    descr = "FINALIZAR MANUTENÇÃO",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 9,
                    descr = "CALIBRAGEM DE PNEU",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 10,
                    descr = "TROCA DE PNEU",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 11,
                    descr = "APONTAR CARRETEL",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 12,
                    descr = "FINALIZAR BOLETIM",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 13,
                    descr = "RETORNAR PRA LISTA",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                )
            )
        )

        if (level == 5) return

    }

    private suspend fun initialRegisterSec(level: Int) {
        functionActivityDao.insertAll(
            listOf(
                FunctionActivityRoomModel(
                    idFunctionActivity = 1,
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE
                ),
                FunctionActivityRoomModel(
                    idFunctionActivity = 2,
                    idActivity = 1,
                    typeActivity = TypeActivity.TRANSHIPMENT
                ),
                FunctionActivityRoomModel(
                    idFunctionActivity = 3,
                    idActivity = 1,
                    typeActivity = TypeActivity.IMPLEMENT
                )
            )
        )

        functionStopDao.insertAll(
            listOf(
                FunctionStopRoomModel(
                    idFunctionStop = 1,
                    idStop = 1,
                    typeStop = TypeStop.REEL
                )
            )
        )

        if (level == 1) return

        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                id = 1,
                regOperator = 19759,
                idEquip = 20,
                typeEquip = TypeEquip.NORMAL,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 1000.0,
                statusCon = true
            )
        )

        noteMotoMecDao.insert(
            NoteMotoMecRoomModel(
                id = 1,
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                idStop = 1,
                statusCon = true
            )
        )

        if (level == 2) return

    }
}