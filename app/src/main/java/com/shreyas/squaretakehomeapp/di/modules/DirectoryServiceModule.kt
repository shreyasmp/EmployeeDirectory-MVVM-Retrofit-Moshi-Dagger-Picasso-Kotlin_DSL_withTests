package com.shreyas.squaretakehomeapp.di.modules

import com.shreyas.squaretakehomeapp.service.DirectoryService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object DirectoryServiceModule {

    private const val EMPLOYEE_DIRECTORY_URL = "https://s3.amazonaws.com/sq-mobile-interview/"

    @Provides
    internal fun provideDirectoryService(okHttpClient: OkHttpClient): DirectoryService {
        return Retrofit.Builder()
            .baseUrl(EMPLOYEE_DIRECTORY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(DirectoryService::class.java)
    }

    @Provides
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    internal fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}