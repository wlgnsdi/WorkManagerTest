package com.healthyryu.workmanagertest

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class WorkManagerT(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val input = inputData.getString(INPUT)
        println(input)
        for (num in 0..100) {
            println(">> Num : $num")
            Thread.sleep(100)
        }

        val outputData = workDataOf(OUTPUT to "가나다", OUTPUT2 to 123)

        return Result.success(outputData)
    }

    companion object {
        const val INPUT = "input_data"
        const val OUTPUT = "output_data"
        const val OUTPUT2 = "output_data2"
    }
}