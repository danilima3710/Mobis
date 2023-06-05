package com.mobis.telaPrincipal.ui.Relatorio;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobis.databinding.FragmentRelatorioBinding;
import com.mobis.login.TelaLogin;
import com.mobis.mascaras.DataMascara;
import com.mobis.models.Movimentacao;
import com.mobis.trataError.TrataErroRelatorio;
import com.mobis.validacoes.ValidaRelatorio;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RelatorioFragment extends Fragment {

    private FragmentRelatorioBinding binding;
    private EditText txtDataInicial;
    private EditText txtDataFinal;
    private Button btnFiltrar;
    private PieChart pieChart;
    private List<Movimentacao> movimentacaoList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRelatorioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        iniciarComponentes();

        binding.fabExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), TelaLogin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    aplicarFiltro(view);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        buscaPreencheGrafico();
    }

    private void iniciarComponentes() {
        txtDataInicial = binding.txtDataInicial;
        txtDataFinal   = binding.txtDataFinal;
        btnFiltrar     = binding.btnFiltrar;
        pieChart       = binding.pieChart;

        txtDataInicial.addTextChangedListener(new DataMascara(txtDataInicial));
        txtDataFinal.addTextChangedListener(new DataMascara(txtDataFinal));

        movimentacaoList = new ArrayList<>();

        buscaPreencheGrafico();
    }

    private void buscaPreencheGrafico() {
        String idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String dataInicial = txtDataInicial.getText().toString();
        String dataFinal = txtDataFinal.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Movimentacao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    movimentacaoList.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Movimentacao movimentacao = dataSnapshot.getValue(Movimentacao.class);

                            if (filtraMovimentacao(movimentacao, dataInicial, dataFinal, idUsuario))
                                movimentacaoList.add(movimentacao);
                    }

                    preencheGrafico();

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void preencheGrafico() {

        float valorEntrada = 0f;
        float valorSaida = 0f;

        for (Movimentacao movimentacao : movimentacaoList) {

            switch (movimentacao.getTipoMovimentacao()) {
                case ENTRADA: valorEntrada += movimentacao.getValor(); break;
                case SAIDA: valorSaida += movimentacao.getValor(); break;
            }
        }

        ArrayList<PieEntry> visitors = new ArrayList<>();
        visitors.add(new PieEntry(valorEntrada, "Entrada"));
        visitors.add(new PieEntry(valorSaida, "Saida"));

        PieDataSet pieDataSet = new PieDataSet(visitors, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        float resultado = valorEntrada - valorSaida;
        StringBuilder textoCentro = new StringBuilder();

        int color;

        if (resultado < 0) {
            textoCentro.append('-');
            color = Color.RED;
        } else
            color = Color.GREEN;

        textoCentro.append(String.format("%.2f", resultado));

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText(textoCentro.toString());
        pieChart.setCenterTextColor(color);
        pieChart.animate();
    }

    private boolean filtraMovimentacao(Movimentacao movimentacao, String sDataInicial, String sDataFinal, String idUsuario) throws ParseException {

        if (!movimentacao.getIdUsuario().equals(idUsuario))
            return false;

        Date data;

        if (!sDataInicial.trim().isEmpty()) {
            data = DataMascara.convertStringToDate(sDataInicial);

            if (DataMascara.convertStringToDate(movimentacao.getDataMovimentacao()).before(data))
                return false;
        }

        if (!sDataFinal.trim().isEmpty()) {
            data = DataMascara.convertStringToDate(sDataFinal);

            if (DataMascara.convertStringToDate(movimentacao.getDataMovimentacao()).after(data))
                return false;
        }

        return true;
    }

    private void aplicarFiltro(View view) throws ParseException {
        if (TrataErroRelatorio.trataErroFiltro(view, ValidaRelatorio.validaFiltros(txtDataInicial.getText().toString(), txtDataFinal.getText().toString())))
            return;

        buscaPreencheGrafico();
    }
}