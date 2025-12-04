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
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.lib.FIELD_ARRIVAL
import br.com.usinasantafe.cmm.lib.CERTIFICATE
import br.com.usinasantafe.cmm.lib.CHECK_WILL
import br.com.usinasantafe.cmm.lib.COUPLING_COURTYARD
import br.com.usinasantafe.cmm.lib.COUPLING_FIELD
import br.com.usinasantafe.cmm.lib.COUPLING_TRAILER
import br.com.usinasantafe.cmm.lib.ECM
import br.com.usinasantafe.cmm.lib.EXIT_LOADING
import br.com.usinasantafe.cmm.lib.EXIT_MILL
import br.com.usinasantafe.cmm.lib.EXIT_SCALE
import br.com.usinasantafe.cmm.lib.EXIT_TO_DEPOSIT
import br.com.usinasantafe.cmm.lib.EXIT_TO_FIELD
import br.com.usinasantafe.cmm.lib.EXIT_UNLOADING
import br.com.usinasantafe.cmm.lib.FERTIGATION
import br.com.usinasantafe.cmm.lib.FINISH_DISCHARGE
import br.com.usinasantafe.cmm.lib.FINISH_MECHANICAL
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.HOSE_COLLECTION
import br.com.usinasantafe.cmm.lib.IMPLEMENT
import br.com.usinasantafe.cmm.lib.ITEM_NORMAL
import br.com.usinasantafe.cmm.lib.LOADING
import br.com.usinasantafe.cmm.lib.LOADING_COMPOUND
import br.com.usinasantafe.cmm.lib.LOADING_INPUT
import br.com.usinasantafe.cmm.lib.MECHANICAL
import br.com.usinasantafe.cmm.lib.NOTE_MECHANICAL
import br.com.usinasantafe.cmm.lib.PCOMP_COMPOUND
import br.com.usinasantafe.cmm.lib.PCOMP_INPUT
import br.com.usinasantafe.cmm.lib.PERFORMANCE
import br.com.usinasantafe.cmm.lib.PMM
import br.com.usinasantafe.cmm.lib.REEL
import br.com.usinasantafe.cmm.lib.RETURN_MILL
import br.com.usinasantafe.cmm.lib.STOP
import br.com.usinasantafe.cmm.lib.STOP_COURTYARD
import br.com.usinasantafe.cmm.lib.TIRE
import br.com.usinasantafe.cmm.lib.TIRE_CHANGE
import br.com.usinasantafe.cmm.lib.TIRE_INFLATION
import br.com.usinasantafe.cmm.lib.TRANSHIPMENT
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.lib.TypeStop
import br.com.usinasantafe.cmm.lib.UNCOUPLING_COURTYARD
import br.com.usinasantafe.cmm.lib.UNCOUPLING_FIELD
import br.com.usinasantafe.cmm.lib.UNCOUPLING_TRAILER
import br.com.usinasantafe.cmm.lib.UNLOADING_COMPOUND
import br.com.usinasantafe.cmm.lib.UNLOADING_HILO
import br.com.usinasantafe.cmm.lib.UNLOADING_INPUT
import br.com.usinasantafe.cmm.lib.WAITING_ALLOCATION
import br.com.usinasantafe.cmm.lib.WAITING_LOADING
import br.com.usinasantafe.cmm.lib.WAITING_UNLOADING
import br.com.usinasantafe.cmm.lib.WEIGHING
import br.com.usinasantafe.cmm.lib.WEIGHING_LOADED
import br.com.usinasantafe.cmm.lib.WEIGHING_SCALE
import br.com.usinasantafe.cmm.lib.WEIGHING_TARE
import br.com.usinasantafe.cmm.lib.WORK
import br.com.usinasantafe.cmm.utils.dataMenu
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
    fun check_return_failure_if_not_exist_flavor_fielded() =
        runTest {
            val result = usecase("test")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception: Flavor not found"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_moto_mec_shared_preferences__pmm__() =
        runTest {
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IListItemMenu.pmmList -> IMotoMecRepository.getIdEquipHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_moto_mec_room__pmm__() =
        runTest {
            initialRegister(1)
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IListItemMenu.pmmList -> IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecRoomDatasource.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Integer br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getId()' on a null object reference"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_equip_room__pmm__() =
        runTest {
            initialRegister(2)
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IListItemMenu.pmmList -> IEquipRepository.getTypeEquipByIdEquip -> IEquipRoomDatasource.getTypeEquipByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'br.com.usinasantafe.cmm.utils.TypeEquip br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getTypeEquip()' on a null object reference"
            )
        }

    @Test
    fun check_return_failure_if_not_have_id_activity_in_config_shared_preferences__pmm__() =
        runTest {
            initialRegister(3)
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IListItemMenu.pmmList -> IMotoMecRepository.getIdActivityHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_empty_list_if_not_have_data_in_function_activity_room__pmm__() =
        runTest {
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
    fun check_return_empty_list_if_not_have_data_in_item_menu_room__pmm__() =
        runTest {
            initialRegister(4)
            initialRegisterSec(1)
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
    fun check_return_list_if_menu_basic__pmm__() =
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
                        function = 1 to WORK,
                        type = 1 to ITEM_NORMAL,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 2,
                        descr = "PARADO",
                        function = 2 to STOP,
                        type = 1 to ITEM_NORMAL,
                        app = 1 to PMM
                    ),
                )
            )
        }

    @Test
    fun check_return_list_if_menu_is_all_and_equip_normal__pmm__() =
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
                        function = 1 to WORK,
                        type = 1 to ITEM_NORMAL,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 2,
                        descr = "PARADO",
                        function = 2 to STOP,
                        type = 1 to ITEM_NORMAL,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 3,
                        descr = "RENDIMENTO",
                        3 to PERFORMANCE,
                        2 to PERFORMANCE,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 4,
                        descr = "NOVO TRANSBORDO",
                        4 to TRANSHIPMENT,
                        3 to TRANSHIPMENT,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 5,
                        descr = "TROCAR IMPLEMENTO",
                        5 to IMPLEMENT,
                        4 to IMPLEMENT,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 7,
                        descr = "APONTAR MANUTENÇÃO",
                        7 to NOTE_MECHANICAL,
                        6 to MECHANICAL,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 8,
                        descr = "FINALIZAR MANUTENÇÃO",
                        8 to FINISH_MECHANICAL,
                        6 to MECHANICAL,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 9,
                        descr = "CALIBRAGEM DE PNEU",
                        9 to TIRE_INFLATION,
                        7 to TIRE,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 10,
                        descr = "TROCA DE PNEU",
                        10 to TIRE_CHANGE,
                        7 to TIRE,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 11,
                        descr = "APONTAR CARRETEL",
                        11 to REEL,
                        8 to REEL,
                        app = 1 to PMM
                    ),
                )
            )
        }

    @Test
    fun check_return_list_if_menu_is_all_and_equip_fert__pmm__() =
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
                        function = 1 to WORK,
                        type = 1 to ITEM_NORMAL,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 2,
                        descr = "PARADO",
                        function = 2 to STOP,
                        type = 1 to ITEM_NORMAL,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 6,
                        descr = "RECOLHIMENTO MANGUEIRA",
                        6 to HOSE_COLLECTION,
                        5 to FERTIGATION,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 7,
                        descr = "APONTAR MANUTENÇÃO",
                        7 to NOTE_MECHANICAL,
                        6 to MECHANICAL,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 8,
                        descr = "FINALIZAR MANUTENÇÃO",
                        8 to FINISH_MECHANICAL,
                        6 to MECHANICAL,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 9,
                        descr = "CALIBRAGEM DE PNEU",
                        9 to TIRE_INFLATION,
                        7 to TIRE,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 10,
                        descr = "TROCA DE PNEU",
                        10 to TIRE_CHANGE,
                        7 to TIRE,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 11,
                        descr = "APONTAR CARRETEL",
                        11 to REEL,
                        8 to REEL,
                        app = 1 to PMM
                    ),
                )
            )
        }

    @Test
    fun check_return_list_if_menu_is_all__ecm__() =
        runTest(
            timeout = 2.minutes
        ) {
            initialRegister(
                level = 5,
            )
            val result = usecase("ecm")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    ItemMenuModel(
                        id = 16,
                        descr = "SAIDA DA USINA",
                        3 to EXIT_MILL,
                        2 to EXIT_MILL,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 17,
                        descr = "CHEGADA AO CAMPO",
                        4 to FIELD_ARRIVAL,
                        3 to FIELD_ARRIVAL,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 18,
                        descr = "DESENGATE CAMPO",
                        5 to UNCOUPLING_FIELD,
                        7 to UNCOUPLING_TRAILER,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 19,
                        descr = "ENGATE CAMPO",
                        6 to COUPLING_FIELD,
                        8 to COUPLING_TRAILER,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 20,
                        descr = "AGUARDANDO CARREGAMENTO",
                        7 to WAITING_LOADING,
                        1 to ITEM_NORMAL,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 21,
                        descr = "CARREGAMENTO",
                        8 to LOADING,
                        1 to ITEM_NORMAL,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 22,
                        descr = "CERTIFICADO",
                        9 to CERTIFICATE,
                        4 to CERTIFICATE,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 22,
                        descr = "CERTIFICADO",
                        9 to CERTIFICATE,
                        4 to CERTIFICATE,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 23,
                        descr = "PESAGEM NA BALANCA",
                        10 to WEIGHING_SCALE,
                        6 to WEIGHING,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 24,
                        descr = "DESENGATE PATIO",
                        11 to UNCOUPLING_COURTYARD,
                        7 to UNCOUPLING_TRAILER,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 25,
                        descr = "ENGATE PATIO",
                        12 to COUPLING_COURTYARD,
                        8 to COUPLING_TRAILER,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 26,
                        descr = "PARADA NO PATIO",
                        13 to STOP_COURTYARD,
                        1 to ITEM_NORMAL,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 27,
                        descr = "DESCARREGAMENTO HILO",
                        14 to UNLOADING_HILO,
                        1 to ITEM_NORMAL,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 28,
                        descr = "FIM DE DESCARGA",
                        15 to FINISH_DISCHARGE,
                        1 to ITEM_NORMAL,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 29,
                        descr = "AGUARDANDO ALOCACAO",
                        16 to WAITING_ALLOCATION,
                        1 to ITEM_NORMAL,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 30,
                        descr = "RETORNO PRA USINA",
                        17 to RETURN_MILL,
                        5 to RETURN_MILL,
                        app = 2 to ECM
                    ),
                )
            )
        }

    @Test
    fun check_return_failure_if_flow_composting_is_empty__pcomp__() =
        runTest(
            timeout = 2.minutes
        ) {
            initialRegister(
                level = 5,
            )
            val result = usecase("pcomp")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IMotoMecRepository.getFlowCompostingHeader -> IHeaderMotoMecRoomDatasource.getFlowComposting"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception: flowComposting is null"
            )
        }

    @Test
    fun check_return_list_if_menu_is_all__pcomp_input__() =
        runTest(
            timeout = 2.minutes
        ) {
            initialRegister(
                level = 5,
                flowComposting = FlowComposting.INPUT
            )
            val result = usecase("pcomp")
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    ItemMenuModel(
                        id = 32,
                        descr = "PESAGEM TARA",
                        2 to WEIGHING_TARE,
                        2 to WEIGHING_TARE,
                        app = 3 to PCOMP_INPUT
                    ),
                    ItemMenuModel(
                        id = 33,
                        descr = "SAIDA DA BALANCA",
                        3 to EXIT_SCALE,
                        6 to EXIT_SCALE,
                        app = 3 to PCOMP_INPUT
                    ),
                    ItemMenuModel(
                        id = 34,
                        descr = "AGUARDANDO CARREGAMENTO",
                        4 to WAITING_LOADING,
                        1 to ITEM_NORMAL,
                        app = 3 to PCOMP_INPUT
                    ),
                    ItemMenuModel(
                        id = 35,
                        descr = "CARREGAMENTO DE INSUMO",
                        5 to LOADING_INPUT,
                        3 to LOADING_INPUT,
                        app = 3 to PCOMP_INPUT
                    ),
                    ItemMenuModel(
                        id = 36,
                        descr = "SAIDA DO CARREGAMENTO",
                        6 to EXIT_LOADING,
                        1 to ITEM_NORMAL,
                        app = 3 to PCOMP_INPUT
                    ),
                    ItemMenuModel(
                        id = 37,
                        descr = "PESAGEM CARREGADO",
                        7 to WEIGHING_LOADED,
                        4 to WEIGHING_LOADED,
                        app = 3 to PCOMP_INPUT
                    ),
                    ItemMenuModel(
                        id = 38,
                        descr = "SAIDA PARA DEPOSITO",
                        8 to EXIT_TO_DEPOSIT,
                        7 to EXIT_TO_DEPOSIT,
                        app = 3 to PCOMP_INPUT
                    ),
                    ItemMenuModel(
                        id = 39,
                        descr = "AGUARDANDO DESCARREG.",
                        9 to WAITING_UNLOADING,
                        1 to ITEM_NORMAL,
                        app = 3 to PCOMP_INPUT
                    ),
                    ItemMenuModel(
                        id = 40,
                        descr = "DESCARREG. DE INSUMO",
                        10 to UNLOADING_INPUT,
                        10 to UNLOADING_INPUT,
                        app = 3 to PCOMP_INPUT
                    ),
                    ItemMenuModel(
                        id = 41,
                        descr = "SAIDA DO DESCARREGAMENTO",
                        11 to EXIT_UNLOADING,
                        1 to ITEM_NORMAL,
                        app = 3 to PCOMP_INPUT
                    ),
                    ItemMenuModel(
                        id = 42,
                        descr = "VERIFICAR LEIRA",
                        12 to CHECK_WILL,
                        5 to CHECK_WILL,
                        app = 3 to PCOMP_INPUT
                    ),
                )
            )
        }

    @Test
    fun check_return_list_if_menu_is_all__pcomp_compound__() =
        runTest(
            timeout = 2.minutes
        ) {
            initialRegister(
                level = 5,
                flowComposting = FlowComposting.COMPOUND
            )
            val result = usecase("pcomp")
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    ItemMenuModel(
                        id = 45,
                        descr = "PESAGEM TARA",
                        2 to WEIGHING_TARE,
                        2 to WEIGHING_TARE,
                        app = 4 to PCOMP_COMPOUND
                    ),
                    ItemMenuModel(
                        id = 46,
                        descr = "SAIDA DA USINA",
                        3 to EXIT_MILL,
                        6 to EXIT_MILL,
                        app = 4 to PCOMP_COMPOUND
                    ),
                    ItemMenuModel(
                        id = 47,
                        descr = "AGUARDANDO CARREGAMENTO",
                        4 to WAITING_LOADING,
                        1 to ITEM_NORMAL,
                        app = 4 to PCOMP_COMPOUND
                    ),
                    ItemMenuModel(
                        id = 48,
                        descr = "CARREGAMENTO DE COMPOSTO",
                        5 to LOADING_COMPOUND,
                        3 to LOADING_COMPOUND,
                        app = 4 to PCOMP_COMPOUND
                    ),
                    ItemMenuModel(
                        id = 49,
                        descr = "SAIDA DO CARREGAMENTO",
                        6 to EXIT_LOADING,
                        1 to ITEM_NORMAL,
                        app = 4 to PCOMP_COMPOUND
                    ),
                    ItemMenuModel(
                        id = 50,
                        descr = "PESAGEM CARREGADO",
                        7 to WEIGHING_LOADED,
                        4 to WEIGHING_LOADED,
                        app = 4 to PCOMP_COMPOUND
                    ),
                    ItemMenuModel(
                        id = 51,
                        descr = "SAIDA PARA CAMPO",
                        8 to EXIT_TO_FIELD,
                        7 to EXIT_TO_FIELD,
                        app = 4 to PCOMP_COMPOUND
                    ),
                    ItemMenuModel(
                        id = 52,
                        descr = "AGUARDANDO DESCARREG.",
                        9 to WAITING_UNLOADING,
                        1 to ITEM_NORMAL,
                        app = 4 to PCOMP_COMPOUND
                    ),
                    ItemMenuModel(
                        id = 53,
                        descr = "DESCARREG. DE COMPOSTO",
                        10 to UNLOADING_COMPOUND,
                        1 to ITEM_NORMAL,
                        app = 4 to PCOMP_COMPOUND
                    ),
                    ItemMenuModel(
                        id = 54,
                        descr = "SAIDA DO DESCARREGAMENTO",
                        11 to EXIT_UNLOADING,
                        1 to ITEM_NORMAL,
                        app = 4 to PCOMP_COMPOUND
                    ),
                    ItemMenuModel(
                        id = 55,
                        descr = "VERIFICAR LEIRA",
                        12 to CHECK_WILL,
                        5 to CHECK_WILL,
                        app = 4 to PCOMP_COMPOUND
                    ),
                )
            )
        }

    private suspend fun initialRegister(
        level: Int,
        typeEquip: TypeEquip = TypeEquip.NORMAL,
        flagMechanic: Boolean = false,
        flagTire: Boolean = false,
        flowComposting: FlowComposting? = null
    ) {

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                idEquip = 20,
            )
        )

        if (level == 1) return

        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                id = 1,
                regOperator = 19759,
                idEquip = 20,
                typeEquip = TypeEquip.NORMAL,
                flowComposting = flowComposting,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 1000.0,
                statusCon = true
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

        val gson = Gson()
        val itemTypeItemMenu = object : TypeToken<List<ItemMenuRoomModel>>() {}.type
        val itemMenuRoomModel = gson.fromJson<List<ItemMenuRoomModel>>(dataMenu, itemTypeItemMenu)
        itemMenuDao.insertAll(
            itemMenuRoomModel
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