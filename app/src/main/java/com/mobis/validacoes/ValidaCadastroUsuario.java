package com.mobis.validacoes;

import static com.mobis.enumeradores.EnumCampos.campos.CAMPO_CONFIRMA_SENHA;
import static com.mobis.enumeradores.EnumCampos.campos.CAMPO_EMAIL;
import static com.mobis.enumeradores.EnumCampos.campos.CAMPO_SENHA;
import static com.mobis.enumeradores.EnumCampos.campos.CAMPO_VALIDO;
import static com.mobis.enumeradores.EnumCampos.campos.TODOS_CAMPOS;

import com.mobis.enumeradores.EnumCampos;

public class ValidaCadastroUsuario {
    public static boolean validaSenha(String senha, String confirmaSenha) {
        return senha.equals(confirmaSenha);
    }

    public static EnumCampos.campos validaCamposPreenchidos (String email, String senha, String confirmaSenha) {

        if (email.isEmpty() && senha.isEmpty() && confirmaSenha.isEmpty())
            return TODOS_CAMPOS;

        if (email.isEmpty())
            return CAMPO_EMAIL;

        if (senha.isEmpty())
            return CAMPO_SENHA;

        if (confirmaSenha.isEmpty())
            return CAMPO_CONFIRMA_SENHA;

        return CAMPO_VALIDO;
    }
}
