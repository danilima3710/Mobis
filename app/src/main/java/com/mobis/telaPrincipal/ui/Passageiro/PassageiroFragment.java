package com.mobis.telaPrincipal.ui.Passageiro;

import android.content.Intent;
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
import com.mobis.login.TelaLogin;
import com.mobis.models.Passageiro;
import com.mobis.telaPrincipal.TelaPrincipal;

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
                Intent intent = new Intent(getActivity(), TelaCriarPassageiro.class);
                startActivity(intent);
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