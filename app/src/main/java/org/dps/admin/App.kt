package org.dps.admin

import android.app.Application
import android.content.Context
//import com.facebook.drawee.backends.pipeline.Fresco
import org.dps.admin.di.appModule
import org.dps.admin.di.networkModule
import org.dps.admin.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val context = this
        appContext = applicationContext

      //  Fresco.initialize(this);


        val moduleList=listOf(appModule, networkModule, viewModelModule)
        startKoin { modules(moduleList).androidContext(context) }
    }

    companion object {
        @get:Synchronized
        var appContext: Context? = null
            private set
    }

}