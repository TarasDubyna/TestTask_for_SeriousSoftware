package tarikdev.app.testtask_serioussoftware.util

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import tarikdev.app.testtask_serioussoftware.model.QuoteRange
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class GraphDateValueFormatter(val range: QuoteRange): IndexAxisValueFormatter() {

    override fun getFormattedValue(value: Float): String {

        // week (Hourly data)
        // month (Daily data)

        val milliseconds  = value.toLong() * 1000
        val date = Date(milliseconds)
        /*val dateTimeFormat: DateFormat = when(range) {
            QuoteRange.WEEK -> DateFormat.getTimeInstance()
            QuoteRange.MONTH -> DateFormat.getDateInstance()
        }*/
        val dateTimeFormat: DateFormat = when(range) {
            QuoteRange.WEEK -> SimpleDateFormat("HH:mm dd.MM.yyyy")
            QuoteRange.MONTH -> SimpleDateFormat("dd.MM.yyyy")
        }
            //DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())

        return dateTimeFormat.format(date)
    }

}