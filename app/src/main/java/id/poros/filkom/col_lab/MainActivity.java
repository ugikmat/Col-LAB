package id.poros.filkom.col_lab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;

import id.poros.filkom.col_lab.OrgFragment;

import com.google.firebase.auth.FirebaseAuth;

import id.poros.filkom.col_lab.dummy.DummyContent;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OrgFragment.OnListFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener{

    private FirebaseAuth mAuth;

    private final String KEY_POSITION = "keyPosition";

    //TODO: Replace later
    private String fragmentTag="ORGANIZATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Col-LAB");
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

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
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initFragment(savedInstanceState);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        String tag="";

        switch (id){
            case R.id.nav_profile:
                break;
            case R.id.nav_organization:getSupportActionBar().setTitle("Organization");tag="ORGANIZATION";
                break;
            default:getSupportActionBar().setTitle("Other");tag="OTHER";
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (!tag.isEmpty())return switchFragment(tag);
        else startActivity(new Intent(this,ProfileActivity.class));
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
    }

    private void initFragment(Bundle savedInstanceState){
        if (savedInstanceState==null){
            switchFragment("ORGANIZATION");
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        fragmentTag = savedInstanceState.getString(KEY_POSITION);
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(KEY_POSITION,fragmentTag);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private Boolean switchFragment(String tag){
        fragmentTag = tag;
        Fragment fragment = findFragment();
        if (fragment.isAdded()) return false;
        detachFragment();
        attachFragment(fragment,tag);
        getSupportFragmentManager().executePendingTransactions();
        return true;
    }

    private void detachFragment(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if(fragment!=null)getSupportFragmentManager().beginTransaction().detach(fragment).commit();
    }
    private void attachFragment(Fragment fragment, String tag){
        if(fragment.isDetached()){
            getSupportFragmentManager().beginTransaction().attach(fragment).commit();
        }else{
            getSupportFragmentManager().beginTransaction().add(R.id.container,fragment,tag).commit();
        }
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private Fragment findFragment(){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
        if (fragment==null) fragment = createFragment();
        return fragment;
    }

    private Fragment createFragment(){
        Fragment fragment;
        switch (fragmentTag){
//            case "PROFILE": fragment = new ProfileFragment();
//                break;
            case "ORGANIZATION": fragment = new OrgFragment();
                break;
            default: fragment = new ProfileFragment();
        }
        return fragment;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
