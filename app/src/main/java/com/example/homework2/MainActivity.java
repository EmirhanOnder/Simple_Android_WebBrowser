package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPageAdapter viewPageAdapter;
    private EditText urlEditText;
    private Button goButton;
    private Button createTabButton;
    private TextView titleTextView;
    private ImageButton closeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        viewPageAdapter = new ViewPageAdapter(this);
        viewPager2.setAdapter(viewPageAdapter);

        urlEditText = findViewById(R.id.urlEditText);
        goButton = findViewById(R.id.goButton);
        createTabButton = findViewById(R.id.createTabButton);
        titleTextView = findViewById(R.id.titleTextView);
        closeButton = findViewById(R.id.closeButton);

        viewPageAdapter.addTab("");
        viewPageAdapter.notifyDataSetChanged();
        tabLayout.addTab(tabLayout.newTab().setText("Yeni Sekme"));

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlEditText.getText().toString();
                if (!url.isEmpty()) {
                    int selectedTabPosition = tabLayout.getSelectedTabPosition();
                    viewPageAdapter.urls.set(selectedTabPosition, url);
                    WebView webView = findViewById(R.id.webView);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageFinished(WebView view, String url) {
                            String pageTitle = view.getTitle();
                            updateTabText(pageTitle);
                            titleTextView.setText(pageTitle);
                        }
                    });
                    webView.loadUrl(url);

                }
            }
        });
        createTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String defaultUrl = "";
                viewPageAdapter.addTab(defaultUrl);
                viewPageAdapter.notifyDataSetChanged();
                tabLayout.addTab(tabLayout.newTab().setText("Yeni Sekme"));
                int newTabIndex = viewPageAdapter.getItemCount() - 1 ;
                viewPager2.setCurrentItem(newTabIndex);
                tabLayout.selectTab(tabLayout.getTabAt(newTabIndex));

            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedTabPosition = viewPager2.getCurrentItem();
                viewPageAdapter.removeTab(selectedTabPosition);
                tabLayout.removeTabAt(selectedTabPosition);
                viewPageAdapter.notifyDataSetChanged();
                if(viewPageAdapter.urls.size()==0)
                {
                    urlEditText.setText("");
                }


            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
                int selectedPosition = tab.getPosition();
                String url = viewPageAdapter.getUrl(selectedPosition);
                urlEditText.setText(url);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                TabLayout.Tab selectedTab = tabLayout.getTabAt(position);
                if (selectedTab != null) {
                    String pageTitle = selectedTab.getText().toString();
                    TextView titleTextView = findViewById(R.id.titleTextView);
                    if (titleTextView != null) {
                        titleTextView.setText(pageTitle);
                    }
                }
                else
                {
                    titleTextView.setText("");
                }
            }
        });

    }

    private void updateTabText(String pageTitle) {
        int currentPosition = viewPager2.getCurrentItem();
        TabLayout.Tab currentTab = tabLayout.getTabAt(currentPosition);
        if (currentTab != null && pageTitle != null && !pageTitle.isEmpty()) {
            currentTab.setText(pageTitle);
        }
    }
}