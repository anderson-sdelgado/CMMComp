package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.FlowEquipNote
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class IGetFlowEquipTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: GetFlowEquip

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_config_shared_preferences() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetFlowEquip -> IConfigRepository.getIdEquip -> IConfigSharedPreferencesDatasource.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_moto_mec_shared_preferences() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    app = "CMM",
                    idServ = 1,
                    number = 16997417840,
                    version = "1.0",
                    password = "12345",
                    checkMotoMec = true
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetFlowEquip -> IMotoMecRepository.getIdEquipHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_main_if_id_equip_of_header_moto_mec_shared_preferences_is_equals_id_equip_of_config_shared_preferences() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    app = "CMM",
                    idServ = 1,
                    number = 16997417840,
                    version = "1.0",
                    password = "12345",
                    checkMotoMec = true
                )
            )
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    idEquip = 30
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowEquipNote.MAIN
            )
        }

    @Test
    fun check_return_secondary_if_id_equip_of_header_moto_mec_shared_preferences_is_not_equals_id_equip_of_config_shared_preferences() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    app = "CMM",
                    idServ = 1,
                    number = 16997417840,
                    version = "1.0",
                    password = "12345",
                    checkMotoMec = true
                )
            )
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    idEquip = 40
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowEquipNote.SECONDARY
            )
        }
}



