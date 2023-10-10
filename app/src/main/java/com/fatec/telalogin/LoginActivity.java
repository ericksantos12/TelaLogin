package com.fatec.telalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailTextView, senhaTextView;
    private TextView criarTextView, recuperarTextView;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        // Pega as views
        emailTextView = findViewById(R.id.textEmail);
        senhaTextView = findViewById(R.id.textSenha);
        criarTextView = findViewById(R.id.linkCriarConta);
        btnLogin = findViewById(R.id.btnEntrar);
        recuperarTextView = findViewById(R.id.textRecuperarSenha);

        // Seta onClick listener no botao
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { loginEmailPassword(); }
        });

        criarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        recuperarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recoverPassword();
            }
        });
    }

    private void loginEmailPassword() {
        // pega os valores em strings
        String email, senha;
        email = emailTextView.getText().toString();
        senha = senhaTextView.getText().toString();

        // loga o usuario
        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Logado com Sucesso",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Login Falhou", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void recoverPassword(){
        String email = emailTextView.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Insira o email", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Email enviado com sucesso", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao enviar o email", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}