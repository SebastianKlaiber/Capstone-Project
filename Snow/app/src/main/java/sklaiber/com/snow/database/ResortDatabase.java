package sklaiber.com.snow.database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = ResortDatabase.VERSION) public final class ResortDatabase {
  public ResortDatabase() {
  }

  public static final int VERSION = 1;

  @Table(ResortColums.class) public static final String RESORTS = "resorts";
}
