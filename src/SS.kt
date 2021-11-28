import java.io.IOException
import java.net.ServerSocket

class SS : Thread() {

    override fun run() {
        try {
            val server = ServerSocket(Utils.port)
            while (true) {
                AppEcho(server.accept())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            //SS().start()
            val chain = BlockChain()
            chain.addBlock(System.currentTimeMillis(), "Jopa")
            chain.addBlock(System.currentTimeMillis(), "Ford")
            chain.addBlock(System.currentTimeMillis(), "Skoda")
            chain.addBlock(System.currentTimeMillis(), "Jopa")
            chain.verifyChain()
        }
    }
}