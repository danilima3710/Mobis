package com.mobis.validacoes;

import static com.mobis.enumeradores.EnumCampos.*;

import com.mobis.enumeradores.EnumCampos;

public class ValidaLoginUsuario {
    public static EnumCampos validaCamposPreenchidos(String email, String senha) {
        if (email.isEmpty() && senha.isEmpty())
            return TODOS_CAMPOS;

        if (email.isEmpty())
            return CAMPO_EMAIL;

        if (senha.isEmpty())
            return CAMPO_SENHA;

        return CAMPO_VALIDO;
    }
}
