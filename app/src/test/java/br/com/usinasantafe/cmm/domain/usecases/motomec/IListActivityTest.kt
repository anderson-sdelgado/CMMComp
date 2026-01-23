package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.domain.entities.stable.OS
import br.com.usinasantafe.cmm.domain.entities.stable.REquipActivity
import br.com.usinasantafe.cmm.domain.entities.stable.ROSActivity
import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.REquipActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.TypeEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IListActivityTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val osRepository = mock<OSRepository>()
    private val activityRepository= mock<ActivityRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val rOSActivityRepository = mock<ROSActivityRepository>()
    private val rEquipActivityRepository = mock<REquipActivityRepository>()
    private val equipRepository = mock<EquipRepository>()

    private val usecase = IListActivity(
        equipRepository = equipRepository,
        motoMecRepository = motoMecRepository,
        osRepository = osRepository,
        activityRepository = activityRepository,
        rOSActivityRepository = rOSActivityRepository,
        rEquipActivityRepository = rEquipActivityRepository
    )

    private val rEquipActivityList = listOf(
        REquipActivity(
            idREquipActivity = 1,
            idEquip = 1,
            idActivity = 1
        ),
        REquipActivity(
            idREquipActivity = 2,
            idEquip = 1,
            idActivity = 2
        ),
        REquipActivity(
            idREquipActivity = 3,
            idEquip = 1,
            idActivity = 3
        ),
        REquipActivity(
            idREquipActivity = 4,
            idEquip = 1,
            idActivity = 4
        )
    )

    private val rOSActivityList = listOf(
        ROSActivity(
            idROSActivity = 1,
            idOS = 1,
            idActivity = 1
        ),
        ROSActivity(
            idROSActivity = 2,
            idOS = 1,
            idActivity = 2
        ),
        ROSActivity(
            idROSActivity = 3,
            idOS = 1,
            idActivity = 5
        )
    )

    @Test
    fun `Check return failure if have error in ConfigRepository get`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                resultFailure(
                    context = "ConfigRepository.get",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListActivity -> ConfigRepository.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in REquipActivityRepository getListByIdEquip`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
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
            whenever(
                rEquipActivityRepository.listByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    context = "REquipActivityRepository.getListByIdEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListActivity -> REquipActivityRepository.getListByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository getNroOS`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
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
            whenever(
                rEquipActivityRepository.listByIdEquip(1)
            ).thenReturn(
                Result.success(rEquipActivityList)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                resultFailure(
                    context = "HeaderMotoMecRepository.getNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListActivity -> HeaderMotoMecRepository.getNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository getListByNroOS`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
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
            whenever(
                rEquipActivityRepository.listByIdEquip(1)
            ).thenReturn(
                Result.success(rEquipActivityList)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(123456)
            )
            whenever(
                osRepository.listByNroOS(
                    nroOS = 123456
                )
            ).thenReturn(
                resultFailure(
                    context = "OSRepository.getListByNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListActivity -> OSRepository.getListByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ActivityRepository getListByListId without OS`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
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
            whenever(
                rEquipActivityRepository.listByIdEquip(1)
            ).thenReturn(
                Result.success(rEquipActivityList)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(123456)
            )
            whenever(
                osRepository.listByNroOS(
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(emptyList())
            )
            val idList = rEquipActivityList.map { it.idActivity }
            whenever(
                activityRepository.listByIdList(
                    idList = idList
                )
            ).thenReturn(
                resultFailure(
                    context = "ActivityRepository.getListByListId",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListActivity -> ActivityRepository.getListByListId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return list if function execute successfully without OS`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
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
            whenever(
                rEquipActivityRepository.listByIdEquip(1)
            ).thenReturn(
                Result.success(rEquipActivityList)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(123456)
            )
            whenever(
                osRepository.listByNroOS(
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(emptyList())
            )
            val idList = rEquipActivityList.map { it.idActivity }
            whenever(
                activityRepository.listByIdList(
                    idList = idList
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Activity(
                            idActivity = 1,
                            codActivity = 1,
                            descrActivity = "Atividade 1"
                        ),
                        Activity(
                            idActivity = 2,
                            codActivity = 2,
                            descrActivity = "Atividade 2"
                        ),
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idActivity,
                1
            )
            assertEquals(
                entity1.codActivity,
                1
            )
            assertEquals(
                entity1.descrActivity,
                "Atividade 1"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idActivity,
                2
            )
            assertEquals(
                entity2.codActivity,
                2
            )
            assertEquals(
                entity2.descrActivity,
                "Atividade 2"
            )
            assertEquals(
                idList,
                listOf(1, 2, 3, 4)
            )
        }

    @Test
    fun `Check return failure if have error in ROSActivityRepository getListByIdOS with OS`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
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
            whenever(
                rEquipActivityRepository.listByIdEquip(1)
            ).thenReturn(
                Result.success(rEquipActivityList)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(123456)
            )
            whenever(
                osRepository.listByNroOS(
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OS(
                            idOS = 1,
                            nroOS = 123456,
                            idEquip = 1,
                            idLibOS = 1,
                            idPropAgr = 1,
                            areaOS = 0.0
                        )
                    )
                )
            )
            whenever(
                rOSActivityRepository.listByIdOS(
                    idOS = 1
                )
            ).thenReturn(
                resultFailure(
                    context = "ROSActivityRepository.getListByIdOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListActivity -> ROSActivityRepository.getListByIdOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ActivityRepository getListByListId with OS`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
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
            whenever(
                rEquipActivityRepository.listByIdEquip(1)
            ).thenReturn(
                Result.success(rEquipActivityList)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(123456)
            )
            whenever(
                osRepository.listByNroOS(
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OS(
                            idOS = 1,
                            nroOS = 123456,
                            idEquip = 1,
                            idLibOS = 1,
                            idPropAgr = 1,
                            areaOS = 0.0
                        )
                    )
                )
            )
            whenever(
                rOSActivityRepository.listByIdOS(
                    idOS = 1
                )
            ).thenReturn(
                Result.success(rOSActivityList)
            )
            val idActivityEquipList = rEquipActivityList.map { it.idActivity }
            val idActivityOSList = rOSActivityList.map { it.idActivity }
            val idList = idActivityEquipList.intersect(idActivityOSList.toSet()).toList()
            whenever(
                activityRepository.listByIdList(
                    idList = idList
                )
            ).thenReturn(
                resultFailure(
                    context = "ActivityRepository.getListByListId",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListActivity -> ActivityRepository.getListByListId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            assertEquals(
                idActivityEquipList,
                listOf(1, 2, 3, 4)
            )
            assertEquals(
                idActivityOSList,
                listOf(1, 2, 5)
            )
            assertEquals(
                idList,
                listOf(1, 2)
            )
        }

    @Test
    fun `Check return list if function execute successfully with OS`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
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
            whenever(
                rEquipActivityRepository.listByIdEquip(1)
            ).thenReturn(
                Result.success(rEquipActivityList)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(123456)
            )
            whenever(
                osRepository.listByNroOS(
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OS(
                            idOS = 1,
                            nroOS = 123456,
                            idEquip = 1,
                            idLibOS = 1,
                            idPropAgr = 1,
                            areaOS = 0.0
                        )
                    )
                )
            )
            whenever(
                rOSActivityRepository.listByIdOS(
                    idOS = 1
                )
            ).thenReturn(
                Result.success(rOSActivityList)
            )
            val idActivityEquipList = rEquipActivityList.map { it.idActivity }
            val idActivityOSList = rOSActivityList.map { it.idActivity }
            val idList = idActivityEquipList.intersect(idActivityOSList.toSet()).toList()
            whenever(
                activityRepository.listByIdList(
                    idList = idList
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Activity(
                            idActivity = 1,
                            codActivity = 1,
                            descrActivity = "Atividade 1"
                        ),
                        Activity(
                            idActivity = 2,
                            codActivity = 2,
                            descrActivity = "Atividade 2"
                        ),
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idActivity,
                1
            )
            assertEquals(
                entity1.codActivity,
                1
            )
            assertEquals(
                entity1.descrActivity,
                "Atividade 1"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idActivity,
                2
            )
            assertEquals(
                entity2.codActivity,
                2
            )
            assertEquals(
                entity2.descrActivity,
                "Atividade 2"
            )
            assertEquals(
                idActivityEquipList,
                listOf(1, 2, 3, 4)
            )
            assertEquals(
                idActivityOSList,
                listOf(1, 2, 5)
            )
            assertEquals(
                idList,
                listOf(1, 2)
            )
        }



}