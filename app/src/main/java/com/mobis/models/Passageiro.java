package com.mobis.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobis.enumeradores.EnumCadastros;

import java.util.UUID;

public class Passageiro {
    private String id;
    private String nome;
    private String telefone;
    private EnumCadastros.enumSexo sexo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public EnumCadastros.enumSexo getSexo() {
        return sexo;
    }

    public void setSexo(EnumCadastros.enumSexo sexo) {
        this.sexo = sexo;
    }

    public Passageiro(String id, String nome, String telefone, EnumCadastros.enumSexo sexo) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.sexo = sexo;
    }

    public void salvar() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Passageiro").child(id.toString()).setValue(this);
    }
}
