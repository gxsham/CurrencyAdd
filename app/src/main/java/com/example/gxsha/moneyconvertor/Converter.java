package com.example.gxsha.moneyconvertor;

/**
 * Created by gxsham on 4/29/2018.
 */
public class  Converter implements  IConverter
{
    private static final double convertTable[][] =
            {       // usd     eur      gbp     inr
                    {1.00000, 0.80950, 0.69813, 65.6596}, //usd
                    {1.23533, 1.00000, 0.86242, 81.1113}, //eur
                    {1.43239, 1.15952, 1.00000, 94.0502}, //gbp
                    {0.01523, 0.01233, 0.01063, 1.00000}  //inr
            };


    @Override
    public double Convert(double value, int from, int to)
    {
        double coefficient = convertTable[from][to];
        return value * coefficient;
    }
}
