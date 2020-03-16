package com.dileep.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    EditText signupmail,signuppassword,confirmpass;
    TextInputLayout emailLayout,passwordLayout,confirmLayout;
    Button signupbtn;
    TextView txtalready_user,txtBack;
    String cid="",cpass="";
    FirebaseAuth firebaseAuth;
    ProgressDialog login_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupmail=(EditText)findViewById(R.id.signupmail);
        signuppassword=(EditText)findViewById(R.id.signuppassword);
        confirmpass=(EditText)findViewById(R.id.confirmPassword);
        signupbtn=(Button)findViewById(R.id.btnsignUpBtn);
        txtalready_user=(TextView)findViewById(R.id.txtalready_user);
        txtBack=(TextView)findViewById(R.id.txtBack);
        emailLayout=(TextInputLayout)findViewById(R.id.emaillayout);
        passwordLayout=(TextInputLayout)findViewById(R.id.passwordLayout);
        confirmLayout=(TextInputLayout)findViewById(R.id.confirmlayout);

        login_progress=new ProgressDialog(Signup.this);
        firebaseAuth= FirebaseAuth.getInstance();


        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txtalready_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeintent=new Intent(Signup.this,Login.class);
                homeintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(homeintent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_up);
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_data();
            }
        });
    }

    //validation for all values

    private boolean validateName() {
        String emailInput = signupmail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            emailLayout.setError("Field can't be empty");
            return false;
        } else {
            emailLayout.setError(null);
            return true;
        }
    }
    private boolean validatePass() {
        String emailInput = signuppassword.getText().toString().trim();

        if (emailInput.isEmpty()) {
            passwordLayout.setError("Field can't be empty");
            return false;
        } else {
            passwordLayout.setError(null);
            return true;
        }
    }
    private boolean validateConfirm() {
        String emailInput = confirmpass.getText().toString().trim();

        if (emailInput.isEmpty()) {
            confirmLayout.setError("Field can't be empty");
            return false;
        } else if (!confirmpass.getText().toString().equalsIgnoreCase(signuppassword.getText().toString())){
            confirmLayout.setError("Password did not matched..! ");
            return false;
        }
        else {
            confirmLayout.setError(null);
            return true;
        }
    }

    private void validate_data() {

        if (!validateName() | !validatePass() | !validateConfirm()) {
            return;
        } else {
            cid = signupmail.getText().toString();
            cpass = signuppassword.getText().toString();
            login_progress.setMessage("please wait...");
            login_progress.setCancelable(false);
            login_progress.show();
            firebaseAuth.createUserWithEmailAndPassword(cid, cpass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        login_progress.dismiss();
                        Toast.makeText(Signup.this, "User successfully created", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(Signup.this, MainActivity.class);
                        startActivity(login);
                        finish();
                    } else {
                        login_progress.dismiss();
                        Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            });

        }
    }

}
