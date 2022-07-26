package com.eventsapp

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.eventsapp.databinding.DescriptionFragmentBinding
import com.eventsapp.models.AllEvents
import com.eventsapp.models.EventID
import com.eventsapp.retrofit.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL
import java.net.URLEncoder


private const val COLLAPSED_HEIGHT = 228

class BottomFragment : BottomSheetDialogFragment() {

    // Можно обойтись без биндинга и использовать findViewById
    lateinit var binding: DescriptionFragmentBinding

    // Переопределим тему, чтобы использовать нашу с закруглёнными углами
    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DescriptionFragmentBinding.bind(inflater.inflate(R.layout.description_fragment, container))
        return binding.root
    }

    fun htmlTextFormat(str: String) : String {
        var flag: Boolean = false
        var ans: String = ""
        for (i in 0..(str.length - 1)) {
            if (str[i] == '<') flag = true
            if (!flag) ans += str[i]
            if (str[i] == '>') flag = false
        }
        return ans
    }

    fun putInfoIntoViews(inform: EventID?): Void? {
        with(binding) {
            eventName.text = inform?.name
            var st: String = inform?.starts_at?.substring(8, 10) ?: "*"
            st += "." + inform?.starts_at?.substring(5, 7) ?: "*"
            st += "." + inform?.starts_at?.substring(0, 4) ?: "*"
            if (st[0] != '*') {
                st += " " + inform?.starts_at?.substring(11..15)
            }else {
                st = "давно началось"
            }
            var fin: String = inform?.ends_at?.substring(8, 10) ?: "*"
            fin += "." + inform?.ends_at?.substring(5, 7) ?: "*"
            fin += "." + inform?.ends_at?.substring(0, 4) ?: "*"
            if (fin[0] != '*') {
                fin += " " + inform?.ends_at?.substring(11..15)
            }else {
                fin = "ещё не кончилось"
            }
            dateAndTime.text = st + " - " + fin
            dateAndTime1.text = dateAndTime.text
            if (inform?.description_html != "" && inform?.description_html != null) {
                eventDescription.text = htmlTextFormat(inform?.description_html)
            } else {
                eventDescription.text = "Из названия и так все понятно"
            }
            eventDescription1.text = eventDescription.text
        }
        return null
    }

//    fun getUrlWithId(id: Int) : String? {
//
//    }



    fun putPhotoIntoView(inform: EventID?): Void? {
        println(inform?.poster_image?.default_url)
        with(binding) {
            Glide.with(this@BottomFragment)
                .load(inform?.poster_image!!.default_url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(image)
        }
        return null
    }


    // Я выбрал этот метод ЖЦ, и считаю, что это удачное место
    // Вы можете попробовать производить эти действия не в этом методе ЖЦ, а например в onCreateDialog()
    override fun onStart() {
        super.onStart()

        val density = requireContext().resources.displayMetrics.density

        var curEvent = 2007809
        val timepadTAG = "Timepad API"

        CoroutineScope(Dispatchers.IO).launch {
            val result = RetrofitClient.timepadApi.getEventID(curEvent)
            if (result.isSuccessful) {
                val inform = result.body()
                withContext(Dispatchers.Main) {
                    putInfoIntoViews(inform)
                    //putPhotoIntoView(inform)
                }
            } else {
                Log.d(timepadTAG, "Не удачный запрос")
                Log.d(timepadTAG, result.errorBody().toString())
                println(result.message())
                println("${result.code()}")
            }
        }




        dialog?.let {
            // Находим сам bottomSheet и достаём из него Behaviour
            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            // Выставляем высоту для состояния collapsed и выставляем состояние collapsed
            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED


            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    // Нам не нужны действия по этому колбеку
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    with(binding) {
                        // Нас интересует только положительный оффсет, тк при отрицательном нас устроит стандартное поведение - скрытие фрагмента
                        if (slideOffset > 0) {
                            // Делаем "свёрнутый" layout более прозрачным
                            layoutCollapsed.alpha = 1 - 2 * slideOffset
                            // И в то же время делаем "расширенный layout" менее прозрачным
                            layoutExpanded.alpha = slideOffset * slideOffset




                            // Когда оффсет превышает половину, мы скрываем collapsed layout и делаем видимым expanded
                            if (slideOffset > 0.5) {
                                layoutCollapsed.visibility = View.GONE
                                layoutExpanded.visibility = View.VISIBLE
                            }

                            // Если же оффсет меньше половины, а expanded layout всё ещё виден, то нужно скрывать его и показывать collapsed
                            if (slideOffset < 0.5 && binding.layoutExpanded.visibility == View.VISIBLE) {
                                layoutCollapsed.visibility = View.VISIBLE
                                layoutExpanded.visibility = View.INVISIBLE
                            }


                        }
                    }
                }
            })
        }
    }
}