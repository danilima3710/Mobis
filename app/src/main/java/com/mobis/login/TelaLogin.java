package com.mobis.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobis.R;
import com.mobis.apresentaMensagem.ApresentaMensagem;
import com.mobis.telaPrincipal.TelaPrincipal;
import com.mobis.trataError.TrataErroCamposPreenchidos;
import com.mobis.validacoes.ValidaLoginUsuario;

public class TelaLogin extends AppCompatActivity {

    private TextView text_criar_conta;
    private EditText edit_email;
    private EditText edit_senha;
    private Button btn_login;
    private ProgressBar progressBar;
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

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edit_email.getText().toString();
                String senha = edit_senha.getText().toString();

                if (TrataErroCamposPreenchidos.trataInconsistenciaInformacao(view, ValidaLoginUsuario.validaCamposPreenchidos(email, senha)))
                    return;

                progressBar.setVisibility(View.VISIBLE);

                AutenticarUsuario(email, senha, view);
            }
        });
    }

    private void AutenticarUsuario(String email, String senha, View view) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            telaPrincipal();
                        }
                    }, 2000);
                }
                else {
                    ApresentaMensagem.ApresentaMensagemRapida(view, "Erro ao realizar o login");
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void telaPrincipal() {
        Intent intent = new Intent(TelaLogin.this, TelaPrincipal.class);
        startActivity(intent);
        finish();
    }

    private void iniciarComponentes() {
        text_criar_conta = findViewById(R.id.textCriarConta);
        edit_email = findViewById(R.id.editEmail);
        edit_senha = findViewById(R.id.editSenha);
        btn_login = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progressBarLogin);
    }
}