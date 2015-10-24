package com.tomaskostadinov.activegermany;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public ArrayList<Activity> items;
    OverviewAdapter adapter;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorAccent));
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
                //myIntent.putExtra("id", id); //Optional parameters
                MainActivity.this.startActivity(addIntent);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        items = new ArrayList<>();

        RecyclerView rvUsers = (RecyclerView) findViewById(R.id.rvActivities);
        adapter = new OverviewAdapter(this, getActivities());
        rvUsers.setAdapter(adapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        downloadData();
    }

    public void downloadData(){

        /**
         * Start JSON data download
         */
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://raw.githubusercontent.com/MaschiniDev/Active-Germany/master/data/activities.json",
                new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                String in = new String(response);
                ParseData(in);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    public void ParseData(String in) {
        try {
            JSONObject object = new JSONObject(in);
            JSONArray activities = object.getJSONArray("activities");

            for (int i = 0; i < activities.length(); i++) {

                JSONObject j = (JSONObject) activities.get(i);
                String tit = j.getString("titel");
                String besch =j.getString("beschreibung");
                String bild = j.getString("bild");

                AddData(bild, besch, tit);
                adapter.notifyItemInserted(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("JSON Parsing", e.toString());
        }
    }

    private ArrayList<Activity> getActivities() {
        return items;
    }

    public void AddData(String img_url, String desc, String title) {
        items.add(new Activity(img_url, desc, title, "10"));
        adapter.notifyItemInserted(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    Integer b = 0;
    private android.os.Handler mHandler = new android.os.Handler();

    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            b = 0;
        } else {
            b = b + 1;
            if(b == 2){
                finish();
            }   else if(b == 1){
                Toast.makeText(getBaseContext(), "Zum Beenden erneut drücken", Toast.LENGTH_LONG).show();
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    b = 0;
                }
            }, 2000);
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.feed) {

        } else if (id == R.id.map) {
            Intent addIntent = new Intent(MainActivity.this, MapsActivity.class);
            //myIntent.putExtra("id", id); //Optional parameters
            MainActivity.this.startActivity(addIntent);
        } else if (id == R.id.category) {
            Intent addIntent = new Intent(MainActivity.this, CategoryActivity.class);
            //myIntent.putExtra("id", id); //Optional parameters
            MainActivity.this.startActivity(addIntent);
        } else if (id == R.id.filter) {
            Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
            //myIntent.putExtra("id", id); //Optional parameters
            MainActivity.this.startActivity(addIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
