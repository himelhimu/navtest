package test.android.sabbir.navdrawer.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sabbir on 8/3/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=2;
    public static final String DATABASE_NAME="myDB";

    //for saving Nasa APOD image to fav table
    public static final String NASA_IMAGE_FAV_TABLE_NAME="nasa_image";

    public static final String IMAGE_TITLE="image_title";
    public static final String IMAGE_EXPLANATION="image_explanation";
    public static final String DATE="image_date";
    public static final String IMAGE_URL="image_url";
    private static final String IMAGE_BLOB="image_data";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql="CREATE TABLE IF NOT EXISTS "+NASA_IMAGE_FAV_TABLE_NAME+" ( " +IMAGE_TITLE+
                " TEXT, "+IMAGE_EXPLANATION+" TEXT,"+DATE+" TEXT,"+IMAGE_URL+
                        " TEXT,"+IMAGE_BLOB+" BLOB);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+NASA_IMAGE_FAV_TABLE_NAME);
        onCreate(db);
    }
}
