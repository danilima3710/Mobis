package com.mobis.telaPrincipal.ui.Movimentacao;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobis.R;
import com.mobis.adapter.MovimentacaoAdapter;
import com.mobis.adapter.PassageiroAdapter;
import com.mobis.databinding.FragmentMovimentacaoBinding;
import com.mobis.login.TelaLogin;
import com.mobis.mascaras.DataMascara;
import com.mobis.models.Movimentacao;
import com.mobis.models.Passageiro;
import com.mobis.telaPrincipal.ui.Passageiro.TelaPassageiro;
import com.mobis.telaPrincipal.ui.Viagem.TelaCriarViagem;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MovimentacaoFragment extends Fragment {
    FragmentMovimentacaoBinding binding;
    ListView listView;
    List<Movimentacao> movimentacaoList;
    TextView texto;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMovimentacaoBinding.inflate(inflater, container, false);
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

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TelaCriarMovimentacao.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                abrirTelaMovimentacao(position);
            }
        });

        return root;
    }

    private void iniciarComponentes() {
        listView = binding.listViewMovimentacao;
        texto = binding.textoCentral;
        movimentacaoList = new ArrayList<>();

        buscaPreencheRecycleView();
    }

    private void abrirTelaMovimentacao(int posicao) {
        Intent intent = new Intent(getActivity(), TelaMovimentacao.class);
        intent.putExtra("Movimentacao", movimentacaoList.get(posicao));
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        buscaPreencheRecycleView();
    }

    private void buscaPreencheRecycleView() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("Movimentacao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movimentacaoList.clear();

                String idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Movimentacao movimentacao = dataSnapshot.getValue(Movimentacao.class);

                    if (movimentacao.getIdUsuario().equals(idUsuario))
                        movimentacaoList.add(movimentacao);
                }

                Collections.sort(movimentacaoList, comparator);
                movimentacaoList.sort(comparator);

                escondeCampos();

                MovimentacaoAdapter movimentacaoAdapter = new MovimentacaoAdapter(getContext(), R.layout.item_movimentacao, movimentacaoList);
                listView.setAdapter(movimentacaoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void escondeCampos() {
        if (movimentacaoList.isEmpty()) {
            texto.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        } else {
            texto.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    Comparator<Movimentacao> comparator = new Comparator<Movimentacao>() {
        @Override
        public int compare(Movimentacao movimentacao1, Movimentacao movimentacao2) {
            try {
                Date data1 = DataMascara.convertStringToDate(movimentacao1.getDataMovimentacao());
                Date data2 = DataMascara.convertStringToDate(movimentacao2.getDataMovimentacao());

                return data2.compareTo(data1);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    };
}