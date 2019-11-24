package io.cogitech.healthclick.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Specialisation implements Parcelable {
    public static final Creator<Specialisation> CREATOR = new Creator<Specialisation>() {
        @Override
        public Specialisation createFromParcel(Parcel in) {
            return new Specialisation(in);
        }

        @Override
        public Specialisation[] newArray(int size) {
            return new Specialisation[size];
        }
    };
    private Integer ID;
    private String Name;
    private Integer SpecialistID;

    protected Specialisation(Parcel in) {
        if (in.readByte() == 0) {
            ID = null;
        } else {
            ID = in.readInt();
        }
        Name = in.readString();
        if (in.readByte() == 0) {
            SpecialistID = null;
        } else {
            SpecialistID = in.readInt();
        }
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

    public Integer getSpecialistID() {
        return SpecialistID;
    }

    public void setSpecialistID(Integer specialistID) {
        SpecialistID = specialistID;
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
        if (SpecialistID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(SpecialistID);
        }
    }
}
