package com.mobis.telaPrincipal.ui.Viagem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobis.R;
import com.mobis.apresentaMensagem.ApresentaMensagem;
import com.mobis.models.Passageiro;
import com.mobis.models.Viagem;
import com.mobis.trataError.TrataErroCamposPreenchidos;
import com.mobis.trataError.TrataErroTelaCriarViagem;
import com.mobis.validacoes.ValidaCadastroViagem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TelaCriarViagem extends AppCompatActivity {

    //EditText
    private EditText txtDataHora;
    private EditText txtOrigem;
    private EditText txtDestino;
    private EditText txtObservacao;

    //TextView
    private TextView textAdicionarPassageiro;
    private TextView textAdicionarObservacao;

    //Outros
    private AutoCompleteTextView autoCompleteNomePassageiro;
    private Button btnCadastrar;

    //Listas
    private List<Passageiro> passageiroList;
    private List<String> passageiroNomes;
    private List<Passageiro> passageiroSelecionadoList;
    private List<String> observacaoList;

    //Entidades
    private Passageiro passageiroAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_criar_viagem);

        iniciarComponentes();
        buscaPreencheSpinnerPassageiro();

        //Ao selecionar um passageiro
        autoCompleteNomePassageiro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for (Passageiro passageiro : passageiroList) {
                    if (passageiro.getNome().equals(autoCompleteNomePassageiro.getText().toString())) {
                        passageiroAtual = passageiro;
                    }
                }
            }
        });

        //Ao adicionar um passageiro
        textAdicionarPassageiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarPassageiro(view);
            }
        });

        //Ao adicionar uma observação
        textAdicionarObservacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarObservacao(view);
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarViagem(view);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        buscaPreencheSpinnerPassageiro();
    }

    private void iniciarComponentes() {
        //EditText
        textAdicionarPassageiro = findViewById(R.id.textPassageiro         );
        textAdicionarObservacao = findViewById(R.id.textAdicionarObservacao);
        txtObservacao           = findViewById(R.id.txt_observacao         );
        txtDataHora             = findViewById(R.id.txt_data);

        //TextView
        txtOrigem  = findViewById(R.id.txt_endereco_origem );
        txtDestino = findViewById(R.id.txt_endereco_destino);

        //Listas
        passageiroList            = new ArrayList<>();
        passageiroSelecionadoList = new ArrayList<>();
        passageiroNomes           = new ArrayList<>();
        observacaoList            = new ArrayList<>();

        //Outros
        autoCompleteNomePassageiro = findViewById(R.id.autoCompleteNomePassageiro);
        btnCadastrar               = findViewById(R.id.buttonCadastrar           );

    }

    private void buscaPreencheSpinnerPassageiro() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("Passageiro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                passageiroList .clear();
                passageiroNomes.clear();

                String idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Passageiro passageiro = dataSnapshot.getValue(Passageiro.class);

                    if (passageiro.getIdUsuario().equals(idUsuario) && !ValidaCadastroViagem.verificarPassageiroSelecionado(passageiroSelecionadoList, passageiro)) {
                        passageiroNomes.add(passageiro.getNome());
                        passageiroList.add(passageiro);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(TelaCriarViagem.this, android.R.layout.simple_spinner_item, passageiroNomes);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                autoCompleteNomePassageiro.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void adicionarPassageiro(View view) {

        if (!TrataErroTelaCriarViagem.trataErroAdicionar(view, ValidaCadastroViagem.validaAdicionarPassageiro(passageiroSelecionadoList, passageiroAtual))) {
            passageiroSelecionadoList.add(passageiroAtual);
            passageiroAtual = null;
            autoCompleteNomePassageiro.setText("");
        }
    }

    private void adicionarObservacao(View view) {
        String observacao = txtObservacao.getText().toString();

        if (!TrataErroTelaCriarViagem.trataErroAdicionar(view, ValidaCadastroViagem.validaAdicionarObservacao(observacao))) {
            observacaoList.add(observacao);
            txtObservacao.setText("");
        }
    }

    private void criarViagem(View view) {
        String dataHora = txtDataHora.getText().toString();
        String origem   = txtOrigem  .getText().toString();
        String destino  = txtDestino .getText().toString();

        if (TrataErroCamposPreenchidos.trataInconsistenciaInformacao(view, ValidaCadastroViagem.validaCamposPreenchidos(dataHora, origem, destino)))
            return;

        Viagem viagem = new Viagem(UUID.randomUUID().toString(), dataHora, origem, destino, 0f, passageiroSelecionadoList, observacaoList, false);
        viagem.setIdUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());

        TrataErroTelaCriarViagem.trataErroCadastrar(view, viagem.salvar());

        abrirTelaViagem();
    }

    private void abrirTelaViagem() {
        finish();
    }
}