package gb.stoliarenkoas.ru.lesson7;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

public class FragmentOne extends Fragment {

    ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        final TextView textView = view.findViewById(R.id.text_view);
        textView.setOnClickListener((v) -> {
            final FragmentTwo fragmentTwo = new FragmentTwo();

            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setPathMotion(new ArcMotion());
            changeBounds.setInterpolator(new BounceInterpolator());

            fragmentTwo.setSharedElementEnterTransition(changeBounds);
            getFragmentManager().beginTransaction().replace(R.id.container, fragmentTwo)
                    .addSharedElement(textView, getString(R.string.text_view_transition))
                    .addToBackStack(FragmentOne.class.getName())
                    .commit();
        });

        pager = view.findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());
        pagerAdapter.addFragment(PagerFragment.createInstance(Color.RED));
        pagerAdapter.addFragment(PagerFragment.createInstance(0xFFFF8800));
        pagerAdapter.addFragment(PagerFragment.createInstance(Color.YELLOW));
        pagerAdapter.addFragment(PagerFragment.createInstance(Color.GREEN));
        pagerAdapter.addFragment(PagerFragment.createInstance(Color.CYAN));
        pagerAdapter.addFragment(PagerFragment.createInstance(Color.BLUE));
        pagerAdapter.addFragment(PagerFragment.createInstance(Color.MAGENTA));
        pager.setAdapter(pagerAdapter);


        return view;
    }

}
