package com.dileep.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    ProgressDialog login_progress;
    FirebaseAuth firebaseAuth;
    EditText logimMail,loginPassword;
    TextView createAccount;
    Button loginButton;
    CoordinatorLayout root;
    TextInputLayout textinputemail,textinputpassword;
    String cid="",cpass="";

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentuser=firebaseAuth.getCurrentUser();
        if(currentuser==null){
         /*   Intent login=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(login);
            finish();*/
        }else{
            Intent homeintent=new Intent(Login.this,MainActivity.class);
            homeintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(homeintent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_up);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        logimMail=(EditText)findViewById(R.id.loginmail);
        loginPassword=(EditText)findViewById(R.id.loginpassword);
        createAccount=(TextView)findViewById(R.id.createAccount);
        loginButton=(Button)findViewById(R.id.loginBtn);
        textinputemail=(TextInputLayout)findViewById(R.id.textinputemail);
        textinputpassword=(TextInputLayout)findViewById(R.id.textinputpassword);
        login_progress=new ProgressDialog(Login.this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate_data();

            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signup=new Intent(Login.this,Signup.class);
                startActivity(signup);
            }
        });

    }
    private boolean validateName() {
        String emailInput = logimMail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            textinputemail.setError("Field can't be empty");
            return false;
        } else {
            textinputemail.setError(null);
            return true;
        }
    }
    private boolean validatePass() {
        String emailInput = loginPassword.getText().toString().trim();

        if (emailInput.isEmpty()) {
            textinputpassword.setError("Field can't be empty");
            return false;
        } else {
            textinputpassword.setError(null);
            return true;
        }
    }
    private void validate_data() {

        if(!validateName() | !validatePass()){
            return;
        }else {
            cid=logimMail.getText().toString();
            cpass=loginPassword.getText().toString();
            login_progress.setMessage("please wait...");
            login_progress.setCancelable(false);
            login_progress.show();

            firebaseAuth.signInWithEmailAndPassword(cid,cpass).addOnCompleteListener(Login.this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        login_progress.dismiss();
                        Intent home=new Intent(Login.this,MainActivity.class);
                        home.putExtra("id",cid);
                        home.putExtra("passwd",cpass);
                        startActivity(home);

                    }
                    else{
                        login_progress.dismiss();
                        Toast.makeText(Login.this, "Login is incorrect", Toast.LENGTH_SHORT).show();
                      /*  Snackbar snak=Snackbar.make(root,"Login is inCorrect",Snackbar.LENGTH_LONG);
                        snak.setAction("REGISTER", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent singup=new Intent(MainActivity.this,Signup.class);
                                startActivity(singup);
                            }
                        });
                        snak.show();*/

                    }
                }
            });
/*            if(cid.equals(emailid)&&cpass.equals(pass)){
                //Toast.makeText(getApplicationContext(),"Login is Correct",Toast.LENGTH_SHORT).show();


            Intent home=new Intent(MainActivity.this,Home.class);
               home.putExtra("id",cid);
               home.putExtra("passwd",cpass);
                startActivity(home);

            }
            else{
                //here snak bar comes
                //Toast.makeText(getApplicationContext(),"Login is inCorrect"+cid+" "+cpass+" "+emailid+" "+pass,Toast.LENGTH_SHORT).show();
                Snackbar snak=Snackbar.make(root,"Login is inCorrect",Snackbar.LENGTH_LONG);
                snak.setAction("REGISTER", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singup=new Intent(MainActivity.this,Signup.class);
                        startActivity(singup);
                    }
                });
                snak.show();

            }*/

        }
    }
}
