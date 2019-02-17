package multilevel.multilevelmarkitning.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import multilevel.multilevelmarkitning.Customer.Customer_Home_Page;
import multilevel.multilevelmarkitning.R;

import static android.text.TextUtils.*;

public class User_Login extends AppCompatActivity {
    EditText username;
    EditText userpassword;
    TextView usersigup;
    Button userbtn;
    Intent intent;
    String usrid;
    CheckBox checkBox;
    ProgressDialog progressDialog;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__login);
        intent=this.getIntent();
        username=(EditText)findViewById(R.id.user_userid);
        checkBox=(CheckBox)findViewById(R.id.user_checkBox);
        progressDialog=new ProgressDialog(this);
        if(intent!=null) {
            usrid = intent.getStringExtra("userid");
            //Toast.makeText(getApplicationContext(),usrid,Toast.LENGTH_SHORT).show();
            username.setText(usrid);
        }
        userpassword=(EditText)findViewById(R.id.user_userpassword);
        usersigup=(TextView)findViewById(R.id.user_usersignup);
        userbtn=(Button)findViewById(R.id.user_usersubmit);
        loginPreferences=getSharedPreferences("userLogin",MODE_PRIVATE);
        loginPrefsEditor=loginPreferences.edit();

        username.setText(loginPreferences.getString("username",null));
        userpassword.setText(loginPreferences.getString("password",null));



        usersigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),User_Register.class));
            }
        });


        userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserID=username.getText().toString().trim();
                String Password=userpassword.getText().toString().trim();
                if(TextUtils.isEmpty(UserID) )
                {
                    username.setError("please fill the fields");
                    userpassword.setError("please fill the fields");
                }
                else if(TextUtils.isEmpty(Password))
                {
                    userpassword.setError("please fill the fields");
                }
                else
                {
                    if(checkBox.isChecked())
                    {
                        loginPrefsEditor.putString("username",UserID);
                        loginPrefsEditor.putString("password",Password);
                        loginPrefsEditor.commit();
                    }
                    if(Password.length()<6)
                    {
                        userpassword.setError("Minimum 6 lengths required");
                    }
                    else
                    {
                        progressDialog.setTitle("Processing...");
                        progressDialog.setMessage("Please wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(true);
                        progressDialog.show();
                    }
                    Intent intent=new Intent(getApplicationContext(),User_Show.class);
                    startActivity(intent);
                }
            }
        });
    }
}

