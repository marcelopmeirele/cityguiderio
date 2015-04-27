package cityguide.com.cityguide;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class Buttons extends Activity implements
        LocationListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    LocationManager locationManager;
    String provider;
    float lat;
    float lng;
    ViewPager mViewPager;

    public void MovePrevious (View view) {

        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    public void MoveNext(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    public void DeOnde (View view) {
        View parentView = (View) view.getParent().getParent().getParent();
        EditText etEndereco = (EditText) parentView.findViewById(R.id.adress);
        CardView cardView = (CardView) parentView.findViewById(R.id.card_location);
        if(cardView.getVisibility() == View.VISIBLE) {
            cardView.setVisibility(View.GONE);
            etEndereco.setHint("Ou digite seu endereço");
        } else {
            cardView.setVisibility(View.VISIBLE);
        }
        //Appointment appointment = new Appointment();

    }

    public void RequestLocation (View view) {
        View parentView = (View) view.getParent();
        EditText etEndereco = (EditText) parentView.findViewById(R.id.adress);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);


        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            etEndereco.setHint("Carregando endereço...");
            onLocationChanged(location);
        } else {
            etEndereco.setText("Location not available");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = (float) (location.getLatitude());
        lng = (float) (location.getLongitude());
        String coords = new String();
        coords = String.valueOf(lat).concat(String.valueOf(lng));
        Toast toast = Toast.makeText(this, coords, Toast.LENGTH_SHORT);
        toast.setGravity(5,5,5);
        toast.show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void Quando (View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                (DatePickerDialog.OnDateSetListener) this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

    public void QueHora (View view) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                (TimePickerDialog.OnTimeSetListener) this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.show(getFragmentManager(), "Timepickerdialog");
        Quando(view);
    }

    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        Toast toast = Toast.makeText(this, String.valueOf(date), Toast.LENGTH_SHORT);
        toast.setGravity(5,5,5);
        toast.show();
    }

    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String time = "You picked the following time: "+hourOfDay+"h"+minute;
        Toast toast = Toast.makeText(this, String.valueOf(time), Toast.LENGTH_SHORT);
        toast.setGravity(5,5,5);
        toast.show();
    }

    public void Alarme (View view) {

    }

    public void getaddress(View view) {
        View parentView = (View) view.getParent();
        EditText etEndereco = (EditText) parentView.findViewById(R.id.adress);
        // Do something in response to button
        String coord = getAddress(lat, lng);
        etEndereco.setText(coord);
    }

    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address returnedAddress = addresses.get(0);
                for (int i = 1; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    result.append(returnedAddress.getAddressLine(i)).append("\n");
                }
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        return result.toString();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
}
