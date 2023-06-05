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

public class ObservacaoListaAdapter extends RecyclerView.Adapter<ObservacaoListaAdapter.ObservacaoViewHolder> {
    private List<String> observacaoList;

    public ObservacaoListaAdapter(List<String> observacaoList) {
        this.observacaoList = observacaoList;
    }

    @NonNull
    @Override
    public ObservacaoListaAdapter.ObservacaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_observacao_lista, parent, false);
        return new ObservacaoListaAdapter.ObservacaoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservacaoListaAdapter.ObservacaoViewHolder holder, int position) {
        holder.textViewObservacao.setText(observacaoList.get(position));
    }

    @Override
    public int getItemCount() {
        return observacaoList.size();
    }

    public static class ObservacaoViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewObservacao;

        public ObservacaoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewObservacao = itemView.findViewById(R.id.txtObservacaoLista);
        }
    }
}
