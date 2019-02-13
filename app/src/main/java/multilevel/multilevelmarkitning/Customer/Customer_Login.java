package multilevel.multilevelmarkitning.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import multilevel.multilevelmarkitning.R;

public class Customer_Login extends AppCompatActivity {

    EditText userid;
    EditText password;
    Button loginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__login);
        userid=(EditText)findViewById(R.id.cus_login_id);
        password=(EditText)findViewById(R.id.cus_login_password);
        loginbtn=(Button) findViewById(R.id.cus_login_btn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserID=userid.getText().toString().trim();
                String Password=password.getText().toString().trim();
                if(TextUtils.isEmpty(UserID) || TextUtils.isEmpty(Password))
                {
                    userid.setError("please fill the fields");
                    password.setError("please fill the fields");
                }
                else
                {
                    if(Password.length()<6)
                    {
                        password.setError("Minimum 6 lengths required");
                    }
                    Intent intent=new Intent(getApplicationContext(),Customer_Home_Page.class);
                    startActivity(intent);
                }

            }
        });

    }

//    public void CustomerLogin_Submit(View view) {
//
//        startActivity(new Intent(this,Customer_Home_Page.class));
//    }
}
