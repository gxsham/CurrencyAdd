package com.example.gxsha.moneyconvertor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by gxsham on 4/29/2018.
 */

@Singleton
@Component(modules = {InjectionModule.class})
interface InjectionComponent
{
    CurrencyCalculator provideCalculator();
    void inject(MainActivity main);
}
