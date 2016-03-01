package sklaiber.com.snow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sklaiber on 01.03.16.
 */
public class Item {
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("conditions")
  @Expose
  private String conditions;

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
}
