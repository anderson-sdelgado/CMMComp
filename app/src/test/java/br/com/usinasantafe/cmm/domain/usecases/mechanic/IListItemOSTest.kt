package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.domain.entities.stable.Component
import br.com.usinasantafe.cmm.domain.entities.stable.ItemOSMechanic
import br.com.usinasantafe.cmm.domain.entities.stable.Service
import br.com.usinasantafe.cmm.domain.repositories.stable.ComponentRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemOSMechanicRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ServiceRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.presenter.model.ItemOSMechanicModel
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IListItemOSTest {

    private val itemOSMechanicRepository = mock<ItemOSMechanicRepository>()
    private val mechanicRepository = mock<MechanicRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val serviceRepository = mock<ServiceRepository>()
    private val componentRepository = mock<ComponentRepository>()
    private val usecase = IListItemOS(
        itemOSMechanicRepository = itemOSMechanicRepository,
        mechanicRepository = mechanicRepository,
        equipRepository = equipRepository,
        serviceRepository = serviceRepository,
        componentRepository = componentRepository
    )

    @Test
    fun `Check return failure if have error in EquipRepository getIdEquipMain`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getIdEquipMain",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemOS -> IEquipRepository.getIdEquipMain"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MechanicRepository getNroOS`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                mechanicRepository.getNroOS()
            ).thenReturn(
                resultFailure(
                    "IMechanicRepository.getNroOS",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemOS -> IMechanicRepository.getNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ItemOSMechanicRepository listByIdEquipAndNroOS`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                mechanicRepository.getNroOS()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemOSMechanicRepository.listByIdEquipAndNroOS(1, 1)
            ).thenReturn(
                resultFailure(
                    "IItemOSMechanicRepository.listByIdEquipAndNroOS",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemOS -> IItemOSMechanicRepository.listByIdEquipAndNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return empty list if function execute successfully and list return null`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                mechanicRepository.getNroOS()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemOSMechanicRepository.listByIdEquipAndNroOS(1, 1)
            ).thenReturn(
                Result.success(emptyList())
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList()
            )
        }

    @Test
    fun `Check return correct if function execute successfully and service and component return null`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                mechanicRepository.getNroOS()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemOSMechanicRepository.listByIdEquipAndNroOS(1, 1)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemOSMechanic(
                            id = 1,
                            idEquip = 1,
                            nroOS = 1,
                            seqItem = 1,
                            idServ = 1,
                            idComp = 1
                        )
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
                    ItemOSMechanicModel(
                        seq = 1,
                        service = "",
                        component = ""
                    )
                )
            )
        }

    @Test
    fun `Check return failure if have error in ServiceRepository getById`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                mechanicRepository.getNroOS()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemOSMechanicRepository.listByIdEquipAndNroOS(1, 1)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemOSMechanic(
                            id = 1,
                            idEquip = 1,
                            nroOS = 1,
                            seqItem = 1,
                            idServ = 1,
                            idComp = 1
                        )
                    )
                )
            )
            whenever(
                serviceRepository.getById(1)
            ).thenReturn(
                resultFailure(
                    "IServiceRepository.getById",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemOS -> IServiceRepository.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ComponentRepository getById`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                mechanicRepository.getNroOS()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemOSMechanicRepository.listByIdEquipAndNroOS(1, 1)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemOSMechanic(
                            id = 1,
                            idEquip = 1,
                            nroOS = 1,
                            seqItem = 1,
                            idServ = 1,
                            idComp = 1
                        )
                    )
                )
            )
            whenever(
                serviceRepository.getById(1)
            ).thenReturn(
                Result.success(
                    Service(
                        id = 1,
                        cod = 1,
                        descr = "Service 1"
                    )
                )
            )
            whenever(
                componentRepository.getById(1)
            ).thenReturn(
                resultFailure(
                    "IComponentRepository.getById",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemOS -> IComponentRepository.getById"
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
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                mechanicRepository.getNroOS()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemOSMechanicRepository.listByIdEquipAndNroOS(1, 1)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemOSMechanic(
                            id = 1,
                            idEquip = 1,
                            nroOS = 1,
                            seqItem = 1,
                            idServ = 1,
                            idComp = 1
                        )
                    )
                )
            )
            whenever(
                serviceRepository.getById(1)
            ).thenReturn(
                Result.success(
                    Service(
                        id = 1,
                        cod = 1,
                        descr = "Service 1"
                    )
                )
            )
            whenever(
                componentRepository.getById(1)
            ).thenReturn(
                Result.success(
                    Component(
                        id = 1,
                        cod = 1,
                        descr = "Component 1"
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
                    ItemOSMechanicModel(
                        seq = 1,
                        service = "Service 1",
                        component = "1 - Component 1"
                    )
                )
            )
        }
}