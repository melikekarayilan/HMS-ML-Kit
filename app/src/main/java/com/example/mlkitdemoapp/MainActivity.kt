package com.example.mlkitdemoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.huawei.hmf.tasks.Task
import com.huawei.hms.mlsdk.common.MLApplication
import com.huawei.hms.mlsdk.langdetect.MLLangDetectorFactory
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetector
import com.huawei.hms.mlsdk.translate.MLTranslatorFactory
import com.huawei.hms.mlsdk.translate.cloud.MLRemoteTranslateSetting
import com.huawei.hms.mlsdk.translate.cloud.MLRemoteTranslator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    var sourceLangCode = "en"
    var targetLangCode = "tr"
    var mlRemoteTranslator: MLRemoteTranslator? = null
    var translateSetting: MLRemoteTranslateSetting? = null
    lateinit var translateButton: Button
    lateinit var editText: EditText
    lateinit var textView: TextView
    val languageArray = arrayOf(
        "Chinese",
        "English",
        "French",
        "Arabic",
        "Thai",
        "Spanish",
        "Turkish",
        "Portuguese",
        "Japanese",
        "German",
        "Italian",
        "Russian"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MLApplication.getInstance().apiKey =
            "CgB6e3x9aUovoke0Sfjx/44rDYGfcdiXrhEFgWW2UoJoJqQuvyLCo/TKMCYStsPiFl1BFdvPvMjyAixEHGucgvHv"
        translateButton = findViewById(R.id.btn_translate)
        editText = findViewById(R.id.et_current_language)
        textView = findViewById(R.id.tv_translated)
        language_spinner.onItemSelectedListener = this

        val spinnerAdapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, languageArray)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        language_spinner?.adapter = spinnerAdapter


        translateButton.setOnClickListener {
            val mlRemoteLangDetect: MLRemoteLangDetector = MLLangDetectorFactory.getInstance()
                .remoteLangDetector
            val firstBestDetectTask: Task<String> =
                mlRemoteLangDetect.firstBestDetect(editText.text.toString())
            firstBestDetectTask.addOnSuccessListener {
                sourceLangCode = it
                translateSetting =
                    MLRemoteTranslateSetting.Factory() // Set the source language code. The ISO 639-1 standard is used. This parameter is optional. If this parameter is not set, the system automatically detects the language.
                        .setSourceLangCode(sourceLangCode) // Set the target language code. The ISO 639-1 standard is used.
                        .setTargetLangCode(targetLangCode)
                        .create()
                mlRemoteTranslator =
                    MLTranslatorFactory.getInstance().getRemoteTranslator(translateSetting)

                languageTranslate()
                // Processing logic for detection success.
            }.addOnFailureListener {
                // Processing logic for detection failure.
                Log.d("error", it.toString())
                // Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun languageTranslate() {
        val task = mlRemoteTranslator?.asyncTranslate(editText.text.toString())
        task?.addOnSuccessListener { text ->
            textView.text = text
        }
            ?.addOnFailureListener {}
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when {
            languageArray[p2] == "Chinese" -> {
                targetLangCode = "zh"
            }
            languageArray[p2] == "Russian" -> {
                targetLangCode = "ru"
            }
            languageArray[p2] == "Turkish" -> {
                targetLangCode = "tr"
            }
            languageArray[p2] == "Portuguese" -> {
                targetLangCode = "pt"
            }
            languageArray[p2] == "Japanese" -> {
                targetLangCode = "ja"
            }
            languageArray[p2] == "German" -> {
                targetLangCode = "de"
            }
            languageArray[p2] == "Italian" -> {
                targetLangCode = "it"
            }
            languageArray[p2] == "English" -> {
                targetLangCode = "en"
            }
            languageArray[p2] == "French" -> {
                targetLangCode = "fr"
            }
            languageArray[p2] == "Arabic" -> {
                targetLangCode = "ar"
            }
            languageArray[p2] == "Thai" -> {
                targetLangCode = "th"
            }
            languageArray[p2] == "Spanish" -> {
                targetLangCode = "es"
            }
        }
    }
}