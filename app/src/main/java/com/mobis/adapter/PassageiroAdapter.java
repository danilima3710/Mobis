package com.mobis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mobis.R;
import com.mobis.models.Passageiro;

import java.util.List;

public class PassageiroAdapter extends ArrayAdapter<Passageiro> {
    Context context;
    int recurso;

    public PassageiroAdapter(@NonNull Context context, int resource, @NonNull List<Passageiro> objects) {
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
        TextView textNome = convertView.findViewById(R.id.txtNome);
        TextView textTelefone = convertView.findViewById(R.id.txtTelefone);

        Passageiro passageiro = getItem(position);

        //Setando dados do objeto artigo no item de layout inflado em convertView
        textNome.setText(passageiro.getNome());
        textTelefone.setText(passageiro.getTelefone());

        return convertView;
    }
}
