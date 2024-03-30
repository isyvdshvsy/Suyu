package org.suyu.suyu_emu.fragments

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.suyu.suyu_emu.R

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_fragment) // 设置对应的布局文件

        // 设置按钮点击事件监听器
        findViewById<Button>(R.id.button4).setOnClickListener {
            // 开始下载文件1
            downloadFile("https://example.com/yourfile1.txt", "yourfile1.txt")
        }

        findViewById<Button>(R.id.button5).setOnClickListener {
            // 开始下载文件2
            downloadFile("https://example.com/yourfile2.txt", "yourfile2.txt")
        }

        findViewById<Button>(R.id.button6).setOnClickListener {
            // 开始下载文件3
            downloadFile("https://example.com/yourfile3.txt", "yourfile3.txt")
        }

        findViewById<Button>(R.id.button7).setOnClickListener {
            // 开始下载文件4
            downloadFile("https://example.com/yourfile4.txt", "yourfile4.txt")
        }

        findViewById<Button>(R.id.button8).setOnClickListener {
            // 开始下载文件5
            downloadFile("https://example.com/yourfile5.txt", "yourfile5.txt")
        }
    }

    private fun downloadFile(fileUrl: String, fileName: String) {
        val request = DownloadManager.Request(Uri.parse(fileUrl))
            .setTitle("Downloading File") // 设置下载通知标题
            .setDescription("Downloading...") // 设置下载通知描述
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // 设置下载完成后是否显示通知

        // 设置文件保存路径和文件名
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            fileName
        )

        // 获取DownloadManager的实例
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        // 开始下载
        downloadManager.enqueue(request)
    }

    // 下载完成后弹出对话框
    private fun showDownloadCompleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Download Complete")
            .setMessage("File downloaded successfully!")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
