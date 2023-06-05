package com.mobis.mascaras;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class RealMascara implements TextWatcher {
    private final EditText editText;
    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public RealMascara(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        editText.removeTextChangedListener(this);

        String text = editable.toString();
        String cleanText = text.replaceAll("[^\\d]", ""); // Remove todos os caracteres não numéricos

        try {
            long parsedValue = Long.parseLong(cleanText);
            String formattedText = currencyFormatter.format(parsedValue / 100.0); // Divide por 100 para obter os centavos

            editText.setText(formattedText);
            editText.setSelection(formattedText.length());
        } catch (NumberFormatException e) {
            editText.setText("");
        }

        editText.addTextChangedListener(this);
    }

    public static float convertStringToFloat(String text) throws ParseException {
        Number number = currencyFormatter.parse(text);

        if (number != null) {
            return number.floatValue();
        } else {
            return  0f;
        }
    }

    public static String convertFloatToString(float valor) {
        return currencyFormatter.format(valor);
    }
}
