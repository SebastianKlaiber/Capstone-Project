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

  public class ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.list_item_name_textview) TextView name;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
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

  public ResortAdapter(Context context, View emptyView) {
    mContext = context;
    mEmptyView = emptyView;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    mCursor.moveToPosition(position);
    holder.name.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(ResortColums.NAME)));
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
