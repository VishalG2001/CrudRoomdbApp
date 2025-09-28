package com.crudapp.di

    import android.content.Context
    import androidx.room.Room
    import com.crudapp.data.local.AppDatabase
    import dagger.Module
    import dagger.hilt.InstallIn
    import dagger.hilt.components.SingletonComponent
    import dagger.Provides
    import dagger.hilt.android.qualifiers.ApplicationContext
    import javax.inject.Singleton

    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "app_db")
                .fallbackToDestructiveMigration()
                .build()

        @Provides
        fun providePostDao(db: AppDatabase) = db.postDao()
    }
