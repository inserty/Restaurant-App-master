package com.trattoria.restaurant_app.fragments;

/**
 * Created by Ajira Sivell on 12.05.2017.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.trattoria.restaurant_app.R;
import com.trattoria.restaurant_app.delegate.ScrollViewDelegate;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;

public class ContactViewFragment extends BaseViewPagerFragment
{

    private ScrollView mScrollView;
    private ScrollViewDelegate mScrollViewDelegate = new ScrollViewDelegate();

    public static ContactViewFragment newInstance(int index)
    {
        ContactViewFragment fragment = new ContactViewFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_start_view, container, false);
        mScrollView = (ScrollView) view.findViewById(R.id.startview);
        TextView textView = (TextView) view.findViewById(R.id.text1);
        textView.setText(loadContentString());
        return view;
    }

    @Override
    public boolean isViewBeingDragged(MotionEvent event)
    {
        return mScrollViewDelegate.isViewBeingDragged(event, mScrollView);
    }

    private String loadContentString()
    {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[8 * 1024];
        int len;
        String content = "";
        try
        {
            inputStream = getActivity().getAssets().open("contact.txt");
            while ((len = inputStream.read(buf)) != -1)
            {
                outputStream.write(buf, 0, len);
            }
            content = outputStream.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeSilently(inputStream);
            closeSilently(outputStream);
        }

        return content;
    }

    public void closeSilently(Closeable c)
    {
        if (c == null)
        {
            return;
        }
        try
        {
            c.close();
        } catch (Throwable t)
        {
            // do nothing
        }
    }
}