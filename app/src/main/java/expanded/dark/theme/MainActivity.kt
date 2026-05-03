package expanded.dark.theme

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updateLayoutParams

class MainActivity : AppCompatActivity() {
    lateinit var main :        View
    lateinit var container:    View
    lateinit var marginTop:    View
    lateinit var marginBottom: View
    lateinit var marginLeft:   View
    lateinit var marginRight:  View

    object screen {
        var isWhite = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)

        main         = findViewById(R.id.main)
        container    = findViewById(R.id.container)
        marginTop    = findViewById(R.id.margin_top)
        marginBottom = findViewById(R.id.margin_bottom)
        marginRight  = findViewById(R.id.margin_right)
        marginLeft   = findViewById(R.id.margin_left)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(main) { _, insets ->

            val systemBars  = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val cutout      = insets.getInsets(WindowInsetsCompat.Type.displayCutout())
            val topInset    = maxOf(systemBars.top,    cutout.top)
            val bottomInset = maxOf(systemBars.bottom, cutout.bottom)
            val rightInset  = maxOf(systemBars.right,  cutout.right)
            val leftInset   = maxOf(systemBars.left,   cutout.left)

            main.doOnLayout {
                updateContainerSize(leftInset, topInset, rightInset, bottomInset)
                updateEdgeViews(leftInset, topInset, rightInset, bottomInset)
            }
            insets
        }
        container.requestApplyInsets()
        container.setBackgroundColor(if (screen.isWhite) Color.WHITE else Color.BLACK)
        container.setOnClickListener {
            screen.isWhite = !screen.isWhite
            container.setBackgroundColor(
                if (screen.isWhite) Color.WHITE else Color.BLACK
            )
        }

    }

    private fun updateContainerSize(leftInset: Int, topInset: Int, rightInset: Int, bottomInset: Int) {
        val w = main.width
        val h = main.height

        val usableWidth  = w - leftInset - rightInset
        val usableHeight = h - topInset - bottomInset

        container.updateLayoutParams<FrameLayout.LayoutParams> {
            width        = usableWidth
            height       = usableHeight
            marginStart  = leftInset
            topMargin    = topInset
            marginEnd    = rightInset
            bottomMargin = bottomInset
        }
    }

    private fun updateEdgeViews(leftInset: Int, topInset: Int, rightInset: Int, bottomInset: Int) {
        val w = main.width - leftInset - rightInset

        marginTop.updateLayoutParams< FrameLayout.LayoutParams> {
            width       = w
            height      = topInset
            marginStart = leftInset
        }

        marginBottom.updateLayoutParams<FrameLayout.LayoutParams> {
            height = bottomInset
        }

        marginLeft.updateLayoutParams<FrameLayout.LayoutParams> {
            width = leftInset
        }

        marginRight.updateLayoutParams<FrameLayout.LayoutParams> {
            width = rightInset
        }
    }
}