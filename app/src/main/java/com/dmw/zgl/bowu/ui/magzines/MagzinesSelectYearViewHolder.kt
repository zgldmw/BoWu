package com.dmw.zgl.bowu.ui.magzines

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.dmw.zgl.bowu.R
import com.dmw.zgl.bowu.base.BaseViewGroupAdapter
import com.dmw.zgl.bowu.model.MagzinesSelectYearData
import com.google.android.flexbox.FlexboxLayout

import java.util.ArrayList

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/15 10:22
 * Update:          2017/8/15 10:22
 * Description:     MagzinesSelectYearViewHolder
 */

class MagzinesSelectYearViewHolder(parent: ViewGroup) : View.OnClickListener {
    val wholeView: View
    private val flexboxLayout: FlexboxLayout
    private val baseViewGroupAdapter: BaseViewGroupAdapter<MagzinesSelectYearData>
    private var datas: ArrayList<MagzinesSelectYearData>? = null
    private var onSelectYearListener: OnSelectYearListener? = null

    init {
        val context = parent.context
        wholeView = LayoutInflater.from(context).inflate(R.layout.layout_select_year, parent, false)
        wholeView.visibility = View.GONE
        flexboxLayout = wholeView.findViewById(R.id.year_layout)
        baseViewGroupAdapter = object : BaseViewGroupAdapter<MagzinesSelectYearData>(flexboxLayout) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var contentView = convertView
                if (contentView == null) {
                    contentView = LayoutInflater.from(context).inflate(R.layout.item_select_year, parent, false)
                    contentView!!.setOnClickListener(this@MagzinesSelectYearViewHolder)
                }

                val magzinesSelectYearData = data[position]
                val year = contentView as TextView?
                year!!.text = magzinesSelectYearData.year
                year.isSelected = magzinesSelectYearData.active
                return contentView
            }
        }
    }

    fun setData(years: ArrayList<MagzinesSelectYearData>) {
        datas = years
        wholeView.visibility = View.VISIBLE
        baseViewGroupAdapter.addHomeData(datas)
    }

    override fun onClick(view: View) {
        var selectIndex = 0
        val count = flexboxLayout.flexItemCount
        for (i in 0 until count) {
            val childView = flexboxLayout.getFlexItemAt(i)
            if (childView.isSelected) {
                childView.isSelected = false
            }
            if (childView === view) {
                selectIndex = i
            }
        }
        view.isSelected = true
        if (onSelectYearListener != null) {
            onSelectYearListener!!.onSelectYear(Integer.parseInt(datas!![selectIndex].year))
        }
    }

    fun setOnSelectYearListener(onSelectYearListener: OnSelectYearListener) {
        this.onSelectYearListener = onSelectYearListener
    }

    interface OnSelectYearListener {
        /**
         * Called when select a year.
         */
        fun onSelectYear(year: Int)
    }
}
