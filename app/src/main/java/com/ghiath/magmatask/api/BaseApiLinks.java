package com.ghiath.magmatask.api;

public enum BaseApiLinks {
    BaseURL
    ,BannerURL;

    @Override
    public String toString() {
        switch (this)
        {
            case BaseURL:
                return "";
//                return "https://www.kelshimall.com";
            case BannerURL:
                return "";
//                return "https://www.kelshimall.com/KSM_FILES";
            default: return "";
        }
    }
}
