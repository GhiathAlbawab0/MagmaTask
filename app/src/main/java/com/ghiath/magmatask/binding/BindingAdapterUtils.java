package com.ghiath.magmatask.binding;

import android.view.View;

import androidx.databinding.BindingAdapter;

public class BindingAdapterUtils {
//    @JvmStatic
//    @BindingAdapter("app:loadUrl")
//     public static void  loadUrl(ImageView imageView,String url)
//    {
//        Glide.with(imageView.getContext()).load(url).into(imageView);
//    }
//    @JvmStatic
//    @BindingAdapter("loadUrl")
//    public static void  loadUrl(ImageView imageView,int url)
//    {
//        Glide.with(imageView.getContext()).load(url).into(imageView);
//    }
//    @JvmStatic
    @BindingAdapter("visibleGone")
     public static void  visibleGone(View imageView,Boolean isVisible)
    {
        if(isVisible!=null)
        imageView.setVisibility(isVisible? View.VISIBLE:View.GONE);
    } @BindingAdapter("invisibleGone")
     public static void  invisibleGone(View imageView,Boolean isVisible)
    {
        if(isVisible!=null)
        imageView.setVisibility(isVisible? View.INVISIBLE:View.GONE);
    }@BindingAdapter("visibleInvisible")
     public static void  visibleInvisible(View imageView,Boolean isVisible)
    {
        if(isVisible!=null)
        imageView.setVisibility(isVisible? View.VISIBLE:View.INVISIBLE);
    }
//    @InverseBindingAdapter(attribute = "visibleGone")
//     public static Boolean  getVisibleGone(View imageView)
//    {
//       return Boolean.valueOf(imageView.getVisibility() == View.VISIBLE);
//    }
//    @BindingAdapter("visibleGoneAttrChanged")
//    public static void setListeners(View view, final InverseBindingListener attrChange) {
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (attrChange != null)
//                    attrChange.onChange();
//                }
//            });


//            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
////                    int newVis = view.getVisibility();
////                    if ((int) view.getTag() != newVis) {
////                        view.setTag(view.getVisibility());
////                        //visibility has changed
////                        attrChange.onChange();
////                    }
//                    attrChange.onChange();
//                }
//            });
//    }


    @BindingAdapter("selected")
     public static void  selected(View imageView,boolean isSelected)
    {
        imageView.setSelected(isSelected);
    }

}
