package com.mobis.models;

import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Viagem extends ModelPadrao{
    String id;
    String dataHora;
    String enderecoOrigem;
    String enderecoFim;
    float valorPassageiro;
    List<Passageiro> passageiros;
    List<String> observacoes;
    boolean finalizado;

    public Viagem() {
    }

    public Viagem(String id, String dataHora, String enderecoOrigem, String enderecoFim, float valorPassageiro, List<Passageiro> passageiroList, List<String> observacoes, boolean finalizado) {
        this.id              = id;
        this.dataHora        = dataHora;
        this.enderecoOrigem  = enderecoOrigem;
        this.enderecoFim     = enderecoFim;
        this.valorPassageiro = valorPassageiro;
        this.passageiros     = passageiroList;
        this.observacoes     = observacoes;
        this.finalizado      = finalizado;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getEnderecoOrigem() {
        return enderecoOrigem;
    }

    public void setEnderecoOrigem(String enderecoOrigem) {
        this.enderecoOrigem = enderecoOrigem;
    }

    public String getEnderecoFim() {
        return enderecoFim;
    }

    public void setEnderecoFim(String enderecoFim) {
        this.enderecoFim = enderecoFim;
    }

    public float getValorPassageiro() {
        return valorPassageiro;
    }

    public void setValorPassageiro(float valorPassageiro) {
        this.valorPassageiro = valorPassageiro;
    }

    public List<Passageiro> getPassageiros() {
        return passageiros;
    }

    public void setPassageiros(List<Passageiro> passageiros) {
        this.passageiros = passageiros;
    }

    public List<String> getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(List<String> observacoes) {
        this.observacoes = observacoes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public boolean salvar() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Task task = reference.child("Viagem").child(id.toString()).setValue(this);

        return task.isSuccessful();
    }
}
