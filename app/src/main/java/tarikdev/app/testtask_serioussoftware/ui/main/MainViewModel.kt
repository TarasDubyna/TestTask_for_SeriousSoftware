package tarikdev.app.testtask_serioussoftware.ui.main

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import tarikdev.app.testtask_serioussoftware.data.repository.QuotesRepository
import tarikdev.app.testtask_serioussoftware.data.repository.QuotesRepositoryImpl
import tarikdev.app.testtask_serioussoftware.model.Quote
import tarikdev.app.testtask_serioussoftware.model.QuotePerformance
import tarikdev.app.testtask_serioussoftware.model.QuoteSymbol
import tarikdev.app.testtask_serioussoftware.model.QuoteRange
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainViewModel() : ViewModel() {

    private val TAG = MainViewModel::class.java.simpleName

    private val repository: QuotesRepository by inject(QuotesRepository::class.java)

    val dataRange: MutableLiveData<QuoteRange> = MutableLiveData(QuoteRange.WEEK)
    val graphData: MutableLiveData<HashMap<String, LinkedList<Quote>>> = MutableLiveData(hashMapOf())
    val performanceData: MutableLiveData<HashMap<String, LinkedList<QuotePerformance>>> = MutableLiveData(hashMapOf())

    init {
        viewModelScope.launch {
            Log.d(TAG, "init: range=${dataRange.value}")
            repository.fetchQuoteSymbols(dataRange.value!!)

            repository.quoteSymbols.collect {
                Log.d(TAG, "quoteSymbols: init size=${it.size}")
                graphData.value = convertToGraphData(it)
                performanceData.value = calculatePerformanceData(it)
            }
        }
    }

    fun showQuoteSymbols(range: QuoteRange) {
        if (range != dataRange.value) {
            Log.d(TAG, "showQuoteSymbols: newRange=${range.name}")
            dataRange.value = range
            viewModelScope.launch {
                repository.fetchQuoteSymbols(dataRange.value!!)
            }
        } else {
            Log.d(TAG, "showQuoteSymbols: the same range(${range.name})")
        }
    }

    private fun calculatePerformanceData(quoteSymbols: List<QuoteSymbol>): HashMap<String, LinkedList<QuotePerformance>> {
        val quotePerformancesMap: HashMap<String, LinkedList<QuotePerformance>> = hashMapOf()
        quoteSymbols.forEach { quoteSymbol ->
            val quotePerformance: LinkedList<QuotePerformance> = LinkedList()
            val firstOpenValue = quoteSymbol.opens.first()
            quoteSymbol.opens.forEachIndexed { index, value ->
                val performance: Int = ((value-firstOpenValue)/firstOpenValue*100).toInt()
                quotePerformance.add(QuotePerformance(performance, quoteSymbol.timestamps[index]))
            }
            quotePerformancesMap[quoteSymbol.symbol] = quotePerformance
        }
        return quotePerformancesMap
    }

    private fun convertToGraphData(quoteSymbols: List<QuoteSymbol>): HashMap<String, LinkedList<Quote>> {
        val graphData: HashMap<String, LinkedList<Quote>> = hashMapOf()
        quoteSymbols.forEach {
            val quotes: LinkedList<Quote> = LinkedList()
            val symbol = it.symbol
            it.timestamps.forEachIndexed { index, timestamp ->
                quotes.add(Quote(symbol, timestamp, it.opens[index], it.closures[index], it.highs[index], it.lows[index], it.volumes[index]))
            }
            graphData[symbol] = quotes
        }
        return graphData
    }


}