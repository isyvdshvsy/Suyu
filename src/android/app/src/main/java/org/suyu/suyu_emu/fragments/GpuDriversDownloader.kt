package org.suyu.suyu_emu.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Environment
import android.widget.Toast
import java.io.File
import android.app.ProgressDialog

class GpuDriversDownloader(private val context: Context) {
    private var driverFilePath: String? = null

    // 下载文件的URL列表
    private val downloadUrls = listOf(
        "https://example.com/file1",
        "https://example.com/file2",
        "https://example.com/file3"
    )

    // 文件名列表
    private val fileNames = listOf("file1", "file2", "file3")

    fun checkAndDownload() {
        val gpuDriversDir = Environment.getExternalStorageDirectory().absolutePath + "/gpu_drivers"
        val gpuDriversDirFile = context.getExternalFilesDir("gpu_drivers")

        if (gpuDriversDirFile == null || !gpuDriversDirFile.exists()) {
            // 目录不存在，提示用户下载文件
            showDownloadDialog()
        } else {
            // 目录存在，检查文件是否存在
            val missingFiles = ArrayList<String>()
            for (fileName in fileNames) {
                val file = gpuDriversDir + "/" + fileName
                if (!fileExists(file)) {
                    missingFiles.add(fileName)
                }
            }
            if (missingFiles.isNotEmpty()) {
                // 有文件缺失，提示用户下载
                showDownloadDialog()
            } else {
                // 所有文件都存在，无需下载
                Toast.makeText(context, "所有文件已存在", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDownloadDialog() {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("下载文件")
        alertDialogBuilder.setMessage("需要下载文件以继续。")
        alertDialogBuilder.setPositiveButton("下载") { dialog, which ->
            DownloadFilesTask().execute()
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton("取消") { dialog, which ->
            dialog.dismiss()
        }
        alertDialogBuilder.setCancelable(false)
        val alertDialog: Dialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private inner class DownloadFilesTask : AsyncTask<Void, Int, Void>() {

        private lateinit var progressDialog: ProgressDialog

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(context)
            progressDialog.setTitle("下载中")
            progressDialog.setMessage("请稍候...")
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            progressDialog.setCancelable(false)
            progressDialog.max = downloadUrls.size
            progressDialog.progress = 0
            progressDialog.show()
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            for ((index, url) in downloadUrls.withIndex()) {
                val request = DownloadManager.Request(Uri.parse(url))
                    .setDestinationInExternalFilesDir(context, "gpu_drivers", fileNames[index])
                    .setTitle(fileNames[index])
                    .setDescription("下载文件 $index")
                downloadManager.enqueue(request)
                // 更新进度
                publishProgress(index + 1)
            }

            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            values[0]?.let {
                progressDialog.progress = it
            }
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            progressDialog.dismiss()
            Toast.makeText(context, "文件下载完成", Toast.LENGTH_SHORT).show()

            // 保存驱动程序文件的路径
            driverFilePath = "${context.getExternalFilesDir("gpu_drivers")}/file1" // 这里假设第一个文件是驱动程序文件

            // 调用安装函数
            val driverManagerFragment = DriverManagerFragment()
            val driverFile = File(driverFilePath!!)
            driverManagerFragment.getDriver(driverFile)
        }
    }

    private fun fileExists(filePath: String): Boolean {
        val file = java.io.File(filePath)
        return file.exists()
    }
}
