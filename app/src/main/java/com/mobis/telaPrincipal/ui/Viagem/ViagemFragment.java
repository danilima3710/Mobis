package com.mobis.telaPrincipal.ui.Viagem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mobis.databinding.FragmentViagemBinding;

public class ViagemFragment extends Fragment {

    private FragmentViagemBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViagemViewModel viagemViewModel =
                new ViewModelProvider(this).get(ViagemViewModel.class);

        binding = FragmentViagemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}