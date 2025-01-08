package com.ujjwal.stepathonanimation.onboarding.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.ujjwal.stepathonanimation.R

/**
 * Text arc.
 */
class TextArc @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(
    context,
    attrs,
    defStyleAttr
) {
    private val rect = RectF()
    private val path = Path()
    private var radius = 320
    private var centerAngle = -90
    private var textSize: Int = (resources.displayMetrics.density * 16).toInt()
    private var text = ""
    private var shadowColor = Color.WHITE
    private var shadowX = 0
    private var shadowY = 0
    private var shadowRadius = 30
    private var paddingTop = 0

    private var textColor = Color.WHITE
    private var fontFamily: Typeface? = null
    private var letterSpacing: Float = 0f

    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG)
    private var offset = 0

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextArc)

        radius = typedArray.getDimensionPixelSize(R.styleable.TextArc_radius, radius)

        centerAngle = typedArray.getInteger(R.styleable.TextArc_center_angle, centerAngle)
        textSize = typedArray.getDimensionPixelSize(R.styleable.TextArc_text_size, textSize)
        shadowColor = typedArray.getColor(R.styleable.TextArc_shadow_color, Color.WHITE)
        shadowX = typedArray.getInteger(R.styleable.TextArc_shadow_x, 0)
        shadowY = typedArray.getInteger(R.styleable.TextArc_shadow_y, 0)
        paddingTop = typedArray.getInteger(R.styleable.TextArc_padding_top, 0)
        shadowRadius = typedArray.getInteger(R.styleable.TextArc_shadow_radius, 30)
        text = typedArray.getString(R.styleable.TextArc_text) ?: ""
        textColor = typedArray.getColor(R.styleable.TextArc_text_color, Color.WHITE)

        fontFamily = typedArray.getResourceId(R.styleable.TextArc_font_family, -1).let {
            if (it == -1) {
                null
            } else {
                ResourcesCompat.getFont(context, it)
            }
        }

        letterSpacing = typedArray.getFloat(R.styleable.TextArc_letter_spacing, letterSpacing)

        typedArray.recycle()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        offset = (textSize * 0.75f).toInt()

        val width = if (radius > 0) radius * 2 + offset * 2 else 0
        val height = if (radius > 0) radius * 2 + offset * 2 else 0

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        paintText.color = textColor
        paintText.typeface = fontFamily
        paintText.textSize = textSize.toFloat()
        paintText.letterSpacing = letterSpacing

        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        val textWidth = paintText.measureText(text)
        val circumference = (2 * Math.PI * radius).toFloat() // Circumference length
        val textAngle = textWidth * 360 / circumference // Angle occupied by the text
        val startAngle = centerAngle - textAngle / 2
        val oval = rect.apply {
            left = offset.toFloat()
            top = offset.toFloat() + paddingTop
            right = (radius * 2 + offset).toFloat()
            bottom = (radius * 2 + offset).toFloat()
        }
        path.reset()
        path.addArc(oval, startAngle, 350f)

        paintText.setShadowLayer(
            shadowRadius.toFloat(),
            shadowX.toFloat(),
            shadowY.toFloat(),
            shadowColor
        )

        canvas.drawTextOnPath(text, path, 0f, 0f, paintText)
    }


    /**
     * Sets text.
     * @param text Text.
     */
    fun setText(text: String?) {
        this.text = text ?: ""
        invalidate()
    }

    /**
     * Sets radius.
     * @param radius Radius.
     */
    fun setRadius(radius: Int) {
        this.radius = radius
        invalidate()
    }

    /**
     * Sets center angle.
     * @param centerAngle Center angle.
     */
    fun setCenterAngle(centerAngle: Int) {
        this.centerAngle = centerAngle
        invalidate()
    }

    /**
     * Sets text color.
     * @param textColor Text color.
     */
    fun setTextColor(textColor: Int) {
        this.textColor = textColor
        invalidate()
    }

    /**
     * Sets text size.
     * @param textSize Text size.
     */
    fun setTextSize(textSize: Int) {
        this.textSize = textSize.toFloat().toInt()
        invalidate()
    }

    /**
     * Sets font family.
     * @param fontFamily Font family.
     */
    fun setFontFamily(fontFamily: Typeface?) {
        this.fontFamily = fontFamily
        invalidate()
    }

    /**
     * Sets letter spacing.
     * @param letterSpacing Letter spacing.
     */
    fun setLetterSpacing(letterSpacing: Float) {
        this.letterSpacing = letterSpacing
        invalidate()
    }

}