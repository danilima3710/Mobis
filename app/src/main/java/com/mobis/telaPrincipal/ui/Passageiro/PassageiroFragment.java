package com.mobis.telaPrincipal.ui.Passageiro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mobis.databinding.FragmentPassageiroBinding;

public class PassageiroFragment extends Fragment {
    FragmentPassageiroBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PassageiroViewModel passageiroViewModel =
                new ViewModelProvider(this).get(PassageiroViewModel.class);

        binding = FragmentPassageiroBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}