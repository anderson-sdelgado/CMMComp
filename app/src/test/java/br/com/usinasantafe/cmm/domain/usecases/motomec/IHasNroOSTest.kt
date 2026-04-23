package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.OS
import br.com.usinasantafe.cmm.domain.entities.stable.ROSActivity
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.App
import br.com.usinasantafe.cmm.utils.CheckNetwork
import br.com.usinasantafe.cmm.lib.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.net.SocketTimeoutException

class IHasNroOSTest {

    private val checkNetwork = mock<CheckNetwork>()
    private val osRepository = mock<OSRepository>()
    private val rOSActivityRepository = mock<ROSActivityRepository>()
    private val getToken = mock<GetToken>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val usecase = IHasNroOS(
        checkNetwork = checkNetwork,
        osRepository = osRepository,
        rOSActivityRepository = rOSActivityRepository,
        getToken = getToken,
        motoMecRepository = motoMecRepository,
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository getApp`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                resultFailure(
                    "IConfigRepository.getApp",
                    "-",
                    Exception()
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
                "IHasNroOS -> IConfigRepository.getApp"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository hasByNroOS - App ECM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.ECM)
            )
            whenever(
                osRepository.hasByNro(123456)
            ).thenReturn(
                resultFailure(
                    "IOSRepository.hasByNroOS",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.HEADER_INITIAL
            )
            verify(rOSActivityRepository, never()).deleteAll()
            verify(osRepository, never()).deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHasNroOS -> IOSRepository.hasByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if function execute successfully and hasByNroOS return true - App ECM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.ECM)
            )
            whenever(
                osRepository.hasByNro(123456)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.HEADER_INITIAL
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
    fun `Check return false if function execute successfully and hasByNroOS return false - App ECM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.ECM)
            )
            whenever(
                osRepository.hasByNro(123456)
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.HEADER_INITIAL
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
    fun `Check return failure if have error in ROSActivityRepository deleteAll - FlowApp HEADER_INITIAL and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
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
                "IHasNroOS -> IROSActivityRepository.deleteAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in OSActivityRepository deleteAll - FlowApp HEADER_INITIAL and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
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
            verify(rOSActivityRepository, atLeastOnce()).deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHasNroOS -> IOSRepository.deleteAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository hasNroOS - FlowApp HEADER_INITIAL and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
            whenever(
                osRepository.hasByNro(123456)
            ).thenReturn(
                resultFailure(
                    context = "IOSRepository.hasNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.HEADER_INITIAL
            )
            verify(rOSActivityRepository, atLeastOnce()).deleteAll()
            verify(osRepository, atLeastOnce()).deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHasNroOS -> IOSRepository.hasNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository hasNroOS - FlowApp NOTE_WORK and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
            whenever(
                osRepository.hasByNro(123456)
            ).thenReturn(
                resultFailure(
                    context = "IOSRepository.hasNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            verify(rOSActivityRepository, never()).deleteAll()
            verify(osRepository, never()).deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHasNroOS -> IOSRepository.hasNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if OSRepository checkNroOS is true - FlowApp NOTE_WORK and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
            whenever(
                osRepository.hasByNro(123456)
            ).thenReturn(
                Result.success(true)
            )
            verify(rOSActivityRepository, never()).deleteAll()
            verify(osRepository, never()).deleteAll()
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
    fun `Check return failure if not have connection in network and have error in MotoMecRepository setStatusConHeader - FlowApp NOTE_WORK and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
            whenever(
                osRepository.hasByNro(123456)
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
            verify(rOSActivityRepository, never()).deleteAll()
            verify(osRepository, never()).deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHasNroOS -> IMotoMecRepository.setStatusConHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if not have connection in network - FlowApp NOTE_WORK and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
            whenever(
                osRepository.hasByNro(123456)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                checkNetwork.isConnected()
            ).thenReturn(
                false
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            verify(motoMecRepository, atLeastOnce()).setStatusConHeader(false)
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
    fun `Check return failure if have error in GetToken - FlowApp NOTE_WORK and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
            whenever(
                osRepository.hasByNro(123456)
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
            verify(motoMecRepository, never()).setStatusConHeader(true)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHasNroOS -> GetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository listByNroOS - FlowApp NOTE_WORK and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
            whenever(
                osRepository.hasByNro(123456)
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
                osRepository.listByNro(
                    token = "token",
                    nro = 123456
                )
            ).thenReturn(
                resultFailure(
                    context = "IOSRepository.listByNroOS",
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
                "IHasNroOS -> IOSRepository.listByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if OSRepository listByNroOS return SocketTimeoutException and have error in MotoMecRepository setStatusConHeader - FlowApp NOTE_WORK and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
            whenever(
                osRepository.hasByNro(123456)
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
                osRepository.listByNro(
                    token = "token",
                    nro = 123456
                )
            ).thenReturn(
                resultFailure(
                    context = "IOSRepository.listByNroOS",
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
                "IHasNroOS -> IMotoMecRepository.setStatusConHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if OSRepository listByNroOS return SocketTimeoutException - FlowApp NOTE_WORK and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
            whenever(
                osRepository.hasByNro(123456)
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
                osRepository.listByNro(
                    token = "token",
                    nro = 123456
                )
            ).thenReturn(
                resultFailure(
                    context = "IOSRepository.listByNroOS",
                    message = "-",
                    cause = SocketTimeoutException()
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            verify(motoMecRepository, atLeastOnce()).setStatusConHeader(false)
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
    fun `Check return false if return empty list in OSRepository listByNroOS - FlowApp NOTE_WORK and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
            whenever(
                osRepository.hasByNro(123456)
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
                osRepository.listByNro(
                    token = "token",
                    nro = 123456
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
    fun `Check return failure if have error in ROSActivityRepository listByNroOS - FlowApp NOTE_WORK and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
            whenever(
                osRepository.hasByNro(123456)
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
                osRepository.listByNro(
                    token = "token",
                    nro = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OS(
                            id = 1,
                            idPropAgr = 1,
                            area = 0.0,
                            nro = 123456,
                            idRelease = 1
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
                    context = "IROSActivityRepository.listByNroOS",
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
                "IHasNroOS -> IROSActivityRepository.listByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return false if return empty list in ROSActivityRepository listByNroOS - FlowApp NOTE_WORK and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
            whenever(
                osRepository.hasByNro(123456)
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
                osRepository.listByNro(
                    token = "token",
                    nro = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OS(
                            id = 1,
                            idPropAgr = 1,
                            area = 0.0,
                            nro = 123456,
                            idRelease = 1
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
    fun `Check return failure if have error in OSRepository add - FlowApp NOTE_WORK and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
            whenever(
                osRepository.hasByNro(123456)
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
                osRepository.listByNro(
                    token = "token",
                    nro = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OS(
                            id = 1,
                            idPropAgr = 1,
                            area = 0.0,
                            nro = 1,
                            idRelease = 1
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
                Result.success(Unit)
            )
            whenever(
                osRepository.add(
                    OS(
                        id = 1,
                        idPropAgr = 1,
                        area = 0.0,
                        nro = 1,
                        idRelease = 1
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
                "IHasNroOS -> IOSRepository.add"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ROSActivityRepository addAll - FlowApp NOTE_WORK and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
            whenever(
                osRepository.hasByNro(123456)
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
                osRepository.listByNro(
                    token = "token",
                    nro = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OS(
                            id = 1,
                            idPropAgr = 1,
                            area = 0.0,
                            nro = 1,
                            idRelease = 1
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
                Result.success(Unit)
            )
            whenever(
                osRepository.add(
                    OS(
                        id = 1,
                        idPropAgr = 1,
                        area = 0.0,
                        nro = 1,
                        idRelease = 1
                    )
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                rOSActivityRepository.deleteAll()
            ).thenReturn(
                Result.success(Unit)
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
                "IHasNroOS -> IROSActivityRepository.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - FlowApp NOTE_WORK and App PMM`() =
        runTest {
            whenever(
                configRepository.getApp()
            ).thenReturn(
                Result.success(App.PMM)
            )
            whenever(
                osRepository.hasByNro(123456)
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
                osRepository.listByNro(
                    token = "token",
                    nro = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OS(
                            id = 1,
                            idPropAgr = 1,
                            area = 0.0,
                            nro = 1,
                            idRelease = 1
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
                Result.success(Unit)
            )
            whenever(
                osRepository.add(
                    OS(
                        id = 1,
                        idPropAgr = 1,
                        area = 0.0,
                        nro = 1,
                        idRelease = 1
                    )
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                rOSActivityRepository.deleteAll()
            ).thenReturn(
                Result.success(Unit)
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
                Result.success(Unit)
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