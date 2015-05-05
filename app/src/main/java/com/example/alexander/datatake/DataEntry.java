package com.example.alexander.datatake;

import android.content.Context;

import com.example.alexander.datatake.util.StorageManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alexander on 5/05/15.
 */
public class DataEntry {
  private Long arriveTime;
  private Long exitTime;
  private Long serviceStartTime;
  private Long serviceEndTime;
  private static final SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm:ss - dd MMM") ;

  public Long getArriveTime() {
    return arriveTime;
  }

  public void setArriveTime(Long arriveTime) {
    this.arriveTime = arriveTime;
  }

  public Long getExitTime() {
    return exitTime;
  }

  public void setExitTime(Long exitTime) {
    this.exitTime = exitTime;
  }

  public Long getServiceStartTime() {
    return serviceStartTime;
  }

  public void setServiceStartTime(Long serviceStartTime) {
    this.serviceStartTime = serviceStartTime;
  }

  public Long getServiceEndTime() {
    return serviceEndTime;
  }

  public void setServiceEndTime(Long serviceEndTime) {
    this.serviceEndTime = serviceEndTime;
  }

  public String getArriveFormatedTime(){
    if(arriveTime == null){
      return "No tomada";
    }else{
      Date date = new Date(arriveTime);
      return FORMAT.format(date);
    }
  }

  public String getServiceStartFormatedTime(){
    if(serviceStartTime == null){
      return "No tomada";
    }else{
      Date date = new Date(serviceStartTime);
      return FORMAT.format(date);
    }
  }

  public String getServiceEndFormatedTime(){
    if(serviceEndTime == null){
      return "No tomada";
    }else{
      Date date = new Date(serviceEndTime);
      return FORMAT.format(date);
    }
  }

  public JSONObject toJson(){
    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("llegada", arriveTime);
      jsonObject.put("tiempo_inicio_servicio", serviceStartTime);
      jsonObject.put("tiempo_fin_servicio", serviceEndTime);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return jsonObject;
  }

  public static String generateSaveData(List<DataEntry> dataEntries){
    JSONArray jsonArray = new JSONArray();
    for(DataEntry entry: dataEntries){
      jsonArray.put(entry.toJson());
    }
    return jsonArray.toString();
  }

  public static void saveData(List<DataEntry> dataEntries, Context context){
    StorageManager storageManager = StorageManager.getInstance(context);
    storageManager.setSavedData(generateSaveData(dataEntries));
  }

  public static List<DataEntry> getSavedData(Context context){
    StorageManager storageManager = StorageManager.getInstance(context);
    String jsonSavedData = storageManager.getSavedData();
    List<DataEntry> dataEntries = new ArrayList<>();
    if(jsonSavedData != null) {
      try {
        JSONArray jsonArray = new JSONArray(jsonSavedData);
        for (int i = 0; i < jsonArray.length(); i++) {
          JSONObject jsonObject = jsonArray.getJSONObject(i);
          DataEntry entry = new DataEntry();
          if (jsonObject.has("llegada"))
            entry.setArriveTime(jsonObject.getLong("llegada"));
          if (jsonObject.has("tiempo_inicio_servicio"))
            entry.setServiceStartTime(jsonObject.getLong("tiempo_inicio_servicio"));
          if (jsonObject.has("tiempo_fin_servicio"))
            entry.setServiceEndTime(jsonObject.getLong("tiempo_fin_servicio"));
          dataEntries.add(entry);
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return dataEntries;
  }

  public static String generateCSV(List<DataEntry> dataEntries){
    StringBuilder stringBuilder = new StringBuilder();
    for(DataEntry entry: dataEntries){
      stringBuilder.append(entry.arriveTime).append(',');
      stringBuilder.append(entry.serviceStartTime).append(',');
      stringBuilder.append(entry.serviceEndTime).append('\n');
    }
    return stringBuilder.toString();
  }

}

