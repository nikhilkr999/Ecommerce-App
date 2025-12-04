package com.compose.ecommerceapp.screens.ads

import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.compose.ecommerceapp.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

@Composable
fun NativeAdComposable() {

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .height(320.dp), // AD Card Height
        factory = { context ->

            val adView = LayoutInflater.from(context)
                .inflate(R.layout.native_ad_layout, null) as NativeAdView

            val adLoader = AdLoader.Builder(
                context,
                "ca-app-pub-3940256099942544/2247696110" // test native ad id
            )
                .forNativeAd { nativeAd ->

                    adView.headlineView = adView.findViewById(R.id.ad_headline)
                    adView.bodyView = adView.findViewById(R.id.ad_body)
                    adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
                    adView.iconView = adView.findViewById(R.id.ad_app_icon)
                    adView.mediaView = adView.findViewById(R.id.ad_media)

                    (adView.headlineView as TextView).text = nativeAd.headline
                    (adView.bodyView as TextView).text = nativeAd.body ?: ""
                    (adView.callToActionView as Button).text = nativeAd.callToAction

                    nativeAd.icon?.let {
                        (adView.iconView as ImageView).setImageDrawable(it.drawable)
                    }

                    val mediaView = adView.mediaView as MediaView
                    mediaView.setMediaContent(nativeAd.mediaContent)

                    adView.setNativeAd(nativeAd)
                }
                .withAdListener(object : AdListener() {})
                .build()

            adLoader.loadAd(AdRequest.Builder().build())

            adView
        }
    )
}
