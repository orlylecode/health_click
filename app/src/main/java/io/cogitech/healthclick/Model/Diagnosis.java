package io.cogitech.healthclick.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Diagnosis implements Parcelable {
    public static final Creator<Diagnosis> CREATOR = new Creator<Diagnosis>() {
        @Override
        public Diagnosis createFromParcel(Parcel in) {
            return new Diagnosis(in);
        }

        @Override
        public Diagnosis[] newArray(int size) {
            return new Diagnosis[size];
        }
    };
    private List<Specialisation> Specialisation;
    private Issue Issue;

    public Diagnosis() {
        super();
    }

    public Diagnosis(List<io.cogitech.healthclick.Model.Specialisation> specialisation, io.cogitech.healthclick.Model.Issue issue) {
        Specialisation = specialisation;
        Issue = issue;
    }

    protected Diagnosis(Parcel in) {
        Specialisation = in.createTypedArrayList(io.cogitech.healthclick.Model.Specialisation.CREATOR);
        Issue = in.readParcelable(io.cogitech.healthclick.Model.Issue.class.getClassLoader());
    }

    public static Creator<Diagnosis> getCREATOR() {
        return CREATOR;
    }

    public List<io.cogitech.healthclick.Model.Specialisation> getSpecialisation() {
        return Specialisation;
    }

    public void setSpecialisation(List<io.cogitech.healthclick.Model.Specialisation> specialisation) {
        Specialisation = specialisation;
    }

    public io.cogitech.healthclick.Model.Issue getIssue() {
        return Issue;
    }

    public void setIssue(io.cogitech.healthclick.Model.Issue issue) {
        Issue = issue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(Specialisation);
        dest.writeParcelable(Issue, flags);
    }
}
