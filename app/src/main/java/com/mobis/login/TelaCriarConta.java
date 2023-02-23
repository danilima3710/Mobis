package com.mobis.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.mobis.R;
import com.mobis.apresentaMensagem.ApresentaMensagem;
import com.mobis.enumeradores.EnumCampos;
import com.mobis.trataError.TrataErroCamposPreenchidos;
import com.mobis.trataError.TrataErroCriarConta;
import com.mobis.validacoes.ValidaCadastroUsuario;

public class TelaCriarConta extends AppCompatActivity {

    private EditText editEmail, editSenha, editConfirmaSenha;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_criar_conta);

        getSupportActionBar().hide();
        iniciaComponentes();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();
                String confirmaSenha = editConfirmaSenha.getText().toString();

                if (TrataErroCamposPreenchidos.trataInconsistenciaInformacao(view, ValidaCadastroUsuario.validaCamposPreenchidos(email, senha, confirmaSenha)))
                    return;

                if (!ValidaCadastroUsuario.validaSenha(senha, confirmaSenha)) {
                    ApresentaMensagem.ApresentaMensagemRapida(view, "As senhas estão diferentes");
                    return;
                }

                cadastrarUsuario(view, email, senha);
            }
        });
    }

    private void cadastrarUsuario(View view, String email, String senha) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(TelaCriarConta.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    ApresentaMensagem.ApresentaMensagemRapida(view, "Usuário cadastrado com suceeo");
                    telaPrincipal();
                } else {
                    ApresentaMensagem.ApresentaMensagemRapida(view, TrataErroCriarConta.getMessagemErro(task));
                }
            }
        });
    }
    private void iniciaComponentes() {
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        editConfirmaSenha = findViewById(R.id.editConfirmaSenha);
        btnCadastrar = findViewById(R.id.buttonCadastrar);
    }

    private void telaPrincipal() {
        Intent intent = new Intent(TelaCriarConta.this, TelaLogin.class);
        startActivity(intent);
        finish();
    }
}