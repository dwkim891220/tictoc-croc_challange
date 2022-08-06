package com.example.tictoccroc.repositories.remote

import android.content.Context
import androidx.databinding.ktx.BuildConfig
import com.example.tictoccroc.constants.ApiUrls
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): TicTocCrocApiService =
        retrofit.create(TicTocCrocApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(
        @ApplicationContext context: Context,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiUrls.ENDPOINT)
            .client(
                OkHttpClient.Builder().apply {
                    readTimeout(30, TimeUnit.SECONDS)
                    connectTimeout(30, TimeUnit.SECONDS)
                    cache(Cache(context.cacheDir, (10 * 1024 * 1024).toLong()))

                    if(BuildConfig.DEBUG) {
                        addInterceptor(
                            HttpLoggingInterceptor().apply {
                                level = HttpLoggingInterceptor.Level.HEADERS
                                level = HttpLoggingInterceptor.Level.BODY
                            }
                        )
                    }
                }.build())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}