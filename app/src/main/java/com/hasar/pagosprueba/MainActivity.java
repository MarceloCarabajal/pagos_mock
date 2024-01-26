package com.hasar.pagosprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private Spinner cardTypeSp, paymentMethodSp, installmentsSp;
    private Button operationButton;
    private ArrayAdapter<CharSequence> paymentMethodAdapter;
    private EditText amountInput;
    private TextView brandLabel, paymentMethodLabel, installmentLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardTypeSp = findViewById(R.id.cardTypeSpinner);
        //obtengo referencias
        paymentMethodSp = findViewById(R.id.paymentMethodSpinner);
        installmentsSp = findViewById(R.id.installmentsSpinner);
        brandLabel = findViewById(R.id.brandLabel);
        operationButton = findViewById(R.id.operationButton);
        paymentMethodLabel = findViewById(R.id.paymentMethodLabel);
        installmentLabel = findViewById(R.id.installmentsLabel);

        //ocultar spinners
        cardTypeSp.setVisibility(View.GONE);
        paymentMethodSp.setVisibility(View.GONE);
        installmentsSp.setVisibility(View.GONE);
        //ocultar textviews
        brandLabel.setVisibility(View.GONE);
        paymentMethodLabel.setVisibility(View.GONE);
        installmentLabel.setVisibility(View.GONE);
        
        //logica del ingreso de importe
        amountInput = findViewById(R.id.amountInput);

        amountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ocultar spinners
                cardTypeSp.setVisibility(View.GONE);
                paymentMethodSp.setVisibility(View.GONE);
                installmentsSp.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //mostrar spinners
                    cardTypeSp.setVisibility(View.VISIBLE);
                    paymentMethodSp.setVisibility(View.VISIBLE);
                    installmentsSp.setVisibility(View.VISIBLE);
                    //mostrar textviews
                    brandLabel.setVisibility(View.VISIBLE);
                    paymentMethodLabel.setVisibility(View.VISIBLE);
                    installmentLabel.setVisibility(View.VISIBLE);

                } else {
                    //ocultar spinners
                    cardTypeSp.setVisibility(View.GONE);
                    paymentMethodSp.setVisibility(View.GONE);
                    installmentsSp.setVisibility(View.GONE);
                    //ocultar textviews
                    brandLabel.setVisibility(View.GONE);
                    paymentMethodLabel.setVisibility(View.GONE);
                    installmentLabel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //creacion de los spinners con sus adapter
        ArrayAdapter<CharSequence> cardTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CardTypes.TYPES);
        cardTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardTypeSp.setAdapter(cardTypeAdapter);

        cardTypeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Actualiza las opciones del segundo Spinner aca
                String cardType = CardTypes.TYPES[position];
                paymentMethodAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, CardOptions.OPTIONS.get(cardType));
                paymentMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                paymentMethodSp.setAdapter(paymentMethodAdapter);

                /*
                if (position == 0) { // VISA
                    paymentMethodAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.visa_options, android.R.layout.simple_spinner_item);
                    paymentMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    paymentMethodSp.setAdapter(paymentMethodAdapter);
                }
                // Agrega casos para MASTERCARD y AMEX
                if (position == 1) {
                    paymentMethodAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.master_options, android.R.layout.simple_spinner_item);
                    paymentMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    paymentMethodSp.setAdapter(paymentMethodAdapter);
                }
                if (position == 2) {
                    paymentMethodAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.amex_options, android.R.layout.simple_spinner_item);
                    paymentMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    paymentMethodSp.setAdapter(paymentMethodAdapter);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Hacer algo cuando no se selecciona ninguna opción
            }
        });

        paymentMethodSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtiene la opción seleccionada en el primer Spinner
                CharSequence cardType = cardTypeAdapter.getItem(cardTypeSp.getSelectedItemPosition());

                // Verifica la opción seleccionada y configura el Spinner de cuotas

                if ("-".equals(cardType)) {
                    CharSequence paymentMethod = paymentMethodAdapter.getItem(position);

                    if ("-".equals(paymentMethod)) {
                        //mostrar spinner de cuotas con el guion "-"
                        ArrayAdapter<CharSequence> installmentsAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, new CharSequence[]{"-"});
                        installmentsSp.setAdapter(installmentsAdapter);
                        installmentsSp.setVisibility(View.VISIBLE);
                    }
                }else if ("VISA".equals(cardType)) {
                    CharSequence paymentMethod = paymentMethodAdapter.getItem(position);

                    if ("Debito VISA".equals(paymentMethod)) {
                        // Muestra el Spinner de cuotas con solo una opción
                        ArrayAdapter<CharSequence> installmentsAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, new CharSequence[]{"1 Cuota"});
                        installmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        installmentsSp.setAdapter(installmentsAdapter);
                        installmentsSp.setVisibility(View.VISIBLE);
                    } else if ("Credito VISA".equals(paymentMethod)) {
                        // Muestra el Spinner de cuotas con opciones desde 1 hasta 36
                        ArrayAdapter<CharSequence> installmentsAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item);
                        for (int i = 1; i <= 36; i++) {
                            installmentsAdapter.add("" + i + " Cuotas");
                        }
                        installmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        installmentsSp.setAdapter(installmentsAdapter);
                        installmentsSp.setVisibility(View.VISIBLE);
                    }
                } else if ("MASTERCARD".equals(cardType)) {
                    CharSequence paymentMethod = paymentMethodAdapter.getItem(position);
                    if ("Maestro".equals(paymentMethod) || "MasterCard".equals(paymentMethod)) {
                        //muestra spinner de cuotas con solo una opcion
                        ArrayAdapter<CharSequence> installmentsAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, new CharSequence[]{"1 Cuota"});
                        installmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        installmentsSp.setAdapter(installmentsAdapter);
                        installmentsSp.setVisibility(View.VISIBLE);
                    } else if ("MasterDebit".equals(paymentMethod)) {
                        //muestro spinner de cuotas con opciones desde 1 hasta 36
                        ArrayAdapter<CharSequence> installlmentsAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item);
                        for (int i = 1; i <= 36; i++) {
                            installlmentsAdapter.add("" + i + " Cuotas");
                        }
                        installlmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        installmentsSp.setAdapter(installlmentsAdapter);
                        installmentsSp.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Haz algo cuando no se selecciona ninguna opción
            }
        });

            operationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // genero datos ficticios para simular una respuesta de pago exitosa
                String authorizationCode = "MOCK_AUTHORIZATION_CODE";
                String paymentGateway = "MOCK_PAYMENT_GATEWAY";
                String uniqueNumber = "MOCK_UNIQUE_NUMBER";
                String batch = "MOCK_BATCH";
                String voucherNumber = "MOCK_VOUCHER_NUMBER";
                String commerceNumber = "MOCK_COMMERCE_NUMBER";
                String terminalNumber = "MOCK_TERMINAL_NUMBER";
                String cardNumber = "**** **** **** 1234"; // Asume que el número de tarjeta es 16 dígitos

                // muestro los datos ficticios en la interfaz de usuario
                //creo nuevo intent
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);

                //agrego los datos al intent
                intent.putExtra("authorizationCode", authorizationCode);
                intent.putExtra("paymentGateway", paymentGateway);
                intent.putExtra("uniqueNumber", uniqueNumber);
                intent.putExtra("batch", batch);
                intent.putExtra("voucherNumber", voucherNumber);
                intent.putExtra("commerceNumber", commerceNumber);
                intent.putExtra("terminalNumber", terminalNumber);
                intent.putExtra("cardNumber", cardNumber);

                //iniciar la segunda actividad
                startActivity(intent);

                //reestabler editText luego de enviar la info
                amountInput.setText("");

                //reestabler spinners luego de enviar la info
                cardTypeSp.setSelection(0);
                paymentMethodSp.setSelection(0);

            }
        });

    }

    /*private void operationButton() {
        //obtener el monto ingresado en etAmount
        double amount = Double.parseDouble(amountInput.getText().toString());

    }*/
}
