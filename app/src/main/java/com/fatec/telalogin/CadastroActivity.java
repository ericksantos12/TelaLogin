package com.fatec.telalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private EditText emailTextView, senhaTextView, confSenhaTextView;
    private Button btnCadastrar;
    private FirebaseAuth mAuth;
    private CheckBox checkBoxTermos;

    private TextView termosTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_layout);

        mAuth = FirebaseAuth.getInstance();

        emailTextView = findViewById(R.id.textEmail);
        senhaTextView = findViewById(R.id.textSenha);
        confSenhaTextView = findViewById(R.id.textConfirmarSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        checkBoxTermos = findViewById(R.id.checkConfirmar);
        termosTextView = findViewById(R.id.textConfirmTermos);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });

        termosTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroActivity.this, TermosActivity.class);
                startActivity(intent);
            }
        });
    }
    private void registerNewUser(){
        String email, password, confirmPassword;

        email = emailTextView.getText().toString();
        password = senhaTextView.getText().toString();
        confirmPassword = confSenhaTextView.getText().toString();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "As duas senhas não são iguais", Toast.LENGTH_LONG).show();
            return;
        }

        if (!checkBoxTermos.isChecked()) {
            Toast.makeText(getApplicationContext(), "Concorde com os termos de uso", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "O Cadastro foi completo", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Cadastro falhou, tente novamente", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}