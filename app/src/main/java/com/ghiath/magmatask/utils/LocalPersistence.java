package com.ghiath.magmatask.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import timber.log.Timber;


/**
 *
 * Writes/reads an object to/from a private local file
 *
 *
 */
public class LocalPersistence {


    /**
     *
     * @param context
     * @param object
     * @param filename
     */

//    public static final String REGISTRATION_FILE="REGISTRATION_FILE";
//    public static final String ATTACHMENT_FILE="ATTACHMENT_FILE";
    public static final String USER_LOGIN_FILE="USER_LOGIN_FILE";
    public static final String CREATE_NEW_POST_FILE="CREATE_NEW_POST_FILE";

    Context context;

    public LocalPersistence(Context context) {
        this.context = context;
    }

//    public  void putUserObjectWrapper( User object)
//    {
//        writeObjectToFile(context,object,USER_LOGIN_FILE);
//    }
    public  void deleteUserObjectWrapper( )
    {
        deleteFile(context,USER_LOGIN_FILE);
    }
//    public User getUserObjectWrapper()
//    {
//        return (User)readObjectFromFile(context,USER_LOGIN_FILE);
//    }


    private static void writeObjectToFile(Context context, Object object, String filename) {

        ObjectOutputStream objectOut = null;
        try {

            FileOutputStream fileOut = context.openFileOutput(filename, Activity.MODE_PRIVATE);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(object);
            fileOut.getFD().sync();

        } catch (IOException e) {
            e.printStackTrace();
            Timber.e(e);
        } finally {
            if (objectOut != null) {
                try {
                    objectOut.close();
                } catch (IOException e) {
                    // do nowt
                }
            }
        }
    }
    private static void deleteFile(Context context, String filename) {

//        ObjectOutputStream objectOut = null;
        try {

            File file = context.getApplicationContext().getFileStreamPath(filename);
            Timber.d("ghiath file.getAbsolutePath() =%s",file.getAbsolutePath());
            if(file.exists())
                Timber.d("ghiath file.delete =%s", file.delete()); // done here
            if(file.exists()){
                Timber.d("ghiath context.getApplicationContext().deleteFile =%s", context.getApplicationContext().deleteFile(file.getName()));
            }
            if(file.exists()){
                String root = Environment.getExternalStorageDirectory().toString();
                File file2 = new File(root + file.getAbsolutePath());
                Timber.d("ghiath file2.delete =%s", file2.delete());
            }

        } catch (Exception e) {
            e.printStackTrace();
            Timber.e(e);

        }
    }


    /**
     *
     * @param context
     * @param filename
     * @return
     */
    private static Object readObjectFromFile(Context context, String filename) {

        ObjectInputStream objectIn = null;
        Object object = null;
        try {

            FileInputStream fileIn = context.getApplicationContext().openFileInput(filename);
            objectIn = new ObjectInputStream(fileIn);
            object = objectIn.readObject();

        } catch (FileNotFoundException e) {
            // Do nothing
        } catch (IOException e) {
            e.printStackTrace();
            Timber.e(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Timber.e(e);
        } finally {
            if (objectIn != null) {
                try {
                    objectIn.close();
                } catch (IOException e) {
                    // do nowt
                }
            }
        }

        return object;
    }

}