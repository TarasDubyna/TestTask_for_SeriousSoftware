package tarikdev.app.testtask_serioussoftware.util

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import tarikdev.app.testtask_serioussoftware.model.QuoteRange
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class GraphDateValueFormatter(val range: QuoteRange): IndexAxisValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        val date = Date(value.toLong() * 1000)
        val dateFormat: DateFormat = when(range) {
            QuoteRange.WEEK -> SimpleDateFormat("HH:mm dd.MM")
            QuoteRange.MONTH -> SimpleDateFormat("dd.MM")
        }
        return dateFormat.format(date)
    }

}