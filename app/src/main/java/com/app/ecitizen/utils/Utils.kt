package com.app.ecitizen.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.URLUtil
import android.widget.Toast
import com.app.ecitizen.R


fun Context.downloadFile(url: String, fileName: String, desc: String) {

    if(URLUtil.isValidUrl(url).not()){
        Toast.makeText(this, getString(R.string.this_download_file_have_some_issue_with_url), Toast.LENGTH_SHORT).show()
    }
    val request = DownloadManager.Request(Uri.parse(url))
        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        .setTitle(fileName)
        .setDescription(desc)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(false)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
    val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)

    Toast.makeText(this, getString(R.string.download_started_to_check_download_status_check_status_bar), Toast.LENGTH_SHORT).show()
}