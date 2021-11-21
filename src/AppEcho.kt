import java.net.Socket
import java.util.*

class AppEcho(socket: Socket) : Observer {
    private val writer: ThreadWriter

    override fun update(o: Observable, arg: Any) {
        if (o.javaClass == ThreadReader::class.java) {
            val rdr = o as ThreadReader
            writer.write("> ${rdr.str} \n")
        } else if (o.javaClass == ThreadWriter::class.java) {
            println("Send bytes " + (o as ThreadWriter).strlen)
        }
    }

    init {
        println(javaClass.simpleName + " started with " + socket.inetAddress.hostName + " (${socket.remoteSocketAddress}) at " + Utils.time())

        val reader = ThreadReader(socket)
        reader.addObserver(this)
        Thread(reader).start()

        writer = ThreadWriter(socket)
        writer.addObserver(this)
    }
}