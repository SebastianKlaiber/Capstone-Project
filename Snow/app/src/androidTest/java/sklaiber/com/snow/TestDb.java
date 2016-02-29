package sklaiber.com.snow;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;
import sklaiber.com.snow.database.ResortColums;
import sklaiber.com.snow.database.ResortProvider;

/**
 * Created by sklaiber on 29.02.16.
 */

public class TestDb extends AndroidTestCase {

  void deleteTheDatabase() {
    mContext.deleteDatabase("resortDatabase.db");
  }

  public void setUp() {
    deleteTheDatabase();
  }

  public void testInsertDataToDatabase() {
    String name = "Resort 1";

    ContentValues cv = new ContentValues();
    cv.put(ResortColums.NAME, name);
    cv.put(ResortColums.CONDITIONS, "good");

    mContext.getContentResolver().insert(ResortProvider.Resorts.CONTENT_URI, cv);

    Cursor cursor = mContext.getContentResolver().query(ResortProvider.Resorts.CONTENT_URI,
        null,
        null,
        null,
        null);

    assertTrue("Error: This means that we were unable to query the database for the table information.",
        cursor.moveToFirst());

    if (cursor != null) {
      cursor.moveToFirst();
      assertEquals(name, cursor.getString(cursor.getColumnIndex(ResortColums.NAME)));
    }
  }
}
