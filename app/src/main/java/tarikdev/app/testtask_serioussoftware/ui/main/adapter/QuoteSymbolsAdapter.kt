package tarikdev.app.testtask_serioussoftware.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tarikdev.app.testtask_serioussoftware.R
import kotlinx.android.synthetic.main.vh_quote.*
import tarikdev.app.testtask_serioussoftware.model.QuoteRange
import tarikdev.app.testtask_serioussoftware.model.api.QuoteSymbol
import tarikdev.app.testtask_serioussoftware.ui.main.adapter.LAYOUT_RES
import tarikdev.app.testtask_serioussoftware.ui.main.adapter.QuoteVH

class QuoteSymbolsAdapter : RecyclerView.Adapter<QuoteVH>() {

    private lateinit var range: QuoteRange
    private val data: MutableList<QuoteSymbol> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteVH =
        QuoteVH(LayoutInflater.from(parent.context).inflate(LAYOUT_RES, parent, false))

    override fun onBindViewHolder(holder: QuoteVH, position: Int) {
        holder.bind(data[position], range)
    }

    override fun getItemCount(): Int = data.size

    fun update(data: List<QuoteSymbol>, range: QuoteRange) {
        this.range = range
        this.data.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }


}