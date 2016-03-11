package sklaiber.com.snow.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

public class ResortColums {

  @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement public static final String _ID =
      "_id";

  @DataType(DataType.Type.TEXT) @NotNull public static final String NAME = "name";

  @DataType(DataType.Type.TEXT) @NotNull public static final String CONDITIONS = "conditions";

  @DataType(DataType.Type.REAL) public static final String LATITUDE = "latitude";

  @DataType(DataType.Type.REAL) public static final String LONGITUDE = "longitude";

  @DataType(DataType.Type.INTEGER) public static final String SNOW_MOUNTAIN = "snow_mountain";

  @DataType(DataType.Type.INTEGER) public static final String SNOW_VALLEY = "snow_valley";

  @DataType(DataType.Type.INTEGER) public static final String NEW_SNOW = "new_snow";

  @DataType(DataType.Type.TEXT) public static final String HOMEPAGE = "homepage";

  @DataType(DataType.Type.TEXT) public static final String PHONE_NUMBER = "phone";
}
