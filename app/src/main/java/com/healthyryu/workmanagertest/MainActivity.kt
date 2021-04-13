package com.healthyryu.workmanagertest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.healthyryu.workmanagertest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btn.setOnClickListener {

                val inputData = Data.Builder().putString(WorkManagerT.INPUT, "input data").build()
                val request = OneTimeWorkRequestBuilder<WorkManagerT>().setInputData(inputData).build()
                val worker = WorkManager.getInstance(this@MainActivity)
                worker.enqueue(request)

                worker.getWorkInfoByIdLiveData(request.id).observe(this@MainActivity, Observer {
                    println(">> WorkInfo : ${it.state}")
                    // 돌아오는 값 -> WorkInfo : SUCCEEDED

                    when (it.state) {
                        WorkInfo.State.RUNNING -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        WorkInfo.State.SUCCEEDED -> {
                            Toast.makeText(this@MainActivity, "Completed", Toast.LENGTH_SHORT).show()
                            progressBar.visibility = View.GONE
                        }
                        WorkInfo.State.FAILED, WorkInfo.State.BLOCKED -> {
                            Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                            progressBar.visibility = View.GONE
                        }
                        WorkInfo.State.CANCELLED -> {
                            Toast.makeText(this@MainActivity, "Canceled", Toast.LENGTH_SHORT).show()
                            progressBar.visibility = View.GONE
                        }
                        else -> {
                        }
                    }

                    println(">> ${it.outputData}")
                    // 결과값 -> Data {output_data2 : 123, output_data : 가나다, }
                })
            }
        }
    }
}