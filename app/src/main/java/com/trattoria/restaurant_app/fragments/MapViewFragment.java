package com.trattoria.restaurant_app.fragments;

/**
 * Created by Ajira Sivell on 12.05.2017.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.trattoria.restaurant_app.R;
import com.trattoria.restaurant_app.delegate.ScrollViewDelegate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;

public class MapViewFragment extends BaseViewPagerFragment
        implements OnMapReadyCallback
{

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    private ScrollView mScrollView;
    private ScrollViewDelegate mScrollViewDelegate = new ScrollViewDelegate();


    public static MapViewFragment newInstance(int index)
    {
        MapViewFragment fragment = new MapViewFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_map_view, container, false);
        mScrollView = (ScrollView) mView.findViewById(R.id.startview);

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);
        if (mMapView != null)
        {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
            mMapView.setOnTouchListener(new View.OnTouchListener()
            {
                public boolean onTouch(View v, MotionEvent event)
                {
                    if (event.getPointerCount() > 1)
                    {
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap)
    {
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener()
        {
            @Override
            public void onCameraMove()
            {
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(40.689247, -74.044502)));
            }
        });
        mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.689247, -74.044502))
                .title("Statue of Liberty")
                .snippet("SAMPEL POJNT")).showInfoWindow();

        CameraPosition Liberty = CameraPosition.builder()
                .target(new LatLng(40.689247, -74.044502))
                .zoom(16)
                .bearing(0)
                .tilt(45)
                .build();

        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
    }

    @Override
    public boolean isViewBeingDragged(MotionEvent event)
    {
        return mScrollViewDelegate.isViewBeingDragged(event, mScrollView);
    }

    private String loadContentString()
    {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[8 * 1024];
        int len;
        String content = "";
        try
        {
            inputStream = getActivity().getAssets().open("start.txt");
            while ((len = inputStream.read(buf)) != -1)
            {
                outputStream.write(buf, 0, len);
            }
            content = outputStream.toString();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            closeSilently(inputStream);
            closeSilently(outputStream);
        }

        return content;
    }

    public void closeSilently(Closeable c)
    {
        if (c == null)
        {
            return;
        }
        try
        {
            c.close();
        } catch (Throwable t)
        {
            // do nothing
        }
    }
}