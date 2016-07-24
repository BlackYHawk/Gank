package com.hawk.gank.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtil {
	
	private static final String PREFERENCE_NAME = "hawk";      //首选项名
	private static final String HEAD_PATH = "head";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	
	public static String getHeadPath(Context context)
	{
		if(context == null){
			return null;
		}
		SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, 0);
		
		if(pref == null)
			return null;
		return pref.getString(HEAD_PATH, null);
	}
	
	public static boolean setHeadPath(Context context, String path)
	{
		if(context == null){
			return false;
		}
		SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, 0);
		
		Editor editor = pref.edit();
		
		if(editor == null)
			return false;
		editor.putString(HEAD_PATH, path);
		
		return editor.commit();
	}

	public static String getUsername(Context context)
	{
		if(context == null){
			return null;
		}
		SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, 0);

		if(pref == null)
			return null;
		return pref.getString(USERNAME, null);
	}

	public static boolean setUsername(Context context, String username)
	{
		if(context == null){
			return false;
		}
		SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, 0);

		Editor editor = pref.edit();

		if(editor == null)
			return false;
		editor.putString(USERNAME, username);

		return editor.commit();
	}


	public static String getPassword(Context context)
	{
		if(context == null){
			return null;
		}
		SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, 0);

		if(pref == null)
			return null;
		return pref.getString(PASSWORD, null);
	}

	public static boolean setPassword(Context context, String password)
	{
		if(context == null){
			return false;
		}
		SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, 0);

		Editor editor = pref.edit();

		if(editor == null)
			return false;
		editor.putString(PASSWORD, password);

		return editor.commit();
	}
	
}
