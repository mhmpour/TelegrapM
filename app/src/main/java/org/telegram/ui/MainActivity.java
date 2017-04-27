package org.telegram.ui;

import android.app.ListActivity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.telegram.messenger.R;
import org.telegram.ui.model.Flower;
import org.telegram.ui.parsers.FlowerJSONParser;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {


    TextView output;
    ProgressBar pb;
    List<MyTask> tasks;

    List<Flower> flowerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();


        if (isOnline()) {
          //  requestData("http://services.hanselandpetal.com/feeds/flowers.xml");
            requestData("http://www.aparat.com/etc/api/lastvideos");

        } else {
            Toast.makeText(this, "لطفا به اینترنت وصل شوید", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*if (item.getItemId() == R.id.action_get_data) {
            if (isOnline()) {
                requestData("http://services.hanselandpetal.com/secure/flowers.json");
            } else {
                Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
            }
        }*/
        return false;
    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);
    }

    protected void updateDisplay() {
        //Use FlowerAdapter to display data


        ListView ListVideo = getListView();
        FlowerAdapter adapter = new FlowerAdapter(this, R.layout.item_flower, flowerList);
        setListAdapter(adapter);
        ListVideo.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position,long id){

                final Flower ItemSelected = (Flower) parent
                        .getItemAtPosition(position);

                Intent nextScreen = new Intent(getApplicationContext(), LaunchActivity.class);
                nextScreen.putExtra("VideoTitle",ItemSelected.getName());
                nextScreen.putExtra("Link",ItemSelected.getLink());
                nextScreen.putExtra("VideoLink",ItemSelected.getLinkvideo());
               // nextScreen.putExtra("Photo",ItemSelected.getBitmap());
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                nMgr.cancel(1);
                nextScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(nextScreen);
            }
        });

    }



    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private class MyTask extends AsyncTask<String, String, List<Flower>> {

        @Override
        protected void onPreExecute() {
            if (tasks.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected List<Flower> doInBackground(String... params) {

            //String content = HttpManager.getData(params[0], "feeduser", "feedpassword");
            String content = HttpManager.getData(params[0]);
            flowerList = FlowerJSONParser.parseFeed(content);
            //flowerList = FlowerXMLParser.parseFeed(content);

            return flowerList;
        }

        @Override
        protected void onPostExecute(List<Flower> result) {

            tasks.remove(this);
            if (tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }

            if (result == null) {
                Toast.makeText(MainActivity.this, "Web service not available", Toast.LENGTH_LONG).show();
                return;
            }

            flowerList = result;
            updateDisplay();

        }

    }

}