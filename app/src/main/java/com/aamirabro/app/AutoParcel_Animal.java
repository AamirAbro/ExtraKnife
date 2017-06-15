package com.aamirabro.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aamirabro on 05/05/2017.
 */

final class AutoParcel_Animal extends Animal implements Parcelable {
    private final String name;
    private final int numberOfLegs;

    AutoParcel_Animal(String name, int numberOfLegs) {
        if (name == null) {
            throw new NullPointerException("Null name");
        }
        this.name = name;
        this.numberOfLegs = numberOfLegs;
    }
    @Override
    String name() {
        return name;
    }
    @Override
    int numberOfLegs() {
        return numberOfLegs;
    }
    @Override
    public String toString() {
        return "Animal{"
                + "name=" + name
                + ", numberOfLegs=" + numberOfLegs
                + "}";
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Animal) {
            Animal that = (Animal) o;
            return (this.name.equals(that.name()))
                    && (this.numberOfLegs == that.numberOfLegs());
        }
        return false;
    }
    @Override
    public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= name.hashCode();
        h *= 1000003;
        h ^= numberOfLegs;
        return h;
    }
    public static final Parcelable.Creator<Animal> CREATOR = new Parcelable.Creator<Animal>() {
        @Override public Animal createFromParcel(Parcel in) {
            return new AutoParcel_Animal(in);
        }
        @Override public Animal[] newArray(int size) {
            return new Animal[size];
        }
    };
    private final static ClassLoader CL = AutoParcel_Animal.class.getClassLoader();
    private AutoParcel_Animal(Parcel in) {
        this(
                (String) in.readValue(CL),
                (Integer) in.readValue(CL));
    }
    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(numberOfLegs);
    }
    @Override public int describeContents() {
        return 0;
    }
}
