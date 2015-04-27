package cityguide.com.cityguide;

import java.util.Locale;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


public class Mapa extends Activity {

    public SectionsPagerAdapter mSectionsPagerAdapter;
    public ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

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

    /*public class ViewHolder extends RecyclerView.ViewHolder {
    }*/
}
