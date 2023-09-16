package com.example.mywidgetkotlins

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.android.pie.chart.PieChartView
import com.android.pie.chart.PieItem
import java.text.SimpleDateFormat
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    private lateinit var selectedDateTextView: TextView

    private var chart: PieChartView? = null
    private var chart2: PieChartView? = null
    var db: DataBaseHelper? = null
    var neram: MutableList<chartone_model?>? = null
    var kalam: MutableList<ModelTwo?>? = null
    var gowri: List<ModelOne>? = null
    var sharedPreference: SharedPreference = SharedPreference()
    var todayString = ""
    var todayDay = ""
    var custom: CustomAnalogClock? = null
    var clk: InnerClock? = null
    var model: chartone_model? = null
    var one: ModelOne? = null
    var second: ModelTwo? = null
    var morning: TextView? = null
    var evening: TextView? = null
    var gowri_morning: TextView? = null
    var gowri_evening: TextView? = null
    var ra_m: TextView? = null
    var ra_e: TextView? = null
    var ku_m: TextView? = null
    var ku_e: TextView? = null
    var em_m: TextView? = null
    var em_e: TextView? = null
    var back: CardView? = null
    var mor: String = ""
    var eve: String = ""






    var N_mor_Hour = ""
    var N_mor_Min = ""
    var EN_Mor_hour = ""
    var EN_Mor_in = ""

    val mClockHours = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    val angleof = floatArrayOf(0f,30f, 60f, 90f, 120f, 150f, 180f, 210f, 240f, 270f, 300f, 330f, 360f)

    val mClockmin = intArrayOf(0, 15, 30, 45)
    val sweepanngle_S_Hour = floatArrayOf(0f, 7.5f, 15f, 22.5f)
    val sweepanngle_S_Min = floatArrayOf(0f, 6.25f, 12.5f, 24.5f)
    var sizeTo_pos= 25f
    var sizeTo_pos_eve= 25f

    var startanglenallanerm = 10f
    var startanglenallanerm_eve= 10f

    var startanglenallanerm_size = 10f

    var sizeToGow_pos_Eve= 25f
    var sizeTo_gow_eve= 25f
    var startanglegow_Eve = 10f

    var sizeToGow_pos_Mor= 25f
    var sizeTo_pos_Mor= 25f
    var startangleMorm = 10f

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chart = findViewById(R.id.pie_chart_view)
        chart2 = findViewById(R.id.pie_chart_view2)

        // Access the com.example.mywidgetkotlins.TimeMarksHalfCircleView instance
/*
// Customize the time marks
        val newTimeMarks = listOf("Morning", "Noon", "Afternoon", "Evening", "Night")
        timeMarksHalfCircleView.setTimeMarks(newTimeMarks)*/




        custom = findViewById(R.id.custom)
        // clk=findViewById(R.id.inner);
        morning = findViewById(R.id.morning)
        evening = findViewById(R.id.evening)
        gowri_morning = findViewById(R.id.gowri_morning)
        gowri_evening = findViewById(R.id.gowri_evening)
        ra_m = findViewById(R.id.ra_m)
        ra_e = findViewById(R.id.ra_e)
        ku_m = findViewById(R.id.ku_m)
        ku_e = findViewById(R.id.ku_e)
        em_m = findViewById(R.id.em_m)
        em_e = findViewById(R.id.em_e)
        back = findViewById(R.id.back)
        db = DataBaseHelper(this)
        model = chartone_model()
        second = ModelTwo()
        one = ModelOne()
        neram = ArrayList<chartone_model?>()
        kalam = ArrayList<ModelTwo?>()
        gowri = ArrayList<ModelOne>()


        selectedDateTextView = findViewById(R.id.selectedDateTextView)
        val calendarIcon: ImageView = findViewById(R.id.calendarIcon)

        calendarIcon.setOnClickListener {
            showDatePickerDialog()
        }





        if (sharedPreference.getInt(
                this@MainActivity,
                "DB_MOVE" + Utils.versioncode_get(this@MainActivity)
            ) === 0
        ) {
            //new BackgroundMoveDbFromAsset().execute();
            BackgroundMoveDbFromAsset()
        } else {
            date()
            database_nallaneram()
            database_gowri()
            //database_kalangal()
            chart?.setPieItems(
                listOf(
                    PieItem(
                        0.1f,
                        Color.RED,
                        startangleMorm,
                        sizeToGow_pos_Mor,

                    ),
                    PieItem(
                        0.6f,
                        Color.BLUE,
                        startanglegow_Eve,
                        sizeToGow_pos_Eve

                    ),
                    PieItem(
                        0.6f,
                        Color.GREEN,
                        startanglenallanerm_eve,
                        sizeTo_pos,
                    ),
                    PieItem(
                        0.6f,
                        Color.YELLOW,
                        startanglenallanerm,
                        sizeTo_pos
                    ),
                    )
            )
        }
         chart2?.setPieItems(
             listOf(
                 PieItem(
                     1f,
                     Color.BLACK,
                     160f,25f
                 ),
                 PieItem(
                     2f,
                     Color.BLUE,
                     60f,25f

                 ),
                 PieItem(
                     3f,
                     Color.BLACK,
                     210f,25f
                 ),
                 PieItem(
                     4f,
                     Color.RED,
                     330f,25f
                 )
             )
         )
        //  back.setOnClickListener(View.OnClickListener { finish() })
    }

    fun getMillisFromTime(hours: Int, minutes: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    /*
    public class BackgroundMoveDbFromAsset extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            Utils.mProgress(MainActivity.this, "ஏற்றுகிறது. காத்திருக்கவும் ...", false).show();
        }

        @Override
        protected String doInBackground(String... params) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TestAdapter mDbHelper = new TestAdapter(MainActivity.this);
                    mDbHelper.createDatabase();
                    mDbHelper.open();
                    mDbHelper.close();
                    sharedPreference.putInt(getApplicationContext(), "DB_MOVE_neww", 1);
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            date();
            database_nallaneram();
            database_gowri();
            database_kalangal();
            Utils.mProgress.dismiss();
        }
    }
*/
    fun BackgroundMoveDbFromAsset() {
        // Utils.mProgress(MainActivity.this, "தரவிறக்கம் செய்கிறது காத்திருக்கவும்..", false).show();
        val pDialog = ProgressDialog(this@MainActivity)
        pDialog.setMessage("தரவிறக்கம் செய்கிறது காத்திருக்கவும்...")
        pDialog.show()
        val handler: Handler = object : Handler(Looper.myLooper()!!) {
            override fun handleMessage(msg: Message) {
                val runnable = Runnable {
                    if (applicationContext != null) {
                        println("---copying end ")
                        try {
                            pDialog.dismiss()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        date()
                        database_nallaneram()
                        database_gowri()
                        //database_kalangal()
                        sharedPreference.putInt(
                            this@MainActivity,
                            "DB_MOVE" + Utils.versioncode_get(this@MainActivity),
                            1
                        )
                    }
                }
                runOnUiThread(runnable)
            }
        }
        val checkUpdate: Thread = object : Thread() {
            override fun run() {
                try {
                    println("---copying starts")
                    val mDbHelper = DBAdapter(this@MainActivity)
                    mDbHelper.createDatabase()
                    mDbHelper.open()
                    mDbHelper.close()
                } catch (e: Exception) {
                    println("---copying starts " + e.message)
                }
                handler.sendEmptyMessage(0)
            }
        }
        checkUpdate.start()
    }

    fun date() {
        val todayDate = Calendar.getInstance().time
        val formatter = SimpleDateFormat("EEEE")
        val formatter1 = SimpleDateFormat("dd-MM-yyyy")
        todayString = formatter1.format(todayDate)
        todayDay = formatter.format(todayDate)
        //Toast.makeText(MainActivity.this, "" + todayDay, Toast.LENGTH_SHORT).show();
    }
    fun database_nallaneram() {
        val cursor: Cursor? = db?.getQry("SELECT * FROM main_table WHERE date='$todayString' ")
        if (cursor != null) {
            cursor.moveToFirst()
        }
        if (cursor != null) {
            if (cursor.count != 0) {
                if (cursor != null) {
                    for (i in 0 until cursor.count) {
                        if (cursor != null) {
                            cursor.moveToPosition(i)
                        }
                        // model.nallanerem_m(cursor.getString(cursor.getColumnIndexOrThrow("nallanerem_m")))
                        //  model.setNallanerem_e(cursor.getString(cursor.getColumnIndexOrThrow("nallanerem_e")))
                        /* model.setGowri_m(cursor.getString(cursor.getColumnIndexOrThrow("gowri_m")));
                            model.setGowri_e(cursor.getString(cursor.getColumnIndexOrThrow("gowri_e")));
                            model.setRagu(cursor.getString(cursor.getColumnIndexOrThrow("ragu")));
                            model.setKuligai(cursor.getString(cursor.getColumnIndexOrThrow("kuligai")));
                            model.setEmakandam(cursor.getString(cursor.getColumnIndexOrThrow("emakandam")));*/
                        neram!!.add(
                            model
                        )
                        mor = cursor?.getString(cursor.getColumnIndexOrThrow("nallanerem_m"))
                            .toString()
                        morning!!.text = mor
                        eve = cursor?.getString(cursor.getColumnIndexOrThrow("nallanerem_e"))
                            .toString()
                        evening!!.text = eve

                    }
                }
            }
        }
        // custom.setModel(model)
        if (cursor != null) {
            cursor.close()
        }
        //Toast.makeText(MainActivity.this, neram.get(0).nallanerem_m, Toast.LENGTH_SHORT).show();
    }

    fun processData(data: String,data2: String,data3: String,data4: String,) {
        // Split the data string into separate parts based on spaces
        val parts = data.split(" ")
        val parts2 = data2.split(" ")
        val parts3 = data3.split(" ")
        val parts4 = data4.split(" ")

        // Check if there are at least two parts
        if (parts.size >= 2||parts2.size >= 2||parts3.size >= 2||parts3.size >= 2) {
            try {
                //nallaneram kalai
                val N_mor_Hour = parts[0].split(".")[0].trim().toString() // Extract "10"
                val N_mor_Min = parts[0].split(".")[1].trim() // Extract "30"
                val EN_Mor_hour = parts[2].split(".")[0].trim() // Extract "11"
                val EN_Mor_in = parts[2].split(".")[1].trim() // Extract "45"
//nallaneram maalai
                 val Ev_mor_Hour = parts2[0].split(".")[0].trim().toString() // Extract "10"
                val Ev_mor_Min = parts2[0].split(".")[1].trim() // Extract "30"
                val EvE_Mor_hour = parts2[2].split(".")[0].trim() // Extract "11"
                val EvE_Mor_in = parts2[2].split(".")[1].trim() // Extract "45"
                //gowri kalai
                val Gow_mor_Hour = parts3[0].split(".")[0].trim().toString() // Extract "10"
                val Gow_mor_Min = parts3[0].split(".")[1].trim() // Extract "30"
                val GowE_Mor_hour = parts3[2].split(".")[0].trim() // Extract "11"
                val GowE_Mor_in = parts3[2].split(".")[1].trim() // Extract "45"

                val Gow_eve_Hour = parts4[0].split(".")[0].trim().toString() // Extract "10"
                val Gow_eve_Min = parts4[0].split(".")[1].trim() // Extract "30"
                val GowE_eve_hour = parts4[2].split(".")[0].trim() // Extract "11"
                val GowE_eve_in = parts4[2].split(".")[1].trim() // Extract "45"

//nallaneram
                val N_mor_HourIndex = mClockHours.indexOf(N_mor_Hour.toInt())
                val N_mor_MinIndex = mClockmin.indexOf(N_mor_Min.toInt())
                val E_mor_MinIndex = mClockmin.indexOf(EN_Mor_in.toInt())

                val Ev_mor_HourIndex = mClockHours.indexOf(Ev_mor_Hour.toInt())
                val Ev_mor_MinIndex = mClockmin.indexOf(Ev_mor_Min.toInt())

                //gowri
                val Gow_mor_HourIndex = mClockHours.indexOf(Gow_mor_Hour.toInt())
                val Gow_mor_MinIndex = mClockmin.indexOf(Gow_mor_Min.toInt())
                val Gow_End_mor_MinIndex = mClockmin.indexOf(GowE_Mor_in.toInt())

                //gowri
                val Ev_Gow_HourIndex = mClockHours.indexOf(Gow_eve_Hour.toInt())
                val Ev_Gow_MinIndex = mClockmin.indexOf(Gow_eve_Min.toInt())
                val EV_Gow_MinIndex = mClockmin.indexOf(GowE_eve_in.toInt())
//nalla
                val angleToAdd = angleof[N_mor_HourIndex+2]
                val angleToEvening = angleof[Ev_mor_HourIndex-1]
                //gowri
                val angleToGow = angleof[Gow_mor_HourIndex+1]
                val angleTo_Gow_Evening = angleof[Ev_Gow_HourIndex+1]

                if (N_mor_Min.toInt() == mClockmin[N_mor_MinIndex]||Ev_mor_Hour.toInt() == mClockmin[Ev_mor_MinIndex]  ||
                    Gow_mor_Min.toInt() == mClockmin[Gow_mor_MinIndex]||Gow_eve_Hour.toInt() == mClockmin[Ev_Gow_MinIndex]) {
                    val sizeToAdd = sweepanngle_S_Hour[N_mor_MinIndex]
                    val sizeToEve = sweepanngle_S_Hour[Ev_mor_MinIndex]

                    val sizeToGowm = sweepanngle_S_Hour[Gow_mor_MinIndex]
                    val sizeToGowEve = sweepanngle_S_Hour[Ev_Gow_MinIndex]

                        sizeTo_pos = sweepanngle_S_Min[E_mor_MinIndex]
                        sizeTo_pos = sizeTo_pos+30f
                    sizeTo_pos_eve = sweepanngle_S_Min[Ev_mor_MinIndex]
                    sizeTo_pos_eve = sizeTo_pos_eve+30f

                    startanglenallanerm = angleToAdd + sizeToAdd
                    startanglenallanerm_eve = angleToEvening + sizeToEve
                    //
                    sizeToGow_pos_Mor = sweepanngle_S_Min[Gow_End_mor_MinIndex]
                    sizeToGow_pos_Mor = sizeToGow_pos_Mor+30f

                    sizeToGow_pos_Eve = sweepanngle_S_Min[Ev_Gow_MinIndex]
                    sizeToGow_pos_Eve = sizeToGow_pos_Eve+30f

                    startangleMorm = angleToGow + sizeToGowm
                    startanglegow_Eve = angleTo_Gow_Evening + sizeToGowEve

                } else {
                    startanglenallanerm = angleToAdd
                    startanglenallanerm_eve = angleToEvening
                }
            } catch (e: NumberFormatException) {
                // Handle the case where the conversion to int fails
                println("Invalid integer format in data: $data")
            }
        } else {
            // Handle the case where there are not enough parts in the string
            println("Invalid data format: $data")
        }
    }

    fun database_gowri() {
        neram = ArrayList<chartone_model?>()
        val cursor: Cursor? = db?.getQry("SELECT * FROM gowri_neram WHERE date='$todayString' ")
        if (cursor != null) {
            cursor.moveToFirst()
        }
        if (cursor != null) {
            if (cursor.count != 0) {
                for (i in 0 until cursor.count) {
                    cursor.moveToPosition(i)
                    // model.setGowri_m(cursor.getString(cursor.getColumnIndexOrThrow("gowri_m")))
                    // model.setGowri_e(cursor.getString(cursor.getColumnIndexOrThrow("gowri_e")))
                    neram!!.add(model)
                    val gow_mor = cursor.getString(cursor.getColumnIndexOrThrow("gowri_m"))
                    gowri_morning!!.text = gow_mor
                    val gow_eve = cursor.getString(cursor.getColumnIndexOrThrow("gowri_e"))
                    gowri_evening!!.text = gow_eve
                    println("hello---" + cursor.getString(cursor.getColumnIndexOrThrow("gowri_m")))

                    processData(mor,eve,gow_mor,gow_eve)

                }
            } else {
                println("hello---" + cursor.count)
            }
        }
        // custom.setModel(model)
        if (cursor != null) {
            cursor.close()
        }
    }

/*
    fun database_kalangal() {
        var weekdays = 1
        when (todayDay) {
            "Sunday" -> weekdays = 1
            "Monday" -> weekdays = 2
            "Tuesday" -> weekdays = 3
            "Wednesday" -> weekdays = 4
            "Thursday" -> weekdays = 5
            "Friday" -> weekdays = 6
            "Saturday" -> weekdays = 7
        }
        val cursor: Cursor? =
            db?.getQry("SELECT ragu, kuligai, emakandam, soolam, parikaram FROM kalangal WHERE weekday = $weekdays")
        if (cursor != null) {
            cursor.moveToFirst()
        }
        if (cursor != null) {
            if (cursor.count != 0) {
                if (cursor != null) {
                    for (i in 0 until cursor.count) {
                        if (cursor != null) {
                            cursor.moveToPosition(i)
                        }
                               kalam!!.add(second)
                        var rag_m: String?
                        var rag_e: String?
                        var kul_m: String?
                        var kul_e: String?
                        var ema_m: String?
                        var ema_e: String?
                        rag_m = cursor.getString(cursor.getColumnIndexOrThrow("ragu"))
                        ra_m!!.text = rag_m
                        rag_e = cursor.getString(cursor.getColumnIndexOrThrow("ragu"))
                        ra_e!!.text = rag_e
                        kul_m = cursor.getString(cursor.getColumnIndexOrThrow("kuligai"))
                        ku_m!!.text = kul_m
                        kul_e = cursor.getString(cursor.getColumnIndexOrThrow("kuligai"))
                        ku_e!!.text = kul_e
                        ema_m = cursor.getString(cursor.getColumnIndexOrThrow("emakandam"))
                        em_m!!.text = ema_m
                        ema_e = cursor.getString(cursor.getColumnIndexOrThrow("emakandam"))
                        em_e!!.text = ema_e
                    }
                }
            }
        }
        // custom.setModel_mod(second)
        if (cursor != null) {
            cursor.close()
        }
    }
*/

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, year, month, dayOfMonth ->
                // Handle the date selection here and update the TextView
                val selectedDate = "$dayOfMonth-${month + 1}-$year" // Month is zero-based
                selectedDateTextView.text = "$selectedDate"
            }, year, month, dayOfMonth)

        datePickerDialog.show()
    }
}