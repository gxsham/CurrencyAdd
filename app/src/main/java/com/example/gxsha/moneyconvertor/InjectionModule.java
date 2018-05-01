package com.example.gxsha.moneyconvertor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gxsham on 4/29/2018.
 */
@Module
public class InjectionModule
{
    @Provides
    @Singleton
    Converter provideConverter(){
        return new Converter();
    }

    @Provides
    @Singleton
    CurrencyCalculator provideCalculator()
    {
        return new CurrencyCalculator(new Converter());
    }
}
