package com.edlo.demodrama.ui.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.edlo.demodrama.BuildConfig
import com.edlo.demodrama.databinding.ActivityVideoStreamingBinding
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.analytics.PlaybackStats
import com.google.android.exoplayer2.decoder.DecoderCounters
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory
import com.google.android.exoplayer2.source.*
import com.google.android.exoplayer2.source.dash.DashMediaSource
//import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.*
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.video.VideoRendererEventListener
import okhttp3.OkHttpClient
import java.io.InputStream
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.*


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class VideoStreamingActivity : AppCompatActivity() {

    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var binding: ActivityVideoStreamingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVideoStreamingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ignoreSSLHandshake()

        val vid_url: String? = intent?.extras?.getString(VIDEO_URL)

        vid_url?.let{
//            initExoPlayerBase(Uri.parse(it))
            initExoPLayer(Uri.parse(it))
        } ?: run {
            finish()
        }
//
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initExoPLayer(mp4VideoUri: Uri) {

        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        val simpleExoPlayerView = binding.videoView
        simpleExoPlayerView.useController = true //set to true or false to see controllers
        simpleExoPlayerView.requestFocus()

        simpleExoPlayerView.player = simpleExoPlayer

        val httpSourceFactory = createDataSourceFactory(this, Util.getUserAgent(this, BuildConfig.APPLICATION_ID))
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this, null, httpSourceFactory)
        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mp4VideoUri)
        val loopingSource = LoopingMediaSource(videoSource)
        // Prepare the player with the source.
        simpleExoPlayer.prepare(loopingSource)

        simpleExoPlayer.addListener(object : Player.EventListener {
            override fun onLoadingChanged(isLoading: Boolean) {}
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when(playbackState) {
                    PlaybackStats.PLAYBACK_STATE_JOINING_FOREGROUND, PlaybackStats.PLAYBACK_STATE_BUFFERING -> binding.progressBar.visibility = View.VISIBLE
                    else -> binding.progressBar.visibility = View.GONE
                }
            }
            override fun onPlayerError(error: ExoPlaybackException) {
                simpleExoPlayer.stop()
            }
        })
        simpleExoPlayer.playWhenReady = true //run file/link when ready to play.
    }

    override fun onDestroy() {
        super.onDestroy()
        simpleExoPlayer.release()
    }

    companion object {
        const val VIDEO_URL = "videoUrl"

        fun createDataSourceFactory(context: Context?,
                                    userAgent: String?): DefaultDataSourceFactory? {
            //// Default parameters, except allowCrossProtocolRedirects is true
//            var okHttpClient = OkHttpClient()
//            try {
//                val ksTrust: KeyStore = KeyStore.getInstance("BKS")
//                val instream: InputStream = context!!.resources.openRawResource(R.raw.mytruststore)
//                ksTrust.load(instream, "secret".toCharArray())
//
//                // TrustManager decides which certificate authorities to use.
//                val tmf: TrustManagerFactory = TrustManagerFactory
//                        .getInstance(TrustManagerFactory.getDefaultAlgorithm())
//                tmf.init(ksTrust)
//                val sslContext: SSLContext = SSLContext.getInstance("TLS")
//                sslContext.init(null, tmf.getTrustManagers(), null)
//                val trustManager = getSystemDefaultTrustManager()
//                trustManager?.let{
//                    okHttpClient = OkHttpClient.Builder()
//                            .sslSocketFactory(sslContext.socketFactory, trustManager)
//                            .build()
//                }
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            val httpDataSourceFactory: DataSource.Factory = OkHttpDataSourceFactory(okHttpClient, userAgent)
            val httpDataSourceFactory = DefaultHttpDataSourceFactory(
                    userAgent,
                    null,
                    DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                    DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                    true /* allowCrossProtocolRedirects */
            )
            return DefaultDataSourceFactory(
                    context,
                    null,
                    httpDataSourceFactory
            )
        }
        fun getSystemDefaultTrustManager(): X509TrustManager? {
            return try {
                val trustManagerFactory = TrustManagerFactory.getInstance(
                        TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(null as KeyStore?)
                val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers
                if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
                    throw IllegalStateException("Unexpected default trust managers:"
                            + Arrays.toString(trustManagers))
                }
                trustManagers[0] as X509TrustManager
            } catch (e: GeneralSecurityException) {
                throw AssertionError() // The system has no TLS. Just give up.
            }
        }

        fun ignoreSSLHandshake() {
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf<X509Certificate>()
                    }
                    override fun checkClientTrusted(certs: Array<X509Certificate?>?, authType: String?) {}
                    override fun checkServerTrusted(certs: Array<X509Certificate?>?, authType: String?) {}
                })
                val sc = SSLContext.getInstance("TLS")
                // trustAllCerts信任所有的证书
                sc.init(null, trustAllCerts, SecureRandom())
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
                HttpsURLConnection.setDefaultHostnameVerifier(object: HostnameVerifier {
                    override fun verify(hostname: String?, session: SSLSession?): Boolean {
                        return true
                    }
                })
            } catch (e: java.lang.Exception) {
            }
        }
    }
}
