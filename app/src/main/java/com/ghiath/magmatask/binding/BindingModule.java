package com.ghiath.magmatask.binding;

import android.content.Context;


import com.ghiath.magmatask.di.DataBinding;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BindingModule {


    @Singleton
    @Provides
    @DataBinding
    ContextBindingAdapters provideContextBindingAdapters(Context context) {
        return new ContextBindingAdapters(context);
    }
//    @Provides
//    @DataBinding
//    FragmentBindingAdapters provideFragmentBindingAdapters(Fragment fragment) {
//        return new FragmentBindingAdapters(fragment);
//    }

//    @Provides
//    @DataBinding
//    androidx.databinding.DataBindingComponent provideFragmentBindingAdapters(ContextBindingAdapters contextBindingAdapters,Fragment fragment) {
//        return new DataBindingComponent(){
//            @Override
//            public ContextBindingAdapters getContextBindingAdapters() {
//                return contextBindingAdapters;
//            }
//
//            @Override
//            public FragmentBindingAdapters getFragmentBindingAdapters() {
//                return null;
//            }
//        };
//    }
}