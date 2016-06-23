package mobapptut.com.zambelislightscamapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import zambelislights.com.cameraapp.R;


public class noImageFound extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_image_found);

    }

    public void goToSu (View view) {
//        goToUrl("http://www.zambelislights.gr/");
        goToUrl("http://www.zambelislights.gr/wp/fotistika-esoterikou-xorou/polyfota/125-135-3388-κρεμαστό-μεταλλικό-φωτιστικό/");
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}
