package com.app.ace.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.app.ace.R;
import com.app.ace.entities.MapScreenItem;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.UserProfile;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.map.abstracts.GoogleMapOptions;
import com.app.ace.map.abstracts.MapMarkerItemBinder;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.TitleBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static com.app.ace.R.id.txt_noresult;


public class MapScreenFragment extends BaseFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AnyEditTextView edtsearch;

    @InjectView(R.id.txt_noresult)
    private TextView txt_noresult;

    private ArrayList<MapScreenItem> userCollection = new ArrayList<>();
    public static MapScreenFragment newInstance() {
        return new MapScreenFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txt_noresult.setVisibility(View.GONE);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    private void getSearchUserData() {
        if (edtsearch.getText().toString() != null) {
            Call<ResponseWrapper<ArrayList<UserProfile>>> callBack = webService.getSearchUser(edtsearch.getText().toString(), AppConstants.trainer);

            callBack.enqueue(new Callback<ResponseWrapper<ArrayList<UserProfile>>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<ArrayList<UserProfile>>> call,
                                       Response<ResponseWrapper<ArrayList<UserProfile>>> response) {

                    if (response.body()!= null)
                        if (response.body().getResult().size() <= 0) {
                            txt_noresult.setVisibility(View.VISIBLE);
                        }
                        else{
                            txt_noresult.setVisibility(View.GONE);
                        bindview(response.body().getResult());
                    addMarker();
                }}

                @Override
                public void onFailure(Call<ResponseWrapper<ArrayList<UserProfile>>> call, Throwable t) {
                    Log.e("Search", t.toString());
                }
            });

        }
    }

    private void bindview(ArrayList<UserProfile> resultuser) {
        userCollection = new ArrayList<>();
        try {
        for (UserProfile user : resultuser) {
            if(!user.getGym_latitude().isEmpty()) {

                    userCollection.add(new MapScreenItem(user.getGym_latitude(),
                            user.getGym_longitude(), user.getProfile_image()));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error " + e.getMessage());
        }

    }



    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.showCancelButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");
            }
        });
        edtsearch = titleBar.showSearchBar();
        edtsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getSearchUserData();
                }
                return false;
            }
        });

    }

    void addMarker()
    {

        GoogleMapOptions<MapScreenItem> googleMapOptions = new GoogleMapOptions<>(getDockActivity(),
                mMap,
                userCollection,
                null,
                new MapMarkerItemBinder(getMainActivity())
        );
        googleMapOptions.addMarkers();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title(getString(R.string.Marker_in)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

        addMarker();
    }
}
