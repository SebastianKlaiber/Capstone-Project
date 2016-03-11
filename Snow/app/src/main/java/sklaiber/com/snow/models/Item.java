package sklaiber.com.snow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("conditions")
  @Expose
  private String conditions;
  @SerializedName("latitude")
  @Expose
  private Double latitude;
  @SerializedName("longitude")
  @Expose
  private Double longitude;
  @SerializedName("snowDepthMountain")
  @Expose
  private Integer snowDepthMountain;
  @SerializedName("getSnowDepthValley")
  @Expose
  private Integer getSnowDepthValley;
  @SerializedName("newSnow")
  @Expose
  private Integer newSnow;
  @SerializedName("homepage")
  @Expose
  private String homepage;
  @SerializedName("phoneNumber")
  @Expose
  private String phoneNumber;

  /**
   *
   * @return
   * The name
   */
  public String getName() {
    return name;
  }

  /**
   *
   * @param name
   * The name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   *
   * @return
   * The conditions
   */
  public String getConditions() {
    return conditions;
  }

  /**
   *
   * @param conditions
   * The conditions
   */
  public void setConditions(String conditions) {
    this.conditions = conditions;
  }

  /**
   *
   * @return
   * The latitude
   */
  public Double getLatitude() {
    return latitude;
  }

  /**
   *
   * @param latitude
   * The latitude
   */
  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  /**
   *
   * @return
   * The longitude
   */
  public Double getLongitude() {
    return longitude;
  }

  /**
   *
   * @param longitude
   * The longitude
   */
  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  /**
   *
   * @return
   * The snowDepthMountain
   */
  public Integer getSnowDepthMountain() {
    return snowDepthMountain;
  }

  /**
   *
   * @param snowDepthMountain
   * The snowDepthMountain
   */
  public void setSnowDepthMountain(Integer snowDepthMountain) {
    this.snowDepthMountain = snowDepthMountain;
  }

  /**
   *
   * @return
   * The getSnowDepthValley
   */
  public Integer getGetSnowDepthValley() {
    return getSnowDepthValley;
  }

  /**
   *
   * @param getSnowDepthValley
   * The getSnowDepthValley
   */
  public void setGetSnowDepthValley(Integer getSnowDepthValley) {
    this.getSnowDepthValley = getSnowDepthValley;
  }

  /**
   *
   * @return
   * The newSnow
   */
  public Integer getNewSnow() {
    return newSnow;
  }

  /**
   *
   * @param newSnow
   * The newSnow
   */
  public void setNewSnow(Integer newSnow) {
    this.newSnow = newSnow;
  }

  /**
   *
   * @return
   * The homepage
   */
  public String getHomepage() {
    return homepage;
  }

  /**
   *
   * @param homepage
   * The homepage
   */
  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  /**
   *
   * @return
   * The phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   *
   * @param phoneNumber
   * The phoneNumber
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

}
