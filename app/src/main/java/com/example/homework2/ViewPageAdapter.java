package com.example.homework2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends FragmentStateAdapter {
    public ArrayList<String> urls = new ArrayList<>();

    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void addTab(String url) {
        urls.add(url);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position < urls.size()) {
            return WebViewFragment.newInstance(urls.get(position),"");
        } else {
            return new Fragment();
        }
    }

    public String getUrl(int position) {
        if (position < urls.size()) {
            return urls.get(position);
        } else {
            return "";
        }
    }

    public void removeTab(int position) {
        if (position >= 0 && position < urls.size()) {
            urls.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }


}
