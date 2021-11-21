import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.Socket
import java.util.*

class ThreadWriter(s: Socket) : Observable() {
    lateinit var out: BufferedWriter
    var strlen = 0

    override fun notifyObservers() {
        setChanged()
        notifyObservers(this)
    }

    fun write(str: String) {
        Thread {
            try {
                //BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.write(str)
                out.flush()
                strlen = str.length
                notifyObservers()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    init {
        try {
            out = BufferedWriter(OutputStreamWriter(s.getOutputStream()))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}