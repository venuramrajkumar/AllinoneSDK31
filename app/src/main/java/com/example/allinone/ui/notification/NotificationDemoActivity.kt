package com.example.allinone.ui.notification

import android.app.PendingIntent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.allinone.R
import com.example.allinone.databinding.ActivityNotificationBinding

class NotificationDemoActivity : AppCompatActivity() {

    lateinit var binding: ActivityNotificationBinding

    //Biometric prompt
    lateinit var biometricPrompt: BiometricPrompt
    lateinit var promptInfo: BiometricPrompt.PromptInfo


    //Handle Fingerprint sensor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupBiometric()

        //Registering channel is done in Application class.
        binding.triggernotification.setOnClickListener {

            MyAppsNotificationManager.triggerNotification(
                NotificationDetailActivity::class.java,
                getString(R.string.NEWS_CHANNEL_ID),
                "News",
                "Breaking News",
                "BigNews",
                1,
                false,
                resources.getInteger(R.integer.notificationId),
                PendingIntent.FLAG_UPDATE_CURRENT,
                this.applicationContext
            )

        }

        binding.cancelNotification.setOnClickListener {
            MyAppsNotificationManager.cancelNotification(
                resources.getInteger(R.integer.notificationId),
                NotificationManagerCompat.from(this.applicationContext)
            )
        }

        binding.updateNotification.setOnClickListener {
            MyAppsNotificationManager.updateWithPicture(
                NotificationDetailActivity::class.java,
                "Updated News",
                "Breaking Burst News",
                getString(R.string.NEWS_CHANNEL_ID),
                resources.getInteger(R.integer.notificationId),
                "This is a updated information for bigpicture String",
                PendingIntent.FLAG_UPDATE_CURRENT,
                this.applicationContext
            )
        }

        binding.stackBuilderNavigation.setOnClickListener {

            MyAppsNotificationManager.triggerNotificationWithBackStack(
                NotificationDetailActivity::class.java,
                getString(R.string.NEWS_CHANNEL_ID),
                "Gmail N0tification",
                "Gmail Detail -> Gmail list-> Home screen",
                "GMAIL",
                NotificationCompat.PRIORITY_DEFAULT,
                true,
                PendingIntent.FLAG_UPDATE_CURRENT,
                resources.getInteger(R.integer.notificationId),
                this.applicationContext
            )
        }


    }

    private fun setupBiometric() {
        val biometricManager: BiometricManager = BiometricManager.from(this)

        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> Toast.makeText(
                this,
                "NO hardware",
                Toast.LENGTH_SHORT
            ).show()

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> Toast.makeText(
                this,
                "Device Doesn't support",
                Toast.LENGTH_SHORT
            ).show()

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> Toast.makeText(
                this,
                "Fingerprint not enroleld",
                Toast.LENGTH_SHORT
            ).show()

            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                Toast.makeText(
                    this,
                    "Security Update Req",
                    Toast.LENGTH_SHORT
                ).show()
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                Toast.makeText(
                    this,
                    "Error Unsupproted",
                    Toast.LENGTH_SHORT
                ).show()
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                Toast.makeText(
                    this,
                    "Unknow Status",
                    Toast.LENGTH_SHORT
                ).show()
            }
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Toast.makeText(
                    this,
                    "Success",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object :
            BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)

                Toast.makeText(baseContext, "Login succcess", Toast.LENGTH_SHORT).show()
                binding.notiDemoLayout.visibility = View.VISIBLE
            }

        })

        promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle("Authenticate")
            .setDescription("Keep your finger on sensor")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL or
                        BiometricManager.Authenticators.BIOMETRIC_WEAK
            )
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}