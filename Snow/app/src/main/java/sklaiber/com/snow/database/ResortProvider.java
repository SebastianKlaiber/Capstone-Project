package sklaiber.com.snow.database;

import android.net.Uri;
import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = ResortProvider.AUTHORITY, database = ResortDatabase.class)
public class ResortProvider {
  public static final String AUTHORITY = "sklaiber.com.snow";
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

  interface Path {
    String RESORTS = "resorts";
  }

  private static Uri buildUri(String... paths) {
    Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
    for (String path : paths) {
      builder.appendPath(path);
    }
    return builder.build();
  }

  @TableEndpoint(table = ResortDatabase.RESORTS) public static class Resorts {
    @ContentUri(
        path = Path.RESORTS,
        type = "vnd.android.cursor.dir/resorts",
        defaultSort = ResortColums.NAME + " DESC") public static final Uri CONTENT_URI =
        buildUri(Path.RESORTS);

    @InexactContentUri(
        name = "RESORT_ID",
        path = Path.RESORTS + "/#",
        type = "vnd.android.cursor.item/resort",
        whereColumn = ResortColums._ID,
        pathSegment = 1) public static Uri withId(long id) {
      return buildUri(Path.RESORTS, String.valueOf(id));
    }
  }
}
