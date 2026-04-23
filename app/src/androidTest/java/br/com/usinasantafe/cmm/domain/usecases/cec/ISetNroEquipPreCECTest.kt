package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IHeaderPreCECSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.EquipSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.EquipSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.TypeEquip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetNroEquipPreCECTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetNroEquipPreCEC

    @Inject
    lateinit var preCECSharedPreferencesDatasource: IHeaderPreCECSharedPreferencesDatasource

    @Inject
    lateinit var equipSharedPreferencesDatasource: EquipSharedPreferencesDatasource

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_nro_in_equip_shared_preferences() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipPreCEC -> IEquipRepository.getNroEquipMain -> IEquipSharedPreferencesDatasource.getNro"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_true_and_alter_data_if_process_execute_successfully_and_cod_class_is_equals_to_1() =
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
            assertEquals(
                result.getOrThrow(),
                true
            )
            val resultAfter = preCECSharedPreferencesDatasource.get()
            assertEquals(
                resultAfter.isSuccess,
                true
            )
            val model = resultAfter.getOrThrow()
            assertEquals(
                model.nroEquip,
                2200
            )
        }

    @Test
    fun check_return_false_and_alter_data_if_process_execute_successfully_and_cod_class_is_different_from_1() =
        runTest {
            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 1,
                    nro = 2200,
                    codClass = 2,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
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
            assertEquals(
                result.getOrThrow(),
               false
            )
            val resultAfter = preCECSharedPreferencesDatasource.get()
            assertEquals(
                resultAfter.isSuccess,
                true
            )
            val model = resultAfter.getOrThrow()
            assertEquals(
                model.nroEquip,
                2200
            )
        }

}