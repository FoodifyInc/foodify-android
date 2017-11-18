package com.app.foodify.foodifyinc;

/**
 * Created by agoston on 11/18/17.
 */

/**
 * Interface that contains a callback that's called when an AsyncTask is completed.
 * @param <T> Type of the result of the AsyncTask
 */
public interface AsyncTaskCompleted<T> {
    /**
     * Called when AsyncTask is completed
     * @param val The value returned from the AsyncTask
     */
    void onTaskCompleted(T val);
}
