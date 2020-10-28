package com.ghiath.magmatask.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.ghiath.magmatask.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import timber.log.Timber;

public class GeneralUtils {

    public static <T> boolean areEqualIgnoringOrder(List<T> list1, List<T> list2, Comparator<? super T> comparator) {

        // if not the same size, lists are not equal
        if (list1.size() != list2.size()) {
            return false;
        }

        // create sorted copies to avoid modifying the original lists
        List<T> copy1 = new ArrayList<>(list1);
        List<T> copy2 = new ArrayList<>(list2);

        Collections.sort(copy1, comparator);
        Collections.sort(copy2, comparator);

        // iterate through the elements and compare them one by one using
        // the provided comparator.
        Iterator<T> it1 = copy1.iterator();
        Iterator<T> it2 = copy2.iterator();
        while (it1.hasNext()) {
            T t1 = it1.next();
            T t2 = it2.next();
            if (comparator.compare(t1, t2) != 0) {
                // as soon as a difference is found, stop looping
                return false;
            }
        }
        return true;
    }

    public static <T> List<T> filterList(List<T> list, String query, Contained<T> contained) {


        if (query == null)
            return list;
        if (query.isEmpty())
            return list;

        List<T> copy1 = new ArrayList<>();


        for (T t1 : list) {
            T t = contained.isContained(t1, query);
            if (t != null)
                copy1.add(t);
        }
        return copy1;
    }

    public interface Contained<C> {
        C isContained(C item, String s);
    }

    public static void setViewMargins(Context con, ViewGroup.LayoutParams params,
                                      int left, int top, int right, int bottom, View view) {

        final float scale = con.getResources().getDisplayMetrics().density;
        // convert the DP into pixel
        int pixel_left = (int) (left * scale + 0.5f);
        int pixel_top = (int) (top * scale + 0.5f);
        int pixel_right = (int) (right * scale + 0.5f);
        int pixel_bottom = (int) (bottom * scale + 0.5f);

        ViewGroup.MarginLayoutParams s = (ViewGroup.MarginLayoutParams) params;
        s.setMargins(pixel_left, pixel_top, pixel_right, pixel_bottom);

        view.setLayoutParams(params);
    }

    public static void copyToClipBoard(Context context, Object s) {
        GeneralUtils.showDialogNoButtons(context, context.getString(R.string.copied), 500);
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(null, new Gson().toJson(s));
        clipboard.setPrimaryClip(clip);
    }

    public static void showDialog(Context context,
                                  String msg
            , @org.jetbrains.annotations.Nullable String positiveButton
            , @org.jetbrains.annotations.Nullable DialogInterface.OnClickListener positiveOnClick
            , @org.jetbrains.annotations.Nullable String negativeButton
            , @org.jetbrains.annotations.Nullable DialogInterface.OnClickListener negativeOnClick
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(msg);
        if (negativeButton == null || !negativeButton.isEmpty())
            builder.setNegativeButton(
                    negativeButton == null ? context.getString(R.string.cancel) : negativeButton
                    , (dialog, id) -> {
                        if (negativeOnClick != null)
                            negativeOnClick.onClick(dialog, id);

                        try {
                            dialog.dismiss();
                        } catch (Exception e) {
                            Timber.e(e);
                        }
                    });
        if (positiveButton != null && positiveOnClick != null)
            builder.setPositiveButton(positiveButton, positiveOnClick);

        builder.create().show();
    }

    public static void showDialogNoButtons(Context context,
                                           String msg
            , int timeintervalInMilliSec
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);


        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialog -> new CountDownTimer(timeintervalInMilliSec, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                try {
                    alertDialog.dismiss();
                } catch (Exception ee) {
                    Timber.e(ee);
                }


            }
        }.start());
        alertDialog.show();
    }

    public static void showDialogNoButtonsWithImg(Context context
            , int imgId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ImageView img_question_mark_feature = new ImageView(context);
        img_question_mark_feature.setImageResource(imgId);

        builder.setView(img_question_mark_feature);


        AlertDialog alertDialog = builder.create();
        if (alertDialog.getWindow() != null)
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        alertDialog.show();
    }


    public static void hideKeyboardFromFragment(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
