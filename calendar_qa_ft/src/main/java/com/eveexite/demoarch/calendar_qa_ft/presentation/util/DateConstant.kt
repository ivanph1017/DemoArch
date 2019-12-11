package com.eveexite.demoarch.calendar_qa_ft.presentation.util

object DateConstant {

    val locale: String = "es_PE"
    val dateFormat: String = "dd/MM/yyyy"
    val timeFormat: String = "hh:mm aa"
    val dateTimeTemplate: String = "1$%s 2$%s"
    val dateTimeFormat: String = String.format(
        dateTimeTemplate,
        dateFormat,
        timeFormat
    )

}