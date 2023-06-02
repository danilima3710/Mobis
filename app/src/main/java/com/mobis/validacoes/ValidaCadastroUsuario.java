package com.mobis.validacoes;

import static com.mobis.enumeradores.EnumCampos.*;

import com.mobis.enumeradores.EnumCampos;

public class ValidaCadastroUsuario {
    public static boolean validaSenha(String senha, String confirmaSenha) {
        return senha.equals(confirmaSenha);
    }

    public static EnumCampos validaCamposPreenchidos (String email, String senha, String confirmaSenha) {

        if (email.trim().isEmpty() && senha.trim().isEmpty() && confirmaSenha.trim().isEmpty())
            return TODOS_CAMPOS;

        if (email.trim().isEmpty())
            return CAMPO_EMAIL;

        if (senha.trim().isEmpty())
            return CAMPO_SENHA;

        if (confirmaSenha.trim().isEmpty())
            return CAMPO_CONFIRMA_SENHA;

        return CAMPO_VALIDO;
    }
}
