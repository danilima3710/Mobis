package com.mobis.models;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobis.enumeradores.EnumSexo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Passageiro extends ModelPadrao implements Serializable {
    private String id;
    private String nome;
    private String telefone;
    private EnumSexo sexo;

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

    public EnumSexo getSexo() {
        return sexo;
    }

    public void setSexo(EnumSexo sexo) {
        this.sexo = sexo;
    }

    public Passageiro(String id, String nome, String telefone, EnumSexo sexo) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.sexo = sexo;
    }

    public Passageiro() {

    }

    public boolean salvar() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Task task = reference.child("Passageiro").child(id.toString()).setValue(this);

        return task.isSuccessful();
    }

    public boolean editar() {
        DatabaseReference registroRef = FirebaseDatabase.getInstance().getReference().child("Passageiro").child(id);

        Map<String, Object> atualizacoes = new HashMap<>();
        atualizacoes.put("nome"    , nome    );
        atualizacoes.put("telefone", telefone);
        atualizacoes.put("sexo"    , sexo    );

        Task task = registroRef.updateChildren(atualizacoes);

        return task.isSuccessful();
    }

    public boolean remover() {
        DatabaseReference registroRef = FirebaseDatabase.getInstance().getReference().child("Passageiro").child(id);
        Task task = registroRef.removeValue();

        return task.isSuccessful();
    }
}
