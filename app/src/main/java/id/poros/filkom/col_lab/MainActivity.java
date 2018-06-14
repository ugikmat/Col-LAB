package id.poros.filkom.col_lab;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingLayout;

import id.poros.filkom.col_lab.inter.OnFragmentInteractionListener;
import id.poros.filkom.col_lab.model.AgendaContent;
import id.poros.filkom.col_lab.model.EventContent;
import id.poros.filkom.col_lab.model.OrganizationContent;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener{

    private FirebaseAuth mAuth;

    private final String KEY_POSITION = "keyPosition";

    private String fragmentTag="ORGANIZATION";

    private com.robertlevonyan.views.customfloatingactionbutton.FloatingLayout fab;
    private com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton fabJoin;
    private com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Col-LAB");
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        fabJoin = findViewById(R.id.fab_join);
        fabAdd = findViewById(R.id.fab_add);

        fab = findViewById(R.id.fab_menu);
        fab.setOnMenuExpandedListener(new FloatingLayout.OnMenuExpandedListener() {
            @Override
            public void onMenuExpanded() {

            }

            @Override
            public void onMenuCollapsed() {

            }
        });

        initNavigationDrawer(toolbar);

        initFragment(savedInstanceState);
    }

    private void initNavigationDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_agenda);
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

        Class<?> cls=null;

        switch (id){
            case R.id.nav_profile:tag="ACTIVITY"; cls = ProfileActivity.class;
                break;
            case R.id.nav_feedback:tag="ACTIVITY"; cls = FeedbackActivity.class;
                break;
            case R.id.nav_agenda:getSupportActionBar().setTitle("Agenda");tag="AGENDA";fabAdd.setText("New Agenda");
                break;
            case R.id.nav_event:getSupportActionBar().setTitle("Event");tag="EVENT";fabAdd.setText("New Event");fabJoin.setText("Join Event");
                break;
            case R.id.nav_organization:getSupportActionBar().setTitle("Organization");tag="ORGANIZATION";fabAdd.setText("New Organization");fabJoin.setText("Join Organization");
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (tag.equalsIgnoreCase("ACTIVITY"))startActivity(new Intent(this,cls));
        else if(!tag.isEmpty()) return switchFragment(tag);
        else Toast.makeText(this,"Unimplemented Yet!",Toast.LENGTH_SHORT).show();
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
            switchFragment("AGENDA");
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
            case "AGENDA": fragment = new AgendaFragment();
                break;
            case "EVENT": fragment = new EventFragment();
                break;
            case "ORGANIZATION": fragment = new OrgFragment();
                break;
            default: fragment = new Fragment();
        }
        return fragment;
    }

    @Override
    public void onListFragmentInteraction(OrganizationContent.OrganizationItem item) {

    }

    @Override
    public void onListFragmentInteraction(AgendaContent.AgendaItem item) {

    }

    @Override
    public void onListFragmentInteraction(EventContent.EventItem item) {

    }
}