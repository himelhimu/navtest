package test.android.sabbir.navdrawer.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

import test.android.sabbir.navdrawer.models.NasaPhoto;
import test.android.sabbir.navdrawer.utilites.Helper;

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

  public void insertFavNasaImage(NasaPhoto nasaPhoto, Bitmap bitmap){
      SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
      ContentValues contentValues=new ContentValues();
      contentValues.put(IMAGE_TITLE,nasaPhoto.getTitle());
      contentValues.put(IMAGE_EXPLANATION,nasaPhoto.getExplanation());
      contentValues.put(DATE,nasaPhoto.getDate());
      contentValues.put(IMAGE_URL,nasaPhoto.getUrl());
      contentValues.put(IMAGE_BLOB, Helper.getBytes(bitmap));

      sqLiteDatabase.insert(NASA_IMAGE_FAV_TABLE_NAME,null,contentValues);
      Log.d("TAG","Data "+nasaPhoto.getTitle());
  }

 public ArrayList<NasaPhoto> getAllFavNasaImage(){
     ArrayList<NasaPhoto> photoArrayList=new ArrayList<>();
     String sql="SELECT * FROM "+NASA_IMAGE_FAV_TABLE_NAME;
     SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
     Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
     if (cursor!=null && cursor.getCount()>0){
         cursor.moveToFirst();
         do{
             NasaPhoto nasaPhoto=new NasaPhoto();
             nasaPhoto.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
             nasaPhoto.setTitle(cursor.getString(cursor.getColumnIndex(IMAGE_TITLE)));
             nasaPhoto.setUrl(cursor.getString(cursor.getColumnIndex(IMAGE_URL)));
             nasaPhoto.setBitmap(Helper.getImage(cursor.getBlob(cursor.getColumnIndex(IMAGE_BLOB))));
             nasaPhoto.setExplanation(cursor.getString(cursor.getColumnIndex(IMAGE_EXPLANATION)));

             photoArrayList.add(nasaPhoto);
         }while (cursor.moveToNext());
         cursor.close();
     }


     return photoArrayList;
  }
}
