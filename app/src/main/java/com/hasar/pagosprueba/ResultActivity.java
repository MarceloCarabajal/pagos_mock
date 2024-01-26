package com.hasar.pagosprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    TextView resultTextView;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //obtengo las referencias
        resultTextView = findViewById(R.id.ResultTextView);
        backButton = findViewById(R.id.backButton);

        //obtener los datos del intent
        Intent intent = getIntent();
        String authorizationCode = intent.getStringExtra("authorizathionCode");
        String paymentGateway = intent.getStringExtra("paymentGateway");
        String uniqueNumber = intent.getStringExtra("uniqueNumber");
        String batch = intent.getStringExtra("batch");
        String voucherNumber = intent.getStringExtra("voucherNumber");
        String commerceNumber = intent.getStringExtra("commerceNumber");
        String terminalNumber = intent.getStringExtra("terminalNumber");
        String cardNumber = intent.getStringExtra("cardNumber");

        resultTextView.setText("Authorization Code: " + authorizationCode + "\nPayment Gateway: " +
                paymentGateway + "\nUnique Number: " + uniqueNumber + "\nBatch: " + batch +
                "\nVoucher Number: " + voucherNumber + "\nCommerce Number: "+ commerceNumber +
                "\nTerminal Number: " + terminalNumber + "\nCard Number: " + cardNumber);

        //------------------------------------------------------------

        //listener para que al clickear boton, vuelva atras
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}