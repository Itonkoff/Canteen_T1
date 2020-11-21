package com.kofu.brighton.canteen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kofu.brighton.canteen.fragments.FirstFragmentDirections;
import com.kofu.brighton.canteen.fragments.LoginFragmentDirections;
import com.kofu.brighton.canteen.models.Meal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MainActivityCallBacks {

    public static final String TOKEN_LABEL = "token";
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPreferences = getPreferences(Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void setToken(String token) {
        mEditor.putString(TOKEN_LABEL, token);
        mEditor.apply();
    }

    @Override
    public String getToken() {
        return mPreferences.getString(TOKEN_LABEL, "");
    }

    @Override
    public void hideFab() {
        if (mFab.getVisibility() == View.VISIBLE) {
            mFab.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showFab() {
        if (mFab.getVisibility() == View.INVISIBLE) {
            mFab.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void billStudent(Meal meal) {
        Navigation
                .findNavController(this.findViewById(R.id.nav_host_fragment))
                .navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment(meal.id));
    }
}