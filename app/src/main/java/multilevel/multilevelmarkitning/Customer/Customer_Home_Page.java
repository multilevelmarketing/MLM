package multilevel.multilevelmarkitning.Customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import multilevel.multilevelmarkitning.R;
import multilevel.multilevelmarkitning.User.Create_Customer;

public class Customer_Home_Page extends AppCompatActivity {

     TextView Customer_Commesion,Customer_Create,Customer_Profile;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.customer_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.customer_logout)
        {
            SharedPreferences loginPreferences=getSharedPreferences("customerLogin",MODE_PRIVATE);
            SharedPreferences.Editor loginPrefsEditor=loginPreferences.edit();
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
            finish();
            startActivity(new Intent(Customer_Home_Page.this,Customer_Login.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__home__page);
        Customer_Commesion=(TextView)findViewById(R.id.Customer_comission);
        Customer_Create=(TextView)findViewById(R.id.Customer_create);
        Customer_Profile=(TextView)findViewById(R.id.cus_profile);



       Customer_Commesion.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

            startActivity(new Intent(Customer_Home_Page.this,Purchase_Commision.class));

           }
       });


       Customer_Create.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               startActivity(new Intent(Customer_Home_Page.this, customer_create_customer.class));

           }
       });

       Customer_Profile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(getApplicationContext(),Customer_show.class);
               startActivity(new Intent(intent));
           }
       });

    }
}
