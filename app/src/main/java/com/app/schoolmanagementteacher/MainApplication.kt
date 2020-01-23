package com.app.schoolmanagementteacher

import android.app.Application
import com.app.schoolmanagementteacher.attendance.AttendenceViewmodel
import com.app.schoolmanagementteacher.attendance.AttendenceViewmodelFactory
import com.app.schoolmanagementteacher.home.HomeViewModel
import com.app.schoolmanagementteacher.home.HomeViewModelFactory
import com.app.schoolmanagementteacher.homework.HomeworkViewmodel
import com.app.schoolmanagementteacher.homework.HomeworkViewmodelFactory
import com.app.schoolmanagementteacher.login.LoginViewmodel
import com.app.schoolmanagementteacher.login.LoginViewmodelFactory
import com.app.schoolmanagementteacher.network.MyApi
import com.app.schoolmanagementteacher.network.Repository
import com.app.schoolmanagementteacher.notice.NoticeViewmodel
import com.app.schoolmanagementteacher.notice.NoticeViewmodelFactory
import com.app.schoolmanagementteacher.photopicker.loader.GlideImageLoader
import lv.chi.photopicker.ChiliPhotoPicker
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MainApplication:Application(),KodeinAware {
    override val kodein= Kodein.lazy {
        bind() from singleton { MyApi() }
        bind() from singleton { Repository(instance()) }
        bind() from singleton { LoginViewmodelFactory(instance()) }
        bind() from singleton { LoginViewmodel(instance()) }

        bind() from singleton { HomeViewModelFactory(instance()) }
        bind() from singleton { HomeViewModel(instance()) }

        bind() from singleton { HomeworkViewmodel(instance()) }
        bind() from singleton { HomeworkViewmodelFactory(instance()) }

        bind() from singleton { NoticeViewmodel(instance()) }
        bind() from singleton { NoticeViewmodelFactory(instance()) }

        bind() from singleton { AttendenceViewmodelFactory(instance()) }
        bind() from singleton { AttendenceViewmodel(instance()) }
     }

    override fun onCreate() {
        super.onCreate()
        ChiliPhotoPicker.init(
            loader = GlideImageLoader(),
            authority = "com.app.schoolmanagementteacher.fileprovider"
        )

    }
}