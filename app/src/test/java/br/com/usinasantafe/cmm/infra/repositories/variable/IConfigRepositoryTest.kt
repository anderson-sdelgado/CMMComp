package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.DatasourceException
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
                Result.failure(
                    DatasourceException(
                        function = "ConfigSharedPreferencesDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val result = repository.get()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> ConfigSharedPreferencesDatasource.get"
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
    fun `getFlagUpdate - Check return failure if have error in ConfigSharedPreferences get`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "ConfigSharedPreferencesDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val result = repository.getFlagUpdate()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> ConfigSharedPreferencesDatasource.get"
            )
        }

    @Test
    fun `getFlagUpdate - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    ConfigSharedPreferencesModel(
                        flagUpdate = FlagUpdate.OUTDATED
                    )
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
    fun `hasConfig - Check return failure if have error in ConfigSharedPreferencesDatasource has`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.has()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "ConfigSharedPreferencesDatasource.has",
                        cause = Exception()
                    )
                )
            )
            val result = repository.hasConfig()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> ConfigSharedPreferencesDatasource.has"
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
    fun `getPassword - Check return failure if have error in ConfigSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "ConfigSharedPreferencesDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val result = repository.getPassword()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> ConfigSharedPreferencesDatasource.get"
            )
        }

    @Test
    fun `getPassword - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    ConfigSharedPreferencesModel(
                        password = "12345"
                    )
                )
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
                Result.failure(
                    DatasourceException(
                        function = "ConfigRetrofitDatasource.recoverToken",
                        cause = Exception()
                    )
                )
            )
            val result = repository.send(entity)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> ConfigRetrofitDatasource.recoverToken"
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
                idBD = 1
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
                1
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
                Result.failure(
                    DatasourceException(
                        function = "ConfigSharedPreferencesDatasource.save",
                        cause = Exception()
                    )
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
                "Failure Datasource -> ConfigSharedPreferencesDatasource.save"
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

}