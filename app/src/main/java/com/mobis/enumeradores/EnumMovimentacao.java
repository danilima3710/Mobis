package com.mobis.enumeradores;

import static com.mobis.enumeradores.EnumMovimentacao.TipoMovimentacao.*;

public class EnumMovimentacao {
    public enum TipoMovimentacao {
        ENTRADA,
        SAIDA,
        NAO_RESPONDER
    }

    public static TipoMovimentacao stringToEnum(String opcao) {
        switch (opcao) {
            case "Entrada": return ENTRADA;
            case "Saída"  : return SAIDA;
            default       : return NAO_RESPONDER;
        }
    }
}
