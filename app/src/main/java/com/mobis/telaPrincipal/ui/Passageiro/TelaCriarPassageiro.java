package com.mobis.telaPrincipal.ui.Passageiro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mobis.R;

public class TelaCriarPassageiro extends AppCompatActivity {

    private Spinner spinnerSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_criar_passageiro);

        iniciarComponentes();

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Sexo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapter);
    }

    private void iniciarComponentes() {
        spinnerSexo = findViewById(R.id.spn_sexo);
    }
}