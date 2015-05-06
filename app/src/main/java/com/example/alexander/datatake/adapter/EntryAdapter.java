package com.example.alexander.datatake.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alexander.datatake.DataEntry;
import com.example.alexander.datatake.R;

import java.util.Date;
import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {
  private List<DataEntry> mDataset;
  private Context context;

  public EntryAdapter(List<DataEntry> myDataset, Context context) {
    mDataset = myDataset;
    this.context = context;
  }

  public void addEntry(DataEntry entry) {
    mDataset.add(entry);
    this.notifyItemInserted(mDataset.size() - 1);
    DataEntry.saveData(mDataset, context);
  }

  public void changeEntry() {
    if (!mDataset.isEmpty()) {
      DataEntry entry = mDataset.get(mDataset.size() - 1);
      entry.setServiceEndTime(new Date().getTime());
      mDataset.set(mDataset.size() - 1, entry);
      this.notifyItemChanged(mDataset.size() - 1);
      DataEntry.saveData(mDataset, context);
    }
  }

  public void setmDataset(List<DataEntry> mDataset) {
    this.mDataset = mDataset;
  }

  public List<DataEntry> getmDataset() {
    return mDataset;
  }

  @Override
  public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View root = inflater.inflate(R.layout.entry_list_item, parent, false);
    return new EntryViewHolder(root);
  }


  @Override
  public void onBindViewHolder(EntryViewHolder entryViewHolder, int i) {
    DataEntry entryPoint = mDataset.get(i);
    entryViewHolder.arriveTimeText.setText(entryPoint.getArriveFormatedTime());
    entryViewHolder.serviceStartTime.setText(entryPoint.getServiceStartFormatedTime());
    entryViewHolder.serviceEndTime.setText(entryPoint.getServiceEndFormatedTime());
  }

  @Override
  public int getItemCount() {
    return mDataset.size();
  }

  public static class EntryViewHolder extends RecyclerView.ViewHolder {

    public TextView arriveTimeText;

    public TextView serviceStartTime;
    public TextView serviceEndTime;


    public EntryViewHolder(View v) {
      super(v);
      arriveTimeText = (TextView) v.findViewById(R.id.arrive_time);
      serviceStartTime = (TextView) v.findViewById(R.id.start_service_time);
      serviceEndTime = (TextView) v.findViewById(R.id.exit_time);
    }


  }
}