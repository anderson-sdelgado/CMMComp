package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.lib.TypeEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISendDataConfigTest {

    private val configRepository = mock<ConfigRepository>()
    private val sendDataConfig = ISendDataConfig(
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if set value incorrect`() =
        runTest {
            val result = sendDataConfig(
                number = "dfas",
                password = "12345",
                nroEquip = "310",
                app = "pmm",
                version = "1.00"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendDataConfig"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"dfas\""
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository send`() =
        runTest {
            whenever(
                configRepository.send(
                    Config(
                        number = 16997417840,
                        password = "12345",
                        nroEquip = 310,
                        app = "PMM",
                        version = "1.00"
                    )
                )
            ).thenReturn(
                resultFailure(
                    "IConfigRepository.send",
                    "-",
                    Exception()
                )
            )
            val result = sendDataConfig(
                number = "16997417840",
                password = "12345",
                nroEquip = "310",
                app = "pmm",
                version = "1.00"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendDataConfig -> IConfigRepository.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configRepository.send(
                    Config(
                        number = 16997417840,
                        password = "12345",
                        nroEquip = 310,
                        app = "PMM",
                        version = "1.00"
                    )
                )
            ).thenReturn(
                Result.success(
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
                            flagMechanic = true,
                            flagTire = true
                        )
                    )
                )
            )
            val result = sendDataConfig(
                number = "16997417840",
                password = "12345",
                nroEquip = "310",
                app = "pmm",
                version = "1.00"
            )
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
                        flagMechanic = true,
                        flagTire = true
                    )
                )
            )
        }

}