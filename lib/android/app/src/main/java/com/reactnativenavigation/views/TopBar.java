package com.reactnativenavigation.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reactnativenavigation.parse.Button;
import com.reactnativenavigation.parse.NavigationOptions;
import com.reactnativenavigation.utils.ImageUtils;
import com.reactnativenavigation.utils.UiUtils;
import com.reactnativenavigation.viewcontrollers.ContainerViewController;

import java.util.ArrayList;

public class TopBar extends AppBarLayout {
	private final Toolbar titleBar;
	private ContainerViewController.ContainerView containerView;

	public TopBar(final Context context, ContainerViewController.ContainerView containerView) {
		super(context);

		this.titleBar = new Toolbar(context);



		this.containerView = containerView;
		addView(titleBar);
    }

	public void setTitle(String title) {
		titleBar.setTitle(title);
	}

	public String getTitle() {
		return titleBar.getTitle() != null ? titleBar.getTitle().toString() : "";
	}

	public void setTitleTextColor(@ColorInt int color) {
		titleBar.setTitleTextColor(color);
	}

	public void setTitleFontSize(float size) {
		TextView titleTextView = getTitleTextView();
		if (titleTextView != null) {
			titleTextView.setTextSize(size);
		}
	}

	public void setTitleTypeface(Typeface typeface) {
		TextView titleTextView = getTitleTextView();
		if (titleTextView != null) {
			titleTextView.setTypeface(typeface);
		}
	}

	public void setButtons(ArrayList<Button> leftButtons, ArrayList<Button> rightButtons) {
		setLeftButtons(leftButtons);
		setRightButtons(rightButtons);
    }

	public TextView getTitleTextView() {
		return findTextView(titleBar);
	}

	@Override
	public void setBackgroundColor(@ColorInt int color) {
		titleBar.setBackgroundColor(color);
	}

	public Toolbar getTitleBar() {
		return titleBar;
	}

	@Nullable
	private TextView findTextView(ViewGroup root) {
		for (int i = 0; i < root.getChildCount(); i++) {
			View view = root.getChildAt(i);
			if (view instanceof TextView) {
				return (TextView) view;
			}
			if (view instanceof ViewGroup) {
				return findTextView((ViewGroup) view);
			}
		}
		return null;
	}

	private void setLeftButtons(ArrayList<Button> leftButtons) {
		if(leftButtons == null || leftButtons.isEmpty()) {
			titleBar.setNavigationIcon(null);
			return;
		}

		if(leftButtons.size() > 1) {
			Log.w("RNN", "Use a custom TopBar to have more than one left button");
		}

		Button leftButton = leftButtons.get(0);
		setLeftButton(leftButton);
	}

	private void setLeftButton(final Button button) {
		TitleBarButton leftBarButton = new TitleBarButton(this.containerView, this.titleBar, button);
		leftBarButton.setNavigationIcon(getContext());
	}

	private void setRightButtons(ArrayList<Button> rightButtons) {
		if(rightButtons == null || rightButtons.size() == 0) {
			return;
		}

		Menu menu = getTitleBar().getMenu();
		menu.clear();

		for (int i = 0; i < rightButtons.size(); i++){
	   		Button button = rightButtons.get(i);
			TitleBarButton titleBarButton = new TitleBarButton(this.containerView, this.titleBar, button);
			titleBarButton.addToMenu(getContext(), menu);
       }
    }


}
