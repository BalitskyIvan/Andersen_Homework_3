package balitsky.petproject.andersenhomework

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


class SecondTaskActivity : AppCompatActivity() {

    private var loaderType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_task)
        loaderType = intent.getStringExtra(MenuActivity.LOADER_TYPE).toString()
    }

    fun onDownloadButtonClicked(view: View) {
        val url = findViewById<EditText>(R.id.editText).text.toString()
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE;
        when (loaderType) {
            MenuActivity.OPEN_WITH_GLIDE_TAG -> downloadImageWithGlide(url)
            MenuActivity.OPEN_WITH_PICASSO_TAG -> downloadImageWithPicaso(url)
            MenuActivity.OPEN_WITH_DEFAULT_LIBRARY_TAG -> downloadImageWithDefaultLibrary(url)
            else -> {
                onError()
            }
        }
    }

    private fun onError() {
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE;
        Toast.makeText(
            this@SecondTaskActivity,
            "Download failed :(",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun downloadImageWithGlide(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE;
                    findViewById<ImageView>(R.id.image_view).setImageBitmap(resource)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    onError()
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    private fun downloadImageWithPicaso(url: String) {
        Picasso.with(this).load(url).into(findViewById(R.id.image_view), object : Callback {
            override fun onSuccess() {
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE; }

            override fun onError() {
                onError()
            }
        });
    }

    private fun downloadImageWithDefaultLibrary(urlAddr: String) {
        thread {
            try {
                val url = URL(urlAddr)
                val connection: HttpURLConnection = url
                    .openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                val bitmap: Bitmap = BitmapFactory.decodeStream(input)
                runOnUiThread {
                    findViewById<ImageView>(R.id.image_view).setImageBitmap(bitmap)
                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE;
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread { onError() }
            }
        }
    }
}