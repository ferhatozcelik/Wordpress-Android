package org.ferhatozcelik.util

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author ferhatozcelik
 * @since 2023-04-02
 */

class DateUtil{

    companion object{
        fun changeDateFormat(strDate: String?): String {
            if(strDate.isNullOrEmpty()){
               return ""
            }
            return try{
                val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val requiredSdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                requiredSdf.format(sourceSdf.parse(strDate))
            }catch (ex: Exception){
                ""
            }
        }

        fun convertDateFormat(strDate: String?): String {
            if(strDate.isNullOrEmpty()){
                return ""
            }
            return try{
                val sourceSdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                val requiredSdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                requiredSdf.format(sourceSdf.parse(strDate))
            }catch (ex: Exception){
                ""
            }
        }

        fun convertTimeFormat(strDate: String?): String {
            if(strDate.isNullOrEmpty()){
                return ""
            }
            return try{
                val sourceSdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                val requiredSdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                requiredSdf.format(sourceSdf.parse(strDate))
            }catch (ex: Exception){
                ""
            }
        }
    }
}

