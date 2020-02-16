package com.app.schoolmanagementadmin

import android.app.Application
import com.app.schoolmanagementadmin.ui.attendance.AttendenceViewmodel
import com.app.schoolmanagementadmin.ui.attendance.AttendenceViewmodelFactory
import com.app.schoolmanagementadmin.ui.businfo.BusInfoViewmodel
import com.app.schoolmanagementadmin.ui.event.EventViewmodel
import com.app.schoolmanagementadmin.ui.event.EventViewmodelFactory
import com.app.schoolmanagementadmin.ui.feeinfo.FeeInfoViewmodel
import com.app.schoolmanagementadmin.ui.feeinfo.FeeInfoViewmodelFactory
import com.app.schoolmanagementadmin.ui.home.HomeViewModel
import com.app.schoolmanagementadmin.ui.home.HomeViewModelFactory
import com.app.schoolmanagementadmin.ui.homework.HomeworkViewmodel
import com.app.schoolmanagementadmin.ui.homework.HomeworkViewmodelFactory
import com.app.schoolmanagementadmin.ui.leave.LeaveViewmodel
import com.app.schoolmanagementadmin.ui.leave.LeaveViewmodelFactory
import com.app.schoolmanagementadmin.ui.login.LoginViewmodel
import com.app.schoolmanagementadmin.ui.login.LoginViewmodelFactory
import com.app.schoolmanagementadmin.network.MyApi
import com.app.schoolmanagementadmin.network.Repository
import com.app.schoolmanagementadmin.network.VideoRepository
import com.app.schoolmanagementadmin.network.YoutubeAPI
import com.app.schoolmanagementadmin.ui.notice.NoticeViewmodel
import com.app.schoolmanagementadmin.ui.notice.NoticeViewmodelFactory
import com.app.schoolmanagementadmin.ui.photopicker.loader.GlideImageLoader
import com.app.schoolmanagementadmin.ui.result.ResultViewmodel
import com.app.schoolmanagementadmin.ui.result.ResultmodelFactory
import com.app.schoolmanagementadmin.ui.timetable.BusInfoViewmodelFactory
import com.app.schoolmanagementadmin.ui.timetable.TimeTableViewmodel
import com.app.schoolmanagementadmin.ui.timetable.TimeTableViewmodelFactory
import com.app.schoolmanagementadmin.upcomingtest.TestViewmodel
import com.app.schoolmanagementadmin.upcomingtest.TestViewmodelFactory
import com.app.schoolmanagementadmin.videos.VideosViewModel
import com.app.schoolmanagementadmin.videos.VideosViewModelFactory
import com.app.schoolmanagementadmin.videos.YoutubeDetailViewModel
import com.app.schoolmanagementadmin.videos.YoutubeDetailViewModelFactory
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

        bind() from singleton { TestViewmodelFactory(instance()) }
        bind() from singleton { TestViewmodel(instance()) }

        bind() from singleton { TimeTableViewmodel(instance()) }
        bind() from singleton { TimeTableViewmodelFactory(instance()) }

        bind() from singleton { BusInfoViewmodelFactory(instance()) }
        bind() from singleton { BusInfoViewmodel(instance()) }

        bind() from singleton { VideosViewModel(instance()) }
        bind() from singleton { VideosViewModelFactory(instance()) }
        bind() from singleton { VideoRepository(instance()) }
        bind() from singleton { YoutubeAPI() }
        bind() from singleton { YoutubeDetailViewModelFactory(instance()) }
        bind() from singleton { YoutubeDetailViewModel(instance()) }

        bind() from singleton { LeaveViewmodel(instance()) }
        bind() from singleton { LeaveViewmodelFactory(instance()) }

        bind() from singleton { FeeInfoViewmodel(instance()) }
        bind() from singleton { FeeInfoViewmodelFactory(instance()) }

        bind() from singleton { ResultViewmodel(instance()) }
        bind() from singleton { ResultmodelFactory(instance()) }

        bind() from singleton { EventViewmodel(instance()) }
        bind() from singleton { EventViewmodelFactory(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        ChiliPhotoPicker.init(
            loader = GlideImageLoader(),
            authority = "com.app.schoolmanagementteacher.fileprovider"
        )

    }
}