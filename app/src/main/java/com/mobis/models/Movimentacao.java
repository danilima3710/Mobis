package com.mobis.models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobis.enumeradores.EnumMovimentacao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Movimentacao extends ModelPadrao implements Serializable {
    String id;
    float valor;
    String dataMovimentacao;
    String descricao;
    EnumMovimentacao.TipoMovimentacao tipoMovimentacao;

    public Movimentacao(){

    }

    public Movimentacao(String id, String idUsuario, String data, float valor, String descricao, EnumMovimentacao.TipoMovimentacao tipoMovimentacao) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.dataMovimentacao = data;
        this.valor = valor;
        this.descricao = descricao;
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(String dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public EnumMovimentacao.TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(EnumMovimentacao.TipoMovimentacao tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public boolean salvar() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Task task = reference.child("Movimentacao").child(id.toString()).setValue(this);

        return task.isSuccessful();
    }

    public boolean editar() {
        DatabaseReference registroRef = FirebaseDatabase.getInstance().getReference().child("Movimentacao").child(id);

        Map<String, Object> atualizacoes = new HashMap<>();
        atualizacoes.put("dataMovimentacao", dataMovimentacao);
        atualizacoes.put("descricao"       , descricao       );
        atualizacoes.put("tipoMovimentacao", tipoMovimentacao);
        atualizacoes.put("valor"           , valor           );

        Task task = registroRef.updateChildren(atualizacoes);

        return task.isSuccessful();
    }

    public boolean remover() {
        DatabaseReference registroRef = FirebaseDatabase.getInstance().getReference().child("Movimentacao").child(id);
        Task task = registroRef.removeValue();

        return task.isSuccessful();
    }
}
