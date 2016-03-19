package sklaiber.com.snow.ui.detail;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import sklaiber.com.snow.R;
import sklaiber.com.snow.database.ResortColums;
import sklaiber.com.snow.database.ResortProvider;

public class InfoFragment extends DialogFragment
    implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

  private static final int URL_LOADER = 0;

  @Bind(R.id.info_phone_number_layout) RelativeLayout mPhoneNumberLayout;
  @Bind(R.id.info_homepage_layout) RelativeLayout mHomepageLayout;
  @Bind(R.id.info_phone_number) TextView mPhoneNumberTv;
  @Bind(R.id.info_homepage) TextView mHomepageTv;

  public InfoFragment() {
  }

  public static InfoFragment newInstance(String name) {
    InfoFragment infoFragment = new InfoFragment();
    Bundle args = new Bundle();
    args.putString("name", name);
    infoFragment.setArguments(args);
    return infoFragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_info, container);

    ButterKnife.bind(this, view);

    mPhoneNumberLayout.setOnClickListener(this);
    mHomepageLayout.setOnClickListener(this);

    getLoaderManager().initLoader(URL_LOADER, null, this);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getDialog().setTitle(R.string.title_info_fragment);
  }

  @Override public void onResume() {
    ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
    params.width = WindowManager.LayoutParams.MATCH_PARENT;
    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    super.onResume();
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.info_homepage_layout:
        Intent homepage = new Intent(Intent.ACTION_VIEW);
        homepage.setData(Uri.parse("http://" + mHomepageTv.getText().toString()));
        startActivity(homepage);
        break;
      case R.id.info_phone_number_layout:
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + mPhoneNumberTv.getText().toString()));
        startActivity(intent);
        break;
    }
  }

  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getContext(), ResortProvider.Resorts.CONTENT_URI, null, "name=?",
        new String[] { getArguments().getString("name") }, null);
  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    if (data.getColumnCount() != 0) {
      data.moveToFirst();
      mPhoneNumberTv.setText(data.getString(data.getColumnIndexOrThrow(ResortColums.PHONE_NUMBER)));
      mHomepageTv.setText(data.getString(data.getColumnIndexOrThrow(ResortColums.HOMEPAGE)));
    }
  }

  @Override public void onLoaderReset(Loader<Cursor> loader) {

  }
}
