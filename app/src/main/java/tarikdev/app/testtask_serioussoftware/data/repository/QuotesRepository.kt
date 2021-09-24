package tarikdev.app.testtask_serioussoftware.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.inject
import tarikdev.app.testtask_serioussoftware.APP_TAG
import tarikdev.app.testtask_serioussoftware.model.Quote
import tarikdev.app.testtask_serioussoftware.model.api.QuoteSymbol
import tarikdev.app.testtask_serioussoftware.model.QuoteRange
import tarikdev.app.testtask_serioussoftware.model.api.ResponseQuotes
import tarikdev.app.testtask_serioussoftware.util.JsonFileReaderUtil
import java.util.*
import kotlin.collections.ArrayList

interface QuotesRepository {
    suspend fun getWeekQuotes(): MutableStateFlow<List<QuoteSymbol>>
    suspend fun getMonthQuotes(): MutableStateFlow<List<QuoteSymbol>>
}

class QuotesRepositoryImpl : QuotesRepository {

    private val context: Context by inject(Context::class.java)
    private val gson: Gson = Gson()

    override suspend fun getWeekQuotes(): MutableStateFlow<List<QuoteSymbol>> =
        MutableStateFlow(readQuoteSymbols(QuoteRange.WEEK.fileName))

    override suspend fun getMonthQuotes(): MutableStateFlow<List<QuoteSymbol>> =
        MutableStateFlow(readQuoteSymbols(QuoteRange.MONTH.fileName))

    private fun readQuoteSymbols(fileName: String): List<QuoteSymbol> {
        val json = JsonFileReaderUtil.getJsonDataFromAsset(context, fileName)
        return gson.fromJson(json, ResponseQuotes::class.java).content.quoteSymbols
    }

}