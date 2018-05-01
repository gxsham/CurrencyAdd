package com.example.gxsha.moneyconvertor;
import android.graphics.drawable.Icon;

import java.security.InvalidParameterException;

import javax.inject.Inject;


/**
 * Created by gxsham on 4/29/2018.
 */
public class CurrencyCalculator
{
    private static final int arrayLength = 3;
    private IConverter converterService;

    @Inject
    public CurrencyCalculator(IConverter converterService){
        this.converterService = converterService;
    }

    public String AddCurrencies(CharSequence firstValue, CharSequence secondValue, int firstCurrency, int secondCurrency, int resultCurrency)
    {
        double first = 0;
        double second = 0;
        double result = 0;
       
        try {
            first = Double.parseDouble(firstValue.toString());
        }catch (Exception ex){
            throw  ex;
        }

        try {
            second = Double.parseDouble(secondValue.toString());
        }catch (Exception ex){
            throw  ex;
        }

        if (firstCurrency < 0 || firstCurrency > arrayLength )
        {
            throw new InvalidParameterException("First currency not valid");
        }

        if(secondCurrency < 0 || secondCurrency > arrayLength)
        {
            throw  new InvalidParameterException("Second currency not valid");
        }

        if(resultCurrency < 0 || resultCurrency > arrayLength){
            throw new InvalidParameterException("Result currency not valid");
        }

        //let's suppose Convert method is very heavy so we try to avoid it's call when not needed
        //with other words - more things to test
        //https://www.meme-arsenal.com/memes/14f6b29982b84db62035c0eaf5c9423a.jpg
        if (firstCurrency != resultCurrency)
        {
            result += converterService.Convert(first, firstCurrency, resultCurrency);
        }
        else
        {
            result += first;
        }

        if (secondCurrency != resultCurrency)
        {
            result += converterService.Convert(second, secondCurrency, resultCurrency);
        }
        else
        {
            result += second;
        }

        return Double.toString(result);
    }
}
