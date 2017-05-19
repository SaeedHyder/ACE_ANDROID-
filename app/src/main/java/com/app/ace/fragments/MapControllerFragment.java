package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.ace.BaseApplication;
import com.app.ace.R;
import com.app.ace.entities.GoogleGeoCodeResponse;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.GPSHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.IGetLocation;
import com.app.ace.retrofit.GoogleServiceResponse;
import com.app.ace.ui.views.AnyEditTextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

/**
 * Created by saeedhyder on 5/9/2017.
 */



public class MapControllerFragment extends BaseFragment {

    @InjectView(R.id.searchEditText)
    private EditText searchEditText;

    @InjectView(R.id.btnUseLocation)
    private Button btnUseLocation;

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private boolean mIsLocation = false;

    private LatLng scammerLocation;

    private IGetLocation delegate;

    String SearchedAddress;

    public static MapControllerFragment newInstance() {
        return new MapControllerFragment();
    }

    public void setDelegate(IGetLocation delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMapView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mapcontroller, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListener();

    }

    private void setListener() {

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchLocation();
                    return true;
                }
                return false;
            }
        });

        btnUseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsLocation = !mIsLocation;
                setLocationButton(mIsLocation);
            }
        });
    }

    private void setMapView() {
        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MapControllerFragment.this.googleMap = googleMap;

                if (null != googleMap) {

                    googleMap.getUiSettings().setAllGesturesEnabled(false);
                    //googleMap.setMyLocationEnabled(true);

                    if (mGpsTracker.getLocation() != null) {
                        moveGoogleMapToLocation(0, 0);
                    } else {
                        if (!CheckGPSAvailibility.checkGPSAvailibility(getDockActivity())) {
                            GPSHelper.showGPSDisabledAlertToUser(getDockActivity());
                            setLocationButton(mIsLocation);
                        }
                    }
                }
            }
        });

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.mapView, mapFragment).commit();
    }

    private void moveGoogleMapToLocation(double lat, double lng) {
        try {

            scammerLocation = new LatLng(lat, lng);

            googleMap.getUiSettings().setZoomControlsEnabled(false);

            googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    // Cleaning all the markers.
                    if (googleMap != null) {
                        googleMap.clear();
                    }

                    mIsLocation = false;
                    setLocationButton(mIsLocation);

                    scammerLocation = googleMap.getCameraPosition().target;
                }
            });




           /* googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                public void onCameraChange(CameraPosition cameraPosition) {

                    mIsLocation = false;
                    setLocationButton(mIsLocation);

                    scammerLocation = cameraPosition.target;

                }
            });*/

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(scammerLocation, AppConstants.zoomIn));
        } catch (Exception e) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.went_wrong));
        }


    }

    private void setLocationButton(boolean isLocationSet) {
        if (isLocationSet) {
            if (scammerLocation != null) {
               /* if (delegate == null) {


                } else {*/



                delegate.onLocationSet(scammerLocation, SearchedAddress);
                BaseApplication.getBus().post("true");
                //}
            } else {
                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.Unable_location));
            }
        } else {

            btnUseLocation.setText(R.string.Use_This_Location);

        }


    }

    private void searchLocation() {
        getMainActivity().onLoadingStarted();
        btnUseLocation.setEnabled(false);
        if (delegate != null) {
            btnUseLocation.setText(R.string.Loading_Location);
        }
        Call<GoogleServiceResponse<List<GoogleGeoCodeResponse>>> request = googleWebService.getLatLongInfo(
                searchEditText.getText().toString(),
                "false");
        request.enqueue(new Callback<GoogleServiceResponse<List<GoogleGeoCodeResponse>>>() {
            @Override
            public void onResponse(Call<GoogleServiceResponse<List<GoogleGeoCodeResponse>>> call, Response<GoogleServiceResponse<List<GoogleGeoCodeResponse>>> listGoogleServiceResponse) {
                getMainActivity().onLoadingFinished();
                try {
                    if (listGoogleServiceResponse.body().getResults().size() > 0) {

                        btnUseLocation.setEnabled(true);
                        hideKeyboard();
                        moveGoogleMapToLocation(Double.valueOf(listGoogleServiceResponse.body().getResults().get(0).getGeometry().getLocation().getLat()), Double.valueOf(listGoogleServiceResponse.body().getResults().get(0).getGeometry().getLocation().getLng()));
                        SearchedAddress = listGoogleServiceResponse.body().getResults().get(0).getFormatted_address();
                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), "No location found");
                        btnUseLocation.setEnabled(true);
                        setLocationButton(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    UIHelper.showLongToastInCenter(getDockActivity(), "No location found");
                    btnUseLocation.setEnabled(true);
                    setLocationButton(false);
                }
            }

            @Override
            public void onFailure(Call<GoogleServiceResponse<List<GoogleGeoCodeResponse>>> call, Throwable error) {
                getMainActivity().onLoadingFinished();
              //  RetrofitErrorHandler.onServiceFail(getDockActivity(), error);
                setLocationButton(false);
                btnUseLocation.setEnabled(true);
            }
        });

    }

    public LatLng getScammerLocation() {
        return scammerLocation;
    }

}