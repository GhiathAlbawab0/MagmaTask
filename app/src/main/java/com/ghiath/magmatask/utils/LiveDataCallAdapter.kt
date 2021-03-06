///*
// * Copyright (C) 2017 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.ghiath.kelshimall.utils
//
//
//import androidx.lifecycle.LiveData
//import com.ghiath.kelshimall.api.ApiResponse
//import retrofit2.Call
//import retrofit2.CallAdapter
//import retrofit2.Callback
//import retrofit2.Response
//import timber.log.Timber
//import java.lang.reflect.Type
//import java.util.concurrent.atomic.AtomicBoolean
//
///**
// * A Retrofit adapter that converts the Call into a LiveData of ApiResponse.
// * @param <R>
//</R> */
//class LiveDataCallAdapter<R>(private val responseType: Type) :
//    CallAdapter<R, LiveData<ApiResponse<R>>> {
//
//    override fun responseType() = responseType
//
//    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
//        return object : LiveData<ApiResponse<R>>() {
//            private var started = AtomicBoolean(false)
//            override fun onActive() {
//                super.onActive()
//                if (started.compareAndSet(false, true)) {
//                    call.enqueue(object : Callback<R> {
//                        override fun onResponse(call: Call<R>, response: Response<R>) {
//
//
//                            try {
//                                if (response.isSuccessful) {
////                                    val gson = Gson()
////                                    val type = object : TypeToken<BaseResponseModel?>() {}.type
////                                    val stringbody = gson.toJson(response?.body()!!)?.toString()
////                                    val baseResponse: BaseResponseModel? = gson.fromJson(stringbody!!, type!!)
//////                           val err = ErrorCodeInit(baseResponse?.toString())
//////                                Timber.d("Ghiath live: type= %s, stringbody= %s, baseResponse= %s", type, stringbody, baseResponse)
//
////                                    if ("9000" == baseResponse?.errorCode || baseResponse?.errorCode == null || baseResponse?.errorDescription == null) {
//                                    postValue(ApiResponse.create())//the only original line
////                                    } else
////                                        onFailure(call, Throwable(baseResponse?.errorDescription,Throwable(baseResponse?.errorCode)))
//                                } else
//                                    onFailure(call, Throwable(response.body()?.toString()))
//                            } catch (e: Exception) {
//                                Timber.e(e, "ghiath live")
//                            }
//
//
//                        }
//
//                        override fun onFailure(call: Call<R>, throwable: Throwable) {
//                            postValue(ApiResponse.create(throwable))
//                        }
//                    })
//                }
//            }
//        }
//    }
//}
