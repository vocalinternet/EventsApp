package com.eventsapp

import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.eventsapp.databinding.DescriptionFragmentBinding
import com.eventsapp.models.EventID
import com.eventsapp.retrofit.ApiService.Companion.api
import com.eventsapp.retrofit.RetrofitClient
import com.eventsapp.retrofit.RetrofitServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.*
import retrofit2.Response

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

    fun putInfoIntoViews(inform: EventID?): Void? {
        with(binding) {
            eventName.text = inform?.name
            var str: String = inform?.starts_at?.substring(8, 10) ?: "*"
            str = str + "." + inform?.starts_at?.substring(5, 7) ?: "*"
            str = str + "." + inform?.starts_at?.substring(0, 5) ?: "*"
            if (str[0] != '*') {
                dateAndTime.text = str
            }else {
                dateAndTime.text = "давно началось"
            }
            dateAndTime1.text = dateAndTime.text
//            println("HERE!!!")
//            println(inform?.description_short)
//            println(inform?.description_html)
//            println(inform?.starts_at)
//            println(inform?.ends_at)

            if (inform?.description_html != "") {
                eventDescription.text = inform?.description_html
            } else {
                eventDescription.text = "Из названия и так все понятно"
            }
            eventDescription1.text = eventDescription.text
        }
        return null
    }

    // Я выбрал этот метод ЖЦ, и считаю, что это удачное место
    // Вы можете попробовать производить эти действия не в этом методе ЖЦ, а например в onCreateDialog()
    override fun onStart() {
        super.onStart()

        // Плотность понадобится нам в дальнейшем
        val density = requireContext().resources.displayMetrics.density


        var curEvent = 250748
        val timepadTAG = "Timepad API"

        CoroutineScope(Dispatchers.IO).launch {
            val result = RetrofitClient.timepadApi.getEventID(curEvent)
            if (result.isSuccessful) {
                val inform = result.body()

                withContext(Dispatchers.Main) {
                    putInfoIntoViews(inform)
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