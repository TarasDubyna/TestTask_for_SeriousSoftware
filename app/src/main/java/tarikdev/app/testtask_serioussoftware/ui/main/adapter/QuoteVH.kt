package tarikdev.app.testtask_serioussoftware.ui.main.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.vh_quote.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tarikdev.app.testtask_serioussoftware.R
import tarikdev.app.testtask_serioussoftware.model.QuoteRange
import tarikdev.app.testtask_serioussoftware.model.api.QuoteSymbol
import tarikdev.app.testtask_serioussoftware.util.DataUtil
import tarikdev.app.testtask_serioussoftware.util.GraphDateValueFormatter
import kotlin.math.roundToLong

const val LAYOUT_RES = R.layout.vh_quote

class QuoteVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var range: QuoteRange

    init {
        initGraphs()
    }

    fun bind(quoteSymbol: QuoteSymbol, range: QuoteRange) {
        this.range = range
        itemView.symbol_text.text = quoteSymbol.symbol
        changeXAxisValueFormatter()
        updateQuotesGraph(quoteSymbol)
        updatePerformanceGraph(quoteSymbol)
    }

    private fun initGraphs() {
        itemView.quote_graph.apply {
            setBackgroundColor(Color.WHITE)
            description.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setLabelCount(3, true)
        }
        itemView.performance_graph.apply {
            setBackgroundColor(Color.WHITE)
            description.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setLabelCount(3, true)
        }
    }

    private fun updateQuotesGraph(quoteSymbol: QuoteSymbol) {
        CoroutineScope(Dispatchers.Default).launch {
            val dataSet = DataUtil.getQuoteCandleDataSet(quoteSymbol)
            launch(Dispatchers.Main) {
                itemView.quote_graph.apply {
                    data = CandleData(dataSet)
                    invalidate()
                }
            }
        }
    }

    private fun updatePerformanceGraph(quoteSymbol: QuoteSymbol) {
        CoroutineScope(Dispatchers.Default).launch {
            val dataSet = DataUtil.getPerformanceLineDataSet(quoteSymbol)
            launch(Dispatchers.Main) {
                itemView.performance_graph.apply {
                    data = LineData(dataSet)
                    invalidate()
                }
            }
        }
    }

    private fun changeXAxisValueFormatter() {
        itemView.quote_graph.xAxis.valueFormatter = GraphDateValueFormatter(range)
        itemView.performance_graph.xAxis.valueFormatter = GraphDateValueFormatter(range)
    }



}