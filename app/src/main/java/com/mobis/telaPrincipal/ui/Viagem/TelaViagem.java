package com.mobis.telaPrincipal.ui.Viagem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
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
import com.mobis.adapter.ObservacaoListaAdapter;
import com.mobis.adapter.PassageiroListaAdapter;
import com.mobis.enumeradores.Acoes;
import com.mobis.enumeradores.EnumMovimentacao;
import com.mobis.mascaras.DataHoraMascara;
import com.mobis.mascaras.RealMascara;
import com.mobis.models.Movimentacao;
import com.mobis.models.Passageiro;
import com.mobis.models.Viagem;
import com.mobis.trataError.TrataErroCamposPreenchidos;
import com.mobis.trataError.TrataErroTelaCriarMovimentacao;
import com.mobis.trataError.TrataErroTelaCriarPassageiro;
import com.mobis.trataError.TrataErroTelaCriarViagem;
import com.mobis.validacoes.ValidaCadastroViagem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TelaViagem extends AppCompatActivity {

    //EditText
    private EditText txtDataHora;
    private EditText txtOrigem;
    private EditText txtDestino;
    private EditText txtObservacao;
    private EditText txtValorPassageiro;

    //TextView
    private TextView textAdicionarPassageiro;
    private TextView textAdicionarObservacao;

    //RecyclerView
    private RecyclerView recyclerPassageiro;
    private RecyclerView recyclerObservacao;
    private RecyclerView.LayoutManager layoutManagerPassageiro;
    private RecyclerView.LayoutManager layoutManagerObservacao;


    //Outros
    private AutoCompleteTextView autoCompleteNomePassageiro;
    private Button btnEditar;
    private Button btnRemover;
    private Button btnFinalizar;

    //Listas
    private List<Passageiro> passageiroList;
    private List<String> passageiroNomes;
    private List<Passageiro> passageiroSelecionadoList;
    private List<String> observacaoList;

    //Entidades
    private Passageiro passageiroAtual;
    private Viagem viagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_viagem);

        viagem = (Viagem) getIntent().getSerializableExtra("Viagem");

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

        //Editar viagem
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarTelaConfirmacao(view, Acoes.EDITANDO);
            }
        });

        //Remover viagem
        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarTelaConfirmacao(view, Acoes.REMOVENDO);
            }
        });

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    finalizarViagem(view);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //Remover um passageiro da lista
        recyclerPassageiro.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null) {
                        int position = rv.getChildAdapterPosition(childView);
                        removePassageiro(position);
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        //Remover uma observação da lista
        recyclerObservacao.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null) {
                        int position = rv.getChildAdapterPosition(childView);
                        removeObservacao(position);
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    private void iniciarComponentes() {
        //EditText
        txtOrigem          = findViewById(R.id.txt_endereco_origem_consulta );
        txtDestino         = findViewById(R.id.txt_endereco_destino_consulta);
        txtObservacao      = findViewById(R.id.txt_observacao_consulta      );
        txtDataHora        = findViewById(R.id.txt_data_consulta            );
        txtValorPassageiro = findViewById(R.id.txt_valor_passageiro_consulta);
        //TextView
        textAdicionarPassageiro = findViewById(R.id.textPassageiro_consulta         );
        textAdicionarObservacao = findViewById(R.id.textAdicionarObservacao_consulta);

        //RecyclerView
        recyclerPassageiro = findViewById(R.id.rcv_passageiro_consulta);
        recyclerObservacao = findViewById(R.id.rcv_observacao_consulta);
        layoutManagerPassageiro = new LinearLayoutManager(this);
        layoutManagerObservacao = new LinearLayoutManager(this);

        //Listas
        passageiroList            = new ArrayList<>();
        passageiroSelecionadoList = new ArrayList<>();
        passageiroNomes           = new ArrayList<>();
        observacaoList            = new ArrayList<>();

        //Outros
        autoCompleteNomePassageiro = findViewById(R.id.autoCompleteNomePassageiro_consulta);
        btnEditar                  = findViewById(R.id.button_editar_viagem               );
        btnRemover                 = findViewById(R.id.button_remover_viagem              );
        btnFinalizar               = findViewById(R.id.button_finalizar_viagem            );

        //Aplicando mascara
        txtDataHora.addTextChangedListener(new DataHoraMascara(txtDataHora));

        //Carregando informações na tela
        txtDataHora.setText(viagem.getDataHora());
        txtOrigem.setText(viagem.getEnderecoOrigem());
        txtDestino.setText(viagem.getEnderecoFim());
        txtValorPassageiro.setText(RealMascara.convertFloatToString(viagem.getValorPassageiro()));
        passageiroSelecionadoList = viagem.getPassageiros();
        observacaoList = viagem.getObservacoes();

        //Atualizando as listas na tela
        atualizaRecycleViewPassageiro();
        atualizaRecycleViewObservacao();

        if (viagem.isFinalizado())
            btnFinalizar.setVisibility(View.INVISIBLE);
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

                ArrayAdapter<String> adapter = new ArrayAdapter<>(TelaViagem.this, android.R.layout.simple_spinner_item, passageiroNomes);
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

            atualizaRecycleViewPassageiro();
        }
    }

    private void removePassageiro(int posicao) {
        passageiroSelecionadoList.remove(posicao);
        atualizaRecycleViewPassageiro();
    }

    private void atualizaRecycleViewPassageiro() {

        if (passageiroSelecionadoList == null)
            return;

        recyclerPassageiro.setLayoutManager(layoutManagerPassageiro);
        PassageiroListaAdapter passageiroListaAdapter = new PassageiroListaAdapter(passageiroSelecionadoList);
        recyclerPassageiro.setAdapter(passageiroListaAdapter);
    }

    private void adicionarObservacao(View view) {
        String observacao = txtObservacao.getText().toString();

        if (!TrataErroTelaCriarViagem.trataErroAdicionar(view, ValidaCadastroViagem.validaAdicionarObservacao(observacao))) {
            observacaoList.add(observacao);
            txtObservacao.setText("");
        }

        atualizaRecycleViewObservacao();
    }

    private void removeObservacao(int posicao) {
        observacaoList.remove(posicao);
        atualizaRecycleViewObservacao();
    }

    private void atualizaRecycleViewObservacao() {
        if (observacaoList == null)
            return;

        recyclerObservacao.setLayoutManager(layoutManagerObservacao);
        ObservacaoListaAdapter observacaoListaAdapter = new ObservacaoListaAdapter(observacaoList);
        recyclerObservacao.setAdapter(observacaoListaAdapter);
    }

    private void editarViagem(View view) throws ParseException {
        viagem.setDataHora(txtDataHora.getText().toString());
        viagem.setEnderecoOrigem(txtOrigem.getText().toString());
        viagem.setEnderecoFim(txtDestino .getText().toString());
        viagem.setPassageiros(passageiroSelecionadoList);
        viagem.setObservacoes(observacaoList);
        viagem.setValorPassageiro(RealMascara.convertStringToFloat(txtValorPassageiro.getText().toString()));

        if (TrataErroCamposPreenchidos.trataInconsistenciaInformacao(view, ValidaCadastroViagem.validaCamposPreenchidos(viagem.getDataHora(), viagem.getEnderecoOrigem(), viagem.getEnderecoFim(), viagem.getValorPassageiro())))
            return;

        if (TrataErroTelaCriarViagem.trataErroDataHora(view, ValidaCadastroViagem.validaDataHoraPreenchida(viagem.getDataHora())))
            return;

        TrataErroTelaCriarViagem.trataErroEditar(view, viagem.editar());

        abrirTelaViagem();
    }

    private void removerViagem(View view) {
        TrataErroTelaCriarViagem.trataErroRemover(view, viagem.remover());
        abrirTelaViagem();
    }

    private void finalizarViagem(View view) throws ParseException {
        viagem.setFinalizado(true);

        if (TrataErroTelaCriarViagem.trataErroFinalizar(view, ValidaCadastroViagem.validaFinalizarViagem(viagem.getDataHora())))
            return;

        boolean result = viagem.finalizar();
        TrataErroTelaCriarViagem.trataErroFinalizar(view, result);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setId(UUID.randomUUID().toString());
        movimentacao.setIdUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
        movimentacao.setValor(viagem.getValorPassageiro() * viagem.getPassageiros().size());
        movimentacao.setTipoMovimentacao(EnumMovimentacao.TipoMovimentacao.ENTRADA);

        Date data = DataHoraMascara.convertStringToDate(viagem.getDataHora());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String sData = dateFormat.format(data);

        movimentacao.setDataMovimentacao(sData);
        movimentacao.setDescricao(String.format("Viagem %s -> %s", viagem.getEnderecoOrigem(), viagem.getEnderecoFim()));

        TrataErroTelaCriarMovimentacao.trataErroCadastrar(view, movimentacao.salvar());

        abrirTelaViagem();
    }

    private void criarTelaConfirmacao(View view, Acoes acao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmação");
        String message = "";

        if (acao == Acoes.EDITANDO)
            message = "Você deseja editar?";
        else
            message = "Você deseja remover?";

        builder.setMessage(message);

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (acao == Acoes.EDITANDO)
                        editarViagem(view);
                    else
                        removerViagem(view);
                }
                catch (Exception e){
                }
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Ação a ser executada ao clicar no botão "Não"
                // Pode ser deixado em branco se não for necessário
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void abrirTelaViagem() {
        finish();
    }
}