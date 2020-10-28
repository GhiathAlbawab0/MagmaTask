package com.ghiath.magmatask;

public enum ImageStatusEnum{
        APPROVED,REJECTED,PENDING;

    ImageStatusEnum() {
    }

    @Override
        public String toString() {
            switch (this)
            {
                case APPROVED:return "APPROVED";
                case REJECTED:return "REJECTED";
                case PENDING:return "PENDING";
                default:return "";
            }

        }


    }