package sklaiber.com.snow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skipj on 11.01.2016.
 */
public class Resort {

  @SerializedName("items")
  @Expose
  private List<Item> items = new ArrayList<Item>();

  /**
   *
   * @return
   * The items
   */
  public List<Item> getItems() {
    return items;
  }

  /**
   *
   * @param items
   * The items
   */
  public void setItems(List<Item> items) {
    this.items = items;
  }

}
