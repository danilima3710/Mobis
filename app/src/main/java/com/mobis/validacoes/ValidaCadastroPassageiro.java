package com.mobis.validacoes;

import static com.mobis.enumeradores.EnumCampos.*;
import com.mobis.enumeradores.EnumCampos;

public class ValidaCadastroPassageiro {
    public static EnumCampos validaCamposPreenchidos(String nome) {
        return nome.trim().isEmpty() ? CAMPO_NOME : CAMPO_VALIDO;
    }
}
