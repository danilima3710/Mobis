package com.mobis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mobis.R;
import com.mobis.models.Viagem;

import java.util.List;

public class ViagemAdapter extends ArrayAdapter<Viagem> {
    Context context;
    int recurso;

    public ViagemAdapter(@NonNull Context context, int resource, @NonNull List<Viagem> objects) {
        super(context, resource, objects);

        this.context = context;
        this.recurso = resource;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        //Inflar lyaout com item de recurso recebido no construtor
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(recurso, parent, false);

        //Associando as referências dos objetos instanciados na view com variávaeis locais.
        TextView textDataHora = convertView.findViewById(R.id.txtData   );
        TextView textOrigem   = convertView.findViewById(R.id.txtOrigem );
        TextView textDestino  = convertView.findViewById(R.id.txtDestino);

        Viagem viagem = getItem(position);

        //Setando dados do objeto artigo no item de layout inflado em convertView
        textDataHora.setText(viagem.getDataHora());
        textOrigem.setText(viagem.getEnderecoOrigem());
        textDestino.setText(viagem.getEnderecoFim());

        return convertView;
    }
}
