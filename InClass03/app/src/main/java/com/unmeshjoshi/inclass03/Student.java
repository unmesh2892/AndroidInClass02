package com.unmeshjoshi.inclass03;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

    String name;
    String email;
    String department;
    int seekPercentage;
    int deptId;

    public Student(String name, String email, String department, int seekPercentage,int deptId) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.seekPercentage = seekPercentage;
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", seekPercentage=" + seekPercentage +

                '}';
    }

    protected Student(Parcel in) {

        this.name = in.readString();
        this.email = in.readString();
        this.department = in.readString();
        this.seekPercentage=in.readInt();
        this.deptId = in.readInt();

    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.department);
        dest.writeInt(this.seekPercentage);
        dest.writeInt(this.deptId);
    }
}
