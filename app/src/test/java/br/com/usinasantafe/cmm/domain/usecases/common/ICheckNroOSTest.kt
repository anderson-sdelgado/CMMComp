package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.entities.stable.OS
import br.com.usinasantafe.cmm.domain.entities.stable.ROSActivity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.CheckNetwork
import br.com.usinasantafe.cmm.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.net.SocketTimeoutException

class ICheckNroOSTest {

    private val checkNetwork = mock<CheckNetwork>()
    private val osRepository = mock<OSRepository>()
    private val rOSActivityRepository = mock<ROSActivityRepository>()
    private val getToken = mock<GetToken>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = ICheckNroOS(
        checkNetwork = checkNetwork,
        osRepository = osRepository,
        rOSActivityRepository = rOSActivityRepository,
        getToken = getToken,
        motoMecRepository = motoMecRepository
    )

    @Test
    fun `Check return failure if have error in ROSActivityRepository deleteAll`() =
        runTest {
            whenever(
                rOSActivityRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    context = "IROSActivityRepository.deleteAll",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IROSActivityRepository.deleteAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in OSActivityRepository deleteAll`() =
        runTest {
            whenever(
                rOSActivityRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    context = "IOSRepository.deleteAll",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IOSRepository.deleteAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository checkNroOS - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                osRepository.checkNroOS(123456)
            ).thenReturn(
                resultFailure(
                    context = "IOSRepository.checkNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IOSRepository.checkNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if OSRepository checkNroOS is true - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                osRepository.checkNroOS(123456)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
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
    fun `Check return failure if not have connection in network and have error in MotoMecRepository setStatusConHeader - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                osRepository.checkNroOS(123456)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                false
            )
            whenever(
                motoMecRepository.setStatusConHeader(false)
            ).thenReturn(
                resultFailure(
                    context = "IMotoMecRepository.setStatusConHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IMotoMecRepository.setStatusConHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if not have connection in network`() =
        runTest {
            whenever(
                osRepository.checkNroOS(123456)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                false
            )
            whenever(
                motoMecRepository.setStatusConHeader(false)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
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
    fun `Check return failure if have error in GetToken - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                osRepository.checkNroOS(123456)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                resultFailure(
                    context = "GetToken",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> GetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository getListByNroOS - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                osRepository.checkNroOS(123456)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                osRepository.getListByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                resultFailure(
                    context = "IOSRepository.getListByNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IOSRepository.getListByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if OSRepository getListByNroOS return SocketTimeoutException and have error in MotoMecRepository setStatusConHeader - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                osRepository.checkNroOS(123456)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                osRepository.getListByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                resultFailure(
                    context = "IOSRepository.getListByNroOS",
                    message = "-",
                    cause = SocketTimeoutException()
                )
            )
            whenever(
                motoMecRepository.setStatusConHeader(false)
            ).thenReturn(
                resultFailure(
                    context = "IMotoMecRepository.setStatusConHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IMotoMecRepository.setStatusConHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if OSRepository getListByNroOS return SocketTimeoutException - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                osRepository.checkNroOS(123456)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                osRepository.getListByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                resultFailure(
                    context = "IOSRepository.getListByNroOS",
                    message = "-",
                    cause = SocketTimeoutException()
                )
            )
            whenever(
                motoMecRepository.setStatusConHeader(false)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
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
    fun `Check return false if return empty list in OSRepository getListByNroOS - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                osRepository.checkNroOS(123456)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                osRepository.getListByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
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
    fun `Check return failure if have error in ROSActivityRepository getListByNroOS - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                osRepository.checkNroOS(123456)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                osRepository.getListByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OS(
                            idOS = 1,
                            idEquip = 1,
                            idPropAgr = 1,
                            areaOS = 0.0,
                            nroOS = 123456,
                            idLibOS = 1
                        )
                    )
                )
            )
            whenever(
                rOSActivityRepository.listByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                resultFailure(
                    context = "IROSAtivRepository.getListByNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IROSAtivRepository.getListByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return false if return empty list in ROSActivityRepository getListByNroOS - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                osRepository.checkNroOS(123456)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                osRepository.getListByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OS(
                            idOS = 1,
                            idEquip = 1,
                            idPropAgr = 1,
                            areaOS = 0.0,
                            nroOS = 123456,
                            idLibOS = 1
                        )
                    )
                )
            )
            whenever(
                rOSActivityRepository.listByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
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
    fun `Check return failure if have error in OSRepository add - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                osRepository.checkNroOS(123456)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                osRepository.getListByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OS(
                            idOS = 1,
                            idEquip = 1,
                            idPropAgr = 1,
                            areaOS = 0.0,
                            nroOS = 1,
                            idLibOS = 1
                        )
                    )
                )
            )
            whenever(
                rOSActivityRepository.listByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        ROSActivity(
                            idROSActivity = 1,
                            idOS = 1,
                            idActivity = 1
                        )
                    )
                )
            )
            whenever(
                osRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osRepository.add(
                    OS(
                        idOS = 1,
                        idEquip = 1,
                        idPropAgr = 1,
                        areaOS = 0.0,
                        nroOS = 1,
                        idLibOS = 1
                    )
                )
            ).thenReturn(
                resultFailure(
                    context = "IOSRepository.add",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IOSRepository.add"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ROSActivityRepository addAll - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                osRepository.checkNroOS(123456)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                osRepository.getListByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OS(
                            idOS = 1,
                            idEquip = 1,
                            idPropAgr = 1,
                            areaOS = 0.0,
                            nroOS = 1,
                            idLibOS = 1
                        )
                    )
                )
            )
            whenever(
                rOSActivityRepository.listByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        ROSActivity(
                            idROSActivity = 1,
                            idOS = 1,
                            idActivity = 1
                        )
                    )
                )
            )
            whenever(
                osRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osRepository.add(
                    OS(
                        idOS = 1,
                        idEquip = 1,
                        idPropAgr = 1,
                        areaOS = 0.0,
                        nroOS = 1,
                        idLibOS = 1
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                rOSActivityRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                rOSActivityRepository.addAll(
                    listOf(
                        ROSActivity(
                            idROSActivity = 1,
                            idOS = 1,
                            idActivity = 1
                        )
                    )
                )
            ).thenReturn(
                resultFailure(
                    context = "IROSActivityRepository.addAll",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IROSActivityRepository.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                osRepository.checkNroOS(123456)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                osRepository.getListByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OS(
                            idOS = 1,
                            idEquip = 1,
                            idPropAgr = 1,
                            areaOS = 0.0,
                            nroOS = 1,
                            idLibOS = 1
                        )
                    )
                )
            )
            whenever(
                rOSActivityRepository.listByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        ROSActivity(
                            idROSActivity = 1,
                            idOS = 1,
                            idActivity = 1
                        )
                    )
                )
            )
            whenever(
                osRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osRepository.add(
                    OS(
                        idOS = 1,
                        idEquip = 1,
                        idPropAgr = 1,
                        areaOS = 0.0,
                        nroOS = 1,
                        idLibOS = 1
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                rOSActivityRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                rOSActivityRepository.addAll(
                    listOf(
                        ROSActivity(
                            idROSActivity = 1,
                            idOS = 1,
                            idActivity = 1
                        )
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
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