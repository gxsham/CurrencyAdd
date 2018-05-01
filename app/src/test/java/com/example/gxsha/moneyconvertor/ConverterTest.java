package com.example.gxsha.moneyconvertor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ConverterTest
{
    private static final double testConvertTable[][] =
            {       // usd     eur      gbp     inr
                    {1.00000, 0.80950, 0.69813, 65.6596}, //usd
                    {1.23533, 1.00000, 0.86242, 81.1113}, //eur
                    {1.43239, 1.15952, 1.00000, 94.0502}, //gbp
                    {0.01523, 0.01233, 0.01063, 1.00000}  //inr
            };

    private IConverter converter;

    @Before
    public void Setup()
    {
        converter = new Converter();
    }

    @Test
    public void Convert_Successfully()
    {
        for (int i = 0 ; i < 4 ; i++)
        {
            for (int j = 0 ; j < 4; j++)
            {
                assertEquals(2*testConvertTable[i][j], converter.Convert(2, i, j), 0.0001);
            }
        }
    }


    @Test(expected = Exception.class)
    public void Convert_NegativeIndex_Throws()
    {
        converter.Convert(1, -1, -2);
    }

    @Test(expected = Exception.class)
    public void Convert_IndexTooBig_Throws()
    {
        converter.Convert(1, 4, 4);
    }
}
