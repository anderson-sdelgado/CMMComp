package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.lib.TypeEquipMain
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISaveDataConfigTest {

    private val configRepository = mock<ConfigRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val usecase = ISaveDataConfig(
        configRepository = configRepository,
        equipRepository = equipRepository
    )

    @Test
    fun `Check return failure if input data config is incorrect`() =
        runTest {
            val result = usecase(
                number = "16997417840a",
                password = "12345",
                version = "1.00",
                app = "PMM",
                checkMotoMec = false,
                idServ = 1,
                equip = Equip(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquipMain = TypeEquipMain.NORMAL,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveDataConfig"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"16997417840a\""
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository save`() =
        runTest {
            whenever(
                configRepository.save(
                    Config(
                        number = 16997417840,
                        password = "12345",
                        version = "1.00",
                        app = "PMM",
                        nroEquip = 310,
                        checkMotoMec = false,
                        idServ = 1,
                    )
                )
            ).thenReturn(
                resultFailure(
                    "IConfigRepository.save",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                number = "16997417840",
                password = "12345",
                version = "1.00",
                app = "PMM",
                checkMotoMec = false,
                idServ = 1,
                equip = Equip(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquipMain = TypeEquipMain.NORMAL,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveDataConfig -> IConfigRepository.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository saveEquipMain`() =
        runTest {
            whenever(
                configRepository.save(
                    Config(
                        number = 16997417840,
                        password = "12345",
                        version = "1.00",
                        app = "PMM",
                        nroEquip = 310,
                        checkMotoMec = false,
                        idServ = 1,
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                equipRepository.saveEquipMain(
                    Equip(
                            id = 10,
                            nro = 2200,
                            codClass = 1,
                            descrClass = "TRATOR",
                            codTurnEquip = 1,
                            idCheckList = 1,
                            typeEquipMain = TypeEquipMain.NORMAL,
                            hourMeter = 5000.0,
                            classify = 1,
                            flagMechanic = true,
                            flagTire = true
                        )
                    )
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.saveEquipMain",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                number = "16997417840",
                password = "12345",
                version = "1.00",
                app = "PMM",
                checkMotoMec = false,
                idServ = 1,
                equip = Equip(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquipMain = TypeEquipMain.NORMAL,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveDataConfig -> IEquipRepository.saveEquipMain"
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
                configRepository.save(
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
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                equipRepository.saveEquipMain(
                    Equip(
                        id = 10,
                        nro = 2200,
                        codClass = 1,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeEquipMain = TypeEquipMain.NORMAL,
                        hourMeter = 5000.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                number = "16997417840",
                password = "12345",
                version = "1.00",
                app = "PMM",
                checkMotoMec = false,
                idServ = 1,
                equip = Equip(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquipMain = TypeEquipMain.NORMAL,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
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