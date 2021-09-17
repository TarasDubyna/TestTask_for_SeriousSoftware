package tarikdev.app.testtask_serioussoftware

import android.app.Application
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import tarikdev.app.testtask_serioussoftware.data.repository.QuotesRepository
import tarikdev.app.testtask_serioussoftware.data.repository.QuotesRepositoryImpl

class App: Application() {

    val appModule = module {
        single<QuotesRepository> { QuotesRepositoryImpl() }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
                modules(appModule)
        }
    }
}