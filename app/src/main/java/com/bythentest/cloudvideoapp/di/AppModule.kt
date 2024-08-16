package com.bythentest.cloudvideoapp.di

import com.bythentest.cloudvideoapp.data.remote.RemoteDataSource
import com.bythentest.cloudvideoapp.data.remote.UploadRepository
import com.bythentest.cloudvideoapp.data.remote.network.ApiService
import com.bythentest.cloudvideoapp.domain.repository.IUploadRepository
import com.bythentest.cloudvideoapp.domain.usecase.UploadUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("api_key", "a2W4RIrTUU-MsRSHOnT7PMzeztY") // Set API key in header
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.cloudinary.com/v1_1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single<IUploadRepository> { UploadRepository(get()) }
}

val useCaseModule = module {
     single { UploadUseCase(get()) }
}
