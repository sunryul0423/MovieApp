package com.movie.di

import com.movie.model.view.MovieDetailCreditViewModel
import org.koin.dsl.module.module

val movieDetailCreditModule = module {

    factory {
        MovieDetailCreditViewModel()
    }
}