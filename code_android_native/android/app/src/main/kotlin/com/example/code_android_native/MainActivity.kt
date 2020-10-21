package com.example.code_android_native

import io.flutter.embedding.android.FlutterActivity
import android.app.Activity;
import android.net.Uri;
import android.content.Intent;
import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.NonNull
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result

import io.flutter.plugins.GeneratedPluginRegistrant
import java.lang.invoke.MethodHandle


class MainActivity: FlutterActivity() {
    val ENGINE_ID = "1";
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val flutterEngine = FlutterEngine(this)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        FlutterEngineCache.getInstance().put(ENGINE_ID, flutterEngine)
        val chanel = MethodChannel(flutterEngine.dartExecutor,"com.example.code_android_native/myChanel")
        chanel.setMethodCallHandler(
                object : MethodChannel.MethodCallHandler {
                    override fun onMethodCall( call: MethodCall,  result: MethodChannel.Result) {
                        val url = call.argument<String>("url")
                        if (call.method.equals(("openBrowser"))){
                            openBrowser(url.toString())
                        }else
                        {
                            result.notImplemented()
                        }
                    }

                }
        )
        startActivity(FlutterActivity.withCachedEngine(ENGINE_ID).build(this))
    }

    private fun openBrowser(url : String) {
        val Intent = Intent(Intent.ACTION_VIEW)
        Intent.setData(Uri.parse(url))
        this.startActivity(intent)
    }
}


