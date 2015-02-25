package jp.mdnht.bikenavigator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;

import static jp.mdnht.bikenavigator.common.Utils.*;

public class MainActivity extends ActionBarActivity implements DataApi.DataListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MapFragment mMapFragment;
    private MapView mMapView;
    private Bitmap mMapSnapShotBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, BuildConfig.BUILD_TYPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add map fragment
        GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
       /* mMapFragment = MapFragment.newInstance(options);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.mapfragmentcontainer, mMapFragment)
                    .commit();
        }*/


        //rest
        mMapView = new MapView(this,options);//MapFragment.newInstance(options);
        mMapView.onCreate(savedInstanceState);//
        //add map
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.mapViewContainer);
        linearLayout.addView(mMapView, new LinearLayout.LayoutParams(300, 300));

        /*int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        linearLayout.measure(spec, spec);
        linearLayout.layout(0, 0, linearLayout.getWidth(), linearLayout.getHeight());*/

        //button
        ((Button) findViewById(R.id.captureButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCaptureButtonClick();
                /*mMapFragment.getMap().snapshot(new GoogleMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap) {
                        onSnapShotReady(bitmap);
                    }
                });*/
            }
        });
    }

    private void onCaptureButtonClick(){
        LOGD(TAG, "ssssssssssssssssss");
        Intent i = new Intent(this, MapFetchService.class);
        i.setAction(MapFetchService.ACTION_GET_MAP);
        this.startService(i);
        /*mMapView.getMap().snapshot(new GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                onSnapShotReady(bitmap);
            }
        });*/
    }

    private void onSnapShotReady(Bitmap bitmap){
        mMapSnapShotBitmap = bitmap;
        //((ImageView) findViewById(R.id.mapImage)).setImageBitmap(bitmap);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmap);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.mapViewContainer);
        linearLayout.addView(imageView, new LinearLayout.LayoutParams(300, 300));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        LOGD(TAG, "aaaa");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
