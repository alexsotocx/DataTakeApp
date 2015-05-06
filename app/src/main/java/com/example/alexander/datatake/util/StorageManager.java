package com.example.alexander.datatake.util;

/**
 * Created by alexander on 5/05/15.
 */


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class StorageManager {

  private static final String JSON_SAVED_KEY = "saved_data";

  private static final int PRIVATE_MODE = 0;
  private static final String PREF_NAME = "saved_Data";

  private SharedPreferences pref;
  private Editor editor;
  private Context context;

  //Singleton
  private static StorageManager instance;

  protected StorageManager(Context context) {
    this.context = context;
    pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    editor = pref.edit();
  }

  public static StorageManager getInstance(Context context) {
    if (instance == null) {
      instance = new StorageManager(context);
    }
    return instance;
  }

  public static StorageManager getInstance() {
    return instance;
  }


  public void setSavedData(String savedJson) {
    editor.putString(JSON_SAVED_KEY, savedJson);

    editor.commit();
  }


  public String getSavedData() {
    return pref.getString(JSON_SAVED_KEY, null);
  }


}