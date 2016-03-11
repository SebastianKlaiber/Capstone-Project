package sklaiber.com.snow;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;
import sklaiber.com.snow.database.ResortColums;
import sklaiber.com.snow.database.ResortProvider;

public class TestDb extends AndroidTestCase {

  void deleteTheDatabase() {
    mContext.deleteDatabase("resortDatabase.db");
  }

  @Override protected void setUp() throws Exception {
    super.setUp();
    deleteTheDatabase();
  }

  public void testInsertDataToDatabase() {
    String name = "Resort 1";

    ContentValues cv = new ContentValues();
    cv.put(ResortColums.NAME, name);
    cv.put(ResortColums.CONDITIONS, "good");
    cv.put(ResortColums.LATITUDE, 42f);
    cv.put(ResortColums.LONGITUDE, 11f);

    mContext.getContentResolver().insert(ResortProvider.Resorts.CONTENT_URI, cv);

    Cursor cursor = mContext.getContentResolver().query(ResortProvider.Resorts.CONTENT_URI,
        null,
        null,
        null,
        null);

    assert cursor != null;
    assertTrue("Error: This means that we were unable to query the database for the table information.",
        cursor.moveToFirst());

    cursor.moveToFirst();
    assertEquals(name, cursor.getString(cursor.getColumnIndex(ResortColums.NAME)));
    assertEquals(42f, cursor.getFloat(cursor.getColumnIndexOrThrow(ResortColums.LATITUDE)));
    assertEquals(11f, cursor.getFloat(cursor.getColumnIndexOrThrow(ResortColums.LONGITUDE)));
  }
}
