package com.example.chechnumberofcontacts

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity.javaClass.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        askForPermissions()

//        checkAndShowNumberOfContacts()
    }

    private fun askForPermissions() {
//        val ALL_PERMISSIONS = 101
//        val permissions = arrayOf(
//            Manifest.permission.READ_CONTACTS
//        )
//        ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)

        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                Toast.makeText(this@MainActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    this@MainActivity,
                    "Permission Denied\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(Manifest.permission.READ_CONTACTS)
            .check()

        if (checkIfHasPermission(Manifest.permission.READ_CONTACTS)) {
            Log.v(TAG, "Permissions ok")
        } else {
            Log.e(TAG, "Permissions not granted!")
        }
    }

    private fun checkIfHasPermission(permission: String): Boolean {
        val res: Int = this.checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }

    fun checkAndShowNumberOfContacts(view: View) {
        var cursor: Cursor =
            managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        var count: Int = cursor.getCount()
        tv1.text = count.toString()

        cursor =
            managedQuery(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        count = cursor.count
        tv2.text = count.toString()

        cursor =
            managedQuery(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        count = cursor.count
        tv3.text = count.toString()
    }

}
