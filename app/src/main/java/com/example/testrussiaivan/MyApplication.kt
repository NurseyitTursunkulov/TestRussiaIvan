package com.example.testrussiaivan

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
class MyApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        // start Koin!
        startKoin {
            // Android context
            androidLogger()
            androidContext(this@MyApplication)
            // use the Android context given there
            // load properties from assets/koin.properties file
            androidFileProperties()


            // modules
            modules(myModule)
        }
    }
    val myModule: Module = module { viewModel { MyViewModel() }}

}