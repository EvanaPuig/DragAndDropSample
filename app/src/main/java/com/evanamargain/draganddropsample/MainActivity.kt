package com.evanamargain.draganddropsample

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Insets
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.DragShadowBuilder
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setMargins
import com.evanamargain.draganddropsample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnTouchListener, View.OnDragListener {
  private lateinit var binding: ActivityMainBinding
  private val TAG = MainActivity::class.java.simpleName

  lateinit var items: ArrayList<Item>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    items = Item.createItemList(20)

    val height = getItemHeight(items.count())
    val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
    layoutParams.setMargins(0)

    for (item in items) {
      val itemView = TextView(this)
      itemView.text = item.name
      itemView.layoutParams = layoutParams
      binding.itemsList.addView(itemView)

    }

    binding.onboardingSurveyRangeSelectSelectorItem.text = items.first().name

    binding.onboardingSurveyRangeSelectSelectorItem.layoutParams.height = height

    setListeners()
  }

  private fun getItemHeight(amountOfItems: Int): Int {
    binding.viewContainer.layoutParams.height
    val windowMetrics: WindowMetrics = this.windowManager.currentWindowMetrics
    val heightOfScreen = windowMetrics.bounds.height()
    Log.d(TAG, "height $heightOfScreen")
    return (heightOfScreen * 0.8 / amountOfItems).toInt()
  }

  private fun setListeners() {
    binding.onboardingSurveyRangeSelectSelectorItem.setOnTouchListener(this)
    binding.onboardingSurveyRangeSelectContainer.setOnDragListener(this)
  }

  private fun setSelectorText() {

  }

  override fun onDrag(view: View, dragEvent: DragEvent):Boolean {
    Log.d(TAG, "onDrag: view->$view\n DragEvent$dragEvent")
    when (dragEvent.action) {
      DragEvent.ACTION_DRAG_ENDED -> {
        Log.d(TAG, "onDrag: ACTION_DRAG_ENDED ")
        return true
      }
      DragEvent.ACTION_DRAG_EXITED -> {
        Log.d(TAG, "onDrag: ACTION_DRAG_EXITED")
        return true
      }
      DragEvent.ACTION_DRAG_ENTERED -> {
        Log.d(TAG, "onDrag: ACTION_DRAG_ENTERED")
        return true
      }
      DragEvent.ACTION_DRAG_STARTED -> {
        Log.d(TAG, "onDrag: ACTION_DRAG_STARTED")
        return true
      }
      DragEvent.ACTION_DROP -> {
        Log.d(TAG, "onDrag: ACTION_DROP")
        val tvState = dragEvent.localState as View
        Log.d(TAG, "onDrag:viewX" + dragEvent.x + "viewY" + dragEvent.y)
        Log.d(TAG, "onDrag: Owner->" + tvState.parent)
        val tvParent = tvState.parent as ViewGroup
        tvParent.removeView(tvState)
        val container = view as LinearLayout
        container.addView(tvState)
        tvParent.removeView(tvState)
        tvState.x = 0f
        tvState.y = dragEvent.y
        view.addView(tvState)
        view.setVisibility(View.VISIBLE)
        return true
      }
      DragEvent.ACTION_DRAG_LOCATION -> {
        Log.d(TAG, "onDrag: ACTION_DRAG_LOCATION")
        val tvState = dragEvent.localState as View
        Log.d(TAG, "onDrag:viewX" + dragEvent.x + "viewY" + dragEvent.y)
        Log.d(TAG, "onDrag: Owner->" + tvState.parent)
        val tvParent = tvState.parent as ViewGroup
        tvParent.removeView(tvState)
        val container = view as LinearLayout
        container.addView(tvState)
        tvParent.removeView(tvState)
        tvState.x = 0f
        tvState.y = dragEvent.y
        view.addView(tvState)
        view.setVisibility(View.VISIBLE)
        return true
      }
      else -> return false
    }
  }
  override fun onTouch(view: View, motionEvent: MotionEvent):Boolean {
    Log.d(TAG, "onTouch: view->view$view\n MotionEvent$motionEvent")
    return when (motionEvent.action) {
      MotionEvent.ACTION_DOWN -> {
        val dragShadowBuilder = CustomDragShadowBuilder(view)
        view.startDragAndDrop(null, dragShadowBuilder, view, 0)
        true
      }
      else -> false
    }
  }
}

internal class CustomDragShadowBuilder(
  view: View?
) : DragShadowBuilder(view) {
  private val shadow = ColorDrawable(Color.TRANSPARENT)

  override fun onProvideShadowMetrics(outShadowSize: Point?, outShadowTouchPoint: Point?) {
    super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint)
    outShadowTouchPoint?.set(view.left, view.top)
  }

  override fun onDrawShadow(canvas: Canvas) {
    // Draws the ColorDrawable in the Canvas passed in from the system.
    shadow.draw(canvas)
  }
}



