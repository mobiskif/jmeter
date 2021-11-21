import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Utils{

    companion object{

        @JvmStatic
        var df: DateFormat = SimpleDateFormat("dd.MM.YYYY")
        @JvmStatic
        var tf: DateFormat = SimpleDateFormat("HH:mm:ss")
        @JvmStatic
        public var port = 9510
        @JvmStatic
        var host = "92.100.199.33"

        @JvmStatic
        fun printThreadInfo(o: Any) {
            val threadCurrent = Thread.currentThread()
            threadCurrent.name = o.javaClass.name
            val threadGroup = threadCurrent.threadGroup
            val threadParent = threadGroup.parent
            val titleString = threadParent.name + ":" + threadGroup.name + ":" + threadCurrent.name
            println(titleString)
        }

        @JvmStatic
        fun time(): String {
            return tf.format(Date().time)
        }

        @JvmStatic
        fun date(): String {
            return df.format(Date().time)
        }

    }

}