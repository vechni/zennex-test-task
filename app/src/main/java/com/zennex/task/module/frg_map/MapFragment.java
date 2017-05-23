package com.zennex.task.module.frg_map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zennex.task.R;
import com.zennex.task.common.interfaces.OnBackPressedListener;
import com.zennex.task.common.mvp.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapFragment extends BaseFragment implements MapContract.View, OnMapReadyCallback, OnBackPressedListener {

    public static final String TAG = "tag_map_frg";
    public static final int PERMISSIONS_ACCESS_FINE_LOCATION = 3;
    public static final double COORDINATE_LAT_TOMSK = 56.47377476;
    public static final double COORDINATE_LNG_TOMSK = 84.95250105;
    public static final int ZOOM_MAP_DEFAULT = 10;

    @BindView(R.id.frg_map_txt_latitude) TextView txtLatitude;
    @BindView(R.id.frg_map_txt_longitude) TextView txtLongitude;
    private GoogleMap map = null;
    private SupportMapFragment mapFrg;

    @Inject MapPresenter presenter;
    private MapRouter router;

    // region - Lifecycle -

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        router = (MapRouter) activity;
        getFragmentComponent().inject(this);
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root;
        root = inflater.inflate(R.layout.frg_map, container, false);

        ButterKnife.bind(this, root);

        initScreen();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    // endregion


    // region - Event handlers -

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng tomsk = new LatLng(COORDINATE_LAT_TOMSK, COORDINATE_LNG_TOMSK);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tomsk, ZOOM_MAP_DEFAULT));

        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_ACCESS_FINE_LOCATION);
            return;
        }

        presenter.requestCoordinateLocation();
    }

    @Override
    public void onBackPressed() {
        router.navigateToMainScreen();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.requestCoordinateLocation();
            }
        }
    }

    // endregion


    // region - Contract -

    @Override
    public void showCoordinateLocation() {

        Location location = null;
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String bestProvider = locationManager.getBestProvider(criteria, true);

        if (bestProvider != null) {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                location = locationManager.getLastKnownLocation(bestProvider);
            } else {
                showToastShort(getString(R.string.error_geolocation));
                return;
            }
        }

        if (location == null) {
            showToastShort(getString(R.string.txt_error));
            return;
        }

        String lat = String.valueOf(location.getLatitude());
        String lon = String.valueOf(location.getLongitude());
        txtLatitude.append(lat);
        txtLongitude.append(lon);

        LatLng currentPlace = new LatLng(location.getLatitude(), location.getLongitude());
        this.map.addMarker(new MarkerOptions().position(currentPlace).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title(getString(R.string.text_current_position)));
        this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPlace, 12));
    }

    // endregion


    // region - Methods -

    private void initScreen() {
        setTextTitleToolbar(getString(R.string.title_map));

        mapFrg = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg_map_container_map);
        mapFrg.getMapAsync(this);
    }

    // endregion
}