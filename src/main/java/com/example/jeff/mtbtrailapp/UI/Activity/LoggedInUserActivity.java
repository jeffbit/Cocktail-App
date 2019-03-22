package com.example.jeff.mtbtrailapp.UI.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeff.mtbtrailapp.Adapter.DrinkAdapter;
import com.example.jeff.mtbtrailapp.Model.DrinkInfo;
import com.example.jeff.mtbtrailapp.Model.DrinkList;
import com.example.jeff.mtbtrailapp.Model.SearchTerm;
import com.example.jeff.mtbtrailapp.R;
import com.example.jeff.mtbtrailapp.Rest.GetDataService;
import com.example.jeff.mtbtrailapp.Rest.RetrofitClientInstance;
import com.example.jeff.mtbtrailapp.UI.Fragment.EditUserInfoFragment;
import com.example.jeff.mtbtrailapp.UI.Fragment.ViewUserInfoFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoggedInUserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        EditUserInfoFragment.OnFragmentInteractionListener,
        ViewUserInfoFragment.OnFragmentInteractionListener {


    public static final ViewUserInfoFragment sViewUserInfoFragment = new ViewUserInfoFragment();


    //UI
    private DrawerLayout drawer;
    private TextView emailHeaderNav;
    private ImageView imageHeaderNav;
    private Camera camera;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private DrinkAdapter drinkAdapter;
    ProgressDialog progressDialog;


    private AlertDialog.Builder searchDialog;
    private AlertDialog alertDialog;
    private FirebaseFirestore firebaseFirestore;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getDrinksBySearch("");


        loadingDialog();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        floatingActionButton = findViewById(R.id.floatingActionButtonDF);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //search function, to allow user to search for trial
                loadingDialog();
                showInputSearch();


            }
        });


    }

    //loading progress dialog

    public void loadingDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

    public void showInputSearch() {
        searchDialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.fragment_searchdialog, null);
        final EditText searchEt = view.findViewById(R.id.searchDialogET);
        TextView searchTv = view.findViewById(R.id.searchDialogTV);
        Button searchButton = view.findViewById(R.id.searchDialogBTN);

        searchDialog.setView(view);
        alertDialog = searchDialog.create();
        alertDialog.show();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                SearchTerm searchTerm = new SearchTerm();
                searchTerm.setSearchId("1");
                if (!searchEt.getText().toString().isEmpty()) {
                    String search = searchEt.getText().toString();
                    searchTerm.setSearch(search);


                    getDrinksBySearch(searchTerm.getSearch());
                    drinkAdapter.notifyDataSetChanged();
                    alertDialog.dismiss();

                }
                Map<String, String> userMap = new HashMap<>();
                userMap.put("searchTerm", searchTerm.getSearch());
                userMap.put("searchId", searchTerm.getSearchId());

                //adds a new user search term
                firebaseCall();
                firebaseFirestore.collection("users").document(userId).collection("search").document(searchTerm.getSearchId()).set(userMap);


            }
        });
    }


    private void firebaseCall() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private void generateDrinkList(ArrayList<DrinkInfo> drinkList) {
        recyclerView = findViewById(R.id.recylerViewDF);
        drinkAdapter = new DrinkAdapter(drinkList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(drinkAdapter);

    }

    public void getDrinksBySearch(String search) {
        GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<DrinkList> call = getDataService.getDrinksByName(search);
        call.enqueue(new Callback<DrinkList>() {
            @Override
            public void onResponse(Call<DrinkList> call, Response<DrinkList> response) {
                generateDrinkList(response.body().getDrinks());
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<DrinkList> call, Throwable t) {
                Toast.makeText(LoggedInUserActivity.this, "Code: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logged_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.navFavoriteDrinks) {
            Intent intent = new Intent(this, FavoriteDrinksFrameActivity.class);
            startActivity(intent);
            onBackPressed();

            return true;

        } else if (id == R.id.navProfile) {
            Intent intent = new Intent(this, UserInfoFrameActivity.class);
            startActivity(intent);
            onBackPressed();

            return true;


        } else if (id == R.id.navSignOut) {
            signOutAlert();

            return true;


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //creates alertdialog to verify user signing out
    public void signOutAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Confirm");
        alert.setMessage("Are you sure you want to sign out " + FirebaseAuth.getInstance().getCurrentUser().getEmail());

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                onBackPressed();

            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.create();
        alert.show();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                imageHeaderNav.setImageBitmap(bp);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
