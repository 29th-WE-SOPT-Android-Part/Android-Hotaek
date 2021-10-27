package org.sopt.myapplication.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class MyDecoration(
    private val dividerHeight : Int,
    private val dividerColor: Int
): RecyclerView.ItemDecoration() {
    private val paint = Paint()
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        myDivider(c, parent, color = dividerColor )
    }


    private fun myDivider(c:Canvas, parent:RecyclerView, color: Int){
        paint.color = color


        for (i in 0 until parent.childCount){
            //뷰 객체 가져오기
            val child = parent.getChildAt(i)
            val param = child.layoutParams as RecyclerView.LayoutParams

            val dividerTop = child.bottom + param.bottomMargin
            val dividerBottom = dividerTop + dividerHeight

            c.drawRect(
                child.left.toFloat(),
                dividerTop.toFloat(),
                child.right.toFloat(),
                dividerBottom.toFloat(),
                paint
            )

        }
        }

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.bottom = dividerHeight
    }
}
