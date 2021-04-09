package com.shreyas.squaretakehomeapp.utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.robolectric.Robolectric
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

/**
 *  Utility class to read assets folder in test and bring the recorded json object as json response string
 */
object TestJsonUtils {

    private val TAG = TestJsonUtils::class.java.simpleName

    private val moshi = Moshi.Builder().build()

    private fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            val inputStream: InputStream =
                    this.javaClass.classLoader!!.getResourceAsStream(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            jsonString = String(buffer, StandardCharsets.UTF_8)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun <T> getObjectFromJsonFile(
            jsonFile: String,
            tClass: Class<T>
    ): T? {
        var inputStream: InputStream? = null
        try {
            inputStream = this.javaClass.classLoader!!.getResourceAsStream(jsonFile)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            val json = String(buffer, StandardCharsets.UTF_8)
            return getObjectFromJsonString(json, tClass)
        } catch (exception: Exception) {
            Log.d(TAG, "Exception: ${exception.message}")
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (exception: IOException) {
                    Log.d(TAG, "IOException: ${exception.message}")
                }
            }
        }
        return null
    }

    private fun <T> getObjectFromJsonString(
            jsonData: String,
            tClass: Class<T>
    ): T? {
        val adapter: JsonAdapter<T> = moshi.adapter(tClass)
        return adapter.fromJson(jsonData)
    }

    fun <T> getJson(fileName: String, tClass: Class<T>?): List<T>? {
        val jsonString = getJsonDataFromAsset(fileName)
        Log.i(TAG, "JSON String: $jsonString")
        val type = Types.newParameterizedType(List::class.java, tClass)
        val adapter: JsonAdapter<List<T>> = moshi.adapter(type)
        return adapter.fromJson(fileName)
    }

    fun getJsonAsString(fileName: String): String {
        val location = this.javaClass.classLoader!!.getResource(fileName)
        val file = File(location.path)
        return String(file.readBytes())
    }

    fun <T> getObjectList(jsonString: String, className: Class<T>): List<T> {
        val list: MutableList<T> = ArrayList()
        try {
            val type = Types.newParameterizedType(List::class.java, className)
            val adapter: JsonAdapter<T> = moshi.adapter(type)
            adapter.fromJson(jsonString)?.let { list.add(it) }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun startFragment(fragment: Fragment) {
        val activity = Robolectric.buildActivity(FragmentActivity::class.java)
                .create()
                .start()
                .resume()
                .get()
        val fragmentManager = activity.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(fragment, null)
        fragmentTransaction.commit()
    }
}