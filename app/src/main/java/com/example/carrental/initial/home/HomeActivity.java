package com.example.carrental.initial.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.carrental.R;
import com.example.carrental.initial.MainActivity;
import com.example.carrental.initial.history.HistoryFragment;
import com.example.carrental.initial.host.HostFragment;
import com.example.carrental.initial.host.HostPojo;
import com.example.carrental.initial.profile.ProfileFragment;
import com.example.carrental.initial.search.SearchFragment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView main_nav;
    FrameLayout main_frame;

    public static String back_check="home";

    private HomeFragment homeFragment;
    private HistoryFragment historyFragment;
    private SearchFragment searchFragment;
    private ProfileFragment profileFragment;
    private HostFragment hostFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        main_frame = findViewById(R.id.main_frame);
        main_nav = findViewById(R.id.main_nav);


        historyFragment = new HistoryFragment();
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        profileFragment = new ProfileFragment();
        hostFragment = new HostFragment();

        setTitle("Car Rental");
        setfragment(homeFragment);


//        switch (back_check)
//        {
//            case "history":
//                setfragment(historyFragment);
//                setTitle("History");
//                break;
//            case "host":
//                setfragment(homeFragment);
//                setTitle("Host");
//                break;
//            case "profile":
//                setfragment(profileFragment);
//                setTitle("Profile");
//                break;
//            case "search":
//                setfragment(searchFragment);
//                setTitle("Search");
//                break;
//            default:
//                setfragment(homeFragment);
//                setTitle("Car Rental");
//
//        }



        main_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.navigation_home :
                        setTitle("Car Rental");
                        setfragment(homeFragment);
                        return true;
                    case R.id.nav_history :
                        back_check="history";
                        setfragment(historyFragment);
                        setTitle("History");
                        return true;
                    case R.id.nav_host :
                        back_check="host";
                        setfragment(hostFragment);
                        setTitle("Host");
                        return true;
                    case R.id.nav_profile :
                        back_check="profile";
                        setfragment(profileFragment);
                        setTitle("Profile");
                        return true;
                    case R.id.nav_search :
                        back_check="search";
                        setfragment(searchFragment);
                        setTitle("Search");
                        return true;
                    default:
                        return false;

                }
            }

        });
    }


    private void setfragment(Fragment fragment) {
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.main_frame,fragment);
        fm.addToBackStack(null);
        fm.commit();

    }

}
