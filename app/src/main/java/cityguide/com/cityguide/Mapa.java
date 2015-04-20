package cityguide.com.cityguide;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;


public class Mapa extends Activity implements
        LocationListener,
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private LocationManager locationManager;
    private String provider;
    float lat;
    float lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    public void MovePrevious (View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    public void MoveNext(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    public void DeOnde (View view) {
        View parentView = (View) view.getParent().getParent().getParent();
        CardView cardView = (CardView) parentView.findViewById(R.id.card_location);
        if(cardView.getVisibility() == View.VISIBLE) {
            cardView.setVisibility(View.GONE);
        } else {
            cardView.setVisibility(View.VISIBLE);
        }
    }

    public void RequestLocation (View view) {
        View parentView = (View) view.getParent();
        Button btnDaqui = (Button) view.findViewById(R.id.daqui);
        EditText etEndereco = (EditText) parentView.findViewById(R.id.adress);

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        //btnDaqui.setBackgroundColor(0xdfdfdf00);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            etEndereco.setHint("Carregando endereço...");
            onLocationChanged(location);
        } else {
            etEndereco.setText("Location not available");
        }
    }

    public void Quando (View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                (DatePickerDialog.OnDateSetListener) Mapa.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

    public void Alarme (View view) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() { return 5; }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            return getResources().getStringArray(R.array.sectionTitle)[position].toUpperCase(l);
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private int sectionNumber;

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);

            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        // Store instance variables based on arguments passed
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER, 0);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_mapa, container, false);
            TextView title = (TextView) rootView.findViewById(R.id.section_title);
            ImageView background = (ImageView) rootView.findViewById(R.id.background_image);

            String fragTitle = container.getResources().getStringArray(R.array.sectionTitle)[sectionNumber];
            String fragImage = container.getResources().getStringArray(R.array.sectionImage)[sectionNumber];
            int imageResource = container.getResources().getIdentifier(fragImage, "drawable", rootView.getContext().getPackageName());
            Drawable imageDrawable = getResources().getDrawable(imageResource);
            title.setText(fragTitle);
            background.setImageDrawable(imageDrawable);

            return rootView;
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
        // TODO Auto-generated method stub

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


    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String time = "You picked the following time: "+hourOfDay+"h"+minute;
        Toast.makeText(this, String.valueOf(time), Toast.LENGTH_SHORT).show();
    }


    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        Toast.makeText(this, String.valueOf(date), Toast.LENGTH_SHORT).show();
    }

}
