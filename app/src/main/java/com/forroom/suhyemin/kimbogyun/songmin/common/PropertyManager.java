package com.forroom.suhyemin.kimbogyun.songmin.common;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.forroom.suhyemin.kimbogyun.songmin.ForRoomApplication;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.LoginValueObject;

/**
 * Created by ssong on 2016-02-17.
 */
public class PropertyManager {
    private static PropertyManager instance;
    public static PropertyManager getInstance(){
        if(instance == null){
            instance = new PropertyManager();
        }
        return instance;
    }
    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private PropertyManager(){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(ForRoomApplication.getFRContext());
        mEditor = mPrefs.edit();
    }

    public static final String USER_ID = "userid";
    public static final String USER_NAME = "username";
    public static final String USER_WAY = "userway";
    public static final String GOOD_VERSION = "goodversion";
    public static final String USER_PASSWORD = "usrpwd";
    public static final String USER_EMAIL = "useremail";
    public static final String PUSH_SWITCH = "push";

    public void setLogin(LoginValueObject lg){
        mEditor.putString(USER_ID, lg.userid);
        mEditor.putString(USER_NAME, lg.name);
        mEditor.putString(USER_WAY, lg.way);
        mEditor.commit();
    }

    public void setEmail(String email){
        mEditor.putString(USER_EMAIL, email);
        mEditor.commit();
    }

    public void setID(String id){
        mEditor.putString(USER_ID, id);
        mEditor.commit();
    }
    public void setWay(String wat){
        mEditor.putString(USER_WAY, wat);
        mEditor.commit();
    }
    public void setName(String nm){
        mEditor.putString(USER_NAME, nm);
        mEditor.commit();
    }
    public void setPassword(String pwd){
        mEditor.putString(USER_PASSWORD, pwd);
        mEditor.commit();
    }
    public void setPushSwitch(String push){
        mEditor.putString(PUSH_SWITCH, push);
        mEditor.commit();
    }
    public String getPassword(){
        return mPrefs.getString(USER_PASSWORD, "");
    }
    public String getid(){
        return mPrefs.getString(USER_ID, "");
    }
    public String getname(){
        return mPrefs.getString(USER_NAME, "");
    }
    public String getway(){
        return mPrefs.getString(USER_WAY, "");
    }
    public String getmail(){
        return mPrefs.getString(USER_EMAIL, "");
    }
    public String getPushSwitch(){
        return mPrefs.getString(PUSH_SWITCH, "true");
    }

    public void setGoodVersion(int vs){
        mEditor.putInt(GOOD_VERSION, vs);
        mEditor.commit();

    }
    public int getGoodVersion(){
        return mPrefs.getInt(GOOD_VERSION, 0);
    }
}
