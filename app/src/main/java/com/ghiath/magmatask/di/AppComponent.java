package com.ghiath.magmatask.di;

import com.ghiath.kelshimall.di.AppModule;
import com.ghiath.magmatask.MagmaApplication;
import com.ghiath.magmatask.MainActivity;
import com.ghiath.magmatask.binding.BindingModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class
        , AppModule.class
        ,LocalManagerModule.class
        , BindingModule.class


        , MainActivity.ActivityModule.class

})
 interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        public Builder application(MagmaApplication application);
        public AppComponent build();
    }

    void  inject(MagmaApplication magmaApplication);
}
