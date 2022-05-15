package com.psm.horrorg.DatePicker

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.psm.horrorg.R
import java.util.*

//Parametros, se pasan al ejectar
class DatePickerFragment( val listener:(day:Int, month:Int, year:Int)->Unit):DialogFragment() , DatePickerDialog.OnDateSetListener{

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth,month,year) //ejecuta el codigo en el showDatePickerDialog que esta en el RegisterActivity
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog { //
        val c:Calendar = Calendar.getInstance()
        //pedimos los datos
        val day:Int =c.get(Calendar.DAY_OF_MONTH)
        val month:Int =c.get(Calendar.MONTH)
        val year:Int =c.get(Calendar.YEAR)

        //hacemos la instancia
        val picker = DatePickerDialog(activity as Context, R.style.datePickerTheme,this ,year, month, day)
        picker.datePicker.maxDate=c.timeInMillis
        return picker

    }
}