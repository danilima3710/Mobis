package com.mobis.apresentaMensagem;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class ApresentaMensagem {

    public static void ApresentaMensagemRapida(View view, String mensagem) {
        Snackbar snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(Color.WHITE);
        snackbar.setTextColor(Color.BLACK);
        snackbar.show();
    }
}
