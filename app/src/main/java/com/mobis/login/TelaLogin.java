package com.mobis.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mobis.R;

public class TelaLogin extends AppCompatActivity {

    private TextView text_criar_conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        getSupportActionBar().hide();
        iniciarComponentes();

        text_criar_conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaLogin.this, TelaCriarConta.class);
                startActivity(intent);
            }
        });
    }

    private void iniciarComponentes() {
        text_criar_conta = findViewById(R.id.textCriarConta);
    }
}