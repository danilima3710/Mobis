package com.mobis.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.mobis.R;
import com.mobis.enumeradores.EnumCampos;
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

                if (trataInconsistenciaInformacao(view, ValidaCadastroUsuario.validaCamposPreenchidos(email, senha, confirmaSenha)))
                    return;

                if (!ValidaCadastroUsuario.validaSenha(senha, confirmaSenha)) {
                    apresentaMensagemInconsistenciaSenha(view);
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

                System.out.println(task.getResult().toString());

                if (task.isSuccessful()) {
                    apresentaMensagem(view, "Usuário cadastrado com suceeo");
                } else {
                    apresentaMensagem(view, task.getException().getMessage());
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

    private boolean trataInconsistenciaInformacao(View view, EnumCampos.campos campos) {
        
        String mensagem = "";
        switch (campos) {
            case TODOS_CAMPOS: mensagem = "Nenhum campo foi preenchido"; break;
            case CAMPO_EMAIL: mensagem = "Falta informar o email"; break;
            case CAMPO_SENHA: mensagem = "Falta informar a senha"; break;
            case CAMPO_CONFIRMA_SENHA: mensagem = "Falta informar a confirmação da senha"; break;
            default: return false;
        }

        apresentaMensagem(view, mensagem);

        return true;
    }

    private void apresentaMensagemInconsistenciaSenha (View view) {
        apresentaMensagem(view, "As senhas estão diferentes");
    }

    private void apresentaMensagem(View view, String mensagem) {
        Snackbar snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(Color.WHITE);
        snackbar.setTextColor(Color.BLACK);
        snackbar.show();
    }
}