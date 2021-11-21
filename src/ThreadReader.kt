import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.Socket
import java.util.*

class ThreadReader(var socket: Socket) : Observable(), Runnable {
    lateinit var istrm: BufferedReader
    var str = ""

    override fun notifyObservers() {
        setChanged()
        notifyObservers(this)
    }

    override fun run() {
        try {
            istrm = BufferedReader(InputStreamReader(socket.getInputStream()))
            while (true) {
                str = istrm.readLine() // ждем сообщения с сервера
                notifyObservers()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}