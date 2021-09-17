package tarikdev.app.testtask_serioussoftware.model

data class Quote(
    val symbol: String,
    val timestamps: Long,
    val opens: Float,
    val closures: Float,
    val highs: Float,
    val lows: Float,
    val volumes: Long
)