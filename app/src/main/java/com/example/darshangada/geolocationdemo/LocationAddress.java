package com.example.darshangada.geolocationdemo;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationAddress {
    private static final String TAG = "LocationAddress";

    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                        }
//                        sb.append("AddressLine : ").append(address.getAddressLine(0)).append("\n\n");
                        sb.append("GetLocality : ").append(address.getLocality()).append("\n\n");
                        sb.append("GetPostalCode : ").append(address.getPostalCode()).append("\n\n");
                        sb.append("GetCountryName : ").append(address.getCountryName()).append("\n\n");
                        sb.append("GetCountryCode : ").append(address.getCountryCode()).append("\n\n");
                        sb.append("GetSubLocality : ").append(address.getSubLocality()).append("\n\n");
                        sb.append("GetSubAdminArea : ").append(address.getSubAdminArea()).append("\n\n");
                        sb.append("GetAdminArea : ").append(address.getAdminArea()).append("\n\n");
                        /*sb.append("Latitude : ").append(address.getLatitude()).append("\n");
                        sb.append("Longitude : ").append(address.getLongitude()).append("\n");*/
                        sb.append("AddressLine : ").append(address.getAddressLine(0)).append("\n\n");
//                        sb.append("AddressList : ").append(addressList.get(0));


                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n\nAddress:\n" + result;
                        bundle.putString("address", result);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n Unable to get address for this lat-long.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
}
