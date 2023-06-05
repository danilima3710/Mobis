package com.mobis.telaPrincipal.ui.Passageiro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mobis.R;
import com.mobis.constantes.ItensSpinner;
import com.mobis.enumeradores.Acoes;
import com.mobis.enumeradores.EnumMovimentacao;
import com.mobis.enumeradores.EnumSexo;
import com.mobis.mascaras.TelefoneMascara;
import com.mobis.models.Movimentacao;
import com.mobis.models.Passageiro;
import com.mobis.trataError.TrataErroCamposPreenchidos;
import com.mobis.trataError.TrataErroTelaCriarMovimentacao;
import com.mobis.trataError.TrataErroTelaCriarPassageiro;
import com.mobis.validacoes.ValidaCadastroMovimentacao;
import com.mobis.validacoes.ValidaCadastroPassageiro;

public class TelaPassageiro extends AppCompatActivity {

    private Passageiro passageiro;
    private Spinner spinnerSexo;
    private EditText txtNome;
    private EditText txtTelefone;
    private Button btnEditar;
    private Button btnRemover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_passageiro);

        passageiro = (Passageiro) getIntent().getSerializableExtra("Passageiro");

        iniciarComponentes(passageiro);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarTelaConfirmacao(view, Acoes.EDITANDO);
            }
        });

        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarTelaConfirmacao(view, Acoes.REMOVENDO);
            }
        });
    }

    private void iniciarComponentes(Passageiro passageiro) {
        spinnerSexo = findViewById(R.id.spn_sexo_consulta        );
        txtNome     = findViewById(R.id.txt_nome_consulta        );
        txtTelefone = findViewById(R.id.txt_telefone_consulta    );
        btnRemover  = findViewById(R.id.button_remover_passageiro);
        btnEditar   = findViewById(R.id.button_editar_passageiro );

        txtTelefone.addTextChangedListener(new TelefoneMascara(txtTelefone));

        inserirValorSpinner();

        txtNome.setText(passageiro.getNome());
        txtTelefone.setText(passageiro.getTelefone());
        spinnerSexo.setSelection(getPosicaoTipoSexo(passageiro.getSexo()));
    }

    private void inserirValorSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ItensSpinner.opcoesSexo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapter);
    }

    private int getPosicaoTipoSexo(EnumSexo enumSexo) {
        switch (enumSexo) {
            case MASCULINO    : return 1;
            case FEMININO     : return 2;
            case INDEFINIDO   : return 3;
            case NAO_RESPONDER: return 4;
            default           : return 0;
        }
    }

    private void editarPassageiro(View view) {
        passageiro.setNome(txtNome.getText().toString());
        passageiro.setTelefone(txtTelefone.getText().toString());
        passageiro.setSexo(EnumSexo.stringToEnum(spinnerSexo.getSelectedItem().toString()));

        if (TrataErroCamposPreenchidos.trataInconsistenciaInformacao(view, ValidaCadastroPassageiro.validaCamposPreenchidos(passageiro.getNome())))
            return;

        TrataErroTelaCriarPassageiro.trataErroEditar(view, passageiro.editar());
        abrirTelaPassageiro();
    }

    private void removerPassageiro(View view) {
        TrataErroTelaCriarPassageiro.trataErroRemover(view, passageiro.remover());
        abrirTelaPassageiro();
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
                        editarPassageiro(view);
                    else
                        removerPassageiro(view);
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

    private void abrirTelaPassageiro() {
        finish();
    }
}