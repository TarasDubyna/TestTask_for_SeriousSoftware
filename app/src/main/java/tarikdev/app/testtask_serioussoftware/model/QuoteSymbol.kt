package tarikdev.app.testtask_serioussoftware.model

data class QuoteSymbol(
    val symbol: String,
    val timestamps: List<Long>,
    val opens: List<Float>,
    val closures: List<Float>,
    val highs: List<Float>,
    val lows: List<Float>,
    val volumes: List<Long>
    )
