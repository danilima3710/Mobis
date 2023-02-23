package com.mobis.validacoes;

import com.mobis.enumeradores.EnumCampos;

public class ValidaLoginUsuario {
    public static EnumCampos.campos validaCamposPreenchidos(String email, String senha) {
        if (email.isEmpty() && senha.isEmpty())
            return EnumCampos.campos.TODOS_CAMPOS;

        if (email.isEmpty())
            return EnumCampos.campos.CAMPO_EMAIL;

        if (senha.isEmpty())
            return EnumCampos.campos.CAMPO_SENHA;

        return EnumCampos.campos.CAMPO_VALIDO;
    }
}
