package sklaiber.com.snow.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by sklaiber on 23.02.16.
 */
public class ResortColums {

  @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement public static final String _ID =
      "_id";

  @DataType(DataType.Type.TEXT) @NotNull public static final String NAME = "name";

  @DataType(DataType.Type.TEXT) @NotNull public static final String CONDITIONS = "conditions";
}
