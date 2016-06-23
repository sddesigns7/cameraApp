package mobapptut.com.zambelislightscamapp;

/**
 * Created by sylvia on 4/14/2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import zambelislights.com.cameraapp.R;

public class CategoryMain extends Activity {

    ArrayList<Actors> actorsList;

    ActorsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_cat);
        actorsList = new ArrayList<Actors>();
        //new JSONAsyncTask().execute("http://silviadwomoh.com/and/jsonActors.json.json");
        //new JSONAsyncTask().execute("http://www.zambelislights.gr/wp/lvapp/");
        new JSONAsyncTask().execute("http://silviadwomoh.com/and/zam-sample.json");

        GridView listview = (GridView)findViewById(R.id.list);
        adapter = new ActorsAdapter(getApplicationContext(), R.layout.row_cat, actorsList);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), actorsList.get(position).getImage(), Toast.LENGTH_LONG).show();


                String getImage = actorsList.get(position).getImage();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                   // Toast.makeText(getApplicationContext(), "CatMain-above", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(CategoryMain.this, zambelislightscamapp.class);
                    myIntent.putExtra("testintent", getImage);
                    startActivity(myIntent);
                    finish();

                } else {
                    //Toast.makeText(getApplicationContext(), "CatMain-bellow", Toast.LENGTH_SHORT).show();
                    Intent myIntent1 = new Intent(CategoryMain.this, bellowLollipop.class);
                    myIntent1.putExtra("testintent", getImage);
                    startActivity(myIntent1);
                    finish();

                }
            }
        });
        Intent myIntent1 = new Intent(CategoryMain.this, CategoryMain.class);
        Bundle extras1 = getIntent().getExtras();
        String value1 = extras1.getString("FinalCategory");
 //        Toast.makeText(getApplicationContext(), "yes", Toast.LENGTH_SHORT).show();
        myIntent1.putExtra("FinalCategory1", value1);
    }


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(CategoryMain.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject jsono = new JSONObject(data);
                    //JSONArray jarray = jsono.getJSONArray("actors");


                    String thisis = "ΠΟΛΥΦΩΤΑ - ΚΡΕΜΑΣΤΑ";
                    JSONArray jarray = jsono.getJSONArray(thisis);

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Actors actor = new Actors();

//                        actor.setName(object.getString("name"));
//                        actor.setDescription(object.getString("description"));
//                        actor.setDob(object.getString("dob"));
//                        actor.setCountry(object.getString("country"));
//                        actor.setHeight(object.getString("height"));
//                        actor.setSpouse(object.getString("spouse"));
//                        actor.setChildren(object.getString("children"));
                        actor.setImage(object.getString("image"));

                        actorsList.add(actor);
                    }
                    return true;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }}