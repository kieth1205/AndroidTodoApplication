package com.thang.noteapp.net.response;

import android.os.Parcel;
import android.os.Parcelable;

public class ChildTaskResponse implements Parcelable {

    private int id;
    private String name;
    private int progress = 0;

    public ChildTaskResponse() {
    }

    public ChildTaskResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected ChildTaskResponse(Parcel in) {
        id = in.readInt();
        name = in.readString();
        progress = in.readInt();
    }

    public static final Creator<ChildTaskResponse> CREATOR = new Creator<ChildTaskResponse>() {
        @Override
        public ChildTaskResponse createFromParcel(Parcel in) {
            return new ChildTaskResponse(in);
        }

        @Override
        public ChildTaskResponse[] newArray(int size) {
            return new ChildTaskResponse[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(progress);
    }
}

