package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.myapplication.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {

    Button btnLogin,btnSignup;
    FirebaseAuth auth;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference users = db.getReference("Users");
    RelativeLayout root;
    RelativeLayout page2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        root = findViewById(R.id.root_element);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginWindow();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterWindow();
            }
        });

    }

    private void showLoginWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Log In");
        dialog.setMessage("input email and password");

        LayoutInflater inflater = LayoutInflater.from(this);
        View log_in_window = inflater.inflate(R.layout.log_in_window, null);
        dialog.setView(log_in_window);

        final MaterialEditText email = log_in_window.findViewById(R.id.emailField);
        final MaterialEditText password = log_in_window.findViewById(R.id.passwordField);


        dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Log in", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                if (TextUtils.isEmpty(email.getText().toString())){

                    Snackbar.make(root, "input your email", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().length()<8){

                    Snackbar.make(root, "input your password which more then 8 symbols", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        startActivity(new Intent(MainActivity.this, SecondActivity.class));

                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, "incorect authorization. " + e.getMessage(),Snackbar.LENGTH_LONG).show();
                    }
                });





            }
        });

        dialog.show();
    }

    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Sign Up");
        dialog.setMessage("input all information");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_window = inflater.inflate(R.layout.register_window, null);
        dialog.setView(register_window);

        final MaterialEditText email = register_window.findViewById(R.id.emailField);
        final MaterialEditText password = register_window.findViewById(R.id.passwordField);
        final MaterialEditText phone = register_window.findViewById(R.id.phoneField);
        final MaterialEditText fname = register_window.findViewById(R.id.fNameField);
        final MaterialEditText sname = register_window.findViewById(R.id.sNameField);

        dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Sign up", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                if (TextUtils.isEmpty(email.getText().toString())){

                    Snackbar.make(root, "input your email", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (email.getText().toString().matches("@"))
                {
                    Snackbar.make(root, "imput @ sign", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (password.getText().toString().length()<8){

                    Snackbar.make(root, "input your password which more then 8 symbols", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone.getText().toString())){

                    Snackbar.make(root, "input your phone", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(fname.getText().toString())){

                    Snackbar.make(root, "input your first name", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sname.getText().toString())){

                    Snackbar.make(root, "input your second name", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user = new User();
                                user.setEmail(email.getText().toString());
                                user.setPassword(password.getText().toString());
                                user.setPhone(phone.getText().toString());
                                user.setFname(fname.getText().toString());
                                user.setSname(sname.getText().toString());

                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Snackbar.make(root, " User added", Snackbar.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(root, "User is not register. " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                        });

            }
        });

        dialog.show();
    }
}
