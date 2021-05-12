package balitsky.petproject.andersenhomework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MenuActivity : AppCompatActivity() {

    companion object {
        const val LOADER_TYPE = "LOADER_TYPE"
        const val OPEN_WITH_GLIDE_TAG = "GLIDE"
        const val OPEN_WITH_PICASSO_TAG = "PICASSO"
        const val OPEN_WITH_DEFAULT_LIBRARY_TAG = "DEFAULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    fun openFirstTask(view: View) {
        startActivity(Intent(this, FirstTaskActivity::class.java))
    }

    fun openSecondTaskWithGlide(view: View) {
        startActivity(Intent(this, SecondTaskActivity::class.java).apply {
            putExtra(
                LOADER_TYPE,
                OPEN_WITH_GLIDE_TAG
            )
        })
    }

    fun openSecondTaskWithPicaso(view: View) {
        startActivity(Intent(this, SecondTaskActivity::class.java).apply {
            putExtra(
                LOADER_TYPE,
                OPEN_WITH_PICASSO_TAG
            )
        })
    }

    fun openSecondTaskWithDefaultLibrary(view: View) {
        startActivity(Intent(this, SecondTaskActivity::class.java).apply {
            putExtra(
                LOADER_TYPE,
                OPEN_WITH_DEFAULT_LIBRARY_TAG
            )
        })
    }
}