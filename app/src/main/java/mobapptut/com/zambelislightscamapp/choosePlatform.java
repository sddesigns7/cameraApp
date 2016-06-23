package mobapptut.com.zambelislightscamapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import zambelislights.com.cameraapp.R;

public class choosePlatform extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Toast.makeText(getApplicationContext(), "03-ChoosePlat", Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            //Toast.makeText(getApplicationContext(), "choose platform: Above 4.0", Toast.LENGTH_SHORT).show();

            if(getIntent().getData()!=null) {


                Uri data = getIntent().getData();//set a variable for the Intent
                String scheme = data.toString();//get the scheme (http,https)

                //DownloadImageWithURLTask downloadTask1 = new DownloadImageWithURLTask(bindImage);
                //downloadTask1.execute(scheme);
//SILVIA_TOAST    Toast.makeText(getApplicationContext(), "YEP!GOT IT!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), scheme, Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(choosePlatform.this, zambelislightscamapp.class);
                myIntent.putExtra("testintent", scheme);

                Bundle extras = getIntent().getExtras();
                String value = extras.getString("catNameIntent");
               // Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
                myIntent.putExtra("FinalCategory", "test!");


                // start this activity
                startActivity(myIntent);
                // close this activity
                finish();

            } else {
                //Toast.makeText(getApplicationContext(), "choosePlatform Higher than KITKAT", Toast.LENGTH_SHORT).show();
                //Intent myIntent = new Intent(choosePlatform.this, noImageFound.class);
                Intent myIntent = new Intent(choosePlatform.this, ProductsMain.class);
                Bundle extras = getIntent().getExtras();
                String value = extras.getString("catNameIntent");
                //Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
                myIntent.putExtra("FinalCategory", value);
                startActivity(myIntent);
                // close this activity
                finish();

            }




        }else
        {
            //Toast.makeText(getApplicationContext(), "choose platform: Below 4.0", Toast.LENGTH_SHORT).show();



            // **IF INTENT IS NOT EMPTY
            if(getIntent().getData()!=null) {


                Uri data = getIntent().getData();//set a variable for the Intent
                String idi = getIntent().getStringExtra("id");
                String scheme = data.toString();//get the scheme (http,https)

                //DownloadImageWithURLTask downloadTask1 = new DownloadImageWithURLTask(bindImage);
                //downloadTask1.execute(scheme);

//SILVIA_TOAST                Toast.makeText(getApplicationContext(), "YEP!GOT IT!", Toast.LENGTH_SHORT).show();
//SILVIA_TOAST                Toast.makeText(getApplicationContext(), idi, Toast.LENGTH_SHORT).show();

                Intent myIntent1 = new Intent(choosePlatform.this, bellowLollipop.class);
                myIntent1.putExtra("testintent", scheme);

                // start this activity
                startActivity(myIntent1);
                // close this activity
                finish();

            }else{
                //SILVIA_TOAST               Toast.makeText(getApplicationContext(), "NOPE!!!!", Toast.LENGTH_SHORT).show();
               // Intent myIntent1 = new Intent(choosePlatform.this, noImageFound.class);


                Intent myIntent1 = new Intent(choosePlatform.this, ProductsMain.class);

                String yess ="ΠΟΛΥΦΩΤΑ - ΚΡΕΜΑΣΤΑ";
                Bundle extras = getIntent().getExtras();
                String value = extras.getString("catNameIntent");
                //Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
                myIntent1.putExtra("FinalCategory", value);
                startActivity(myIntent1);

                // close this activity
                finish();
            }






        }
    }
}
