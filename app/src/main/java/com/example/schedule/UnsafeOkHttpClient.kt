package com.example.schedule

import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object UnsafeOkHttpClient {

    // Метод для получения небезопасного SSLSocketFactory
    fun getUnsafeSslSocketFactory(): javax.net.ssl.SSLSocketFactory {
        try {
            // Создаем X509TrustManager, который не проверяет сертификаты
            val trustAllCertificates: TrustManager = object : X509TrustManager {
                // Возвращаем пустой массив, чтобы избежать NullPointerException
                override fun getAcceptedIssuers(): Array<X509Certificate>? = emptyArray()
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            }

            // Инициализируем SSLContext с этим TrustManager
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf(trustAllCertificates), java.security.SecureRandom())

            return sslContext.socketFactory
        } catch (e: Exception) {
            throw RuntimeException("Failed to create an SSLSocketFactory", e)
        }
    }

    // Метод для получения небезопасного X509TrustManager
    fun getUnsafeTrustManager(): X509TrustManager {
        return object : X509TrustManager {
            // Возвращаем пустой массив, чтобы избежать NullPointerException
            override fun getAcceptedIssuers(): Array<X509Certificate>? = emptyArray()
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        }
    }
}
