package com.mobis.trataError;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class TrataErroLogin {
    public static String getMessagemErro(Task<AuthResult> task) {
        try {
            throw task.getException();
        } catch (FirebaseAuthWeakPasswordException e) {
            return "A senha deve conter mais que 6 caracteres!";
        } catch (FirebaseAuthUserCollisionException e) {
            return "O email informado já está sendo utilizado!";
        } catch (FirebaseAuthInvalidCredentialsException e) {
            return "O Email é inválido!";
        } catch (Exception e) {
            return "Erro ao criar conta!";
        }
    }
}
