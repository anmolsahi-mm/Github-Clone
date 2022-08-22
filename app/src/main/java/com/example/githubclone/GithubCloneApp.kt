package com.example.githubclone

import android.app.Application
import timber.log.Timber

class GithubCloneApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.IS_DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}