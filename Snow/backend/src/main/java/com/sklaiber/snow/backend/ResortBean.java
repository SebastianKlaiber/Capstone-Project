package com.sklaiber.snow.backend;

public class ResortBean {

  private String name;
  private String conditions;
  private float latitude;
  private float longitude;
  private int snowDepthMountain;
  private int getSnowDepthValley;
  private int newSnow;
  private String homepage;
  private String phoneNumber;
  private String email;

  public ResortBean(String name, String conditions) {
    this.name = name;
    this.conditions = conditions;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getConditions() {
    return conditions;
  }

  public void setConditions(String conditions) {
    this.conditions = conditions;
  }

  public float getLatitude() {
    return latitude;
  }

  public void setLatitude(float latitude) {
    this.latitude = latitude;
  }

  public float getLongitude() {
    return longitude;
  }

  public void setLongitude(float longitude) {
    this.longitude = longitude;
  }

  public int getSnowDepthMountain() {
    return snowDepthMountain;
  }

  public void setSnowDepthMountain(int snowDepthMountain) {
    this.snowDepthMountain = snowDepthMountain;
  }

  public int getGetSnowDepthValley() {
    return getSnowDepthValley;
  }

  public void setGetSnowDepthValley(int getSnowDepthValley) {
    this.getSnowDepthValley = getSnowDepthValley;
  }

  public int getNewSnow() {
    return newSnow;
  }

  public void setNewSnow(int newSnow) {
    this.newSnow = newSnow;
  }

  public String getHomepage() {
    return homepage;
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
