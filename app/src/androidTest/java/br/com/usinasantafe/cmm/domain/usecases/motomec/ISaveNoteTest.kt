package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionActivityDao
import br.com.usinasantafe.cmm.external.room.dao.variable.CollectionDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.ImplementDao
import br.com.usinasantafe.cmm.external.room.dao.variable.ItemMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.PerformanceDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ImplementSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ImplementSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.lib.TypeEquip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import java.util.Date
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ISaveNoteTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SaveNote

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var implementSharedPreferencesDatasource: ImplementSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var itemMotoMecDao: ItemMotoMecDao

    @Inject
    lateinit var performanceDao: PerformanceDao

    @Inject
    lateinit var collectionDao: CollectionDao

    @Inject
    lateinit var functionActivityDao: FunctionActivityDao

    @Inject
    lateinit var implementDao: ImplementDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_id_in_header_moto_mec_shared_preferences() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveNote -> IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecSharedPreferencesDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }


    @Test
    fun check_return_failure_if_not_have_nroOS_in_header_moto_mec_shared_preferences() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveNote -> IMotoMecRepository.getNroOSHeader -> IHeaderMotoMecSharedPreferencesDatasource.getNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_idActivity_in_header_moto_mec_shared_preferences() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1,
                    nroOS = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveNote -> IMotoMecRepository.getIdActivityHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_moto_mec_room() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1,
                    nroOS = 1,
                    idActivity = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveNote -> IHeaderMotoMecRoomDatasource.setSend"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'void br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.setStatusSend(br.com.usinasantafe.cmm.lib.StatusSend)' on a null object reference"
            )
        }

    @Test
    fun check_insert_item_moto_mec_room_and_not_insert_performance_and_collection() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1,
                    nroOS = 123456,
                    idActivity = 1
                )
            )
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 19759,
                    idEquip = 10,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10000.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
                )
            )
            val listBefore = itemMotoMecDao.all()
            assertEquals(
                listBefore.size,
                0
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val listAfter = itemMotoMecDao.all()
            assertEquals(
                listAfter.size,
                1
            )
            val item = listAfter[0]
            assertEquals(
                item.idHeader,
                1
            )
            assertEquals(
                item.nroOS,
                123456
            )
            assertEquals(
                item.idActivity,
                1
            )
            assertEquals(
                item.idStop,
                null
            )
            val listPerformance = performanceDao.all()
            assertEquals(
                listPerformance.size,
                0
            )
            val listCollection = collectionDao.all()
            assertEquals(
                listCollection.size,
                0
            )
        }

    @Test
    fun check_insert_item_moto_mec_room_and_insert_performance() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1,
                    nroOS = 123456,
                    idActivity = 1
                )
            )
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 19759,
                    idEquip = 10,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10000.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
                )
            )
            functionActivityDao.insertAll(
                listOf(
                    FunctionActivityRoomModel(
                        idFunctionActivity = 1,
                        idActivity = 1,
                        typeActivity = TypeActivity.PERFORMANCE
                    )
                )
            )
            val listBefore = itemMotoMecDao.all()
            assertEquals(
                listBefore.size,
                0
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val listAfter = itemMotoMecDao.all()
            assertEquals(
                listAfter.size,
                1
            )
            val item = listAfter[0]
            assertEquals(
                item.idHeader,
                1
            )
            assertEquals(
                item.nroOS,
                123456
            )
            assertEquals(
                item.idActivity,
                1
            )
            assertEquals(
                item.idStop,
                null
            )
            val listPerformance = performanceDao.all()
            assertEquals(
                listPerformance.size,
                1
            )
            val performance = listPerformance[0]
            assertEquals(
                performance.id,
                1
            )
            assertEquals(
                performance.nroOS,
                123456
            )
            assertEquals(
                performance.idHeader,
                1
            )
            assertEquals(
                performance.value,
                null
            )
            val listCollection = collectionDao.all()
            assertEquals(
                listCollection.size,
                0
            )
        }

    @Test
    fun check_insert_item_moto_mec_room_and_insert_performance_and_collection_and_not_implement() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1,
                    nroOS = 123456,
                    idActivity = 1
                )
            )
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 19759,
                    idEquip = 10,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10000.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
                )
            )
            functionActivityDao.insertAll(
                listOf(
                    FunctionActivityRoomModel(
                        idFunctionActivity = 1,
                        idActivity = 1,
                        typeActivity = TypeActivity.PERFORMANCE
                    ),
                    FunctionActivityRoomModel(
                        idFunctionActivity = 2,
                        idActivity = 1,
                        typeActivity = TypeActivity.COLLECTION
                    )
                )
            )
            val listBefore = itemMotoMecDao.all()
            assertEquals(
                listBefore.size,
                0
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val listAfter = itemMotoMecDao.all()
            assertEquals(
                listAfter.size,
                1
            )
            val item = listAfter[0]
            assertEquals(
                item.idHeader,
                1
            )
            assertEquals(
                item.nroOS,
                123456
            )
            assertEquals(
                item.idActivity,
                1
            )
            assertEquals(
                item.idStop,
                null
            )
            val listPerformance = performanceDao.all()
            assertEquals(
                listPerformance.size,
                1
            )
            val performance = listPerformance[0]
            assertEquals(
                performance.id,
                1
            )
            assertEquals(
                performance.nroOS,
                123456
            )
            assertEquals(
                performance.idHeader,
                1
            )
            assertEquals(
                performance.value,
                null
            )
            val listCollection = collectionDao.all()
            assertEquals(
                listCollection.size,
                1
            )
            val collection = listCollection[0]
            assertEquals(
                collection.id,
                1
            )
            assertEquals(
                collection.nroOS,
                123456
            )
            assertEquals(
                collection.idHeader,
                1
            )
            assertEquals(
                collection.value,
                null
            )
            val listImplement = implementDao.all()
            assertEquals(
                listImplement.size,
                0
            )
        }

    @Test
    fun check_insert_item_moto_mec_room_and_insert_performance_and_collection_and_implement() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1,
                    nroOS = 123456,
                    idActivity = 1
                )
            )
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 19759,
                    idEquip = 10,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10000.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
                )
            )
            functionActivityDao.insertAll(
                listOf(
                    FunctionActivityRoomModel(
                        idFunctionActivity = 1,
                        idActivity = 1,
                        typeActivity = TypeActivity.PERFORMANCE
                    ),
                    FunctionActivityRoomModel(
                        idFunctionActivity = 2,
                        idActivity = 1,
                        typeActivity = TypeActivity.COLLECTION
                    )
                )
            )
            implementSharedPreferencesDatasource.add(
                ImplementSharedPreferencesModel(
                    nroEquip = 10,
                    pos = 1
                )
            )
            implementSharedPreferencesDatasource.add(
                ImplementSharedPreferencesModel(
                    nroEquip = 20,
                    pos = 2
                )
            )
            val listBefore = itemMotoMecDao.all()
            assertEquals(
                listBefore.size,
                0
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val listAfter = itemMotoMecDao.all()
            assertEquals(
                listAfter.size,
                1
            )
            val item = listAfter[0]
            assertEquals(
                item.idHeader,
                1
            )
            assertEquals(
                item.nroOS,
                123456
            )
            assertEquals(
                item.idActivity,
                1
            )
            assertEquals(
                item.idStop,
                null
            )
            val listPerformance = performanceDao.all()
            assertEquals(
                listPerformance.size,
                1
            )
            val performance = listPerformance[0]
            assertEquals(
                performance.id,
                1
            )
            assertEquals(
                performance.nroOS,
                123456
            )
            assertEquals(
                performance.idHeader,
                1
            )
            assertEquals(
                performance.value,
                null
            )
            val listCollection = collectionDao.all()
            assertEquals(
                listCollection.size,
                1
            )
            val collection = listCollection[0]
            assertEquals(
                collection.id,
                1
            )
            assertEquals(
                collection.nroOS,
                123456
            )
            assertEquals(
                collection.idHeader,
                1
            )
            assertEquals(
                collection.value,
                null
            )
            val listImplement = implementDao.all()
            assertEquals(
                listImplement.size,
                2
            )
            val implement1 = listImplement[0]
            assertEquals(
                implement1.id,
                1
            )
            assertEquals(
                implement1.idItem,
                1
            )
            assertEquals(
                implement1.nroEquip,
                10
            )
            assertEquals(
                implement1.pos,
                1
            )
            val implement2 = listImplement[1]
            assertEquals(
                implement2.id,
                2
            )
            assertEquals(
                implement2.idItem,
                1
            )
            assertEquals(
                implement2.nroEquip,
                20
            )
            assertEquals(
                implement2.pos,
                2
            )
        }

}

