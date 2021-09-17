package tarikdev.app.testtask_serioussoftware.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.java.KoinJavaComponent.inject
import tarikdev.app.testtask_serioussoftware.model.QuoteSymbol
import tarikdev.app.testtask_serioussoftware.model.QuoteRange
import tarikdev.app.testtask_serioussoftware.model.ResponseQuotes
import tarikdev.app.testtask_serioussoftware.util.JsonFileReaderUtil

interface QuotesRepository {
    val quoteSymbols: MutableStateFlow<List<QuoteSymbol>>
    suspend fun fetchQuoteSymbols(range: QuoteRange)
}

class QuotesRepositoryImpl: QuotesRepository {

    private val TAG = QuotesRepository::class.java.simpleName

    private val context: Context by inject(Context::class.java)

    private val gson: Gson = Gson()
    override val quoteSymbols: MutableStateFlow<List<QuoteSymbol>> = MutableStateFlow(listOf())

    override suspend fun fetchQuoteSymbols(range: QuoteRange) {
        Log.d(TAG, "fetchQuoteSymbols: fileName=${range.fileName}")

        val json = JsonFileReaderUtil.getJsonDataFromAsset(context, range.fileName)
        val data = gson.fromJson(json, ResponseQuotes::class.java).content.quoteSymbols

        Log.d(TAG, "getQuoteSymbols: ${data.toString()}")
        quoteSymbols.emit(data)
    }

}