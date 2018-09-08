package com.example.mehmetcece.dovizyakala;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Doviz implements Parcelable {
    String id="";
    String isim="";
    String sembol="";
    Bitmap bayrak;

    public Doviz(String id, String isim,String sembol,Bitmap bayrak){
        this.id=id;
        this.isim=isim;
        this.sembol=sembol;
        if (bayrak!=null) this.bayrak = bayrak;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSembol() {
        return sembol;
    }

    public void setSembol(String sembol) {
        this.sembol = sembol;
    }

    public Bitmap getBayrak() {
        return bayrak;
    }

    public void setBayrak(Bitmap bayrak) {
        if (bayrak!=null)this.bayrak = bayrak;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.isim);
        dest.writeString(this.sembol);
        dest.writeParcelable(this.bayrak, flags);
    }

    protected Doviz(Parcel in) {
        this.id = in.readString();
        this.isim = in.readString();
        this.sembol = in.readString();
        this.bayrak = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Doviz> CREATOR = new Creator<Doviz>() {
        @Override
        public Doviz createFromParcel(Parcel source) {
            return new Doviz(source);
        }

        @Override
        public Doviz[] newArray(int size) {
            return new Doviz[size];
        }
    };
}
