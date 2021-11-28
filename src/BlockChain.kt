import java.security.MessageDigest
import java.util.*

class BlockChain {
    private val blockChain: MutableList<Block> = mutableListOf()

    fun getHash(ts:Long, dat:String, prvHash:String, nonce:Int): String {
        var md = MessageDigest.getInstance("SHA-256")
        var istr = ts.toString()+dat+prvHash+nonce
        var input = istr.toByteArray()
        var bytes = md.digest(input)
        var str = Base64.getEncoder().encodeToString(bytes)
        return str
    }

    fun addBlock(ts: Long, dat: String) {
        val t1 = System.currentTimeMillis()
        var nonce = 0
        val prvHash = blockChain.get(blockChain.size-1).hash
        while (true) {
            val newHash = getHash(ts, dat, prvHash, nonce)
            if (newHash.startsWith("0".repeat(3))) {
                //println("Ношол!!!: $nonce, $newHash")
                blockChain.add(Block(ts, dat, newHash, nonce))
                break
            }
            else nonce++
        }
        val t2 = System.currentTimeMillis()
        println((t2-t1).toString() +" ms")
    }

    fun verifyChain() {
        for (i in 1 until blockChain.size) {
            var check = "Error"
            val verHash = getHash(blockChain.get(i).timestamp, blockChain.get(i).data, blockChain.get(i - 1).hash, blockChain.get(i).nonce)
            if (blockChain.get(i).hash.equals(verHash)) check = "Pass"
            println("${blockChain.get(i).data}\t ${blockChain.get(i).hash}\t ${blockChain.get(i).nonce}\t $check")
        }
    }

    init {
        val tm = System.currentTimeMillis()
        val hsh = getHash(tm, "Root", "",0)
        blockChain.add(Block(tm, "Root", hsh, 0))
    }
}