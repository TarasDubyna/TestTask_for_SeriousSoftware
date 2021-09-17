package tarikdev.app.testtask_serioussoftware.util

import android.content.Context
import com.google.gson.Gson
import tarikdev.app.testtask_serioussoftware.model.ResponseQuotes
import java.io.IOException

object JsonFileReaderUtil {

    fun getJsonDataFromAsset(context: Context, fileName: String): String {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return ""
        }
        return jsonString
    }

    suspend fun getResponseQuotes(context: Context, jsonFileName: String): ResponseQuotes {
        val jsonString = getJsonDataFromAsset(context, jsonFileName)
        return Gson().fromJson(jsonString, ResponseQuotes::class.java)
    }

}