package tarikdev.app.testtask_serioussoftware.ui.main

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import tarikdev.app.testtask_serioussoftware.APP_TAG
import tarikdev.app.testtask_serioussoftware.data.repository.QuotesRepository
import tarikdev.app.testtask_serioussoftware.model.Quote
import tarikdev.app.testtask_serioussoftware.model.QuotePerformance
import tarikdev.app.testtask_serioussoftware.model.api.QuoteSymbol
import tarikdev.app.testtask_serioussoftware.model.QuoteRange
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel() : ViewModel() {

    private val TAG = APP_TAG + MainViewModel::class.java.simpleName

    private val repository: QuotesRepository by inject(QuotesRepository::class.java)

    val selectedRange: MutableLiveData<QuoteRange> = MutableLiveData(QuoteRange.WEEK)
    val quoteSymbols: MutableLiveData<List<QuoteSymbol>> = MutableLiveData(listOf())

    init {
        selectedRange.value?.let {
            Log.d(TAG, "init: load ${it.name}")
            getQuoteSymbols(it)
        }
    }

    fun selectRange(range: QuoteRange) {
        Log.d(TAG, "selectRange: ${range.name}")
        if (range != selectedRange.value) {
            getQuoteSymbols(range)
        } else {
            Log.d(TAG, "selectRange: ignore")
        }
    }

    private fun getQuoteSymbols(range: QuoteRange) {
        Log.d(TAG, "getQuoteSymbols: ${range.name}")
        selectedRange.value = range
        viewModelScope.launch {
            when (range) {
                QuoteRange.WEEK -> repository.getWeekQuotes()
                QuoteRange.MONTH -> repository.getMonthQuotes()
            }.collect {
                Log.d(TAG, "getQuoteSymbols: ${range.name}: ${it.toString()}")
                quoteSymbols.value = it
            }

        }
    }

}