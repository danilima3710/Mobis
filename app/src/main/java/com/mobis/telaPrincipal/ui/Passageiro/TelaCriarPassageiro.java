package com.mobis.telaPrincipal.ui.Passageiro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mobis.R;
import com.mobis.apresentaMensagem.ApresentaMensagem;
import com.mobis.constantes.ItensSpinner;
import com.mobis.databinding.ActivityTelaPrincipalBinding;
import com.mobis.enumeradores.EnumSexo;
import com.mobis.models.Passageiro;
import com.mobis.trataError.TrataErroCamposPreenchidos;
import com.mobis.validacoes.ValidaCadastroPassageiro;

import java.util.UUID;

public class TelaCriarPassageiro extends AppCompatActivity {

    private Spinner spinnerSexo;
    private EditText txtNome;
    private EditText txtTelefone;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_criar_passageiro);

        iniciarComponentes();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarPassageiro(view);
            }
        });
    }

    private void iniciarComponentes() {
        spinnerSexo  = findViewById(R.id.spn_sexo       );
        txtNome      = findViewById(R.id.txt_nome       );
        txtTelefone  = findViewById(R.id.txt_telefone   );
        btnCadastrar = findViewById(R.id.buttonCadastrar);

        inserirValorSpinner();
    }

    private void inserirValorSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ItensSpinner.opcoesSexo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapter);
    }

    private void criarPassageiro(View view) {
        String sNome = txtNome.getText().toString();
        String sTelefone = txtTelefone.getText().toString();

        if (TrataErroCamposPreenchidos.trataInconsistenciaInformacao(view, ValidaCadastroPassageiro.validaCamposPreenchidos(sNome)))
            return;

        Passageiro passageiro = new Passageiro(UUID.randomUUID().toString(), sNome, sTelefone, EnumSexo.stringToEnum(spinnerSexo.getSelectedItem().toString()));
        passageiro.setIdUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
        if (!passageiro.salvar())
            ApresentaMensagem.ApresentaMensagemRapida(view, "Erro ao criar um novo passageiro");
        else
            ApresentaMensagem.ApresentaMensagemRapida(view, "Passageiro cadastrado com sucesso");

        abrirTelaPassageiro();
    }

    private void abrirTelaPassageiro() {
        finish();
    }
}