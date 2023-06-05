package com.mobis.telaPrincipal.ui.Movimentacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.mobis.R;
import com.mobis.constantes.ItensSpinner;
import com.mobis.enumeradores.EnumMovimentacao;
import com.mobis.mascaras.DataMascara;
import com.mobis.mascaras.RealMascara;
import com.mobis.models.Movimentacao;
import com.mobis.trataError.TrataErroCamposPreenchidos;
import com.mobis.trataError.TrataErroTelaCriarMovimentacao;
import com.mobis.trataError.TrataErroTelaCriarViagem;
import com.mobis.validacoes.ValidaCadastroMovimentacao;

import java.text.ParseException;
import java.util.UUID;

public class TelaCriarMovimentacao extends AppCompatActivity {

    private Spinner spinnerMovimentacao;
    EditText txtData;
    private EditText txtValor;
    private EditText txtDescricao;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_criar_movimentacao);

        iniciarComponentes();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    criarMovimentacao(view);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void iniciarComponentes() {
        spinnerMovimentacao = findViewById(R.id.spn_movimentacao          );
        txtData             = findViewById(R.id.txt_data_movimentacao     );
        txtValor            = findViewById(R.id.txt_valor_movimentacao    );
        txtDescricao        = findViewById(R.id.txt_descricao_movimentacao);
        btnCadastrar        = findViewById(R.id.buttonCadastrar           );

        //Adicionar Mascaras
        txtData.addTextChangedListener(new DataMascara(txtData));
        txtValor.addTextChangedListener(new RealMascara(txtValor));

        inserirValorSpinner();
    }

    private void inserirValorSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ItensSpinner.opcaoMovimentacao);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMovimentacao.setAdapter(adapter);
    }

    private void criarMovimentacao(View view) throws ParseException {
        String data = txtData.getText().toString();
        String descricao = txtDescricao.getText().toString();
        float valor = RealMascara.convertStringToFloat(txtValor.getText().toString());
        EnumMovimentacao.TipoMovimentacao tipoMovimentacao = EnumMovimentacao.stringToEnum(spinnerMovimentacao.getSelectedItem().toString());

        if (TrataErroCamposPreenchidos.trataInconsistenciaInformacao(view, ValidaCadastroMovimentacao.validaCamposPreenchidos(data, valor, descricao, tipoMovimentacao)))
            return;

        if (TrataErroTelaCriarMovimentacao.trataErroData(view, ValidaCadastroMovimentacao.validaDataPreenchida(data)))
            return;;

        Movimentacao movimentacao = new Movimentacao(UUID.randomUUID().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid(), data, valor, descricao, tipoMovimentacao);

        TrataErroTelaCriarMovimentacao.trataErroCadastrar(view, movimentacao.salvar());

        abrirTelaMovimentacao();
    }

    private void abrirTelaMovimentacao() {
        finish();
    }
}