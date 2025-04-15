package br.com.usinasantafe.cmm

import android.app.Application
import br.com.usinasantafe.cmm.utils.FileLoggingTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CMM : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(FileLoggingTree(this))
    }

}