package tarikdev.app.testtask_serioussoftware.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.main_fragment.*
import tarikdev.app.testtask_serioussoftware.APP_TAG
import tarikdev.app.testtask_serioussoftware.R
import tarikdev.app.testtask_serioussoftware.model.QuoteRange
import java.util.*


class MainFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initViewModel()
        initQuoteSymbolsRecycler()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.selectedRange.observe(viewLifecycleOwner, {
            toolbar.title = "Range: ${it.name}"
        })
        viewModel.quoteSymbols.observe(viewLifecycleOwner, {
            (quote_symbols_rv.adapter as QuoteSymbolsAdapter).update(it, viewModel.selectedRange.value!!)
        })
    }

    private fun initToolbar() {
        toolbar.inflateMenu(R.menu.range_menu)
        toolbar.setOnMenuItemClickListener(this)
    }

    private fun initQuoteSymbolsRecycler() {
        quote_symbols_rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = QuoteSymbolsAdapter()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item == null) return false
        val range = when (item.itemId) {
            R.id.range_menu_week -> QuoteRange.WEEK
            R.id.menu_main_month -> QuoteRange.MONTH
            else -> null
        }
        range?.let {
            viewModel.selectRange(it)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}