package com.mobis.telaPrincipal.ui.Viagem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mobis.adapter.PassageiroAdapter;
import com.mobis.adapter.ViagemAdapter;
import com.mobis.databinding.FragmentViagemBinding;
import com.mobis.models.Passageiro;
import com.mobis.models.Viagem;
import com.mobis.telaPrincipal.ui.Passageiro.TelaCriarPassageiro;

import java.util.ArrayList;
import java.util.List;

public class ViagemFragment extends Fragment {

    private FragmentViagemBinding binding;
    ListView listView;
    List<Viagem> viagemList;
    TextView texto;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentViagemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        iniciaComponentes();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TelaCriarViagem.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        buscaPreencheRecycleView();
    }

    private void iniciaComponentes() {
        listView = binding.listViewViagem;
        texto = binding.textoCentral;
        viagemList = new ArrayList<>();

        buscaPreencheRecycleView();
    }

    private void buscaPreencheRecycleView() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("Viagem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viagemList.clear();

                String idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Viagem viagem = dataSnapshot.getValue(Viagem.class);

                    if (viagem.getIdUsuario().equals(idUsuario))
                        viagemList.add(viagem);
                }

                escondeCampos();

                ViagemAdapter viagemAdapter = new ViagemAdapter(getContext(), R.layout.item_viagem, viagemList);
                listView.setAdapter(viagemAdapter);
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
        if (viagemList.isEmpty()) {
            texto.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        } else {
            texto.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }
}