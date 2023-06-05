package com.mobis.telaPrincipal.ui.Passageiro;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobis.R;
import com.mobis.adapter.PassageiroAdapter;
import com.mobis.databinding.FragmentPassageiroBinding;
import com.mobis.login.TelaLogin;
import com.mobis.models.Passageiro;
import com.mobis.telaPrincipal.ui.Movimentacao.TelaMovimentacao;

import java.util.ArrayList;
import java.util.List;

public class PassageiroFragment extends Fragment {
    FragmentPassageiroBinding binding;
    ListView listView;
    List<Passageiro> passageiroList;
    TextView texto;
    
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPassageiroBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        iniciaComponentes();

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
                Intent intent = new Intent(getActivity(), TelaCriarPassageiro.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                abrirTelaPassageiro(position);
            }
        });

        return root;
    }

    private void iniciaComponentes() {
        listView = binding.listViewPassageiro;
        texto = binding.textoCentral;
        passageiroList = new ArrayList<>();

        buscaPreencheRecycleView();
    }

    private void abrirTelaPassageiro(int posicao) {
        Intent intent = new Intent(getActivity(), TelaPassageiro.class);
        intent.putExtra("Passageiro", passageiroList.get(posicao));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        buscaPreencheRecycleView();
    }

    private void buscaPreencheRecycleView() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("Passageiro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                passageiroList.clear();

                String idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Passageiro passageiro = dataSnapshot.getValue(Passageiro.class);

                    if (passageiro.getIdUsuario().equals(idUsuario))
                        passageiroList.add(passageiro);
                }

                escondeCampos();

                PassageiroAdapter passageiroAdapter = new PassageiroAdapter(getContext(), R.layout.item_passageiro, passageiroList);
                listView.setAdapter(passageiroAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void escondeCampos() {
        if (passageiroList.isEmpty()) {
            texto.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        } else {
            texto.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }
}