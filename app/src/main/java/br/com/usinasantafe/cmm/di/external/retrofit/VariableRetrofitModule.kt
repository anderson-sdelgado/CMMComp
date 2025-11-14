package br.com.usinasantafe.cmm.di.external.retrofit

import br.com.usinasantafe.cmm.di.provider.DefaultRetrofit
import br.com.usinasantafe.cmm.external.retrofit.api.variable.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VariableRetrofitModule {

    @Provides
    @Singleton
    fun checkListApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): CheckListApi = retrofit.create(CheckListApi::class.java)

    @Provides
    @Singleton
    fun configApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): ConfigApi = retrofit.create(ConfigApi::class.java)

    @Provides
    @Singleton
    fun motoMecApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): MotoMecApi = retrofit.create(MotoMecApi::class.java)

}