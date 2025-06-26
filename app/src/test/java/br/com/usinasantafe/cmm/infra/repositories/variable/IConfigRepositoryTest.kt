package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.ConfigRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.ConfigRetrofitModelOutput
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

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
                    "Error",
                    "Exception",
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
                "IConfigRepository.get -> Error -> Exception"
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
                    "Error",
                    "Exception",
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
                "Error -> Exception"
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
                    "Error",
                    "Exception",
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
                "IConfigRepository.send -> Error -> Exception"
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
                idBD = 1,
                idEquip = 10
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
                    idBD = 1,
                    idEquip = 10
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
                        nroEquip = 310,
                        checkMotoMec = false,
                        idBD = 1
                    )
                )
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
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
                    idBD = 1
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Error -> Exception"
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
                        version = "1.00",
                        app = "PMM",
                        nroEquip = 310,
                        checkMotoMec = false,
                        idBD = 1
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.save(
                Config(
                    number = 16997417840,
                    password = "12345",
                    version = "1.00",
                    app = "PMM",
                    nroEquip = 310,
                    checkMotoMec = false,
                    idBD = 1
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
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
            Result.success(true)
        )
        val result = repository.setFlagUpdate(FlagUpdate.UPDATED)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
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
                Result.success(true)
            )
            val result = repository.setStatusSend(StatusSend.SEND)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `getIdEquip - Check return failure if have error in ConfigSharedPreferencesDatasource getIdEquip`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.getIdEquip()
            ).thenReturn(
                resultFailure(
                    "ConfigSharedPreferencesDatasource.getIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.getIdEquip()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.getIdEquip -> ConfigSharedPreferencesDatasource.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getIdEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getIdEquip()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1
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