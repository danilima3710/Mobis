package com.mobis.telaPrincipal.ui.Movimentacao;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mobis.R;
import com.mobis.databinding.FragmentMovimentacaoBinding;

public class MovimentacaoFragment extends Fragment {
    private FragmentMovimentacaoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MovimentacaoViewModel movimentacaoViewModel =
                new ViewModelProvider(this).get(MovimentacaoViewModel.class);

        binding = FragmentMovimentacaoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}