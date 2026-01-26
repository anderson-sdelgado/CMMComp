package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.EquipMainRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.ConfigRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.ConfigRetrofitModelOutput
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TypeEquip
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.assertEquals

class IConfigRepositoryTest {

    private val configSharedPreferencesDatasource = mock<ConfigSharedPreferencesDatasource>()
    private val configRetrofitDatasource = mock<ConfigRetrofitDatasource>()
    private val repository = IConfigRepository(
        configSharedPreferencesDatasource = configSharedPreferencesDatasource,
        configRetrofitDatasource = configRetrofitDatasource
    )

    @Test
    fun `getConfig - Check return failure if have error in ConfigSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.get()
            ).thenReturn(
                resultFailure(
                    "IConfigSharedPreferencesDatasource.get",
                    "-",
                    Exception()
                )
            )
            val result = repository.get()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.get -> IConfigSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getConfig - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    ConfigSharedPreferencesModel(
                        number = 16997417840,
                        password = "123456",
                        checkMotoMec = true,
                        idServ = 1,
                        version = "1.00",
                        app = "PMM",
                        statusSend = StatusSend.SENT
                    )
                )
            )
            val result = repository.get()
            assertEquals(
                result.isSuccess,
                true
            )
            val sharedPreferencesModel = result.getOrNull()!!
            assertEquals(
                sharedPreferencesModel.statusSend,
                StatusSend.SENT
            )
        }

    @Test
    fun `hasConfig - Check return failure if have error in ConfigSharedPreferencesDatasource has`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.has()
            ).thenReturn(
                resultFailure(
                    "IConfigSharedPreferencesDatasource.has",
                    "-",
                    Exception()
                )
            )
            val result = repository.hasConfig()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.hasConfig -> IConfigSharedPreferencesDatasource.has"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `hasConfig - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.has()
            ).thenReturn(
                Result.success(
                    false
                )
            )
            val result = repository.hasConfig()
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
    fun `send - Check return failure if have error in ConfigRetrofitDatasource recoverToken`() =
        runTest {
            val retrofitModelOutput = ConfigRetrofitModelOutput(
                number = 16997417840,
                nroEquip = 310,
                version = "1.00",
                app = "PMM"
            )
            val entity = Config(
                number = 16997417840,
                nroEquip = 310,
                version = "1.00",
                app = "PMM"
            )
            whenever(
                configRetrofitDatasource.recoverToken(
                    retrofitModelOutput = retrofitModelOutput
                )
            ).thenReturn(
                resultFailure(
                    "IConfigRetrofitDatasource.recoverToken",
                    "-",
                    Exception()
                )
            )
            val result = repository.send(entity)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.send -> IConfigRetrofitDatasource.recoverToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return correct if function execute successfully`() =
        runTest {
            val retrofitModelOutput = ConfigRetrofitModelOutput(
                number = 16997417840,
                nroEquip = 310,
                version = "1.00",
                app = "PMM"
            )
            val retrofitModelInput = ConfigRetrofitModelInput(
                idServ = 1,
                equip = EquipMainRetrofitModel(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = 1,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = 0,
                    flagTire = 0
                )
            )
            val entity = Config(
                number = 16997417840,
                nroEquip = 310,
                version = "1.00",
                app = "PMM"
            )
            whenever(
                configRetrofitDatasource.recoverToken(
                    retrofitModelOutput = retrofitModelOutput
                )
            ).thenReturn(
                Result.success(retrofitModelInput)
            )
            val result = repository.send(entity)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Config(
                    idServ = 1,
                    equip = Equip(
                        id = 10,
                        nro = 2200,
                        codClass = 1,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 5000.0,
                        classify = 1,
                        flagMechanic = false,
                        flagTire = false
                    )
                )
            )
        }

    @Test
    fun `save - Check return failure if have error in ConfigSharedPreferencesDatasource save`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.save(
                    ConfigSharedPreferencesModel(
                        number = 16997417840,
                        password = "12345",
                        version = "1.00",
                        app = "PMM",
                        checkMotoMec = false,
                        idServ = 1
                    )
                )
            ).thenReturn(
                resultFailure(
                    "IConfigSharedPreferencesDatasource.save",
                    "-",
                    Exception()
                )
            )
            val result = repository.save(
                Config(
                    number = 16997417840,
                    password = "12345",
                    version = "1.00",
                    app = "PMM",
                    nroEquip = 310,
                    checkMotoMec = false,
                    idServ = 1
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.save -> IConfigSharedPreferencesDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `save - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.save(
                    ConfigSharedPreferencesModel(
                        number = 16997417840,
                        password = "12345",
                        checkMotoMec = true,
                        version = "1.00",
                        app = "PMM",
                        idServ = 1,
                    )
                )
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.save(
                Config(
                    number = 16997417840,
                    password = "12345",
                    checkMotoMec = true,
                    version = "1.00",
                    app = "PMM",
                    idServ = 1,
                    nroEquip = 310,
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `setFlagUpdate - Check return failure if have failure in execution ConfigSharedPreferencesDatasource get`() = runTest {
        whenever(
            configSharedPreferencesDatasource.setFlagUpdate(FlagUpdate.UPDATED)
        ).thenReturn(
            resultFailure(
                "IConfigSharedPreferencesDatasource.setFlagUpdate",
                "-",
                Exception()
            )
        )
        val result = repository.setFlagUpdate(FlagUpdate.UPDATED)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IConfigRepository.setFlagUpdate -> IConfigSharedPreferencesDatasource.setFlagUpdate"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.Exception"
        )
    }

    @Test
    fun `setFlagUpdate - Check return true is execution successfully`() = runTest {
        whenever(
            configSharedPreferencesDatasource.setFlagUpdate(FlagUpdate.UPDATED)
        ).thenReturn(
            Result.success(Unit)
        )
        val result = repository.setFlagUpdate(FlagUpdate.UPDATED)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            Unit
        )
    }

    @Test
    fun `getFlagUpdate - Check return failure if have error in ConfigSharedPreferences getFlagUpdate`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.getFlagUpdate()
            ).thenReturn(
                resultFailure(
                    "ConfigSharedPreferencesDatasource.getFlagUpdate",
                    "-",
                    Exception()
                )
            )
            val result = repository.getFlagUpdate()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.getFlagUpdate -> ConfigSharedPreferencesDatasource.getFlagUpdate",
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getFlagUpdate - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.getFlagUpdate()
            ).thenReturn(
                Result.success(
                    FlagUpdate.OUTDATED
                )
            )
            val result = repository.getFlagUpdate()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlagUpdate.OUTDATED
            )
        }

    @Test
    fun `getPassword - Check return failure if have error in ConfigSharedPreferencesDatasource getPassword`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.getPassword()
            ).thenReturn(
                resultFailure(
                    "ConfigSharedPreferencesDatasource.getPassword",
                    "-",
                    Exception()
                )
            )
            val result = repository.getPassword()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.getPassword -> ConfigSharedPreferencesDatasource.getPassword"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getPassword - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.getPassword()
            ).thenReturn(
                Result.success("12345")
            )
            val result = repository.getPassword()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "12345"
            )
        }
    
    @Test
    fun `getNumber - Check return failure if have error in configSharedPreferencesDatasource getNumber`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.getNumber()
            ).thenReturn(
                resultFailure(
                    "ConfigSharedPreferencesDatasource.getNumber",
                    "-",
                    Exception()
                )
            )
            val result = repository.getNumber()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.getNumber -> ConfigSharedPreferencesDatasource.getNumber"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getNumber - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.getNumber()
            ).thenReturn(
                Result.success(
                    16997417840
                )
            )
            val result = repository.getNumber()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                16997417840
            )
        }

    @Test
    fun `setStatusSend - Check return failure if have error in ConfigSharedPreferencesDatasource setStatusSend`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.setStatusSend(StatusSend.SEND)
            ).thenReturn(
                resultFailure(
                    "ConfigSharedPreferencesDatasource.setStatusSend",
                    "-",
                    Exception()
                )
            )
            val result = repository.setStatusSend(StatusSend.SEND)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.setStatusSend -> ConfigSharedPreferencesDatasource.setStatusSend"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setStatusSend - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.setStatusSend(StatusSend.SEND)
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.setStatusSend(StatusSend.SEND)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
        fun `getIdTurnCheckListLast - Check return failure if have error in ConfigSharedPreferencesDatasource getIdTurnCheckListLast`() =
            runTest {
                whenever(
                    configSharedPreferencesDatasource.getIdTurnCheckListLast()
                ).thenReturn(
                    resultFailure(
                        "IConfigSharedPreferencesDatasource.getIdTurnCheckListLast",
                        "-",
                        Exception()
                    )
                )
                val result = repository.getIdTurnCheckListLast()
                assertEquals(
                    result.isFailure,
                    true
                )
                assertEquals(
                    result.exceptionOrNull()!!.message,
                    "IConfigRepository.getIdTurnCheckListLast -> IConfigSharedPreferencesDatasource.getIdTurnCheckListLast"
                )
                assertEquals(
                    result.exceptionOrNull()!!.cause.toString(),
                    "java.lang.Exception",
                )
            }

        @Test
        fun `getIdTurnCheckListLast - Check return correct if function execute successfully`() =
            runTest {
                whenever(
                    configSharedPreferencesDatasource.getIdTurnCheckListLast()
                ).thenReturn(
                    Result.success(null)
                )
                val result = repository.getIdTurnCheckListLast()
                assertEquals(
                    result.isSuccess,
                    true
                )
                assertEquals(
                    result.getOrNull(),
                    null
                )
            }

    @Test
    fun `getIdTurnCheckListLast - Check return correct if function execute successfully with value`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.getIdTurnCheckListLast()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getIdTurnCheckListLast()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                1
            )
        }

    @Test
    fun `getDateCheckListLast - Check return failure if have error in ConfigSharedPreferencesDatasource getDateCheckListLast`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.getDateCheckListLast()
            ).thenReturn(
                resultFailure(
                    "IConfigSharedPreferencesDatasource.getDateCheckListLast",
                    "-",
                    Exception()
                )
            )
            val result = repository.getDateCheckListLast()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.getDateCheckListLast -> IConfigSharedPreferencesDatasource.getDateCheckListLast"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception",
            )
        }

    @Test
    fun `getDateCheckListLast - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.getDateCheckListLast()
            ).thenReturn(
                Result.success(
                    Date(1750857777000)
                )
            )
            val result = repository.getDateCheckListLast()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Date(1750857777000)
            )
        }

}