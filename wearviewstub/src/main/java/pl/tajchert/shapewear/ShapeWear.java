/*
The MIT License (MIT)

Copyright (c) 2015 Michal Tajchert

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package pl.tajchert.shapewear;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ShapeWear {
    public static enum ScreenShape {ROUND, MOTO_ROUND, RECTANGLE, UNDETECTED}

    private static int screenWidthPX = 0;
    private static boolean setOnApplyWindowInsetsListenerCalled = false;
    private static int screenHeightPX = 0;
    private static OnSizeChangeListener onSizeChangeListener;

    private static ScreenShape shape = ScreenShape.UNDETECTED;
    private static ConcurrentLinkedQueue<OnShapeChangeListener> onShapeChangeListeners = new ConcurrentLinkedQueue<>();

    /**
     * Initialized to determine screen shape
     * @param view
     */
    private static void initShapeDetection(View view){
        if(!setOnApplyWindowInsetsListenerCalled) {
            setOnApplyWindowInsetsListenerCalled = true;
            view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @Override
                    public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    if (insets.isRound()) {
                        shape = ScreenShape.ROUND;
                        if (screenWidthPX == 320 && screenHeightPX == 290) {
                            shape = ScreenShape.MOTO_ROUND;
                        }
                    } else {
                        shape = ScreenShape.RECTANGLE;
                    }
                    if (onShapeChangeListeners != null && !onShapeChangeListeners.isEmpty()) {
                        synchronized (onShapeChangeListeners) {
                            for (OnShapeChangeListener listener : onShapeChangeListeners)
                                listener.shapeDetected(getShape());
                        }
                    }
                    return insets;
                }
            });
            view.requestApplyInsets();
        }
    }

    /**
     * Initialized at any moment of app life cycle to determine screen shape and size
     * @param activity
     */
    public static void initShapeWear(Activity activity){
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        getScreenSize(wm);
        ViewGroup viewGroup = (ViewGroup) (activity).getWindow().getDecorView().findViewById(android.R.id.content);
        initShapeDetection(viewGroup.getChildAt(0));
    }

    /**
     * Initialized at any moment of app life cycle to determine screen shape and size
     * @param context
     */
    public static void initShapeWear(Context context){
        initShapeWear((Activity)context);
    }

    private static void getScreenSize(WindowManager wm) {
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidthPX = size.x;
        screenHeightPX = size.y;
        if(onSizeChangeListener != null){
            onSizeChangeListener.sizeDetected(screenWidthPX, screenHeightPX);
        }
    }

    /**
     * Method used to get most common (for now) parameter, is screen is round or not. Will throw an Exception is it is not detected.
     * @return boolean is screen is round or not
     * @throws ScreenShapeNotDetectedException
     * @deprecated use {@link #getShape()} instead, as it allows to handle all shapes correctly, and Google specifies that more shapes can be introduced in the future.
     */
    @Deprecated
    public static boolean isRound() throws ScreenShapeNotDetectedException {
        if(shape == null || shape.equals(ScreenShape.UNDETECTED)){
            throw new ScreenShapeNotDetectedException("ShapeWear still doesn't have correct screen shape at this point, subscribe to OnShapeChangeListener or call this method later on. Also you can call getShape() to get String representation, will return SHAPE_UNSURE if not specified.");
        } else if (shape.equals(ScreenShape.ROUND)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Safe-proof method, but will return ScreenShape.UNDETECTED instead of throwing Exception.
     * @return String name of screen type
     */
    public static ScreenShape getShape(){
        return shape;
    }

    public static int getScreenWidthPX() {
        return screenWidthPX;
    }

    public static int getScreenHeightPX() {
        return screenHeightPX;
    }

    public static void addOnShapeChangeListener(OnShapeChangeListener onShapeChangeListener) {
        ShapeWear.onShapeChangeListeners.add(onShapeChangeListener);
        if(!getShape().equals(ScreenShape.UNDETECTED) && ShapeWear.onShapeChangeListeners != null && !ShapeWear.onShapeChangeListeners.isEmpty()){
            synchronized (onShapeChangeListeners) {
                for (OnShapeChangeListener listener : onShapeChangeListeners)
                    listener.shapeDetected(getShape());
            }
        }
    }

    public static void removeOnShapeChangeListener(OnShapeChangeListener onShapeChangeListener) {
        synchronized (onShapeChangeListeners) {
            ShapeWear.onShapeChangeListeners.remove(onShapeChangeListener);
        }
    }

    public static void setOnSizeChangeListener(OnSizeChangeListener onSizeChangeListener) {
        ShapeWear.onSizeChangeListener = onSizeChangeListener;
        if(ShapeWear.onSizeChangeListener != null && screenWidthPX != 0 && screenHeightPX != 0){
            ShapeWear.onSizeChangeListener.sizeDetected(screenWidthPX, screenHeightPX);
        }
    }

    public interface OnShapeChangeListener {
        void shapeDetected(ScreenShape screenShape);
    }

    public interface OnSizeChangeListener {
        void sizeDetected(int widthPx, int heightPx);
    }

    public static class ScreenShapeNotDetectedException extends Exception {
        public ScreenShapeNotDetectedException() {
        }

        public ScreenShapeNotDetectedException(String detailMessage) {
            super(detailMessage);
        }
    }
}

