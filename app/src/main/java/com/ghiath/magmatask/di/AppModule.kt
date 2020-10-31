/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ghiath.kelshimall.di

import androidx.room.Room
//import com.ghiath.kelshimall.utils.LiveDataCallAdapterFactory
import com.ghiath.magmatask.MagmaApplication
import com.ghiath.magmatask.R
import com.ghiath.magmatask.api.MagmaService
import com.ghiath.magmatask.db.AdEntityDao
import com.ghiath.magmatask.db.AdImageEntityDao
import com.ghiath.magmatask.db.MagmaDb

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import com.google.common.collect.Lists
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {
//    @Singleton
//    @Provides
//    fun provideGithubService(): MagmaService{
////        val okhttpClient = OkHttpClient()
//////        okhttpClient.callTimeoutMillis()
////        okhttpClient.networkInterceptors().add(element = okhttp3.Interceptor {
////            val requestBuilder = it.request().newBuilder()
////            requestBuilder.header("Content-Type", "application/json")
////            it.proceed(requestBuilder.build())
////        })
//
//
//
//        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger
//        { message -> Timber.tag("OkHttp").d(message) })
//        logging.level = HttpLoggingInterceptor.Level.BODY
//        var client: OkHttpClient? = null
//        try {
//
//            client = OkHttpClient.Builder()
//                    .hostnameVerifier { _, _ -> true }
////                    .sslSocketFactory(TLSSocketFactory())
//                    .addInterceptor(logging)
//                    .addInterceptor { chain ->
//                        val original = chain.request()
//
//                        // Request customization: add request headers
//                        val requestBuilder = original.newBuilder()
//                                .header("Content-Type", "application/json")
//                                .method(original.method(), original.body())
//
//                        val request = requestBuilder.build()
//                        chain.proceed(request)
//                    }
//                    .callTimeout(2, TimeUnit.MINUTES)
//                    .connectTimeout(2, TimeUnit.MINUTES)
//                    .writeTimeout(2, TimeUnit.MINUTES)
//                    .readTimeout(2, TimeUnit.MINUTES)
//                    .build()
//
//        }
//        catch (e:Exception)
//        {
//            Timber.e(e, "client creating")
//        }
//
//        return Retrofit.Builder()
//            .baseUrl("https://maps.googleapis.com/")
////            .baseUrl("http://217.172.190.55:5589/APIV1/")
////            .baseUrl("http://217.172.190.55:5589/APIV1/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(LiveDataCallAdapterFactory())
//            .client(client)
//            .build()
//            .create(MagmaService ::class.java)
//    }

   @Singleton
    @Provides
    fun provideGoogleService(app: MagmaApplication): Storage{
        val credentials :GoogleCredentials = GoogleCredentials.fromStream(( app.applicationContext.resources.openRawResource(R.raw.m)))
                        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"))
       val storage :Storage = StorageOptions.newBuilder().setCredentials(credentials)
                        .setProjectId("ak-works-272219").build().service
       return storage

   }

    @Singleton
    @Provides
    fun provideDb(app: MagmaApplication): MagmaDb {
        return Room
            .databaseBuilder(app, MagmaDb::class.java, "MagmaDB.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideAdImageEntityDao(db: MagmaDb): AdImageEntityDao {
        return db.adImageEntityDao()
    }
    @Singleton
    @Provides
    fun provideAdEntityDao(db: MagmaDb): AdEntityDao {
        return db.adEntityDao()
    }



}
