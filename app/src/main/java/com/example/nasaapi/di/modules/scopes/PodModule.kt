package com.example.nasaapi.di.modules.scopes

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.domain.*
import com.example.nasaapi.di.annotations.PodScope
import com.example.nasaapi.di.annotations.ViewModelKey
import com.example.nasaapi.ui.pictureofthedayfragment.PictureOfTheDayFragmentViewModel
import com.example.nasaapi.ui.pictureofthedayfragment.PodSubcomponentProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface PodModule {

    @PodScope
    @Binds
    @IntoMap
    @ViewModelKey(PictureOfTheDayFragmentViewModel::class)
    fun bindUsersFragmentViewModel(vm: PictureOfTheDayFragmentViewModel): ViewModel

    companion object {
        @PodScope
        @Provides
        fun provideGetPodUseCase(
            domainRepository: DomainRepository
        ): GetPodUseCase {
            return GetPodUseCase(domainRepository)
        }

        @PodScope
        @Provides
        fun provideSaveFavoritePodUseCase(
            domainRepository: DomainRepository
        ): SaveFavoritePodUseCase {
            return SaveFavoritePodUseCase(domainRepository)
        }

        @PodScope
        @Provides
        fun provideDeleteFavoritePodUseCase(
            domainRepository: DomainRepository
        ): DeleteFavoritePodUseCase {
            return DeleteFavoritePodUseCase(domainRepository)
        }

        @PodScope
        @Provides
        fun provideGetAllFavoritePodsUseCase(
            domainRepository: DomainRepository
        ): GetAllFavoritePodsUseCase {
            return GetAllFavoritePodsUseCase(domainRepository)
        }

        @PodScope
        @Provides
        fun providePodSubcomponentProvider(
            application: Application
        ): PodSubcomponentProvider {
            return (application as PodSubcomponentProvider)
        }
    }
}