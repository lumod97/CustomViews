package com.myaconsultancy.customviews.HorizontalSliderFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.github.lumod97.customviews.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

@SuppressLint("CustomView")
public class HorizontalSliderFragment extends FrameLayout {
    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;
    private BottomNavigationView bottomNavigationView;
    private OnChangePageListener onPageChangeListener;

    public HorizontalSliderFragment(@NonNull Context context) {
        super(context);
        init(context);
    }

    public HorizontalSliderFragment(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalSliderFragment(@NonNull Context context, List<Fragment> fragmentList, FragmentActivity activity) {
        super(context);
        init(context);
        setFragments(fragmentList, activity);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.horizontal_slider_fragment_layout, this, true);
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    public void setFragments(List<Fragment> fragmentList, FragmentActivity activity) {
        if (adapter == null) {
            adapter = new ViewPagerAdapter(activity);
            viewPager.setAdapter(adapter);
        }
        adapter.setFragments(fragmentList);
    }


    public void setMenuItems(List<MenuItemsSlider> menuItemsSlider) {
        Menu menu = bottomNavigationView.getMenu();
        menu.clear();

        int i = 0;
        for (MenuItemsSlider item :
                menuItemsSlider) {
            menu.add(0, i, i, item.title).setIcon(item.icon);
        }
        setupNavigation(menuItemsSlider);
    }

    private void setupNavigation(List<MenuItemsSlider> menuItemsSlider) {
        // Sincronizar ViewPager2 con el menú
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId >= 0 && itemId < menuItemsSlider.size()) {
                viewPager.setCurrentItem(itemId);
            }
            return true;
        });

        // Sincronizar menú cuando se cambia de fragment manualmente
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position < bottomNavigationView.getMenu().size()) {
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);
                    if (onPageChangeListener != null) {
                        onPageChangeListener.onChangePage(position, bottomNavigationView.getMenu().getItem(position).getTitle().toString());
                    }
                }
            }

        });
    }

    public void setOnPageChangeListener(OnChangePageListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    public void setShowBottomNavigationView(boolean show) {
        if (show) {
            bottomNavigationView.setVisibility(VISIBLE);
        } else {
            bottomNavigationView.setVisibility(GONE);
        }
    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }

    public interface OnChangePageListener {
        void onChangePage(int position, String title);
    }

    public static class MenuItemsSlider {
        private String title;
        private int icon;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }
    }
}
