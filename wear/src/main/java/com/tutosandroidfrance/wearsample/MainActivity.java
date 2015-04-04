package com.tutosandroidfrance.wearsample;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity{

    private final static String TAG = MainActivity.class.getCanonicalName();

    private GridViewPager pager;
    private DotsPageIndicator dotsPageIndicator;

    private List<Element> elementList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (GridViewPager) findViewById(R.id.pager);
        dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(pager);

        elementList = creerListElements();
        pager.setAdapter(new ElementGridPagerAdapter(elementList,getFragmentManager()));
    }

    private List<Element> creerListElements() {
        List<Element> list = new ArrayList<>();

        list.add(new Element("Element 1", "Description 1", Color.parseColor("#F44336")));
        list.add(new Element("Element 2", "Description 2", Color.parseColor("#E91E63")));
        list.add(new Element("Element 3", "Description 3", Color.parseColor("#9C27B0")));
        list.add(new Element("Element 4", "Description 4", Color.parseColor("#673AB7")));
        list.add(new Element("Element 5", "Description 5", Color.parseColor("#3F51B5")));
        list.add(new Element("Element 6", "Description 6", Color.parseColor("#2196F3")));

        return list;
    }
}
