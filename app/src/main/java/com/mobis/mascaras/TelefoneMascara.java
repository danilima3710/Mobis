package com.mobis.mascaras;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TelefoneMascara implements TextWatcher {

    private final EditText editText;
    private String previaTexto;

    public TelefoneMascara(EditText editText) {
        this.editText = editText;
        previaTexto = "";
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        previaTexto = charSequence.toString();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        editText.removeTextChangedListener(this);

        String text = editable.toString();
        String formattedText = formatPhoneNumber(text);

        editText.setText(formattedText);
        editText.setSelection(formattedText.length());

        editText.addTextChangedListener(this);
    }

    private String formatPhoneNumber(String text) {

        StringBuilder numero = new StringBuilder(text);

        if (text.length() < previaTexto.length()) {
            if (text.length() == 14) {
                numero.deleteCharAt(10);
                numero.insert(9, '-');

                return numero.toString();
            }

            return numero.toString();
        }

        int textLength = text.length();

        if (textLength == 2) {
            numero.insert(0, '(');
            numero.append(") ");
        } else if (textLength == 9) {
            numero.append('-');
        } else if (textLength == 15) {
            numero.deleteCharAt(9);
            numero.insert(10, '-');
        }else if (textLength >= 15)
            return text.substring(0, 14);

        return numero.toString();
    }
}
