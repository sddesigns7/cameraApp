package mobapptut.com.zambelislightscamapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import zambelislights.com.cameraapp.R;

public class CategoryActivity extends Activity {

    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button10;
    private Button button11;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_screen);
       // Toast.makeText(getApplicationContext(), "02-CatAct", Toast.LENGTH_SHORT).show();
//        switch (catName){
//
//            case "ΑΝΕΜΙΣΤΗΡΕΣ":
//                Toast.makeText(getApplicationContext(), "case1", Toast.LENGTH_SHORT).show();
//                button2 = (Button) findViewById(R.id.button2);
//                break;
//            case "ΠΟΛΥΦΩΤΑ - ΚΡΕΜΑΣΤΑ":
//                Toast.makeText(getApplicationContext(), "case2", Toast.LENGTH_SHORT).show();
//                button3 = (Button) findViewById(R.id.button3);
//                break;
//        }

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent catIntent = new Intent(CategoryActivity.this, choosePlatform.class);
                String catName = "ΑΝΕΜΙΣΤΗΡΕΣ";

                catIntent.putExtra("catNameIntent", catName);
                //myIntent1.putExtra("testintent", scheme);
                // start this activity
                startActivity(catIntent);
                // close this activity
                finish();

            }
        });

        // ------------------------

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(), "3 - ΑΠΛΙΚΕΣ", Toast.LENGTH_SHORT).show();

                Intent catIntent = new Intent(CategoryActivity.this, choosePlatform.class);
                String catName = "ΑΠΛΙΚΕΣ";
                catIntent.putExtra("catNameIntent", catName);
                //Toast.makeText(getApplicationContext(), catName, Toast.LENGTH_SHORT).show();
                // start this activity
                startActivity(catIntent);
                // close this activity
                finish();

            }
        });

// ------------------------

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(), "4 - ΦΩΤΙΣΤΙΚΑ ΔΑΠΕΔΟΥ", Toast.LENGTH_SHORT).show();

                Intent catIntent = new Intent(CategoryActivity.this, choosePlatform.class);
                String catName = "ΦΩΤΙΣΤΙΚΑ ΔΑΠΕΔΟΥ";
                catIntent.putExtra("catNameIntent", catName);
                //Toast.makeText(getApplicationContext(), catName, Toast.LENGTH_SHORT).show();
                // start this activity
                startActivity(catIntent);
                // close this activity
                finish();

            }
        });

// ------------------------

        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(), "5 - ΕΠΙΤΡΑΠΕΖΙΑ ΦΩΤΙΣΤΙΚΑ", Toast.LENGTH_SHORT).show();

                Intent catIntent = new Intent(CategoryActivity.this, choosePlatform.class);
                String catName = "ΕΠΙΤΡΑΠΕΖΙΑ ΦΩΤΙΣΤΙΚΑ";
                catIntent.putExtra("catNameIntent", catName);
                //Toast.makeText(getApplicationContext(), catName, Toast.LENGTH_SHORT).show();
                // start this activity
                startActivity(catIntent);
                // close this activity
                finish();

            }
        });

// ------------------------

        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(), "6 - ΦΩΤΙΣΤΙΚΑ LED", Toast.LENGTH_SHORT).show();

                Intent catIntent = new Intent(CategoryActivity.this, choosePlatform.class);
                String catName = "ΦΩΤΙΣΤΙΚΑ LED";
                catIntent.putExtra("catNameIntent", catName);
                //Toast.makeText(getApplicationContext(), catName, Toast.LENGTH_SHORT).show();
                // start this activity
                startActivity(catIntent);
                // close this activity
                finish();

            }
        });

// ------------------------

        button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(), "7 - ΜΟΝΟΦΩΤΑ", Toast.LENGTH_SHORT).show();

                Intent catIntent = new Intent(CategoryActivity.this, choosePlatform.class);
                String catName = "ΜΟΝΟΦΩΤΑ";
                catIntent.putExtra("catNameIntent", catName);
                //Toast.makeText(getApplicationContext(), catName, Toast.LENGTH_SHORT).show();
                // start this activity
                startActivity(catIntent);
                // close this activity
                finish();

            }
        });

// ------------------------

        button8 = (Button) findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(), "8 - ΠΛΑΦΟΝΙΕΡΕΣ", Toast.LENGTH_SHORT).show();

                Intent catIntent = new Intent(CategoryActivity.this, choosePlatform.class);
                String catName = "ΠΛΑΦΟΝΙΕΡΕΣ";
                catIntent.putExtra("catNameIntent", catName);
                //Toast.makeText(getApplicationContext(), catName, Toast.LENGTH_SHORT).show();
                // start this activity
                startActivity(catIntent);
                // close this activity
                finish();

            }
        });

// ------------------------

        button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(), "9 - ΠΟΛΥΕΛΑΙΟΙ", Toast.LENGTH_SHORT).show();

                Intent catIntent = new Intent(CategoryActivity.this, choosePlatform.class);
                String catName = "ΠΟΛΥΕΛΑΙΟΙ";
                catIntent.putExtra("catNameIntent", catName);
                //Toast.makeText(getApplicationContext(), catName, Toast.LENGTH_SHORT).show();
                // start this activity
                startActivity(catIntent);
                // close this activity
                finish();

            }
        });

        // ------------------------

        button10 = (Button) findViewById(R.id.button10);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getApplicationContext(), "10 - ΠΟΛΥΦΩΤΑ - ΚΡΕΜΑΣΤΑ", Toast.LENGTH_SHORT).show();

                Intent catIntent = new Intent(CategoryActivity.this, choosePlatform.class);
                String catName = "ΠΟΛΥΦΩΤΑ - ΚΡΕΜΑΣΤΑ";
                catIntent.putExtra("catNameIntent", catName);
                //Toast.makeText(getApplicationContext(), catName, Toast.LENGTH_SHORT).show();
                // start this activity
                startActivity(catIntent);
                // close this activity
                finish();

            }
        });

        // ------------------------

        button11 = (Button) findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(), "11 - SPOT", Toast.LENGTH_SHORT).show();

                Intent catIntent = new Intent(CategoryActivity.this, choosePlatform.class);
                String catName = "SPOT";
                catIntent.putExtra("catNameIntent", catName);
                //Toast.makeText(getApplicationContext(), catName, Toast.LENGTH_SHORT).show();
                // start this activity
                startActivity(catIntent);
                // close this activity
                finish();

            }
        });



    }
}