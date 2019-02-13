package multilevel.multilevelmarkitning.User;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__login);
        intent=this.getIntent();
        username=(EditText)findViewById(R.id.user_userid);
        if(intent!=null) {
            usrid = intent.getStringExtra("userid");
            //Toast.makeText(getApplicationContext(),usrid,Toast.LENGTH_SHORT).show();
            username.setText(usrid);
        }
        userpassword=(EditText)findViewById(R.id.user_userpassword);
        usersigup=(TextView)findViewById(R.id.user_usersignup);
        userbtn=(Button)findViewById(R.id.user_usersubmit);



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
                if(TextUtils.isEmpty(UserID) || TextUtils.isEmpty(Password))
                {
                    username.setError("please fill the fields");
                    userpassword.setError("please fill the fields");
                }
                else
                {
                    if(Password.length()<6)
                    {
                        userpassword.setError("Minimum 6 lengths required");
                    }
                    Intent intent=new Intent(getApplicationContext(),User_Show.class);
                    startActivity(intent);
                }
            }
        });
    }
}

