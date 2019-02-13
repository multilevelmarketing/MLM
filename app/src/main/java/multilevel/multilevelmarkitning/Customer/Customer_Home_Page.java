package multilevel.multilevelmarkitning.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import multilevel.multilevelmarkitning.R;
import multilevel.multilevelmarkitning.User.Create_Customer;

public class Customer_Home_Page extends AppCompatActivity {

     TextView Customer_Commesion,Customer_Create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__home__page);
        Customer_Commesion=(TextView)findViewById(R.id.Customer_comission);
        Customer_Create=(TextView)findViewById(R.id.Customer_create);

       Customer_Commesion.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

            startActivity(new Intent(Customer_Home_Page.this,Purchase_Commision.class));

           }
       });


       Customer_Create.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               startActivity(new Intent(Customer_Home_Page.this, Create_Customer.class));

           }
       });

    }
}
