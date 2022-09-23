package com.example.storiesig

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class StoriesLogic : AccessibilityService() {


    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        startStoriesWatching(event)
    }

    private fun startStoriesWatching(event: AccessibilityEvent){
        val nodeInfo: AccessibilityNodeInfo = rootInActiveWindow
        val list = nodeInfo.findAccessibilityNodeInfosByViewId("com.instagram.android:id/header_menu_button") ?: null

        if (list != null && list.isNotEmpty()){
            checkAd(event)
        }
    }

    private fun checkAd(event: AccessibilityEvent) {
        val nodeInfo: AccessibilityNodeInfo = rootInActiveWindow
        val list = nodeInfo.findAccessibilityNodeInfosByViewId("com.instagram.android:id/reel_viewer_subtitle") ?: null

        if (list != null && list.isNotEmpty()){
            swipeStory(event)
        } else {
            pressLikeButton(event)
        }

    }

    private fun swipeStory(event: AccessibilityEvent) {
        val gesture = GestureDescription.Builder()
        val path = Path()
        path.moveTo(1200F, 1695F)
        path.lineTo(500F, 1695F)
        gesture.addStroke(GestureDescription.StrokeDescription(path, 100, 150))
        dispatchGesture(gesture.build(),
            object : GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription) {
                    super.onCompleted(gestureDescription)
                }
            }, null)
        Thread.sleep(1000)
        checkAd(event)
    }

    private fun pressLikeButton(event: AccessibilityEvent){
        val nodeInfo: AccessibilityNodeInfo = rootInActiveWindow
        val list = nodeInfo.findAccessibilityNodeInfosByViewId("com.instagram.android:id/toolbar_like_button") ?: null

        if (list != null && list.isNotEmpty()){
            val rect = Rect()
            list[0].getBoundsInScreen(rect)
            val x = rect.exactCenterX()
            val y = rect.exactCenterY()
            val gesture = GestureDescription.Builder()
            val path = Path()
            path.moveTo(x, y)
            path.lineTo(x, y)
            gesture.addStroke(GestureDescription.StrokeDescription(path, 100, 40))
            dispatchGesture(gesture.build(),
                object : GestureResultCallback() {
                    override fun onCompleted(gestureDescription: GestureDescription) {
                        super.onCompleted(gestureDescription)
                    }
                }, null)
            Thread.sleep(500)
            nextStory(event)
        } else {
            checkAd(event)
        }

    }

    private fun nextStory(event: AccessibilityEvent?){
        Thread.sleep(500)
        if (event == null) return

        val gesture = GestureDescription.Builder()
        val path = Path()
        path.moveTo(1266F, 1353F)
        path.lineTo(1266F, 1353F)
        gesture.addStroke(GestureDescription.StrokeDescription(path, 100, 30))
        dispatchGesture(gesture.build(),
            object : GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription) {
                    super.onCompleted(gestureDescription)
                }
            }, null)
        Thread.sleep(1000)
        startStoriesWatching(event)
    }

    override fun onInterrupt() {

    }
}