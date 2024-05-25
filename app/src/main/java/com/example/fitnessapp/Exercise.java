package com.example.fitnessapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
    private int exerciseId;
    private String exerciseName;
    private String target;
    private String difficulty;
    private int noOfSets;
    private int noOfReps;
    private int noOfSeconds;

    public Exercise(int exerciseId, String exerciseName, String target, String difficulty, int noOfSets, int noOfReps, int noOfSeconds) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.target = target;
        this.difficulty = difficulty;
        this.noOfSets = noOfSets;
        this.noOfReps = noOfReps;
        this.noOfSeconds = noOfSeconds;
    }

    protected Exercise(Parcel in) {
        exerciseId = in.readInt();
        exerciseName = in.readString();
        target = in.readString();
        difficulty = in.readString();
        noOfSets = in.readInt();
        noOfReps = in.readInt();
        noOfSeconds = in.readInt();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(exerciseId);
        dest.writeString(exerciseName);
        dest.writeString(target);
        dest.writeString(difficulty);
        dest.writeInt(noOfSets);
        dest.writeInt(noOfReps);
        dest.writeInt(noOfSeconds);
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getTarget() {
        return target;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getNoOfSets() {
        return noOfSets;
    }

    public int getNoOfReps() {
        return noOfReps;
    }

    public int getNoOfSeconds() {
        return noOfSeconds;
    }
}
