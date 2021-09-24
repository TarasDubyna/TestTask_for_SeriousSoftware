package tarikdev.app.testtask_serioussoftware.util

import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import tarikdev.app.testtask_serioussoftware.APP_TAG
import tarikdev.app.testtask_serioussoftware.model.QuoteRange
import tarikdev.app.testtask_serioussoftware.model.api.QuoteSymbol
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DataUtil {

    fun calculatePerformance(firstValue: Float, value: Float): Float = ((value - firstValue) / firstValue * 100)

    fun getPerformanceLineDataSet(quoteSymbol: QuoteSymbol): LineDataSet {
        val entries = quoteSymbol.closures.mapIndexed { index, value ->
            Entry(
                quoteSymbol.timestamps[index].toFloat(),
                calculatePerformance(quoteSymbol.closures.first(), value)
            )
        }
        return LineDataSet(entries, "Performance").apply {
            setDrawIcons(false)
            setColors(Color.BLUE)
        }
    }

    fun getQuoteCandleDataSet(quoteSymbol: QuoteSymbol): CandleDataSet {
        val entries = arrayListOf<CandleEntry>()
        for (i in quoteSymbol.timestamps.indices) {
            entries.add(
                CandleEntry(
                    quoteSymbol.timestamps[i].toFloat(),
                    quoteSymbol.highs[i],
                    quoteSymbol.lows[i],
                    quoteSymbol.opens[i],
                    quoteSymbol.closures[i],
                )
            )
        }
        return CandleDataSet(entries, quoteSymbol.symbol).apply {
            setDrawIcons(false)
            axisDependency = YAxis.AxisDependency.LEFT

            shadowColor = Color.GRAY
            shadowWidth = 0.7f

            decreasingColor = Color.RED
            decreasingPaintStyle = Paint.Style.FILL

            increasingColor = Color.GREEN
            increasingPaintStyle = Paint.Style.FILL

            neutralColor = Color.BLUE

        }
    }

    fun getFormattedDate(timestamp: Long, range: QuoteRange): String {
        val dateFormat: DateFormat = when(range) {
            QuoteRange.WEEK -> SimpleDateFormat("HH:mm dd.MM")
            QuoteRange.MONTH -> SimpleDateFormat("dd.MM")
        }
        return dateFormat.format(Date(timestamp))
    }

}