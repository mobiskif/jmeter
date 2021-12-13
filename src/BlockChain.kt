import java.security.MessageDigest
import java.util.*

class BlockChain {
    private val chain: MutableList<Block> = mutableListOf()
    private var complexity = 2 //динамически изменяется под minigtime
    private val maxminingtime = 400 //задает сложность майнинга

    private fun getHash(timestamp: Long, data: String, hashstr: String, nonce: Int): String {
        val datastr = data + timestamp + hashstr + nonce
        val hashbytes = MessageDigest.getInstance("SHA-256").digest(datastr.toByteArray())
        return Base64.getEncoder().encodeToString(hashbytes)
    }

    fun addBlock(data: String) {
        val timestamp = System.currentTimeMillis()
        var nonce = 0
        while (true) {
            val hash = getHash(timestamp, data, chain[chain.size - 1].hash, nonce) //берем предыдущий хэш (защита от подделок)
            if (hash.startsWith("0".repeat(complexity))) {
                chain.add(Block(timestamp, data, hash, nonce))
                break
            } else nonce++ //повторять, пока не выпадет нужное кол-во нулей (сложность)
        }
        val miningtime = System.currentTimeMillis() - timestamp
        println("$miningtime ms, $complexity")
        if (miningtime < maxminingtime - 200) complexity++
        else if (miningtime > maxminingtime + 100) complexity-- //complexity подстраивается под заданную сложность
    }

    fun verify() {
        for (i in 1 until chain.size) {
            var check = "Error!!"
            val verHash = getHash(chain[i].timestamp, chain[i].data, chain[i - 1].hash, chain[i].nonce)
            if (chain[i].hash == verHash) check = "Pass"
            println("${chain[i].data}\t ${chain[i].hash}\t ${chain[i].nonce}\t $check")
        }
    }

    init {
        chain.add(Block(System.currentTimeMillis(), "Root", "", complexity))
    }
}