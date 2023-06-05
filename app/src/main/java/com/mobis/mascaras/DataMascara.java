package com.mobis.mascaras;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataMascara implements TextWatcher {
    private final EditText editText;
    private String previaTexto;

    public DataMascara(EditText editText) {
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
        String formattedText = formatDate(text);

        editText.setText(formattedText);
        editText.setSelection(formattedText.length());

        editText.addTextChangedListener(this);
    }

    private String formatDate(String text) {

        if (text.length() < previaTexto.length())
            return text;

        int textLength = text.length();

        if (textLength == 2)
            text = text.concat("/");
        else if (textLength == 5)
            text = text.concat("/");
        else if (textLength >= 10)
            return text.substring(0, 10);

        return text;
    }

    public static boolean isDateTimeValid(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static Date convertStringToDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(date);
    }
}
