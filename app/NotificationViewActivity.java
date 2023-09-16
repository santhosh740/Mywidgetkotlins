package nithra.tamil.leaders.celebrities.history.ui

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Html
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.facebook.ads.*
import kotlinx.android.synthetic.main.activity_notification_view.*
import nithra.tamil.leaders.celebrities.history.CodetoTamilUtil.BAMINI
import nithra.tamil.leaders.celebrities.history.CodetoTamilUtil.convertToTamil
import nithra.tamil.leaders.celebrities.history.R
import nithra.tamil.leaders.celebrities.history.ShareApp.shareDialog
import nithra.tamil.leaders.celebrities.history.Utility
import nithra.tamil.leaders.celebrities.history.Utility.isNetworkAvailable
import nithra.tamil.leaders.celebrities.history.Utility.substringsBetween
import nithra.tamil.leaders.celebrities.history.room.entity.NotificationEntity
import nithra.tamil.leaders.celebrities.history.sp
import nithra.tamil.leaders.celebrities.history.view_model.NotificationListViewModel
import org.jetbrains.anko.startActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class NotificationViewActivity : AppCompatActivity() {

    var via: String = ""
    var titleStr: String = ""
    var messageStr: String = ""
    var notificationEntity = NotificationEntity()

    var notificationListViewModel: NotificationListViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_view)

        notificationListViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(
                application
            )
        ).get(
            NotificationListViewModel(application)::class.java
        )


        // intent.setExtrasClassLoader(NotificationEntity::class.java.getClassLoader())
        println("=======check oncreate via:" + intent.getStringExtra("via").toString())
        // println("=======check oncreate contentData:"+intent.getParcelableExtra("contentData"))

        via = intent.getStringExtra("via").toString()
        if (via == "notiList") {
            notificationEntity = intent.getParcelableExtra("contentData")!!
        } else {
            notificationEntity.notiID = intent.getIntExtra("notiID", 0)
            notificationEntity.bm = intent.getStringExtra("bm")
            notificationEntity.message = intent.getStringExtra("message")

            println("=======check oncreate bm:" + notificationEntity.bm)
            println("=======check oncreate message:" + notificationEntity.message)
            println("=======check oncreate notiID:" + notificationEntity.notiID)
        }

        notificationEntity.isRead = 1

        println("====notificationEntity VIEW notiID:" + notificationEntity.notiID)
        notificationListViewModel!!.getNotification(notificationEntity.notiID!!)
            .observe(this) {
                println("====notificationEntity VIEW it.size:" + it.size)
                if (it != null && it.isNotEmpty()) {
                    notificationEntity = it[0]
                    if (notificationEntity.isRead == 0) {
                        notificationEntity.isRead = 1
                        notificationListViewModel!!.update(notificationEntity)
                    }
                    println("====notificationEntity VIEW id:" + notificationEntity.id)
                }
            }

        println("=======check out-- bm:" + notificationEntity.bm)
        println("=======check out-- message:" + notificationEntity.message)

        titleStr = notificationEntity.bm.toString()
        messageStr = notificationEntity.message.toString()

        ads_layview = findViewById(R.id.ads_layview)
        ads_lay = findViewById(R.id.ads_lay)
        // load_banner_adsManager()
        LoadBannerFB(ads_lay)

        println("======check inter-addManager : noti_list" + sp.getInt(this, "noti_list"))

        fbLoadInterstitialAd()

        title_txt.text = titleStr

        msg_webview.settings.domStorageEnabled = true
        msg_webview.setOnLongClickListener({ true })

        val ws: WebSettings = msg_webview.settings
        ws.javaScriptEnabled = true

        val bodyFont =
            ("<style> body { font-size:20px; } table { font-size:20px; <font face='bamini' > }</style>"
                    + "<style> @font-face { font-family:'bamini'; src: url('file:///android_asset/baamini.ttf') } </style>")
        val summary = ("<!DOCTYPE html> <html><head>" + bodyFont + " </head> <body >"
                + "<br>" + messageStr + "</body></html>")


        //var messageContent = convertToTamil(BAMINI, messageStr)

        if (messageStr != null) {
            if (messageStr.length > 4) {
                val str = "" + messageStr.substring(0, 4)
                if (str == "http") {
                    msg_webview.loadUrl("" + messageStr)
                } else {
                    msg_webview.loadDataWithBaseURL("", "" + summary, "text/html", "utf-8", null)
                }
            } else {
                msg_webview.loadDataWithBaseURL("", "" + summary, "text/html", "utf-8", null)
            }
        } else {
            msg_webview.loadDataWithBaseURL("", "" + summary, "text/html", "utf-8", null)
        }

        fab_share.setOnClickListener {
            var resultStr = ""
            var finalResult: String = messageStr
            val str1: String = finalResult.substring(0, 4)
            if (str1 == "http") {
                link_data_get(finalResult.toString(), 0)
            } else {
            val shareString: Array<String?>? = substringsBetween(finalResult, "<tt>", "</tt>")
            if (shareString != null) {
                Log.e("strrr", "" + shareString.size)

                println("=====sss==shareString :" + shareString.size)
                for (i in shareString.indices) {
                    finalResult = finalResult.replace(shareString[i]!!, "((?))$i((?))")
                    println("=====sss== :" + i + " " + shareString[i].toString())
                    Log.e("strrrr1", finalResult)
                }
                println("=====sss== $finalResult")

                finalResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
                    // we are using this flag to give a consistent behaviour
                    Html.fromHtml(finalResult, Html.FROM_HTML_MODE_LEGACY).toString()
                } else {
                    Html.fromHtml(finalResult).toString()
                }
                finalResult = convertToTamil(BAMINI, finalResult)

                // finalResult = convertToTamil(BAMINI, Html.fromHtml(finalResult).toString())

                for (i in shareString.indices) {
                    finalResult = finalResult.replace(
                        "((?))$i((?))",
                        Html.fromHtml(shareString[i]).toString()
                    )
                    Log.e("strrrr2", finalResult)
                }
                val symboles = arrayOf("&#36;", "&#8242;", "&#39;")
                val symboles1 = arrayOf("$", "′", "′")
                for (i in symboles.indices) {
                    finalResult = finalResult.replace(symboles[i], symboles1[i])
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
// FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
                    // we are using this flag to give a consistent behaviour
                    finalResult = Html.fromHtml(finalResult, Html.FROM_HTML_MODE_LEGACY).toString()
                } else {
                    finalResult = Html.fromHtml(finalResult).toString()
                }
                finalResult = convertToTamil(BAMINI, finalResult)

                // finalResult = convertToTamil(BAMINI, Html.fromHtml(finalResult).toString())
                val symboles = arrayOf("&#36;", "&#8242;", "&#39;")
                val symboles1 = arrayOf("$", "′", "′")
                for (i in symboles.indices) {
                    finalResult = finalResult.replace(symboles[i], symboles1[i])
                }
            }

            resultStr = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
                // we are using this flag to give a consistent behaviour
                Html.fromHtml(messageStr, Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                Html.fromHtml(messageStr).toString()
            }
            // resultStr = convertToTamil(BAMINI, resultStr).toString()
            // shareDialog(this, titleStr, resultStr, resources.getString(R.string.notiShareURl))
            shareDialog(this, titleStr, finalResult, resources.getString(R.string.notiShareURl))
        }
        }
        btn_close.setOnClickListener {
            onBackPressed()
        }
    }

    private fun link_data_get(url_str: String, type: Int) {
        val fi_result = arrayOf<String?>("")
        println("url === $url_str")
        var packageManager: PackageManager? = null
        //  Utility. mProgress(this@NotificationViewActivity, "loading...", false)!!.show()
        val handler: Handler =
            object : Handler(Objects.requireNonNull<Looper?>(Looper.myLooper())) {
                override fun handleMessage(msg1: Message) {
                    val runnable = Runnable {
                        // Utility.mProgress.dismiss()
                        try {
                            println("linkkkk : " + fi_result[0])
                            fi_result[0] = fi_result[0]!!.replace("<a href=tel:", "<a tel:")
                            fi_result[0] = fi_result[0]!!.replace("<a href=", " ")
                            println("linkkkkxyz : " + fi_result[0])
                            fi_result[0] = fi_result[0]!!.replace("&lang", "%26lang")
                            val templink: Array<String> =
                                messageStr.split("data=".toRegex()).dropLastWhile { it.isEmpty() }
                                    .toTypedArray()
                            var `val` = ""
                            if (templink != null && templink.size > 1) {
                                `val` = " \n" + templink[1].split(" ".toRegex())
                                    .dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                            }
                            if (fi_result[0] != null) {
                                if (type == 1) {
                                    var result = Html.fromHtml(fi_result[0]).toString()
                                    result = result.replace("%26lang", "&lang")
                                    result = result.replace(">", " ")
                                    result = result.replace("/>", " ")
                                    result = titleStr + "\n\n" + result + "" + `val`
                                    val clipboard =
                                        getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText(
                                        "",
                                        "நித்ராவின் தலைவர்கள் வரலாறு செயலி வாயிலாக இந்த தகவல் பகிரப்பட்டுள்ளது. செயலியை தரவிறக்கம் செய்ய இங்கே கிளிக் செய்யவும்.\n" +
                                                "\n" +resources.getString(R.string.notiShareURl))
                                    clipboard.setPrimaryClip(clip)
                                    Utility.toast_center(applicationContext, "நகலெடுக்கப்பட்டது")
                                } else {
                                    var result = Html.fromHtml(fi_result[0]).toString()
                                    result = result.replace("%26lang", "&lang")
                                    result = result.replace(">", " ")
                                    result = result.replace("/>", " ")
                                    //result=fi_result[0];
                                    result = "" + result + "" + `val`
                                    val finalResult = result
                                    shareDialog(this@NotificationViewActivity, titleStr, finalResult.replace("&", "%26").replace("\\+", "%2B").replace("#", "%23"), resources.getString(
                                        R.string.notiShareURl))

                                    /*   val shareDialog = Dialog(
                                           this@NotificationViewActivity,
                                           android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth
                                       )
                                       shareDialog.setContentView(R.layout.share_dialog)
                                       val share_list =
                                           shareDialog.findViewById<ListView>(R.id.share_list)
                                       listApp = ShareApp.showAllShareApp(this@NotificationViewActivity)
                                       if (listApp != null) {
                                           share_list.adapter =
                                              ShareApp.listAdapter(this@NotificationViewActivity)
                                           val finalResult = result
                                           share_list.onItemClickListener =
                                               OnItemClickListener { parent, view, position, id -> // share(listApp.get(position), finalResult.replaceAll("&", "%26").replaceAll("\\+", "%2B").replaceAll("#", "%23"));
                                                  // share(listApp!![position], finalResult)
                                                   shareDialog(this@NotificationViewActivity, titleStr, finalResult.replace("&", "%26").replace("\\+", "%2B").replace("#", "%23"), resources.getString(R.string.notiShareURl))

                                                   shareDialog.dismiss()
                                               }

                                       }
                                       shareDialog.show()*/
                                }
                            }
                        } catch (e: java.lang.Exception) {
                            println("linkkkk : $e")
                            runOnUiThread { Utility.toast_center(this@NotificationViewActivity, "Try again...") }
                        }
                    }
                    runOnUiThread(runnable)
                }
            }
        val checkUpdate: Thread = object : Thread() {
            override fun run() {
                var inputbuff: BufferedReader? = null
                val sb = StringBuffer("")
                try {
                    val url = URL(url_str)
                    inputbuff = BufferedReader(InputStreamReader(url.openStream()))
                    var inputLine: String?
                    while (inputbuff.readLine().also { inputLine = it } != null) {
                        if (inputLine?.trim { it <= ' ' }!!.length != 0) {
                            sb.append(inputLine)
                        }
                        println("linkkkk : $sb")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    // println("linkkkk : $e")
                }
                fi_result[0] = sb.toString()
                println("linkkk=== " + Html.fromHtml(fi_result[0]))
                println("linkkk123=== " + fi_result[0])
                val message = Message()
                message.obj = sb.toString()
                handler.sendEmptyMessage(0)
            }
        }
        checkUpdate.start()
    }

    private fun LoadBannerFB(ad_layout: LinearLayout) {
        val adView = AdView(
            this,
            getString(R.string.banner_other),
            AdSize.BANNER_HEIGHT_50
        )

        val adListener: AdListener = object : AdListener {
            override fun onError(ad: Ad?, adError: AdError) {
                // Ad error callback
                println(
                    adError.errorCode.toString() + "load faild" + adError.errorMessage
                )
            }

            override fun onAdLoaded(ad: Ad?) {
                // Ad loaded callback
                ad_layout.removeAllViews()
                adView.measure(0, 0)
                val view = RelativeLayout(this@NotificationViewActivity)
                val layoutParams =
                    RelativeLayout.LayoutParams(adView.measuredWidth, adView.measuredHeight)
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                view.layoutParams = layoutParams
                view.addView(adView)
                ad_layout.addView(view)
                ad_layout.visibility = View.VISIBLE

            }

            override fun onAdClicked(ad: Ad?) {
                // Ad clicked callback
            }

            override fun onLoggingImpression(ad: Ad?) {
                // Ad impression logged callback
            }
        }
        // Request an ad
        adView.loadAd(
            adView.buildLoadAdConfig().withAdListener(adListener).build()
        )
    }

    override fun onResume() {
        super.onResume()
        if (isNetworkAvailable(this@NotificationViewActivity)) {
            ads_layview!!.visibility = View.VISIBLE
        } else {
            ads_layview!!.visibility = View.GONE
        }
    }

    var currentDate: String = ""
    override fun onBackPressed() {
        val c = Calendar.getInstance().time
        println("Current time => $c")
        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        currentDate = df.format(c)

        println("===fbLoadInterstitialAd fb_InterstitialAd : $fb_InterstitialAd")

        if (fb_InterstitialAd != null && fb_InterstitialAd!!.isAdLoaded) {
            exitAlertfun()
        } else {
            if (sp.getInt(
                    this@NotificationViewActivity,
                    "noti_list"
                ) === 0 || sp.getInt(this@NotificationViewActivity, "noti_list") === 3
            ) {
                sp.putInt(this@NotificationViewActivity, "noti_list", 1)
            } else {
                var noti_list: Int = sp.getInt(this@NotificationViewActivity, "noti_list")
                noti_list += 1
                sp.putInt(this@NotificationViewActivity, "noti_list", noti_list)
            }
            if (via == "notiList") {
                finish()
            } else {
                finishAffinity()
                if (sp.getString(this@NotificationViewActivity, "indroScreen") != currentDate
                ) {
                    startActivity<SplaceActivity>()
                } else {
                    startActivity<MainActivity>()
                }
            }
        }

    }

    fun exitAlertfun() {
        val alertdia =
            Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
        alertdia.setContentView(R.layout.dialog_alert)
        val title_txt = alertdia.findViewById<TextView>(R.id.title_txt)
        title_txt.text = resources.getString(R.string.exitContent)
        val yes_btn = alertdia.findViewById<Button>(R.id.yes_btn)
        val no_btn = alertdia.findViewById<Button>(R.id.no_btn)
        yes_btn.setOnClickListener {
            alertdia.dismiss()
            fb_InterstitialAd!!.show()
        }
        no_btn.setOnClickListener { alertdia.dismiss() }
        alertdia.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationListViewModel!!.getAll.removeObserver { }
    }
    var ads_layview: RelativeLayout? = null
    private lateinit var ads_lay: LinearLayout


    var fb_InterstitialAd: InterstitialAd? = null
    fun fbLoadInterstitialAd() {

        //interstitialAd initial
        fb_InterstitialAd = InterstitialAd(this, getString(R.string.ins_notification_exit))
        val interstitialAdListener: InterstitialAdListener = object : InterstitialAdListener {
            override fun onError(ad: Ad, adError: AdError) {
                println("===fbLoadInterstitialAd error : " + adError.errorCode + " : " + adError.errorMessage)
            }

            override fun onAdLoaded(ad: Ad) {
                println("===fbLoadInterstitialAd onAdLoaded : ")
            }

            override fun onAdClicked(ad: Ad) {}
            override fun onLoggingImpression(ad: Ad) {}
            override fun onInterstitialDisplayed(ad: Ad) {
                println("Ad ad display")
            }

            override fun onInterstitialDismissed(ad: Ad) {
                println("Ad ad dismis")
                if (sp.getInt(
                        this@NotificationViewActivity,
                        "noti_list"
                    ) === 0 || sp.getInt(this@NotificationViewActivity, "noti_list") === 3
                ) {
                    sp.putInt(this@NotificationViewActivity, "noti_list", 1)
                } else {
                    var noti_list: Int = sp.getInt(this@NotificationViewActivity, "noti_list")
                    noti_list += 1
                    sp.putInt(this@NotificationViewActivity, "noti_list", noti_list)
                }
                if (via == "notiList") {
                    finish()
                } else {
                    finishAffinity()
                    if (sp.getString(this@NotificationViewActivity, "indroScreen") != currentDate
                    ) {
                        startActivity<SplaceActivity>()
                    } else {
                        startActivity<MainActivity>()
                    }
                }
            }
        }

        println(
            "===fbLoadInterstitialAd noti_list : " + sp.getInt(
                this,
                "noti_list"
            )
        )

        if (sp.getInt(
                this,
                "noti_list"
            ) === 0 || sp.getInt(this, "noti_list") === 3
        ) {
            fb_InterstitialAd!!.loadAd(
                fb_InterstitialAd!!.buildLoadAdConfig()
                    .withAdListener(interstitialAdListener)
                    .build()
            )
        }
    }

}