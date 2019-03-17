package multilevel.multilevelmarkitning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import multilevel.multilevelmarkitning.Admin.Admin_Login;
import multilevel.multilevelmarkitning.Admin.Admin_Register;
import multilevel.multilevelmarkitning.Customer.Customer_Login;
import multilevel.multilevelmarkitning.User.User_Login;
import multilevel.multilevelmarkitning.User.User_Register;

public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    Toast backToast;

    @Override
    public void onBackPressed() {

        if(backPressedTime+2000>System.currentTimeMillis())
        {

            backToast.cancel();
            super.onBackPressed();
            //finish();
            return;

        }

        else
        {

            backToast=Toast.makeText(getApplicationContext(),"Please back to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime=System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void Admin(View view) {
        startActivity(new Intent(this, Admin_Login.class));
    }

    public void User(View view) {

        startActivity(new Intent(this,User_Login.class));
    }

    public void Customer(View view) {
        startActivity(new Intent(this, Customer_Login.class));
    }
}

