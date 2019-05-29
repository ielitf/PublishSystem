package com.hskj.publishsystem.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
	    static SharedPreferences sp;
 
	    public static void init(Context context, String name) {
	        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
	    }
	    
        private static final String CACHED_HOMEPAGE_DATA="cached_homepage_data";

        public static void setCachedHomepageData(String jsonStr){
        	if (null != sp) {
	            sp.edit().putString(CACHED_HOMEPAGE_DATA, jsonStr).commit();
	        }
        }
        public static String getCachedHomepageData(){
        	 if (null != sp) {
 	            return sp.getString(CACHED_HOMEPAGE_DATA, null);
 	        }
 	        return null;
        }
        
//
	    private static final String IS_FIRST_USE="Is_app_first_use";

	    public static void setIsFirstUse(boolean value){
	    	 if (null != sp) {
		            sp.edit().putBoolean(IS_FIRST_USE, value).commit();
		        }
	    }
	    public static boolean getIsFirstUse(){
	    	if (null != sp) {
	            return sp.getBoolean(IS_FIRST_USE, true);
	        }
	        return true;
	    }
	    
//	    public static boolean isLogin(){
//	    	String custName=getCachedUsername();
//	    	String custId=getCachedCustId();
//	    	if (TextUtils.isEmpty(custId)||custId.equals("null")) {
//				return false;
//			}
//	    	/*if (TextUtils.isEmpty(custName)||custName.equals("null")) {
//				return false;
//			}*/
//
//	    	return true;
//	    }
//	private static final String KEY_CACHED_USERNAME = "username";
//
//	    public static void setCachedUsername(String username) {
//	        if (null != sp) {
//	            sp.edit().putString(KEY_CACHED_USERNAME, username).commit();
//	        }
//	    }
//
//	    public static String getCachedUsername() {
//	        if (null != sp) {
//	            return sp.getString(KEY_CACHED_USERNAME, null);
//	        }
//	        return null;
//	    }
//
//	    private static final String CACHED_CUSTID="custId";
//
//	    public static void  setCachedCustId(String custId){
//	    	  if (null != sp) {
//		            sp.edit().putString(CACHED_CUSTID, custId).commit();
//		        }
//	    }
//	    public static String getCachedCustId(){
//	    	if (null != sp) {
//	            return sp.getString(CACHED_CUSTID, null);
//	        }
//	        return null;
//	    }
//
//	    private static final String KEY_CACHED_PASSWORD="password";
//	    public static void setCachedPassword(String password){
//	    	 if (null != sp) {
//		            sp.edit().putString(KEY_CACHED_PASSWORD, password).commit();
//		        }
//	    }
//	    public static String getCachedPassword() {
//	        if (null != sp) {
//	            return sp.getString(KEY_CACHED_PASSWORD, null);
//	        }
//	        return null;
//	    }
//	    private static final String KEY_CACHED_AVATAR_PATH = "Chengde__cached_avatar_path";
//
//	    public static void setCachedAvatarPath(String path) {
//	        if (null != sp) {
//	            sp.edit().putString(KEY_CACHED_AVATAR_PATH, path).commit();
//	        }
//	    }
//
//	    public static String getCachedAvatarPath() {
//	        if (null != sp) {
//	            return sp.getString(KEY_CACHED_AVATAR_PATH, null);
//	        }
//	        return null;
//	    }
//	    private static final String KEY_CACHED_FIX_PROFILE_FLAG = "fixProfileFlag";
//
//	    public static void setCachedFixProfileFlag(boolean value) {
//	        if(null != sp){
//	            sp.edit().putBoolean(KEY_CACHED_FIX_PROFILE_FLAG, value).commit();
//	        }
//	    }
//
//	    public static boolean getCachedFixProfileFlag(){
//	        if(null != sp){
//	            return sp.getBoolean(KEY_CACHED_FIX_PROFILE_FLAG, false);
//	        }
//	        return false;
//	    }
//
//	    private static final String SOFT_KEYBOARD_HEIGHT = "SoftKeyboardHeight";
//
//	    public static void setCachedKeyboardHeight(int height){
//	        if(null != sp){
//	            sp.edit().putInt(SOFT_KEYBOARD_HEIGHT, height).commit();
//	        }
//	    }
//
//	    public static int getCachedKeyboardHeight(){
//	        if(null != sp){
//	            return sp.getInt(SOFT_KEYBOARD_HEIGHT, 0);
//	        }
//	        return 0;
//	    }
//
//	    private static final String WRITABLE_FLAG = "writable";
//	    public static void setCachedWritableFlag(boolean value){
//	        if(null != sp){
//	            sp.edit().putBoolean(WRITABLE_FLAG, value).commit();
//	        }
//	    }
//
//	    public static boolean getCachedWritableFlag(){
//	        if(null != sp){
//	            return sp.getBoolean(WRITABLE_FLAG, true);
//	        }
//	        return true;
//	    }
	 /*   private static final String SELECTED_IP = "WSDL_URL";
	    public static void setSelectedIp(String value){
	    	if(null != sp){
	            sp.edit().putString(SELECTED_IP, value+CodeConstants.URL_SUFFIX).commit();
	        }
	    }
	    public static String getSelectedIp(){
	    	 if(null != sp){
		            return sp.getString(SELECTED_IP, CodeConstants.WSDL_URL);
		        }
		        return CodeConstants.WSDL_URL;
	    	
	    }*/
}
