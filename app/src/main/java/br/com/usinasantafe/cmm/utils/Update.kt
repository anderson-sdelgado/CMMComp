package br.com.usinasantafe.cmm.utils

import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.infra.repositories.stable.IActivityRepository
import br.com.usinasantafe.cmm.lib.LevelUpdate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseUpdateTable<T>(
    private val getToken: GetToken,
    private val repository: UpdateRepository<T>,
    private val tableName: String,
    private val classAndMethod: String
) {

    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UpdateStatusState> = flow {
        flowCall(classAndMethod) {

            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, tableName)
            val token = getToken().getOrThrow()
            val entityList = repository.listAll(token).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.CLEAN, tableName)
            repository.deleteAll().getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.SAVE, tableName)
            repository.addAll(entityList).getOrThrow()
        }
    }
}

interface UpdateRepository<T> {
    suspend fun listAll(token: String): Result<List<T>>
    suspend fun deleteAll(): EmptyResult
    suspend fun addAll(list: List<T>): EmptyResult
}

abstract class BaseStableRepository<
        Entity,
        RetrofitModel,
        RoomModel,
        >(
    private val retrofitListAll: suspend (String) -> Result<List<RetrofitModel>>,
    private val roomAddAll: suspend (List<RoomModel>) -> Unit,
    private val roomDeleteAll: suspend () -> Unit,
    private val retrofitToEntity: (RetrofitModel) -> Entity,
    private val entityToRoom: (Entity) -> RoomModel,
    private val classAndMethod: String
) : UpdateRepository<Entity> {

    override suspend fun addAll(list: List<Entity>): EmptyResult =
        call(classAndMethod) {
            roomAddAll(list.map(entityToRoom))
        }

    override suspend fun deleteAll(): EmptyResult =
        call(classAndMethod) {
            roomDeleteAll()
        }

    override suspend fun listAll(token: String): Result<List<Entity>> =
        call(classAndMethod) {
            retrofitListAll(token).getOrThrow()
                .map(retrofitToEntity)
        }
}

interface BaseRoomDatasource<RoomModel> {
    suspend fun addAll(list: List<RoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
}

abstract class BaseRoomDatasourceImpl<RoomModel>(
    private val insertAll: suspend (List<RoomModel>) -> Unit,
    private val deleteAllInternal: suspend () -> Unit
) : BaseRoomDatasource<RoomModel> {

    override suspend fun addAll(list: List<RoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            deleteAllInternal()
        }
}

interface BaseRetrofitDatasource<RetrofitModel> {
    suspend fun listAll(token: String): Result<List<RetrofitModel>>
}

abstract class BaseRetrofitDatasourceImpl<RetrofitModel>(
    private val listAllCall: suspend (String) -> List<RetrofitModel>
) : BaseRetrofitDatasource<RetrofitModel> {

    override suspend fun listAll(token: String): Result<List<RetrofitModel>> =
        result(getClassAndMethod()) {
            listAllCall(token)
        }
}