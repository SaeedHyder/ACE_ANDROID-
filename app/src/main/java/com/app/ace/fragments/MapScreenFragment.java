package com.app.ace.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ace.R;
import com.app.ace.entities.DetailedScreenItem;
import com.app.ace.entities.HomeListDataEnt;
import com.app.ace.entities.MapScreenItem;
import com.app.ace.entities.TraineeScheduleItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.map.abstracts.GoogleMapOptions;
import com.app.ace.map.abstracts.MapMarkerItemBinder;
import com.app.ace.ui.views.TitleBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class MapScreenFragment extends BaseFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<MapScreenItem> CoordinatesCollection = new ArrayList<>();
    public static MapScreenFragment newInstance() {
        return new MapScreenFragment();
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

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getMapData();
    }

    private void getMapData() {

        CoordinatesCollection= new ArrayList<>();
        CoordinatesCollection.add(new MapScreenItem("24.829428","67.073822","drawable://" + R.drawable.profile_pic));
        CoordinatesCollection.add(new MapScreenItem("24.861443","67.010025","drawable://" + R.drawable.profile_pic_trainer));
        CoordinatesCollection.add(new MapScreenItem("24.856844","67.264647","drawable://" + R.drawable.profile_pic));
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
        titleBar.showSearchBar();

    }

    void addMarker()
    {
        GoogleMapOptions<MapScreenItem> googleMapOptions = new GoogleMapOptions<>(getDockActivity(),
                mMap,
                CoordinatesCollection,
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
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

        addMarker();
    }
}
