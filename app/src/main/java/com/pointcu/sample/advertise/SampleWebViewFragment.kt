package com.pointcu.sample.advertise

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.pointcu.sample.R
import com.pointcu.sample.common.BaseFragment

class SampleWebViewFragment : BaseFragment(R.layout.fragment_sample_webview) {

    private lateinit var webView: WebView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView = findViewById(R.id.webView)!!
        initWebView()
        webView.loadUrl(TEST_URL)
    }

    private fun initWebView() {

        with(webView.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            loadsImagesAutomatically = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            allowFileAccess = true
            allowContentAccess = true
        }

        webView.webChromeClient = WebChromeClient()

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                // TODO 로딩 완료 체크한 이후 호출 시 아래 로직 진행하도록 처리 필요
                val uri = request.url

                Log.d(TAG, "[adwon sample] shouldOverrideUrlLoading URL : $uri")
                return handleUrl(uri)
            }
        }
    }

    /**
     * true  : 앱에서 처리 완료
     * false : WebView 계속 진행
     */
    private fun handleUrl(uri: Uri): Boolean {

        val scheme = uri.scheme?.lowercase() ?: return false

        // 앱 내부 이벤트
        if (scheme == "app") {
            return handleAppScheme(uri)
        }

        return when (scheme) {
            "http", "https" -> {
                openBrowser(uri)
                true
            }

            "intent" -> {
                openIntentScheme(uri.toString())
            }

            else -> {
                openCustomScheme(uri)
            }
        }
    }

    /**
     * app://reward
     */
    private fun handleAppScheme(uri: Uri): Boolean {

        when (uri.host) {

            "reward" -> {
                Toast.makeText(
                    requireContext(),
                    "광고 보고 적립하기",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {
                Log.w(TAG, "[adwon sample] Unknown app scheme : $uri")
            }
        }

        return true
    }

    /**
     * http / https
     */
    private fun openBrowser(uri: Uri): Boolean {

        val context = requireContext()
        val pm = context.packageManager

        val browserIntent = Intent(Intent.ACTION_VIEW, uri)

        return try {

            // 1. Chrome 우선
            browserIntent.setPackage("com.android.chrome")

            if (browserIntent.resolveActivity(pm) != null) {
                startActivity(browserIntent)
                return true
            }

            // 2. 기본 브라우저
            browserIntent.setPackage(null)

            if (browserIntent.resolveActivity(pm) != null) {
                startActivity(browserIntent)
                return true
            }

            // 3. Chooser
            val chooser = Intent.createChooser(browserIntent, "브라우저 선택")

            if (chooser.resolveActivity(pm) != null) {
                startActivity(chooser)
                return true
            }

            false

        } catch (e: Exception) {
            Log.e(TAG, "[adwon sample] openBrowser()", e)
            false
        }
    }

    /**
     * intent://...
     */
    private fun openIntentScheme(url: String): Boolean {
        return try {
            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
            val pm = requireContext().packageManager

            if (intent.resolveActivity(pm) != null) {
                startActivity(intent)

            } else {
                val fallback = intent.getStringExtra("browser_fallback_url")

                when {
                    !fallback.isNullOrEmpty() -> {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(fallback)))
                    }

                    !intent.`package`.isNullOrEmpty() -> {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${intent.`package`}")))
                    }
                }
            }
            true

        } catch (e: Exception) {
            Log.e(TAG, "[adwon sample] intent scheme", e)
            false
        }
    }

    /**
     * kakaotalk://
     * ispmobile://
     * payco://
     * market://
     * mailto:
     * tel:
     * ...
     */
    private fun openCustomScheme(uri: Uri): Boolean {

        return try {
            startActivity(Intent(Intent.ACTION_VIEW, uri))
            true

        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, "[adwon sample] No Activity : $uri")
            false

        } catch (e: Exception) {
            Log.e(TAG, "[adwon sample] openCustomScheme()", e)
            false
        }
    }

    override fun onDestroyView() {
        webView.destroy()
        super.onDestroyView()
    }

    companion object {
        private const val TEST_URL = "https://adwon24.github.io/webview-test/test_mobiwith_web_iframe.html"

        fun newInstance() = SampleWebViewFragment()
    }
}