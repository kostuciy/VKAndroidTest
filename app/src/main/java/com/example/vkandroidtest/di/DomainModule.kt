package com.example.vkandroidtest.di

import com.example.vkandroidtest.repository.Repository
import com.example.vkandroidtest.repository.RepositoryImpl
import com.example.vkandroidtest.usecase.ChangePageUseCase
import com.example.vkandroidtest.usecase.DisposeRepoUseCase
import com.example.vkandroidtest.usecase.GetListUseCase
import com.example.vkandroidtest.usecase.GetPageUseCase
import com.example.vkandroidtest.usecase.GetSkipUseCase
import com.example.vkandroidtest.usecase.GetTotalUseCase
import com.example.vkandroidtest.usecase.SearchUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DomainModule {

    @Singleton
    @Binds
    abstract fun bindRepositoryImpl(
        repositoryImpl: RepositoryImpl
    ): Repository
}

@InstallIn(ViewModelComponent::class)
@Module
object UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideChangePageUseCase(repository: Repository) =
        ChangePageUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetListUseCase(repository: Repository) =
        GetListUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetPageUseCase(repository: Repository) =
        GetPageUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetSkipUseCase(repository: Repository) =
        GetSkipUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetTotalUseCase(repository: Repository) =
        GetTotalUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideSearchUseCase(repository: Repository) =
        SearchUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideDisposeRepoUseCase(repository: Repository) =
        DisposeRepoUseCase(repository)
}