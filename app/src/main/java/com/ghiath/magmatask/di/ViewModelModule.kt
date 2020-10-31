/*
 * Copyright (C) 2018 The Android Open Source Project
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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ghiath.kelshimall.viewModel.MagmaViewModelFactory
import com.ghiath.magmatask.viewModel.AdViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Suppress("unused")
@Module
abstract class ViewModelModule {

//    @Binds
//    @IntoMap
//    @ViewModelKey(LoginViewModel::class)
//    abstract fun bindLoginViewModel(userViewModel: LoginViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(VerifyNumberViewModel::class)
//    abstract fun bindVerifyNumberViewModel(verifyNumberViewModel: VerifyNumberViewModel): ViewModel
//
    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(AdViewModel::class)
    abstract fun bindAdViewModel(adViewModel: AdViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: MagmaViewModelFactory): ViewModelProvider.Factory
}
