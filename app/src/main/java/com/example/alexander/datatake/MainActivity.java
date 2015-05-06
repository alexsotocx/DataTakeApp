package com.example.alexander.datatake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.alexander.datatake.adapter.EntryAdapter;

import java.util.Date;
import java.util.List;


public class MainActivity extends ActionBarActivity {

  private RecyclerView mRecyclerView;
  private Button arriveTimeButton;
  private Button serviceStartTimeButton;
  private Button serviceEndTimeButton;
  private EntryAdapter mAdapter;
  private List<DataEntry> dataList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    prepareInterface();
  }

  private void prepareInterface() {
    arriveTimeButton = (Button) findViewById(R.id.arrive_time_button);
    serviceStartTimeButton = (Button) findViewById(R.id.service_start_time_button);
    serviceEndTimeButton = (Button) findViewById(R.id.service_end_time_button);
    ClickListener clickListener = new ClickListener();
    arriveTimeButton.setOnClickListener(clickListener);
    serviceStartTimeButton.setOnClickListener(clickListener);
    serviceEndTimeButton.setOnClickListener(clickListener);

    dataList = DataEntry.getSavedData(getApplicationContext());
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
    mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    mRecyclerView.setHasFixedSize(false);
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mRecyclerView.setLayoutManager(mLayoutManager);
    mAdapter = new EntryAdapter(dataList, getApplicationContext());

    mRecyclerView.setAdapter(mAdapter);
  }


  class ClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
      int viewId = v.getId();
      DataEntry entry = new DataEntry();
      switch (viewId) {
        case R.id.arrive_time_button:
          //Toast.makeText(getApplicationContext(), "Boton 1 "+ new Date().toString() + " oprimido", Toast.LENGTH_SHORT).show();
          entry.setArriveTime(new Date().getTime());
          mAdapter.addEntry(entry);
          mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
          break;
        case R.id.service_start_time_button:
          //Toast.makeText(getApplicationContext(), "Boton 2 "+ new Date().toString() + " oprimido", Toast.LENGTH_SHORT).show();
          entry.setServiceStartTime(new Date().getTime());
          mAdapter.addEntry(entry);
          mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
          break;
        case R.id.service_end_time_button:
          //Toast.makeText(getApplicationContext(), "Boton 3 "+ new Date().toString() + " oprimido", Toast.LENGTH_SHORT).show();
          mAdapter.changeEntry();
          break;
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.export_setting) {
      Intent intent = new Intent(Intent.ACTION_SEND);
      intent.setType("text/plain");
      intent.putExtra(Intent.EXTRA_SUBJECT, "Datos de Simulacion");
      intent.putExtra(Intent.EXTRA_TEXT, DataEntry.generateCSV(mAdapter.getmDataset()));
      startActivity(Intent.createChooser(intent, "Send Email"));
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
