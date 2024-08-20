package com.example.circleimagenativeapp

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.circleimagenativeapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sampleText.text = stringFromJNI()
        binding.btnStart.setOnClickListener {
            binding.btnStart.isVisible = false
            lifecycleScope.launch {
                val bitmap = binding.image.drawable.toBitmap()
                binding.image.setImageBitmap(bitmap.toCircleBitmap())
            }
        }
    }

    private external fun stringFromJNI(): String

    private external fun getCirclePixels(
        pixels: IntArray,
        with: Int,
        height: Int,
        size: Int
    ): IntArray

    companion object {
        init {
            System.loadLibrary("circleimagenativeapp")
            System.loadLibrary("circle_image")
        }
    }

    private fun Bitmap.toCircleBitmap(): Bitmap {
        val temp = this.copy(Bitmap.Config.ARGB_8888, true)
        var pixels = IntArray(temp.width * temp.height)
        temp.getPixels(pixels, 0, temp.width, 0, 0, temp.width, temp.height)

        pixels = getCirclePixels(pixels, temp.width, temp.height, pixels.size)

        temp.setPixels(
            pixels,
            0,
            temp.width,
            0,
            0,
            temp.width,
            temp.height
        )
        return temp
    }
}