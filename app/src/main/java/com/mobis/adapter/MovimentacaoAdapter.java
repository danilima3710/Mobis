package com.mobis.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.mobis.R;
import com.mobis.enumeradores.EnumMovimentacao;
import com.mobis.models.Movimentacao;
import com.mobis.models.Passageiro;

import java.text.NumberFormat;
import java.util.List;

public class MovimentacaoAdapter extends ArrayAdapter<Movimentacao> {
    Context context;
    int recurso;

    public MovimentacaoAdapter(@NonNull Context context, int resource, @NonNull List<Movimentacao> objects) {
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
        TextView textData = convertView.findViewById(R.id.txtDataMovimentacao);
        TextView textDescricao = convertView.findViewById(R.id.txtDescricaoMovimentacao);
        TextView textValor = convertView.findViewById(R.id.txtValor);

        Movimentacao movimentacao = getItem(position);

        //Setando dados do objeto artigo no item de layout inflado em convertView
        textData.setText(movimentacao.getDataMovimentacao());
        textDescricao.setText(movimentacao.getDescricao());

        if (movimentacao.getTipoMovimentacao() == EnumMovimentacao.TipoMovimentacao.ENTRADA) {
            textValor.setText(NumberFormat.getCurrencyInstance().format(movimentacao.getValor()));
            textValor.setTextColor(Color.GREEN);
        }
        else {
            textValor.setText('-'+NumberFormat.getCurrencyInstance().format(movimentacao.getValor()));
            textValor.setTextColor(Color.RED);
        }

        return convertView;
    }
}
