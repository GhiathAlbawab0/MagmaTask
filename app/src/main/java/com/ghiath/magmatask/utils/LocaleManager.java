package com.ghiath.magmatask.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;


public class LocaleManager {

    private static final String SHARED_CODE="LANG_CODE";
    private static final String SHARED_KEY="LANG_KEY";

    public static final String ARABIC_LANGUAGE="A";
    public static final String ENGLISH_LANGUAGE="E";

//    public static void setLocale(Context c) {
//        setNewLocale(c, getLanguage(c));
//    }

    Context context;

    public LocaleManager(Context context) {
        this.context = context;
    }
    public String getLanguage()
    {
        if(context!=null)
        return getLanguage(context);
        else return null;
    }

    public static Boolean isLangArabic(Context context)
    {return getLanguage(context).equalsIgnoreCase(ARABIC_LANGUAGE);}
    public static void setNewLocale(Context c, String language) {
        persistLanguage(c, language);
        updateResources(c, language);
    }

    public static String getLanguage(Context c) {
        SharedPreferences sharedPreferences =c.getSharedPreferences(SHARED_CODE, Context.MODE_PRIVATE);

        String language =  sharedPreferences.getString(SHARED_KEY,null);
        if (language==null)
        {
            language= getDeviceLanguage();
            persistLanguage(c,language);
        }
        return language;
    }

    private static void persistLanguage(Context c, String language) {
        SharedPreferences sharedPreferences =c.getSharedPreferences(SHARED_CODE, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(SHARED_KEY,language).apply();
    }

    private static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
     static String getDeviceLanguage()
    {
        return Locale.getDefault().getLanguage().equalsIgnoreCase("en")?"E":"A";
    }
}