package com.example.noam.eventor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.maps.MapView;

public class EventPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
        Intent intent = getIntent();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_event_page);
// The View with the BottomSheetBehavior
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        ((LockableBottomSheetBehavior) behavior).setLocked(true);
                } else {
                    bottomSheet.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });

    behavior.setHideable(false);
        behavior.setPeekHeight(300);
    }




    public class LockableBottomSheetBehavior<V extends View> extends BottomSheetBehavior<V> {

        private boolean mLocked = false;

        public LockableBottomSheetBehavior() {}

        public LockableBottomSheetBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public void setLocked(boolean locked) {
            mLocked = locked;
        }

        @Override
        public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
            boolean handled = false;

            if (!mLocked) {
                handled = super.onInterceptTouchEvent(parent, child, event);
            }

            return handled;
        }

        @Override
        public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
            boolean handled = false;

            if (!mLocked) {
                handled = super.onTouchEvent(parent, child, event);
            }

            return handled;
        }

        @Override
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child, View directTargetChild, View target, int nestedScrollAxes) {
            boolean handled = false;

            if (!mLocked) {
                handled = super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
            }

            return handled;
        }

        @Override
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed) {
            if (!mLocked) {
                super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
            }
        }

        @Override
        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target) {
            if (!mLocked) {
                super.onStopNestedScroll(coordinatorLayout, child, target);
            }
        }

        @Override
        public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY) {
            boolean handled = false;

            if (!mLocked) {
                handled = super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
            }

            return handled;

        }
    }
}
