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
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Spinner cardTypeSp, paymentMethodSp, installmentsSp;
    private Button operationButton;
    private ArrayAdapter<CharSequence> paymentMethodAdapter;
    private ArrayAdapter<CharSequence> installmentsAdapter;
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
                // actualiza las opciones del segundo Spinner aca
                String cardType = CardTypes.TYPES[position];
                paymentMethodAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, CardOptions.OPTIONS.get(cardType));
                paymentMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                paymentMethodSp.setAdapter(paymentMethodAdapter);

                /*
                //uso de string-array (en strings.xml)
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
                // hacer algo cuando no se selecciona ninguna opción
            }
        });

        paymentMethodSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //obtener la opcion seleccionada en el primer spinner
                CharSequence paymentMethod = paymentMethodAdapter.getItem(position);

                //verificar opcion seleccionada y configurar spinner de cuotas
                String[] installmentsOptions = InstallmentsOptions.OPTIONS.get(paymentMethod);
                installmentsAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, installmentsOptions);
                installmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                installmentsSp.setAdapter(installmentsAdapter);
                installmentsSp.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //hacer algo cuando no se selecciona ninguna opcion
            }
        });
        operationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validar si todos los campos requeridos estan seleccionados
                if(amountInput.getText().toString().isEmpty() ||
                    cardTypeSp.getSelectedItemPosition() == 0 ){
                    //mostrar mensaje de error
                    Toast.makeText(MainActivity.this, "Ingresar todos los campos requeridos", Toast.LENGTH_SHORT).show();
                } else {
                    //obtengo monto ingresado
                    String amountStr = amountInput.getText().toString();
                    double amount = Double.parseDouble(amountStr);

                    //creo nuevo intent para la resultActivity
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);

                    //verificar el monto y agregar distintos mensajes
                    String resultMessage;

                    if (amount >= 0 && amount <= 2000){
                        resultMessage = "Monto insuficiente";
                    } else if (amount == 10000) {
                        resultMessage = "Aprobado";

                        //creo instancia de la clase Random
                        Random rand = new Random();
                        //genero num aleatorio entre 1000-1999
                        int authorizationCode = rand.nextInt(1000) + 1000;

                        //genero datos ficticios de la operacion
                        String cardType = CardTypes.TYPES[cardTypeSp.getSelectedItemPosition()];
                        CharSequence paymentMethod = paymentMethodAdapter.getItem(paymentMethodSp.getSelectedItemPosition());
                        CharSequence installments = installmentsAdapter.getItem(installmentsSp.getSelectedItemPosition());
                        // genero el resto de datos ficticios para simular una respuesta de pago exitosa
                        String authorizationCodeStr = String.valueOf(authorizationCode);
                        String paymentGateway = "MOCK_PAYMENT_GATEWAY";
                        String uniqueNumber = "MOCK_UNIQUE_NUMBER";
                        String batch = "MOCK_BATCH";
                        String voucherNumber = "MOCK_VOUCHER_NUMBER";
                        String commerceNumber = "MOCK_COMMERCE_NUMBER";
                        String terminalNumber = "MOCK_TERMINAL_NUMBER";
                        String cardNumber = "**** **** **** 1234"; // Asume que el número de tarjeta es 16 dígitos

                        //agrego los datos del a operacion al intent
                        intent.putExtra("amount", amountStr);
                        intent.putExtra("cardType", cardType);
                        intent.putExtra("paymentMethod", paymentMethod.toString());
                        intent.putExtra("installments", installments.toString());
                        //agrego los datos al intent
                        intent.putExtra("authorizationCode", authorizationCodeStr);
                        intent.putExtra("paymentGateway", paymentGateway);
                        intent.putExtra("uniqueNumber", uniqueNumber);
                        intent.putExtra("batch", batch);
                        intent.putExtra("voucherNumber", voucherNumber);
                        intent.putExtra("commerceNumber", commerceNumber);
                        intent.putExtra("terminalNumber", terminalNumber);
                        intent.putExtra("cardNumber", cardNumber);
                    } else if (amount == 20000) {
                        resultMessage = "Error de lectura";
                    } else if (amount == 30000) {
                        resultMessage = "Rechazado por pinpad";
                    } else {
                        resultMessage = "Otros";
                    }

                    //agrego mensaje al intent
                    intent.putExtra("resultMessage", resultMessage);

                    //iniciar la segunda actividad
                    startActivity(intent);

                    //reestabler editText luego de enviar la info
                    amountInput.setText("");

                    //reestabler spinners luego de enviar la info
                    cardTypeSp.setSelection(0);
                    paymentMethodSp.setSelection(0);
                }
            }
        });
    }
}
