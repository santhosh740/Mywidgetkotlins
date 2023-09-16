package com.example.mywidgetkotlins

import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

/*
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper"; // Tag just for the LogCat
    // window
    // destination path (location) of our database on device
    ///private static String DB_PATH = "";
    private static final String DB_NAME = "calender.db";// Database name
    private SQLiteDatabase mDataBase;
    private final Context mContext;
    Cursor c;

    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 4);// 1? its Database Version

        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        //database.execSQL("create table if not exists Favorite(ID integer not null primary key autoincrement,BID integer,Title varchar,Subtitle varchar,images varchar)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }


    public Cursor getQry(String Qry) {

        SQLiteDatabase sq = this.getReadableDatabase();
        c = sq.rawQuery(Qry, null);
        return c;
    }

    public void executeSql(String Qry) {

        SQLiteDatabase sq = this.getReadableDatabase();
        sq.execSQL(Qry);

    }

    public void createDataBase() throws IOException {
        // If database not exists copy it from the assets

        boolean mDataBaseExist = checkDataBase();
        //String DB_PATH = mContext.getDatabasePath(DB_NAME).getAbsolutePath();

        String DB_PATH = mContext.getApplicationInfo().dataDir + "/databases/";
        if (mDataBaseExist) {

            File dbFile = new File(DB_PATH + DB_NAME);
            dbFile.delete();
        }

        // if (!mDataBaseExist) {
        this.getReadableDatabase();
        this.close();
        try {
            // Copy the database from assests
            copyDataBase();
            Log.e(TAG, "createDatabase database created");
            // Toast.makeText(mContext,
            // "aptitudequestiondb database copied",
        } catch (IOException mIOException) {
            System.out.println("db error "+mIOException);

        }
        // } else {
        // // Toast.makeText(mContext, "aptitudequestiondb Already exits",
        // // Toast.LENGTH_LONG).show();
        // }
    }

    public void createDataBaseIFexits() throws IOException {
        // If database not exists copy it from the assets

        boolean mDataBaseExist = checkDataBase();

        // mDataBaseExist=false;

        this.getReadableDatabase();
        this.close();
        try {
            // Copy the database from assests
            copyDataBase();
            Log.e(TAG, "createDatabase database created");

        } catch (IOException mIOException) {
            throw new Error("ErrorCopyingDataBase");
        }

    }

    // Check that the database exists here: /data/data/your package/databases/Da
    // Name
    public  boolean checkDataBase() {
        //String DB_PATH = "/data/data/" + mContext.getPackageName() + "/databases/";
        String DB_PATH = mContext.getApplicationInfo().dataDir + "/databases/";
        //String DB_PATH = mContext.getDatabasePath(DB_NAME).getAbsolutePath();

        File dbFile = new File(DB_PATH + DB_NAME);
        // Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    // Copy the database from assets
    public void copyDataBase() throws IOException {
        String DB_PATH = mContext.getApplicationInfo().dataDir + "/databases/";

        //String DB_PATH = "/data/data/" + mContext.getPackageName() + "/databases/";
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        //	int mLength;
        while ((mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    // Open the database, so we can query it
    public boolean openDataBase() throws SQLException {
        String DB_PATH = mContext.getApplicationInfo().dataDir + "/databases/";
        //String DB_PATH = mContext.getDatabasePath(DB_NAME).getAbsolutePath();
        //String DB_PATH = "/data/data/" + mContext.getPackageName() + "/databases/";
        String mPath = DB_PATH + DB_NAME;
        // Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null,
                SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }
    // below is database helper one
}
*/
class DataBaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, 3) {
    private var mDataBase: SQLiteDatabase? = null
    private val mContext: Context
    var c: Cursor? = null
    var mPreferences: SharedPreferences? = null

    init {
        DB_PATH = context.getDatabasePath(DB_NAME).path
        mContext = context
    }

    override fun onCreate(arg0: SQLiteDatabase) {
        // TODO Auto-generated method stub
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO Auto-generated method stub
    }

    fun getQry(Qry: String?): Cursor? {
        val sq = this.readableDatabase
        c = sq.rawQuery(Qry, null)
        return c
    }

    fun executeSql(Qry: String?) {
        val sq = this.readableDatabase
        sq.execSQL(Qry)
    }

    @Throws(IOException::class)
    fun createDataBase() {
        val mDataBaseExist = checkDataBase()
        if (mDataBaseExist) {
            val dbFile = File(DB_PATH + DB_NAME)
            dbFile.delete()
        }
        this.readableDatabase
        close()
        try {
            copyDataBase()
            Log.e(TAG, "createDatabase database created")
        } catch (mIOException: IOException) {
            throw Error("ErrorCopyingDataBase")
        }
    }

    @Throws(IOException::class)
    fun createDataBaseIFexits() {
        val mDataBaseExist = checkDataBase()
        this.readableDatabase
        close()
        try {
            copyDataBase()
            Log.e(TAG, "createDatabase database created")
        } catch (mIOException: IOException) {
            throw Error("ErrorCopyingDataBase")
        }
    }

    fun checkDataBase(): Boolean {
        val dbFile = File(DB_PATH + DB_NAME)
        return dbFile.exists()
    }

    @Throws(IOException::class)
    fun copyDataBase() {
        val mInput = mContext.assets.open(DB_NAME)
        val outFileName = DB_PATH
        val mOutput: OutputStream = FileOutputStream(outFileName)
        val mBuffer = ByteArray(1024)
        var mLength: Int
        while (mInput.read(mBuffer).also { mLength = it } > 0) {
            mOutput.write(mBuffer, 0, mLength)
        }
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    @Throws(SQLException::class)
    fun openDataBase(): Boolean {
        val mPath = DB_PATH + DB_NAME
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY)
        return mDataBase != null
    }

    @Synchronized
    override fun close() {
        if (mDataBase != null) {
            mDataBase!!.close()
        }
        super.close()
    }

    companion object {
        private const val TAG = "DataBaseHelper"
        private var DB_PATH = ""
        private const val DB_NAME = "calender.db"
    }
}