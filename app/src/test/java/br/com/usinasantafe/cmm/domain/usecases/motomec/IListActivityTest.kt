package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.entities.stable.OS
import br.com.usinasantafe.cmm.domain.entities.stable.REquipActivity
import br.com.usinasantafe.cmm.domain.entities.stable.ROSActivity
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.REquipActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IListActivityTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val osRepository = mock<OSRepository>()
    private val activityRepository= mock<ActivityRepository>()
    private val rOSActivityRepository = mock<ROSActivityRepository>()
    private val rEquipActivityRepository = mock<REquipActivityRepository>()

    private val usecase = IListActivity(
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
    fun `Check return failure if have error in MotoMecRepository getIdEquipHeader`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                resultFailure(
                    context = "MotoMecRepository.getIdEquipHeader",
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
                "IListActivity -> MotoMecRepository.getIdEquipHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in REquipActivityRepository listByIdEquip`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
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
    fun `Check return failure if have error in MotoMecRepository getNroOSHeader`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
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
    fun `Check return failure if have error in OSRepository hasByNroOS`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
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
                osRepository.hasByNroOS(
                    nroOS = 123456
                )
            ).thenReturn(
                resultFailure(
                    context = "OSRepository.hasByNroOS",
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
                "IListActivity -> OSRepository.hasByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ActivityRepository listByIdList without OS`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
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
                osRepository.hasByNroOS(
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(false)
            )
            val idActivityEquipList = rEquipActivityList.map { it.idActivity }
            whenever(
                activityRepository.listByIdList(idActivityEquipList)
            ).thenReturn(
                resultFailure(
                    context = "ActivityRepository.listByIdList",
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
                "IListActivity -> ActivityRepository.listByIdList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully without OS`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
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
                osRepository.hasByNroOS(
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(false)
            )
            val idActivityEquipList = rEquipActivityList.map { it.idActivity }
            whenever(
                activityRepository.listByIdList(idActivityEquipList)
            ).thenReturn(
                Result.success(
                    listOf(
                        Activity(
                            id = 1,
                            cod = 1,
                            descr = "Activity 1"
                        ),
                        Activity(
                            id = 2,
                            cod = 2,
                            descr = "Activity 2"
                        ),
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    Activity(
                        id = 1,
                        cod = 1,
                        descr = "Activity 1"
                    ),
                    Activity(
                        id = 2,
                        cod = 2,
                        descr = "Activity 2"
                    ),
                )
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository getByNroOS with OS`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
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
                osRepository.hasByNroOS(
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osRepository.getByNroOS(123456)
            ).thenReturn(
                resultFailure(
                    context = "OSRepository.getByNroOS",
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
                "IListActivity -> OSRepository.getByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ROSActivityRepository listByIdOS with OS`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
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
                osRepository.hasByNroOS(
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osRepository.getByNroOS(123456)
            ).thenReturn(
                Result.success(
                    OS(
                        idOS = 10,
                        nroOS = 123456,
                        idEquip = 1,
                        idLibOS = 1,
                        idPropAgr = 1,
                        areaOS = 0.0
                    )
                )
            )
            whenever(
                rOSActivityRepository.listByIdOS(10)
            ).thenReturn(
                resultFailure(
                    context = "ROSActivityRepository.listByIdOS",
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
                "IListActivity -> ROSActivityRepository.listByIdOS"
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
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
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
                osRepository.hasByNroOS(
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osRepository.getByNroOS(
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
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
                    context = "ActivityRepository.listByIdList",
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
                "IListActivity -> ActivityRepository.listByIdList"
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
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
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
                osRepository.hasByNroOS(
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osRepository.getByNroOS(
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
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
                            id = 1,
                            cod = 1,
                            descr = "Activity 1"
                        ),
                        Activity(
                            id = 2,
                            cod = 2,
                            descr = "Activity 2"
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
                entity1.id,
                1
            )
            assertEquals(
                entity1.cod,
                1
            )
            assertEquals(
                entity1.descr,
                "Activity 1"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.id,
                2
            )
            assertEquals(
                entity2.cod,
                2
            )
            assertEquals(
                entity2.descr,
                "Activity 2"
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