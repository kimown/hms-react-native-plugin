/*
    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License")
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.huawei.hms.rn.health.kits.datacontroller.viewmodel;

import android.util.Log;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.hihealth.DataController;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.data.SampleSet;
import com.huawei.hms.hihealth.options.DeleteOptions;
import com.huawei.hms.hihealth.options.ReadOptions;
import com.huawei.hms.hihealth.options.UpdateOptions;
import com.huawei.hms.hihealth.result.ReadReply;
import com.huawei.hms.rn.health.foundation.listener.ResultListener;
import com.huawei.hms.rn.health.foundation.listener.VoidResultListener;

/**
 * All the tasks for {@link DataController} methods
 * are used in {@link DataViewModel} class.
 *
 * @since v.5.0.1
 */
public class DataViewModel implements DataService {

    private static final String TAG = DataViewModel.class.getSimpleName();

    /**
     * Insert the user's fitness and health data into the Health platform.
     *
     * @param dataController {@link DataController} instance.
     * @param sampleSet {@link SampleSet} instance.
     * @param voidResultListener {@link VoidResultListener} listener.
     */
    @Override
    public void insertData(final DataController dataController, final SampleSet sampleSet,
        final VoidResultListener voidResultListener) {
        Log.i(TAG, "call insertData");
        /* Call the data controller to insert the sampling dataset into the Health platform. */
        Task<Void> insertTask = dataController.insert(sampleSet);

        /* Calling the data controller to insert the sampling dataset. */
        insertTask.addOnSuccessListener(result -> {
            // Result is void instance
            Log.i(TAG, "insertData success");
            voidResultListener.onSuccess(result);

        }).addOnFailureListener(error -> {
            Log.i(TAG, "insertData error");
            voidResultListener.onFail(error);
        });
    }

    /**
     * Deleting the User's Fitness and Health Data
     *
     * <p>
     * Note: Only historical data that has been inserted by the current app can be deleted from the Health platform.
     * </p>
     *
     * @param dataController {@link DataController} instance.
     * @param deleteOptions {@link DeleteOptions} instance.
     * @param voidResultListener {@link VoidResultListener} listener.
     */
    @Override
    public void deleteData(final DataController dataController, final DeleteOptions deleteOptions,
        final VoidResultListener voidResultListener) {
        Log.i(TAG, "call deleteData");
        /* Use the specified condition deletion object to call the data controller to delete the sampling dataset. */
        Task<Void> deleteTask = dataController.delete(deleteOptions);

        /*  Calling the data controller to delete the sampling dataset. */
        deleteTask.addOnSuccessListener(result -> {
            // Result is void instance
            Log.i(TAG, "deleteData success");
            voidResultListener.onSuccess(result);
        }).addOnFailureListener(error -> {
            Log.i(TAG, "deleteData error");
            voidResultListener.onFail(error);
        });
    }

    /**
     * Updating the User's Fitness and Health Data
     * </br>
     * 1. Build the condition for data update: a DataCollector object.
     * 2. Build the sampling data set for the update: create a sampling data set for the update based on the data collector.
     * 3. Build the start time, end time, and incremental step count for a DataType sampling point for the update.
     * 4. Build a DataType sampling point for the update
     * 5. Add an updated DataType sampling point to the sampling data set for the update. You can add more updated sampling points to the sampling data set for the update.
     * 6. Build a parameter object for the update.
     * 7. Use the specified parameter object for the update to call the data controller to modify the sampling dataset.
     *
     * @param dataController {@link DataController} instance.
     * @param updateOptions {@link UpdateOptions} instance.
     * @param voidResultListener {@link VoidResultListener} listener.
     */
    @Override
    public void updateData(final DataController dataController, final UpdateOptions updateOptions,
        final VoidResultListener voidResultListener) {
        Log.i(TAG, "call updateData");
        /*  Use the specified parameter object for the update to call the */
        /*  data controller to modify the sampling dataset. */
        Task<Void> updateTask = dataController.update(updateOptions);

        /* Calling the data controller to modify the sampling dataset. */
        updateTask.addOnSuccessListener(result -> {
            // Result is void instance
            Log.i(TAG, "updateData success");
            voidResultListener.onSuccess(result);
        }).addOnFailureListener(error -> {
            Log.i(TAG, "updateData error");
            voidResultListener.onFail(error);
        });
    }

    /**
     * Querying the User's Fitness and Health Data
     * <p>
     * Note: To read historical data from the Health platform,
     * for example, to read the number of steps taken within a period of time, you can specify the read conditions in ReadOptions.
     * Furthermore, you can specify the data collector, data type, and detailed data.
     * If data is read, the data set will be returned.
     * </p>
     *
     * @param dataController {@link DataController} instance.
     * @param readOptions {@link ReadOptions} instance.
     * @param dataResultListener {@link VoidResultListener} listener.
     */
    @Override
    public void readData(final DataController dataController, final ReadOptions readOptions,
        final ResultListener<ReadReply> dataResultListener) {
        Log.i(TAG, "call readData");
        /* Use the specified condition query object to call the data controller to query the sampling dataset. */
        Task<ReadReply> readReplyTask = dataController.read(readOptions);

        /*  Calling the data controller to delete the sampling dataset. */
        readReplyTask.addOnSuccessListener(readReply -> {
            // Result is ReadReply instance
            Log.i(TAG, "readData success");
            dataResultListener.onSuccess(readReply);
        }).addOnFailureListener(error -> {
            Log.i(TAG, "readData error");
            dataResultListener.onFail(error);
        });
    }

    /**
     * Querying the Summary Fitness and Health Data of the User of the Current day
     *
     * @param dataController {@link DataController} instance.
     * @param dataType {@link DataType} instance.
     * @param dataResultListener {@link VoidResultListener} listener.
     */
    @Override
    public void readToday(final DataController dataController, final DataType dataType,
        final ResultListener<SampleSet> dataResultListener) {
        Log.i(TAG, "call readToday");
        /* Use the specified data type (DT_CONTINUOUS_STEPS_DELTA) to call the data controller to query
         * the summary data of this data type of the current day. */
        Task<SampleSet> todaySummationTask = dataController.readTodaySummation(dataType);

        /* Calling the data controller to query the summary data of the current day.
         * Note: In this example, the inserted data time is fixed at 2020-03-17 09:05:00.
         * When commissioning the API, you need to change the inserted data time to the current date
         * for data to be queried. */
        todaySummationTask.addOnSuccessListener(sampleSet -> {
            // Result is ReadReply instance
            Log.i(TAG, "readToday success");
            dataResultListener.onSuccess(sampleSet);
        });
        todaySummationTask.addOnFailureListener(error -> {
            Log.i(TAG, "readToday error");
            dataResultListener.onFail(error);
        });
    }

    /**
     * Clearing the User's Fitness and Health Data from the Device and Cloud
     *
     * @param dataController {@link DataController} instance.
     * @param dataResultListener {@link VoidResultListener} listener.
     */
    @Override
    public void clearTaskData(final DataController dataController, final VoidResultListener dataResultListener) {
        Log.i(TAG, "call clearTaskData");
             
        /* Call the clearAll method of the data controller to delete data
         * inserted by the current app from the device and cloud. */
        Task<Void> clearTask = dataController.clearAll();

        /* Calling the data controller to clear user data from the device and cloud.
         * Listener needs to be registered to monitor whether the clearance is successful or not. */
        clearTask.addOnSuccessListener(result -> {
            Log.i(TAG, "clearTaskData success");
            dataResultListener.onSuccess(result);
        }).addOnFailureListener(error -> {
            Log.i(TAG, "clearTaskData error");
            dataResultListener.onFail(error);
        });
    }

    /**
     * Querying the Summary Fitness and Health Data of the User between selected dates.
     *
     * @param dataController {@link DataController} instance.
     * @param dataType {@link DataType} instance.
     * @param startTime An 8-digit integer in the format of YYYYMMDD, for example, 20200803.
     * @param endTime An 8-digit integer in the format of YYYYMMDD, for example, 20200903.
     * @param dataResultListener {@link ResultListener } listener.
     */
    @Override
    public void readDailySummation(DataController dataController, DataType dataType, int startTime, int endTime,
        ResultListener<SampleSet> dataResultListener) {
        Log.i(TAG, "call readDailySummation");
        /* Use the specified data type (DT_CONTINUOUS_STEPS_DELTA) to call the data controller to query
         * the summary data of this data type of the selected days. */
        Task<SampleSet> readDailySummationTask = dataController.readDailySummation(dataType, startTime, endTime);

        readDailySummationTask.addOnSuccessListener(sampleSet -> {
            // Result is SampleSet instance
            Log.i(TAG, "readDailySummation success");
            dataResultListener.onSuccess(sampleSet);
        });
        readDailySummationTask.addOnFailureListener(error -> {
            Log.i(TAG, "readDailySummation error");
            dataResultListener.onFail(error);
        });
    }

}
