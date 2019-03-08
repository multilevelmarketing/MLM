package multilevel.multilevelmarkitning.Customer;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import multilevel.multilevelmarkitning.R;

public class Purchase_Commision extends AppCompatActivity {

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase__commision);
        actionBar=getSupportActionBar();
        actionBar.setTitle("Purchase and commission");
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
}
