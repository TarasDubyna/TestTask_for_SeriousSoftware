package tarikdev.app.testtask_serioussoftware.ui.main

import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.scope.scopeActivity
import tarikdev.app.testtask_serioussoftware.R
import tarikdev.app.testtask_serioussoftware.model.Quote
import tarikdev.app.testtask_serioussoftware.model.QuotePerformance
import tarikdev.app.testtask_serioussoftware.model.QuoteRange
import java.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val TAG = MainFragment::class.java.simpleName

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.dataRange.observe(viewLifecycleOwner, {
            week_text.setTextColor(ContextCompat.getColor(week_text.context, R.color.grey))
            month_text.setTextColor(ContextCompat.getColor(week_text.context, R.color.grey))
            when(it) {
                QuoteRange.WEEK -> week_text.setTextColor(ContextCompat.getColor(week_text.context, R.color.black))
                QuoteRange.MONTH -> month_text.setTextColor(ContextCompat.getColor(month_text.context, R.color.black))
            }
        })

        viewModel.performanceData.observe(viewLifecycleOwner, {
            Log.d(TAG, "performanceData.observe: $it")
            //todo:: what to do with this data
        })
        viewModel.graphData.observe(viewLifecycleOwner, {
            val key = it.keys.first()
            Log.d(TAG, "graphData.observe: key=${key} for draw graph")
            drawCandlestick((it[key] ?: listOf()) as LinkedList<Quote>)
        })

        initClickListeners()
        initGraph()

    }

    private fun initClickListeners() {
        week_text.setOnClickListener { viewModel.showQuoteSymbols(QuoteRange.WEEK) }
        month_text.setOnClickListener { viewModel.showQuoteSymbols(QuoteRange.MONTH) }
    }

    private fun initGraph() {
        candlestick_graph.apply {
            setBackgroundColor(Color.WHITE)
            description.isEnabled = false
        }
    }

    private fun drawCandlestick(data: LinkedList<Quote>) {

        val label = data.first.symbol
        val values = data.mapIndexed { index, quote ->
            CandleEntry(
                index.toFloat(),
                quote.volumes + quote.highs,
                quote.volumes - quote.lows,
                quote.opens,
                quote.closures)
        }

        val candleSet = CandleDataSet(values, label)
        candleSet.setDrawIcons(false)
        candleSet.axisDependency = YAxis.AxisDependency.LEFT
        candleSet.shadowColor = Color.DKGRAY
        candleSet.shadowWidth = 0.7f
        candleSet.decreasingColor = Color.RED
        candleSet.decreasingPaintStyle = Paint.Style.FILL
        candleSet.increasingColor = Color.rgb(122, 242, 84)
        candleSet.increasingPaintStyle = Paint.Style.STROKE
        candleSet.neutralColor = Color.BLUE

        //candlestick_graph.clear()
        candlestick_graph.data = CandleData(candleSet)
        candlestick_graph.invalidate()
    }

}