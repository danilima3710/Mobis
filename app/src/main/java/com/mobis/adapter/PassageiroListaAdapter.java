package com.mobis.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobis.R;
import com.mobis.models.Passageiro;

import java.util.List;

public class PassageiroListaAdapter extends RecyclerView.Adapter<PassageiroListaAdapter.PassageiroViewHolder>{

    private List<Passageiro> passageiroList;

    public PassageiroListaAdapter(List<Passageiro> passageiroList) {
        this.passageiroList = passageiroList;
    }

    @NonNull
    @Override
    public PassageiroListaAdapter.PassageiroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_passageiro_lista, parent, false);
        return new PassageiroViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PassageiroListaAdapter.PassageiroViewHolder holder, int position) {
        Passageiro passageiro = passageiroList.get(position);
        holder.textViewNome.setText(passageiro.getNome());
    }

    @Override
    public int getItemCount() {
        return passageiroList.size();
    }

    public static class PassageiroViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNome;

        public PassageiroViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.txtNomePassageiroLista);
        }
    }
}
