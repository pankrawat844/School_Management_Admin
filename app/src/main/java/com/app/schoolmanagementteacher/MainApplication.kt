package com.app.schoolmanagementteacher

import android.app.Application
import com.app.schoolmanagementteacher.attendance.AttendenceViewmodel
import com.app.schoolmanagementteacher.attendance.AttendenceViewmodelFactory
import com.app.schoolmanagementteacher.businfo.BusInfoViewmodel
import com.app.schoolmanagementteacher.event.EventViewmodel
import com.app.schoolmanagementteacher.event.EventViewmodelFactory
import com.app.schoolmanagementteacher.feeinfo.FeeInfoViewmodel
import com.app.schoolmanagementteacher.feeinfo.FeeInfoViewmodelFactory
import com.app.schoolmanagementteacher.home.HomeViewModel
import com.app.schoolmanagementteacher.home.HomeViewModelFactory
import com.app.schoolmanagementteacher.homework.HomeworkViewmodel
import com.app.schoolmanagementteacher.homework.HomeworkViewmodelFactory
import com.app.schoolmanagementteacher.leave.LeaveViewmodel
import com.app.schoolmanagementteacher.leave.LeaveViewmodelFactory
import com.app.schoolmanagementteacher.login.LoginViewmodel
import com.app.schoolmanagementteacher.login.LoginViewmodelFactory
import com.app.schoolmanagementteacher.network.MyApi
import com.app.schoolmanagementteacher.network.Repository
import com.app.schoolmanagementteacher.network.VideoRepository
import com.app.schoolmanagementteacher.network.YoutubeAPI
import com.app.schoolmanagementteacher.notice.NoticeViewmodel
import com.app.schoolmanagementteacher.notice.NoticeViewmodelFactory
import com.app.schoolmanagementteacher.photopicker.loader.GlideImageLoader
import com.app.schoolmanagementteacher.result.ResultViewmodel
import com.app.schoolmanagementteacher.result.ResultmodelFactory
import com.app.schoolmanagementteacher.timetable.BusInfoViewmodelFactory
import com.app.schoolmanagementteacher.timetable.TimeTableViewmodel
import com.app.schoolmanagementteacher.timetable.TimeTableViewmodelFactory
import com.app.schoolmanagementteacher.upcomingtest.TestViewmodel
import com.app.schoolmanagementteacher.upcomingtest.TestViewmodelFactory
import com.app.schoolmanagementteacher.videos.VideosViewModel
import com.app.schoolmanagementteacher.videos.VideosViewModelFactory
import com.app.schoolmanagementteacher.videos.YoutubeDetailViewModel
import com.app.schoolmanagementteacher.videos.YoutubeDetailViewModelFactory
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