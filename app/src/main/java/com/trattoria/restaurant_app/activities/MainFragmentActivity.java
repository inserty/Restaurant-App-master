package com.trattoria.restaurant_app.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.ActionBarActivity;

import com.trattoria.restaurant_app.R;
import com.trattoria.restaurant_app.fragments.MainViewFragment;
import com.trattoria.restaurant_app.tools.ScrollableFragmentListener;
import com.trattoria.restaurant_app.tools.ScrollableListener;


public class MainFragmentActivity extends ActionBarActivity implements ScrollableFragmentListener
{

    public static final String FRAGMENT_MAIN = "FRAGMENT_MAIN";
    public static SparseArrayCompat<ScrollableListener> mScrollableListenerArrays = new SparseArrayCompat<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_view);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_MAIN);
        if (fragment == null) fragment = new MainViewFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, FRAGMENT_MAIN).commit();
    }

    @Override
    public void onFragmentAttached(ScrollableListener listener, int position)
    {
        mScrollableListenerArrays.put(position, listener);
    }

    @Override
    public void onFragmentDetached(ScrollableListener listener, int position)
    {
        mScrollableListenerArrays.remove(position);
    }

    public SparseArrayCompat<ScrollableListener> getScrollableListeners()
    {
        return mScrollableListenerArrays;
    }
}