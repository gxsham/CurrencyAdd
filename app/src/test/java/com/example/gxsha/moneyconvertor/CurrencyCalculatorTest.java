package com.example.gxsha.moneyconvertor;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.security.InvalidParameterException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class CurrencyCalculatorTest
{

    @Mock
    Converter mConvert;

    @InjectMocks
    CurrencyCalculator currencyCalculator = new CurrencyCalculator(mConvert);


    @Test(expected = Exception.class)
    public void AddCurrencies_InvalidFirstValue_Throws()
    {
        currencyCalculator.AddCurrencies("not really a number", "2", 1,1,1);
    }

    @Test(expected = Exception.class)
    public void AddCurrencies_InvalidSecondValue_Throws()
    {
        currencyCalculator.AddCurrencies("2.0", "not really a number", 1,1,1);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void AddCurrencies_FirstCurrencyNegative_Throws()
    {
        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("First currency not valid");
        currencyCalculator.AddCurrencies("1","1",-1,0,0);
    }

    @Test
    public void AddCurrencies_FirstCurrencyTooBig_Throws()
    {
        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("First currency not valid");
        currencyCalculator.AddCurrencies("1","1",4,0,0);
    }

    @Test
    public void AddCurrencies_SecondCurrencyNegative_Throws()
    {
        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Second currency not valid");
        currencyCalculator.AddCurrencies("1","1",0,-1,0);
    }

    @Test
    public void AddCurrencies_SecondCurrencyTooBig_Throws()
    {
        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Second currency not valid");
        currencyCalculator.AddCurrencies("1","1",0,4,0);
    }

    @Test
    public void AddCurrencies_ThirdCurrencyNegative_Throws()
    {
        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Result currency not valid");
        currencyCalculator.AddCurrencies("1","1",0,0,-1);
    }

    @Test
    public void AddCurrencies_ThirdCurrencyTooBig_Throws()
    {
        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Result currency not valid");
        currencyCalculator.AddCurrencies("1","1",0,0,4);
    }


    @Test
    public void AddCurrencies_ConverterCalledTwice_Successful()
    {
        when(mConvert.Convert(1,1,0)).thenReturn(1.23533);
        when(mConvert.Convert(1,2,0)).thenReturn(1.43239);

        assertEquals(Double.toString(1.23533 + 1.43239) , currencyCalculator.AddCurrencies("1", "1", 1,2,0));
        verify(mConvert, times(1)).Convert(1,1,0);
        verify(mConvert, times(1)).Convert(1,2,0);
    }

    @Test
    public void AddCurrencies_ConverterCalledNever_Successful()
    {
        assertEquals(Double.toString(2.00000) , currencyCalculator.AddCurrencies("1", "1", 0,0,0));
        verify(mConvert, never()).Convert(1,1,0);
        verify(mConvert, never()).Convert(1,2,0);
    }
}