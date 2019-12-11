/*
package com.example.kotlinfacebookloginwithtoken.ui

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinfacebookloginwithtoken.R
import com.example.kotlinfacebookloginwithtoken.api.service.MyClient
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.iid.FirebaseInstanceId
import okhttp3.ResponseBody
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class FbLoginActivity : AppCompatActivity() {
    private var loginButton: LoginButton? = null
    private var displayImage: ImageView? = null
    private var displayName: TextView? = null
    private var emailId: TextView? = null
    private var callbackManager: CallbackManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton = findViewById(R.id.login_button1)
        displayName = findViewById(R.id.profile_name)
        emailId = findViewById(R.id.profile_email)
        displayImage = findViewById(R.id.profile_pic)

        val logiE=loginButton
       //loginButton.setReadPermissions(Arrays.asList("email", "public_profile"))
        logiE?.setReadPermissions(Arrays.asList("email", "public_profile"))
        callbackManager = CallbackManager.Factory.create()
        logiE?.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val accessToken = loginResult.accessToken
                userLoginInformation(accessToken)
                token
                insertData()
            }

            override fun onCancel() {}
            override fun onError(error: FacebookException) {}
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun userLoginInformation(newAccessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(newAccessToken) { `object`, response ->
            try {
                name = `object`.getString("name")
                email = `object`.getString("email")
                val image = `object`.getJSONObject("picture").getJSONObject("data").getString("url") //"https://graph.facebook.com/"+id+ "/picture?type=normal";
                insertData()
                emailId!!.text = email
                displayName!!.text = name
                insertData()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name,email,picture.width(200)")
        request.parameters = parameters
        request.executeAsync()
    }

    private var token: String=""
        private get() {
            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this@FbLoginActivity) { instanceIdResult ->
                val newToken = instanceIdResult.token
                Log.e("newToken", newToken)
                token = newToken
            }
            return token
        }

    private fun insertData() {
        val call: Call<ResponseBody?>? = MyClient.instance?.myApi?.insertdata(email,token,alreadyUser = true)//getMyApi().insertdata(email, token)
        call?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {}
            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {}
        })
    }

    companion object {
        var email: String? = null
        var name: String? = null
        var token: String? = null
    }
}*/


package com.example.kotlinfacebookloginwithtoken.ui

import com.example.kotlinfacebookloginwithtoken.R
import com.example.kotlinfacebookloginwithtoken.api.service.MyClient

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult

import org.json.JSONException
import org.json.JSONObject

import java.util.Arrays

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FbLoginActivity:AppCompatActivity() {
    private var loginButton:LoginButton? = null
    private var displayImage:ImageView? = null
    private var displayName:TextView? = null
    private var emailId:TextView? = null

    private var callbackManager:CallbackManager? = null

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.login_button1)
        displayName = findViewById(R.id.profile_name)
        emailId = findViewById(R.id.profile_email)
        displayImage = findViewById(R.id.profile_pic)

        loginButton!!.setReadPermissions(Arrays.asList("email", "public_profile"))
        callbackManager = CallbackManager.Factory.create()

        loginButton!!.registerCallback(callbackManager, object:FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult:LoginResult) {
                val accessToken = loginResult.accessToken
                userLoginInformation(accessToken)
                getToken()
                insertData()
            }

            override fun onCancel() {

            }

            override fun onError(error:FacebookException) {

            }
        })
    }
    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun userLoginInformation(newAccessToken:AccessToken) {
        val request = GraphRequest.newMeRequest(newAccessToken
        ) { `object`, response ->
            try {
                name = `object`.getString("name")
                email = `object`.getString("email")
                val image = `object`.getJSONObject("picture").getJSONObject("data").getString("url")//"https://graph.facebook.com/"+id+ "/picture?type=normal";
                insertData()
                emailId!!.text = email
                displayName!!.text = name

                insertData()
            } catch (e:JSONException) {
                e.printStackTrace()
            }
        }

        val parameters = Bundle()
        parameters.putString("fields", "id,name,email,picture.width(200)")
        request.parameters = parameters
        request.executeAsync()
    }

    private fun getToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this@FbLoginActivity, object:OnSuccessListener<InstanceIdResult> {
            override fun onSuccess(instanceIdResult:InstanceIdResult) {
                val newToken = instanceIdResult.token
                Log.e("newToken", newToken)
                token = newToken
            }
        })
    }

    private fun insertData() {
        val call: Call<ResponseBody?>? = MyClient.instance?.myApi?.insertdata(email,token, alreadyUser)
        call?.enqueue(object:Callback<ResponseBody> {
            override fun onResponse(call:Call<ResponseBody>, response:Response<ResponseBody>) {
                Toast.makeText(this@FbLoginActivity, "Data inserted succesfully..", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call:Call<ResponseBody>, t:Throwable) {
                Toast.makeText(this@FbLoginActivity, "Data Failed", Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {
         var email: String? = null
         var name:String? = null
         var token:String? = null
        var alreadyUser = true
    }
}

private fun <T> Call<T>?.enqueue(callback: Callback<ResponseBody>) {

}
