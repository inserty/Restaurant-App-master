package com.trattoria.restaurant_app.tools;

/**
 * Created by Ajira Sivell on 11.05.2017.
 */

public interface ScrollableFragmentListener
{

    public void onFragmentAttached(ScrollableListener fragment, int position);

    public void onFragmentDetached(ScrollableListener fragment, int position);
}