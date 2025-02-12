package br.com.usinasantafe.cmm.di.persistence

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import br.com.usinasantafe.cmm.R
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.utils.BASE_DB
import br.com.usinasantafe.cmm.utils.BASE_SHARE_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

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
    fun provideRetrofit(@ApplicationContext appContext: Context): Retrofit = Retrofit.Builder()
        .baseUrl(appContext.getString(R.string.base_url))
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideHttpClient())
        .build()

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext appContext: Context): DatabaseRoom {
        return Room.databaseBuilder(
            appContext,
            DatabaseRoom::class.java,
            BASE_DB
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences(BASE_SHARE_PREFERENCES, Context.MODE_PRIVATE)
    }
}