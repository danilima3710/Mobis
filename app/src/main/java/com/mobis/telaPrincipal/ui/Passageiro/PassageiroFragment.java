package com.mobis.telaPrincipal.ui.Passageiro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mobis.apresentaMensagem.ApresentaMensagem;
import com.mobis.databinding.FragmentPassageiroBinding;
import com.mobis.enumeradores.EnumCadastros;
import com.mobis.models.Passageiro;

import java.util.UUID;

public class PassageiroFragment extends Fragment {
    FragmentPassageiroBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPassageiroBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Passageiro passageiro = new Passageiro(UUID.randomUUID().toString(), "Laura", "47 1234-1234", EnumCadastros.enumSexo.FEMININO);
                passageiro.salvar();

                ApresentaMensagem.ApresentaMensagemRapida(view, passageiro.getId().toString());
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}