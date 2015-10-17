package com.tomaskostadinov.activegermany;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public ArrayList<Day> items;
    OverviewAdapter adapter;
    String city, CountryCode, language, unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        items = new ArrayList<>();
        downloadJSON();

            RecyclerView rvUsers = (RecyclerView) findViewById(R.id.rvUsers);
            adapter = new ForecastOverviewAdapter(this, getDays());
            rvUsers.setAdapter(adapter);
            rvUsers.setLayoutManager(new LinearLayoutManager(this));
        }

        private ArrayList<Day> getDays() {
            return items;
        }

        public void downloadJSON() {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://api.openweathermap.org/data/2.5/forecast/daily?q=", new AsyncHttpResponseHandler() {

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
                    Toast.makeText(DailyForecastActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onRetry(int retryNo) {
                    // called when request is retried
                }
            });
        }

        public void ParseData(String in) {
            try {
                JSONObject reader = new JSONObject(in);
                JSONObject city = reader.getJSONObject("city");
                JSONArray list = reader.getJSONArray("list");
                JSONObject JSONList = list.getJSONObject(0);
                for (int i = 0; i < list.length(); i++) {
                    JSONObject forJSONList = list.getJSONObject(i);
                    JSONArray forWeather = forJSONList.getJSONArray("weather");
                    JSONObject forJSONWeather = forWeather.getJSONObject(0);
                    JSONObject fortemp = forJSONList.getJSONObject("temp");
                    Log.i("RecyclerView", "JSON Parsing Nr." + i);
                    items.add(new Day(forJSONList.getInt("dt"), fortemp.getDouble("max"), fortemp.getDouble("min"), forJSONList.getDouble("pressure"), forJSONList.getInt("humidity"), forJSONWeather.getInt("id"), forJSONWeather.getString("description"), forJSONList.getDouble("speed")));
                    Log.i("RecyclerView", "Added items Nr" + i);
                    adapter.notifyItemInserted(0);
                    Log.i("RecyclerView", "notifyItemInserted Nr." + i);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("JSON Parsing", e.toString());
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
