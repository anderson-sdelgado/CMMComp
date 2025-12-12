package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.EquipSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.EquipSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.TypeEquipMain
import br.com.usinasantafe.cmm.utils.token
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class IGetTokenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: GetToken

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var equipSharedPreferencesDatasource: EquipSharedPreferencesDatasource

    @Before
    fun init() {
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
                "IGetToken -> IConfigRepository.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_config_data_in_equip_shared_preferences() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    app = "PMM",
                    idServ = 1,
                    checkMotoMec = true,
                    version = "1.00",
                    number = 123456,
                    password = "123456"
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetToken -> IEquipRepository.getNroEquipMain -> IEquipSharedPreferencesDatasource.getNro"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    app = "PMM",
                    idServ = 1,
                    checkMotoMec = true,
                    version = "1.00",
                    number = 1,
                    password = "123456"
                )
            )
            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 1,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquipMain.NORMAL,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val token = token(
                app = "PMM",
                idServ = 1,
                nroEquip = 2200,
                number = 1,
                version = "1.00"
            )
            assertEquals(
                result.getOrNull()!!,
                token
            )
        }

}