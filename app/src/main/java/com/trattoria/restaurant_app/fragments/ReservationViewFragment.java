package com.trattoria.restaurant_app.fragments;

/**
 * Created by Ajira Sivell on 12.05.2017.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.trattoria.restaurant_app.R;
import com.trattoria.restaurant_app.delegate.ScrollViewDelegate;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import static android.R.id.list;

public class ReservationViewFragment extends BaseViewPagerFragment
{

    Button btnDatePicker;
    Button btnMinus;
    Button btnPlus;

    int mYear;
    int mMonth;
    int mDay;
    int quantityGuests;

    TextView txtDate;

    private ScrollView mScrollView;
    private ScrollViewDelegate mScrollViewDelegate = new ScrollViewDelegate();

    View view;

    public static ReservationViewFragment newInstance(int index)
    {
        ReservationViewFragment fragment = new ReservationViewFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_reservation_view, container, false);
        mScrollView = (ScrollView) view.findViewById(R.id.reservationview);
        btnDatePicker = (Button) view.findViewById(R.id.btn_pick_date);
        txtDate= (TextView) view.findViewById(R.id.txt_picked_date);
        btnMinus = (Button) view.findViewById(R.id.btn_less_guests);
        btnPlus = (Button) view.findViewById(R.id.btn_more_guests);

        btnPlus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                quantityGuests++;
                if (quantityGuests > 100) quantityGuests = 100;
                displayQuantity(quantityGuests);
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                quantityGuests--;
                if (quantityGuests < 0) quantityGuests = 0;
                displayQuantity(quantityGuests);
            }
        });
        btnDatePicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener()
                        {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {

                                if (year < mYear)
                                    Toast.makeText(view.getContext(), "Wybrana data musi być nie może być datą przeszłą!", Toast.LENGTH_SHORT).show();

                                else if (monthOfYear < mMonth && year == mYear)
                                    Toast.makeText(view.getContext(), "Wybrana data musi być nie może być datą przeszłą!", Toast.LENGTH_SHORT).show();

                                else if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                                    Toast.makeText(view.getContext(), "Wybrana data musi być nie może być datą przeszłą!", Toast.LENGTH_SHORT).show();

                                else
                                    txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        return view;
    }

    private void displayQuantity(int quantity)
    {
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(quantity));
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
            inputStream = getActivity().getAssets().open("reservation.txt");
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