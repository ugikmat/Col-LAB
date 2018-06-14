package id.poros.filkom.col_lab.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.poros.filkom.col_lab.R;
import id.poros.filkom.col_lab.Interface.OnFragmentInteractionListener;
import id.poros.filkom.col_lab.model.AgendaContent.AgendaItem;

public class AgendaRecyclerViewAdapter extends RecyclerView.Adapter<AgendaRecyclerViewAdapter.ViewHolder> {

    private final List<AgendaItem> mValues;
    private final OnFragmentInteractionListener mListener;

    public AgendaRecyclerViewAdapter(List<AgendaItem> items, OnFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_org, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mName.setText(mValues.get(position).name);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mName;
        public AgendaItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = view.findViewById(R.id.name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }
    }
}
