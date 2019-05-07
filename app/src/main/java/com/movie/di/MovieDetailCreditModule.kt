package com.movie.di

import com.movie.model.view.DetailCreditCastViewModelFactory
import org.koin.dsl.module.module

val detailCreditCastModule = module {

    single {
        DetailCreditCastViewModelFactory()
    }
}