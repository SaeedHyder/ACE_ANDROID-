package com.app.ace.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.InflateException;
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
import com.app.ace.helpers.DialogHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.map.abstracts.GoogleMapOptions;
import com.app.ace.map.abstracts.MapMarkerItemBinder;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.TitleBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapScreenFragment extends BaseFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private AnyEditTextView edtsearch;
    double latitude;
    double longitude;
    String language = "";
    //@InjectView(R.id.txt_noresult)
    private TextView txt_noresult;
    private View viewParent;
    GoogleApiClient googleApiClient;
    private Location Mylocation;
    private LocationListener listener;
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

        if (viewParent != null) {
            ViewGroup parent = (ViewGroup) viewParent.getParent();
            if (parent != null)
                parent.removeView(viewParent);
        }
        try {
            viewParent = inflater.inflate(R.layout.fragment_map_screen, container, false);

        } catch (InflateException e) {
            e.printStackTrace();
        }
        if (viewParent != null) {
            txt_noresult = (TextView) viewParent.findViewById(R.id.txt_noresult);
        }


        return viewParent;
        // Inflate the layout for this fragment

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.isLanguageArabic()) {
            language = "ar";
        } else
            language = "en";


        txt_noresult.setVisibility(View.GONE);
        getMainActivity().statusCheck();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(getMainActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)

                .addApi(LocationServices.API)
                .build();

    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    private void getSearchUserData() {


        Call<ResponseWrapper<ArrayList<UserProfile>>> callBack = webService.getSearchUser(edtsearch.getText().toString(), AppConstants.trainer, 24.807825187774412, 46.74573140058601, getMainActivity().selectedLanguage());

        callBack.enqueue(new Callback<ResponseWrapper<ArrayList<UserProfile>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<UserProfile>>> call, Response<ResponseWrapper<ArrayList<UserProfile>>> response) {

                if (response.body() != null)
                    if (response.body().getResult().size() <= 0) {
                        if (response.body().getUserDeleted() == 0) {
                            txt_noresult.setVisibility(View.VISIBLE);
                        } else {
                            final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                            dialogHelper.initLogoutDialog(R.layout.dialogue_deleted, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialogHelper.hideDialog();
                                    getDockActivity().popBackStackTillEntry(0);
                                    getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                                }
                            });
                            dialogHelper.showDialog();
                        }
                    } else {
                        txt_noresult.setVisibility(View.GONE);
                        bindview(response.body().getResult());
                        addMarker();
                    }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<UserProfile>>> call, Throwable t) {
                Log.e("Search", t.toString());
            }
        });


    }

    void addMarker() {

        GoogleMapOptions<MapScreenItem> googleMapOptions = new GoogleMapOptions<>(getDockActivity(),
                mMap,
                userCollection,
                null,
                new MapMarkerItemBinder(getMainActivity(), getDockActivity())
        );

        // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(userCollection.get(userCollection.size() - 1).getLat()), Double.valueOf(userCollection.get(userCollection.size() - 1).getLng())), AppConstants.zoomIn));
        //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(language), Double.valueOf(longitude)), AppConstants.zoomIn));

        googleMapOptions.addMarkers();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (userCollection != null) {
                    for (final MapScreenItem item : userCollection) {
                        if (String.valueOf(item.getUserId()).equals(marker.getTag().toString())) {
                            getDockActivity().addDockableFragment(TrainerProfileFragment.newInstance(item.getUserId()), "TrainerProfileFragment");
                        }
                    }
                }
                return true;
            }
        });

       /* if (!edtsearch.getText().toString().equals("")) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(userCollection.get(userCollection.size() - 1).getLat()), Double.valueOf(userCollection.get(userCollection.size() - 1).getLng())), AppConstants.zoomInToTrainer));
        }*/

        if (edtsearch.getText().toString().equals("")) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(24.807825187774412, 46.74573140058601), AppConstants.zoomIn));
        } else {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(userCollection.get(userCollection.size() - 1).getLat()), Double.valueOf(userCollection.get(userCollection.size() - 1).getLng())), AppConstants.zoomInToTrainer));

        }

    }

    private void bindview(ArrayList<UserProfile> resultuser) {
        userCollection = new ArrayList<>();
        try {
            for (UserProfile user : resultuser) {
                if (!user.getGym_latitude().isEmpty()) {

                    userCollection.add(new MapScreenItem(user.getGym_latitude(),
                            user.getGym_longitude(), user.getProfile_image(), user.getId(), latitude, longitude));
                }
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

        addMarker();

    }

    private void getCurrentLocation() {


        if (ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (Mylocation == null) {
            locationRequest.setInterval(1000);
        }
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location mlocation) {
                if (mlocation != null) {
                    Mylocation = mlocation;
                    LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, listener);
                    if (Mylocation != null) {
                        //Getting longitude and latitude

                        longitude = Mylocation.getLongitude();
                        latitude = Mylocation.getLatitude();

                        getSearchUserData();

                    } else {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Can't get your Location Try getting using Location Button");
                    }
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, listener);

            //location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        }

       /* if (ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {

            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }*/

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


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

       /* // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title(getString(R.string.Marker_in)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));*/

        //addMarker();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (getMainActivity().statusCheck())
            getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
