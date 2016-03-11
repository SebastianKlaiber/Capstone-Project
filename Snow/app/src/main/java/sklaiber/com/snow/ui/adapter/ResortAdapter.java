package sklaiber.com.snow.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import sklaiber.com.snow.R;
import sklaiber.com.snow.database.ResortColums;

/**
 * Created by sklaiber on 02.03.16.
 */
public class ResortAdapter extends RecyclerView.Adapter<ResortAdapter.ViewHolder> {

  private Cursor mCursor;
  final private Context mContext;
  final private View mEmptyView;
  final private OnClickHandler mOnClickHandler;

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @Bind(R.id.list_item_name_textview) TextView name;
    @Bind(R.id.list_item_conditions_textview) TextView conditions;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
      int adapterPosition = getAdapterPosition();
      mCursor.moveToPosition(adapterPosition);
      mOnClickHandler.onClick(mCursor.getString(mCursor.getColumnIndexOrThrow(ResortColums.NAME)), 47.2268f, 12.0432f, this);
    }

  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (parent instanceof RecyclerView) {
      int layoutId = R.layout.list_item_resort;
      View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
      view.setFocusable(true);
      return new ViewHolder(view);
    } else {
      throw new RuntimeException("Not bound to RecyclerView");
    }
  }

  public static interface OnClickHandler {
    void onClick(String name, float latitude, float longitude, ViewHolder vh);
  }

  public ResortAdapter(Context context, View emptyView, OnClickHandler onClickHandler) {
    mContext = context;
    mEmptyView = emptyView;
    mOnClickHandler = onClickHandler;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    mCursor.moveToPosition(position);
    holder.name.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(ResortColums.NAME)));
    holder.conditions.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(ResortColums.CONDITIONS)));
  }

  @Override public int getItemCount() {
    if (null == mCursor) return 0;
    return mCursor.getCount();
  }

  public void swapCursor(Cursor newCursor) {
    mCursor = newCursor;
    notifyDataSetChanged();
    mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
  }

  public Cursor getCursor() {
    return mCursor;
  }
}
