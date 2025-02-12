package br.com.usinasantafe.cmm.di.external

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import br.com.usinasantafe.cmm.di.persistence.ApiModule
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApiModule::class]
)
object ApiModuleTest {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(url: String): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideHttpClient())
        .build()

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext appContext: Context): DatabaseRoom {
        return Room.inMemoryDatabaseBuilder(
            appContext, DatabaseRoom::class.java).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("test", Context.MODE_PRIVATE)
    }

}

@Module
@InstallIn(SingletonComponent::class)
class FakeBaseUrlModule {

    @Provides
    @Singleton
    fun provideUrl(): String = "http://localhost:8080/"
}