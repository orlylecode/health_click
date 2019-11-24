package io.cogitech.healthclick.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Issue implements Parcelable {
    public static final Creator<Issue> CREATOR = new Creator<Issue>() {
        @Override
        public Issue createFromParcel(Parcel in) {
            return new Issue(in);
        }

        @Override
        public Issue[] newArray(int size) {
            return new Issue[size];
        }
    };
    private Integer ID;
    private String Name;
    private String Accuracy;
    private String Icd;
    private String IcdName;
    private String ProfName;
    private String Ranking;

    protected Issue(Parcel in) {
        if (in.readByte() == 0) {
            ID = null;
        } else {
            ID = in.readInt();
        }
        Name = in.readString();
        Accuracy = in.readString();
        Icd = in.readString();
        IcdName = in.readString();
        ProfName = in.readString();
        Ranking = in.readString();
    }

    public String getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(String accuracy) {
        Accuracy = accuracy;
    }

    public String getIcd() {
        return Icd;
    }

    public void setIcd(String icd) {
        Icd = icd;
    }

    public String getIcdName() {
        return IcdName;
    }

    public void setIcdName(String icdName) {
        IcdName = icdName;
    }

    public String getProfName() {
        return ProfName;
    }

    public void setProfName(String profName) {
        ProfName = profName;
    }

    public String getRanking() {
        return Ranking;
    }

    public void setRanking(String ranking) {
        Ranking = ranking;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (ID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ID);
        }
        dest.writeString(Name);
        dest.writeString(Accuracy);
        dest.writeString(Icd);
        dest.writeString(IcdName);
        dest.writeString(ProfName);
        dest.writeString(Ranking);
    }
}
