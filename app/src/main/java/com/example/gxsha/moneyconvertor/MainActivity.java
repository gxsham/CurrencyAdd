package com.example.gxsha.moneyconvertor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import javax.inject.Inject;


/**
 * Created by gxsham on 4/29/2018.
 */
public class MainActivity extends AppCompatActivity
{

    private TextView resultValue;
    private EditText firstValue;
    private EditText secondValue;
    private int firstCurrency;
    private int secondCurrency;
    private int resultCurrency;
    private Button convertButton;

    @Inject
    CurrencyCalculator currencyCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstValue = findViewById(R.id.firstValue);
        secondValue = findViewById(R.id.secondValue);
        resultValue = findViewById(R.id.resultValue);
        final Spinner firstSelector = findViewById(R.id.firstSelector);
        final Spinner secondSelector = findViewById(R.id.secondSelector);
        final Spinner resultSelector = findViewById(R.id.resultSelector);
        convertButton = findViewById(R.id.convertButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Currencies, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        firstCurrency = 0;
        secondCurrency = 0;
        resultCurrency = 0;

        firstSelector.setAdapter(adapter);
        secondSelector.setAdapter(adapter);
        resultSelector.setAdapter(adapter);

        convertButton.setEnabled(false);

        InjectionComponent component = DaggerInjectionComponent.builder().build();
        component.inject(this);

        firstSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                firstCurrency = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        secondSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                secondCurrency = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        resultSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                resultCurrency = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        firstValue.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if(charSequence.length()!=0 && secondValue.getText().length() != 0)
                {
                    convertButton.setEnabled(true);
                }
                else
                {
                    convertButton.setEnabled(false);
                }
            }

        @Override
        public void afterTextChanged(Editable editable) {}
        });

        secondValue.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if(charSequence.length()!=0 && firstValue.getText().length() != 0)
                {
                    convertButton.setEnabled(true);
                }
                else
                {
                    convertButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        convertButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                resultValue.setText(currencyCalculator.AddCurrencies(firstValue.getText(), secondValue.getText(), firstCurrency, secondCurrency, resultCurrency));
            }
        });
    }
}

