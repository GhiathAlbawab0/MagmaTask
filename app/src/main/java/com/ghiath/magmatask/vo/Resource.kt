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

package com.ghiath.magmatask.vo

import androidx.databinding.BaseObservable
import com.ghiath.magmatask.utils.GeneralUtils


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?): BaseObservable() {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

    }

    override fun equals(other: Any?): Boolean {
        val ii : Resource<T> = other as Resource<T>
        val statusCheck:Boolean= this.status ==ii.status
        val messageCheck:Boolean =this.message==ii.message

        var dataCheck:Boolean=this.data==ii.data
        if (data is List<*> && ii.data is List<*>)
        {
//            val resourceData =this.data
            dataCheck= GeneralUtils.areEqualIgnoringOrder(this.data,ii.data
                    , Comparator(function = { resource: Any?, resource1: Any? ->

                if(resource?.equals(resource1)!!)
                    return@Comparator 0
                else
                    return@Comparator 1

            }))
        }


        return statusCheck&&dataCheck&&messageCheck
    }
}
