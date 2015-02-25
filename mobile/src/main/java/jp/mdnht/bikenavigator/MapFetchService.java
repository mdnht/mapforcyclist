package jp.mdnht.bikenavigator;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.wearable.Wearable;


import  static jp.mdnht.bikenavigator.common.Utils.*;

public class MapFetchService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MapFetchService.class.getSimpleName();
    public static final String ACTION_GET_MAP = "jp.mdnht.bikenavigator.action.ACTION_GET_MAP";


    private GoogleApiClient mGoogleApiClient;
    private MapView mMapView;
    private boolean mIsCaputureing = false;

    public MapFetchService() {
    }

    private void startFetchMapImage(){
        if(mGoogleApiClient.isConnected()) {
            LOGD(TAG, "start fetching map image");






            //add map
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.addView(mMapView, new ViewGroup.LayoutParams(300, 300));
            int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            linearLayout.measure(spec, spec);
            linearLayout.layout(0, 0, linearLayout.getWidth(), linearLayout.getHeight());

            LOGD(TAG, mMapView.getWidth() +":"+mMapView.getHeight());

            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    LOGD(TAG, "mayp is created");
                    LOGD(TAG, "map is " + googleMap.toString());
                    googleMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
                        @Override
                        public void onSnapshotReady(Bitmap bitmap) {
                            LOGD(TAG, "snapshot is created"+bitmap.getWidth()+"/"+bitmap.getHeight());
                        }
                    });
                }
            });
        }

        /*mMapFragment.getMap().snapshot(new GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                onSnapShotReady(bitmap);
            }
        });*/
    }

    private void onSnapShotReady(Bitmap bitmap) {
        LOGD(TAG, "image snapshot ready");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
        mMapView = new MapView(this,options);//MapFragment.newInstance(options);
        mMapView.onCreate(null);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LOGD(TAG, "start command");
        String action = intent.getAction();
        if (action.equals(ACTION_GET_MAP))
        {
            startFetchMapImage();
            mIsCaputureing = true;
        }
        return Service.START_STICKY_COMPATIBILITY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(Bundle bundle) {
        if(mIsCaputureing){
            startFetchMapImage();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
