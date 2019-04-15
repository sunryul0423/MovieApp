package com.movie.di

import com.movie.model.view.DetailCreditModelFactory
import org.koin.dsl.module.module

val detailCreditModule = module {

    factory {
        DetailCreditModelFactory()
    }
}