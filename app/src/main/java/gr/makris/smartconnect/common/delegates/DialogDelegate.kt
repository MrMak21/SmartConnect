package gr.makris.smartconnect.common.delegates

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatTextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.button.MaterialButton
import gr.makris.smartconnect.R
import gr.makris.smartconnect.application.SmartConnectApplication
import gr.makris.smartconnect.ui.base.BaseActivity
import timber.log.Timber

class DialogDelegate(private val baseActivity: BaseActivity) {

    private var dialog: MaterialDialog? = null

    fun showDialog(
        title: String? = null,
        subTitle: String? = null,
        contentText: String,
        mainButtonText: String = baseActivity.getString(R.string.global_dialog_ok),
        mainButtonCallBack: (DialogDelegate) -> Unit = { _ -> dismiss() },
        secondaryButtonText: String = baseActivity.getString(R.string.global_dialog_cancel)!!,
        secondaryButtonCallBack: ((DialogDelegate) -> Unit)? = null,
        dismissibleOnTouchOutside: Boolean = true,
        cancelable: Boolean = true
    ) {
        if (dialog != null) {
            Timber.d("isShowing - ${dialog!!.isShowing}")
        }
        dialog?.let {
            if (it.isShowing)
                return
        }
        dismiss()

        baseActivity.apply {
            dialog = MaterialDialog(this).show {
                customView(
                    R.layout.layout_dialog,
                    noVerticalPadding = true,
                    dialogWrapContent = true
                )
                val customView = this.getCustomView()

                updateText(title, customView.findViewById(R.id.titleTextView))
                customView.findViewById<View>(R.id.dialogDivider).visibility = if (title != null) View.VISIBLE else View.GONE

                updateText(subTitle, customView.findViewById(R.id.subtitleTextView))
                customView.findViewById<AppCompatTextView>(R.id.mainContentTextView).text = contentText

                val hasTwoButtons = secondaryButtonCallBack != null
                val mainButton = if (hasTwoButtons) customView.findViewById<MaterialButton>(R.id.mainMaterialButton) else customView.findViewById<MaterialButton>(R.id.secondaryMaterialButton)
                val secondaryButton =
                    if (hasTwoButtons) customView.findViewById<MaterialButton>(R.id.secondaryMaterialButton) else customView.findViewById<MaterialButton>(R.id.mainMaterialButton)

                mainButton.text = mainButtonText
                mainButton.setOnClickListener {
                    mainButtonCallBack(this@DialogDelegate)
                }

                if (hasTwoButtons) {
                    secondaryButton?.text = secondaryButtonText
                    secondaryButton?.visibility = View.VISIBLE
                    secondaryButton?.setOnClickListener {
                        secondaryButtonCallBack?.invoke(this@DialogDelegate)
                    }
                } else {
                    secondaryButton?.visibility = View.GONE
                    secondaryButton?.setOnClickListener { dismiss() }
                }

                this.cancelOnTouchOutside(dismissibleOnTouchOutside)
                this.cancelable(cancelable)
                dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    fun dismiss() {
        dialog?.dismiss()
    }

    private fun updateText(text: String?, textView: AppCompatTextView) {
        if (text != null) {
            textView.text = text
            textView.visibility = View.VISIBLE
        } else {
            textView.visibility = View.GONE
        }
    }


}