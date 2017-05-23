package zph.zhjx.com.bddemo.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.GeomagneticField;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import zph.zhjx.com.bddemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TraditionFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button mButton;
    private TextView mTextView;
    private LocationListener mLocationListener;
    private LocationManager mLocationManager;
    private GeomagneticField mGeomagneticField;
    private final int BAIDU_READ_PHONE_STATE=1005;
    public TraditionFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tradition, container, false);
        initview();
        mButton.setOnClickListener(this);
        if (Build.VERSION.SDK_INT>=23){
            initMap();
        }else{
            initGPS();
        }

        return view;
    }

    private void initMap() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity().getApplicationContext(),"没有权限,请手动开启定位权限",Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
        }else{
            initGPS();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //判断gps是否可用
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getActivity(), "gps可用", Toast.LENGTH_LONG).show();
            //开始定位
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50000, 10, mLocationListener);
        } else {
            Toast.makeText(getActivity(), "请打开gps或者选择gps模式为准确度高", Toast.LENGTH_LONG).show();
            //前往设置GPS页面
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }

    private void initGPS() {
        mLocationManager = ((LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE));
        mLocationListener = new MyLocationListner();
    }

    private void initview() {
        mButton = (Button) view.findViewById(R.id.gpgga_btn);
        mTextView = (TextView) view.findViewById(R.id.gpgga_text);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gpgga_btn:

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }
                mLocationManager.addNmeaListener(new GpsStatus.NmeaListener() {

                    @Override
                    public void onNmeaReceived(long timestamp, String nmea) {
                        mTextView.invalidate();
                        //此处以GPGGA为例
                        //$GPGGA,232427.000,3751.1956,N,11231.1494,E,1,6,1.20,824.4,M,-23.0,M,,*7E
                        if (nmea.contains("GPGGA")) {
                            String info[] = nmea.split(",");
                            //GPGGA中altitude是MSL altitude(平均海平面)
                            mTextView.setText(nmea);
                            Log.i("GPGGA", "获取的GPGGA数据是：" + nmea);
                            Log.i("GPGGA", "获取的GPGGA数据length：" + info.length);
                            Log.i("GPGGA", "GPS定位数据：" + info[0]);
                            Log.i("GPGGA", "UTC时间：" + info[1]);

                            Log.i("GPGGA", "纬度：" + info[2]);
                            Log.i("GPGGA", "纬度半球：" + info[3]);
                            Log.i("GPGGA", "经度：" + info[4]);
                            Log.i("GPGGA", "经度半球：" + info[5]);
                            Log.i("GPGGA", "GPS状态：" + info[6]);
                            Log.i("GPGGA", "使用卫星数量：" + info[7]);
                            Log.i("GPGGA", "HDOP-水平精度因子：" + info[8]);
                            Log.i("GPGGA", "椭球高：" + info[9]);
                            Log.i("GPGGA", "大地水准面高度异常差值：" + info[10]);
                            Log.i("GPGGA", "差分GPS数据期限：" + info[11]);
                            Log.i("GPGGA", "差分参考基站标号：" + info[12]);
                            Log.i("GPGGA", "ASCII码的异或校验：" + info[info.length - 1]);
                            //UTC + (＋0800) = 本地（北京）时间
                            int a = Integer.parseInt(info[1].substring(0, 2));
                            a += 8;
                            a%=24;
                            String time = "";
                            String time1 = "";
                            if (a < 10) {
                                time = "0" + a + info[1].substring(2, info[1].length() - 1);
                            } else {
                                time = a + info[1].substring(2, info[1].length() - 1);
                            }
                            time1 = time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);
                            mTextView.setText("获取的GPGGA数据length：" + info.length + "\nUTC时间：" + info[1] + "\n北京时间: " + time1
                                    + "\n纬度：" + info[2] + "\n纬度半球：" + info[3] + "\n经度：" + info[4] + "\n经度半球：" + info[5]
                                    + "\nGPS状态：" + info[6] + "\n使用卫星数量：" + info[7] + "\nHDOP-水平精度因子：" + info[8] + "\n椭球高：" + info[9]
                                    + "\n大地水准面高度异常差值：" + info[10] + "\n差分GPS数据期限：" + info[11] + "\n差分参考基站标号：" + info[12]
                                    + "\nASCII码的异或校验：" + info[info.length - 1]);
                        }
                    }
                });

                break;
        }
    }





    private class MyLocationListner implements LocationListener {

        @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
        @Override
        public void onLocationChanged(Location location) {
            mTextView.invalidate();
            Double longitude = location.getLongitude();
            float accuracy = location.getAccuracy();
            Double latitude = location.getLatitude();
            Double altitude = location.getAltitude();// WGS84
            float bearing = location.getBearing();
            mGeomagneticField = new GeomagneticField((float) location.getLatitude(),
                    (float) location.getLongitude(), (float) location.getAltitude(),
                    System.currentTimeMillis());
            mTextView.setText("Altitude=" + altitude + "\nLongitude=" + longitude + "\nLatitude="
                    + latitude + "\nDeclination=" + mGeomagneticField.getDeclination() + "\nBearing="
                    + bearing + "\nAccuracy=" + accuracy);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    }





    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    initGPS();
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getActivity().getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
