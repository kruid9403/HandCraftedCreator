package com.handcraftedcreator.handcraftedcreator.di

import androidx.room.Room
import com.handcraftedcreator.handcraftedcreator.db.*
import com.handcraftedcreator.handcraftedcreator.managers.FirebaseManager
import com.handcraftedcreator.handcraftedcreator.repos.CustomerRepo
import com.handcraftedcreator.handcraftedcreator.repos.OrderRepo
import com.handcraftedcreator.handcraftedcreator.utils.coroutines.CoroutineConfig
import com.handcraftedcreator.handcraftedcreator.utils.coroutines.CoroutineConfigImpl
import com.handcraftedcreator.handcraftedcreator.viewModel.ProductsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val PROVIDER_DB = "ProviderDb"
val appModule = module {
    single {
        Room.databaseBuilder(androidContext(),
            Database::class.java,
            PROVIDER_DB)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
//            .addTypeConverter(Converters::class)
            .build()
    }
    single { get<Database>().customerDao() }
    single { get<Database>().orderDao() }
    single { FirebaseManager() }
    single { ProductsViewModel(get()) }
    single<CoroutineConfig> { CoroutineConfigImpl() }
    factory { CustomerRepo() }
    factory { OrderRepo() }
}