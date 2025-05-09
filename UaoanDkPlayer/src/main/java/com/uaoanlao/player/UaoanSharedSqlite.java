package com.uaoanlao.player;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UaoanSharedSqlite {
    private Activity activity;
    private SharedPreferences sharedPreferences;
    //初始化
    public void init(Activity mactivity,String name){
        activity=mactivity;
        sharedPreferences = activity.getSharedPreferences(name, MODE_PRIVATE);
    }
    //获取SharedPreferences
    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }
    public SharedPreferences SharedPreferences(){
        return sharedPreferences;
    }
    //写入数据
    public void put(String name,Boolean key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name, key);
        editor.apply();
    }
    public void put(String name,Float key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(name, key);
        editor.apply();
    }
    public void put(String name,int key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name, key);
        editor.apply();
    }
    public void put(String name,long key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(name, key);
        editor.apply();
    }
    public void put(String name,String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, key);
        editor.apply();
    }

    public void putBoolean(String name,Boolean key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name, key);
        editor.apply();
    }
    public void putFloat(String name,Float key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(name, key);
        editor.apply();
    }
    public void putInt(String name,int key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name, key);
        editor.apply();
    }
    public void putLong(String name,long key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(name, key);
        editor.apply();
    }
    public void putString(String name,String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, key);
        editor.apply();
    }

    //写入一个合集
    public void putStringList(String name, StringSet set){
        Set<String> hobbies = new HashSet<>();
        set.put(hobbies);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(name, hobbies);
        editor.apply();
    }
    public interface StringSet{
        void put(Set<String> name);
    }

    //删除
    public void remove(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(name);
        editor.apply();
    }

    //清除全部数据
    public void clear(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    //修改数据
    public boolean setString(String name,String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name,key);
        boolean success = editor.commit();
        if (success) {
            //数据修改成功
            return true;
        } else {
            //数据修改失败
            return false;
        }
    }
    public boolean setBoolean(String name,boolean key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name,key);
        boolean success = editor.commit();
        if (success) {
            //数据修改成功
            return true;
        } else {
            //数据修改失败
            return false;
        }
    }
    public boolean setFloat(String name,float key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(name,key);
        boolean success = editor.commit();
        if (success) {
            //数据修改成功
            return true;
        } else {
            //数据修改失败
            return false;
        }
    }
    public boolean setInt(String name,int key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name,key);
        boolean success = editor.commit();
        if (success) {
            //数据修改成功
            return true;
        } else {
            //数据修改失败
            return false;
        }
    }
    public boolean setLong(String name,long key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(name,key);
        boolean success = editor.commit();
        if (success) {
            //数据修改成功
            return true;
        } else {
            //数据修改失败
            return false;
        }
    }

    //异步提交修改，没有返回值，通常效率更高，因为不会阻塞主线程。
    public void apply(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.apply();
    }

    //获取数据
    public boolean getBoolean(String name){
        return sharedPreferences.getBoolean(name, false);
    }
    public float getFloat(String name){
        return sharedPreferences.getFloat(name, 0.0f);
    }
    public int getInt(String name){
        return sharedPreferences.getInt(name, 0);
    }
    public long getLong(String name){
        return sharedPreferences.getLong(name, 0);
    }
    public String getString(String name){
        return sharedPreferences.getString(name, "未命名");
    }

    //获取全部数据
    public void getAllList(AllList list){
        Map<String,?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String,?> entry : allEntries.entrySet()){
            list.getList(entry.getKey(),entry.getValue().toString(),entry);
        }
    }
    public interface AllList{
        void getList(String name,String key,Map.Entry<String,?> entry);
    }


    //注册一个监听器，当 SharedPreferences 中的数据发生变化时会收到通知。
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    public void setOnUaoanSharedSqliteChangeListener(OnUaoanSharedSqliteChangeListener onUaoanSharedSqliteChangeListener){
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                //Log.d("SharedPreferences", "键 " + key + " 的值发生了变化");
                onUaoanSharedSqliteChangeListener.onSharedPreferenceChanged(sharedPreferences,key);
            }
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }
    public interface OnUaoanSharedSqliteChangeListener{
        void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key);
    }


    //注销之前注册的监听器。
    public void removeOnUaoanSharedSqliteChangeListener(){
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

}