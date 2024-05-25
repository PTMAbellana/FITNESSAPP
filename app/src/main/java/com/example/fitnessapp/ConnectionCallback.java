package com.example.fitnessapp;

public interface ConnectionCallback {
    void onConnectionSuccess(String successMessage);
    void onConnectionFailure(String errorMessage);
}