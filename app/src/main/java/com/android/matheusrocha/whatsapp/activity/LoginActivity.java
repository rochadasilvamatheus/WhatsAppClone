package com.android.matheusrocha.whatsapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.matheusrocha.whatsapp.R;
import com.android.matheusrocha.whatsapp.config.ConfiguracaoFirebase;
import com.android.matheusrocha.whatsapp.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText campoEmail, campoSenha;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = ConfiguracaoFirebase.getFirebaseAutenticacao();

        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);

    }

    public void logarUsuario(Usuario usuario) {

        auth.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    abrirTelaPrincipal();
                } else {

                    String excecao = "";
                    try {
                        throw task.getException();

                    } catch (FirebaseAuthInvalidUserException e) {
                        excecao = "Usuario não está cadastrado.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "E-mail e senha não correspondem a um usuário cadastrado.";
                    } catch (Exception e) {
                        excecao = "Erro ao logar" + e.getMessage();
                    }
                    Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void validarLoginUsuario(View view) {
        
        //Recuperar texto dos campos
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        //Validar se e-mail e senha foram digitados
        if (!textoEmail.isEmpty()) {//verifica e-mail
            if (!textoSenha.isEmpty()) {//verifica senha

                Usuario usuario = new Usuario();
                usuario.setEmail(textoEmail);
                usuario.setSenha(textoSenha);

                logarUsuario(usuario);
            } else {
                Toast.makeText(this, "Preencha a senha!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Preencha o e-mail!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = auth.getCurrentUser();
        if (usuarioAtual != null) {
            abrirTelaPrincipal();
        }
    }

    public void abrirTelaCadastro(View view) {
        startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
    }

    public void abrirTelaPrincipal() {
        startActivity(new Intent(this, MainActivity.class));
    }

}
