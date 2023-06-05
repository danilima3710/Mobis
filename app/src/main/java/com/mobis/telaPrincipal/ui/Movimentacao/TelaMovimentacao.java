package com.mobis.telaPrincipal.ui.Movimentacao;

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
import com.mobis.mascaras.DataMascara;
import com.mobis.mascaras.RealMascara;
import com.mobis.models.Movimentacao;
import com.mobis.trataError.TrataErroCamposPreenchidos;
import com.mobis.trataError.TrataErroTelaCriarMovimentacao;
import com.mobis.validacoes.ValidaCadastroMovimentacao;

import java.text.ParseException;

public class TelaMovimentacao extends AppCompatActivity {

    private Spinner spinnerMovimentacao;
    EditText txtData;
    private EditText txtValor;
    private EditText txtDescricao;
    private Button btnEditar;
    private Button btnRemover;
    private Movimentacao movimentacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_movimentacao);

        movimentacao = (Movimentacao) getIntent().getSerializableExtra("Movimentacao");

        iniciarComponentes(movimentacao);

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

    private void iniciarComponentes(Movimentacao movimentacao) {
        spinnerMovimentacao = findViewById(R.id.spn_movimentacao_consulta          );
        txtData             = findViewById(R.id.txt_data_movimentacao_consulta     );
        txtValor            = findViewById(R.id.txt_valor_movimentacao_consulta    );
        txtDescricao        = findViewById(R.id.txt_descricao_movimentacao_consulta);
        btnEditar           = findViewById(R.id.button_editar_movimentacao         );
        btnRemover          = findViewById(R.id.button_remover_movimentacao        );

        inserirValorSpinner();

        //Adicionar Mascaras
        txtData.addTextChangedListener(new DataMascara(txtData));
        txtValor.addTextChangedListener(new RealMascara(txtValor));

        txtData.setText(movimentacao.getDataMovimentacao());
        txtValor.setText(RealMascara.convertFloatToString(movimentacao.getValor()));
        txtDescricao.setText(movimentacao.getDescricao());
        spinnerMovimentacao.setSelection(getPosicaoTipoMovimentacao(movimentacao.getTipoMovimentacao()));
    }

    private void inserirValorSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ItensSpinner.opcaoMovimentacao);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMovimentacao.setAdapter(adapter);
    }

    private int getPosicaoTipoMovimentacao(EnumMovimentacao.TipoMovimentacao enumMovimentacao) {
        switch (enumMovimentacao) {
            case ENTRADA: return 1;
            case SAIDA  : return 2;
            default: return 0;
        }
    }

    private void editarMovimentacao(View view) throws ParseException {
        movimentacao.setDataMovimentacao(txtData.getText().toString());
        movimentacao.setDescricao(txtDescricao.getText().toString());
        movimentacao.setValor(RealMascara.convertStringToFloat(txtValor.getText().toString()));
        movimentacao.setTipoMovimentacao(EnumMovimentacao.stringToEnum(spinnerMovimentacao.getSelectedItem().toString()));

        if (TrataErroCamposPreenchidos.trataInconsistenciaInformacao(view, ValidaCadastroMovimentacao.validaCamposPreenchidos(movimentacao.getDataMovimentacao(), movimentacao.getValor(), movimentacao.getDescricao(), movimentacao.getTipoMovimentacao())))
            return;

        if (TrataErroTelaCriarMovimentacao.trataErroData(view, ValidaCadastroMovimentacao.validaDataPreenchida(movimentacao.getDataMovimentacao())))
            return;;

        TrataErroTelaCriarMovimentacao.trataErroEditar(view, movimentacao.editar());
        abrirTelaMovimentacao();
    }

    private void removerMovimentacao(View view) {
        TrataErroTelaCriarMovimentacao.trataErroRemover(view, movimentacao.remover());
        abrirTelaMovimentacao();
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
                        editarMovimentacao(view);
                    else
                        removerMovimentacao(view);
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

    private void abrirTelaMovimentacao() {
        finish();
    }
}