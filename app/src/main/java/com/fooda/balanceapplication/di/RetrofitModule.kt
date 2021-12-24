package com.fooda.balanceapplication.di
import com.fooda.balanceapplication.BuildConfig
import com.fooda.balanceapplication.retrofit.ScratchAPI
import com.fooda.balanceapplication.utilits.AppConstants.REQUEST_TIMEOUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {


//    @Singleton
//    @Provides
//    fun provideGsonBuilder(): Gson {
//        return GsonBuilder()
//            //.excludeFieldsWithoutExposeAnnotation()
//            .create()
//    }
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson:  Gson): Retrofit.Builder {
        return Retrofit.Builder()
           // .baseUrl("http://10.0.2.2/")
                .baseUrl(BuildConfig.APP_URL)

                .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder): ScratchAPI {
        return retrofit
            .client(getOkHttpService())
            .build()
            .create(ScratchAPI::class.java)
    }
    private fun getOkHttpService(): OkHttpClient {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(interceptor)
        }
        httpClient.addInterceptor(BasicAuthInterceptor())

        return httpClient.build()
    }
    class BasicAuthInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val newUrl =
                request.url.newBuilder().build()
                      //  .addHeader("Authorization", "Bearer " + token)
              //request.url.newBuilder().addQueryParameter("apiKey", BuildConfig.API_KEY).build()
            request.url.newBuilder().addQueryParameter("Bearer Token", "56|DtD8Y3tRzQqK3AqexnYDqHx3SF5u7icmyfPtUeN7").build()

            val newRequest = request.newBuilder().url(newUrl).build()
            return chain.proceed(newRequest)
        }
    }
}




















