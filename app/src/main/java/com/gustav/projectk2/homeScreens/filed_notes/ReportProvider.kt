package com.gustav.projectk2.homeScreens.filed_notes

import android.util.Log
import com.gustav.projectk2.database.Note
import com.gustav.projectk2.database.NoteEvent
import java.text.SimpleDateFormat
import java.util.*

class ReportProvider {

    companion object{

        var TAG = "GustavsMessage"

        public fun getNoteStrings(note: Note, events: List<NoteEvent>) : MutableList<String?> {
           var report = mutableListOf<String?>()
            report.add(getHtmlString(note, events))
            report.add(getSimpleString(note, events))
           return report
       }

        private fun getSimpleString(note: Note, events: List<NoteEvent>): String {
            var titleAndInfo = note.noteName.toString().toUpperCase()+" <br>"+
                    "${getFirstCellWithSpacing("Opened")}${SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(note.startTimeMilli))}<br>"+
                    "${getFirstCellWithSpacing("Last edited")} ${SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(note.latestEditTimeMilli))}<br>"+
                    "${getFirstCellWithSpacing("Filed")} ${SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(note.endTimeMilli))}<br><br>"

            var eventsTable = ""
                events.forEach {event ->
                    eventsTable += event.eventName.toString().toUpperCase()+"<br>"
                    if(event.isNote && !event.note.isEmpty()) eventsTable += event.note+"<br>"
                    if(event.isAmount&& !event.amount.toString().isEmpty()) eventsTable += getFirstCellWithSpacing("Amount") + event.amount.toString() + " " + event.unit+"<br>"
                    if(event.isPosition&& !event.position.isEmpty()) eventsTable += getFirstCellWithSpacing("Position")+ event.position+"<br>"
                    if(event.startStop) eventsTable +=  getFirstCellWithSpacing("Start") + SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(event.startTimeMilli))+ "<br>"
                    eventsTable += getFirstCellWithSpacing("Done") + SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(event.endTimeMilli))+ "<br><br>"
                }

            return titleAndInfo+eventsTable
        }

        private fun getHtmlString(note: Note, events: List<NoteEvent>): String {
            var eventsTable = ""
            var title = getHtmlTitleRow(note.noteName) +"<br>"
            var infoTable = includeTableStartAndEnd( getHtmlTableRow("Opened", SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(note.startTimeMilli))) +
                                getHtmlTableRow("Last Edited", SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(note.latestEditTimeMilli))) +
                                getHtmlTableRow("Completed", SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(note.endTimeMilli)))) +"<br>"
            events.forEach(){event ->
                 eventsTable += getHtmlTableRow("<b>${event.eventName}</b>", "")
                if(event.isNote && !event.note.isEmpty()) eventsTable += getHtmlTableRow("Note", event.note)
                if(event.isAmount&& !event.amount.toString().isEmpty()) eventsTable += getHtmlTableRow("Amount", event.amount.toString()+ " "+event.unit)
                if(event.isPosition&& !event.position.isEmpty()) eventsTable += getHtmlTableRow("Position", event.position)
                if(event.startStop) eventsTable += getHtmlTableRow("Start", SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(event.startTimeMilli)))
                eventsTable += getHtmlTableRow("Done", SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(event.endTimeMilli)))
                eventsTable += getHtmlTableRow("", "")

            }
            eventsTable = includeTableStartAndEnd(eventsTable)

            return "$title$infoTable$eventsTable</body></html>"
        }

        private fun getHtmlTitleRow(s: String): String {
            return "<html><body><b>$s</b>"
        }

        private fun getHtmlTableRow(firstCell: String, secondCell: String): String {
            return "<tr  ><td style=width:50%; >$firstCell</td><td style=width:50%; >$secondCell</td></tr>"
        }

        private fun includeTableStartAndEnd(htmlString: String):String {
            return "<table style=width:100%;>$htmlString</table>"
        }

        private fun getFirstCellWithSpacing(firstCell: String): String {
            var resultString = firstCell
            val totalLength = 20
            val whiteSpaceLength = totalLength - firstCell.length

            for(i in 0 .. whiteSpaceLength){
                resultString += "&#160;"
            }

            return resultString
        }
    }
}