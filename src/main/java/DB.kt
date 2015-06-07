import EditDistance
import JaroWinkler
import Levenshtein
import MakeDB
import redis.clients.jedis.Jedis
import java.text.SimpleDateFormat
import java.util.*
import kotlin.text.Regex

/**
 * Created by litemn on 30.05.15.
 */


fun main(args: Array<String>) {
    val jedis = Jedis("localhost")
    val param = arrayOf("brand", "model", "engine", "bodytype", "daterelease", "mileage", "pts", "color", "price", "photoid")

    if (args.size() > 0)

        when (args[0].toLowerCase()) {
            "new", "edit" -> {

                val it: Iterator<String> = args.iterator()
                var ID = jedis.get(MakeDB.MAXID)

                var map: MutableMap<String, String> = HashMap()
                if (it.hasNext() && it.next().toLowerCase() == "id") {
                    it.next()

                    ID = it.next()

                    if (ID !is String) {
                        map = jedis.hgetAll(MakeDB.ID_PREFIX + ID)
                    }
                } else {
                    jedis.incr(MakeDB.MAXID)
                }

                while (it.hasNext()) {
                    var value: String = ""
                    val p = it.next().toLowerCase().replace("_", "")
                    when (p) {
                        "brand" -> {
                            map.put(MakeDB.BRAND, it.next())
                        }
                        "model" -> {
                            map.put(MakeDB.MODEL, it.next())
                        }
                        "engine" -> {
                            map.put(MakeDB.ENGINE, it.next())
                        }
                        "bodytype" -> {
                            map.put(MakeDB.BODY_TYPE, it.next())
                        }
                        "daterelease" -> {
                            val pattern: Regex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$".toRegex()
                            value = it.next()
                            if (value.matches(pattern)) {
                                value.replace("(\\/|-|\\.)", "-", true);
                                val format = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(value)
                                map.put(MakeDB.DATE_RELEASE, (format.getTime() / 1000).toString());
                            } else {
                                println("Wrong date release")
                            }
                        }
                        "mileage" -> {
                            val pattern: Regex = "^[0-9]+$".toRegex()
                            value = it.next()
                            if (value.matches(pattern)) {
                                map.put(MakeDB.MILEAGE, value)
                            } else {
                                println("Wrong mileage")
                            }
                        }
                        "pts" -> {
                            val pattern: Regex = "^[0-9]{2}_[а-яА-Я]{2}_[0-9]+$".toRegex()
                            value = it.next()
                            if (value.matches(pattern)) {
                                map.put(MakeDB.PTS, value.replace("_", " "))
                            } else {
                                println("Wrong pts")
                            }
                        }
                        "color" -> {
                            map.put(MakeDB.COLOR, it.next())
                        }
                        "price" -> {
                            val pattern: Regex = "^[0-9]+$".toRegex()
                            value = it.next()
                            if (value.matches(pattern)) {
                                map.put(MakeDB.PRICE, value)
                            } else {
                                println("Wrong price")
                            }
                        }
                        "photoid" -> {
                            map.put(MakeDB.PHOTO_ID, it.next())
                        }
                        else -> {
                            println("param " + p + " wrong")
                            if (it.hasNext())
                                it.next()
                        }
                    }
                }
                map.put(MakeDB.ID_CAR, ID)
                jedis.hmset(MakeDB.ID_PREFIX + ID, map)
                println("changes item with id " + ID)
            }

            "del" -> {
                if (args.size() >= 2) {
                    println("R you sure?(yes/no)")
                    val temp = readLine().toString()
                    if (temp.startsWith("y")) {
                        if (jedis.del(MakeDB.ID_PREFIX + args[1]) == 1L) {
                            println("Deleted")
                        } else {
                            println("Not ok")
                        }
                    } else println("Canceled")
                } else println("Wrong numbers of param ")
            }
            "get" -> {
                if (args.size() > 1) {
                    if (args[1].matches("[0-9]+".toRegex())) {
                        var map: MutableMap<String, String> = HashMap<String, String>()
                        map = jedis.hgetAll(MakeDB.ID_PREFIX + args[1])
                        for (item in map) {
                            if (item.getKey()=="date_release")
                            {
                                val format = SimpleDateFormat("dd MMMM yyyy").format(Date((item.getValue().toLong() * 1000).toLong()))
                                println(item.getKey() + " - " + format)
                            }
                            else {
                                println(item.getKey() + " - " + item.getValue())
                            }
                        }
                    }
                }
            }
            "search" -> {
                if (args.size() > 2) {
                    val find: String = EditDistance.translate(args[2])
                    when (args[1].toLowerCase()) {
                        "color" -> {
                            var s: MutableSet<String> = jedis.smembers(MakeDB.COLOR_SET)
                            if (find in s) {
                                var s: MutableSet<String> = jedis.smembers(find)
                                for (i in s) {
                                    var map: MutableMap<String, String> = HashMap<String, String>()
                                    map = jedis.hgetAll(MakeDB.ID_PREFIX + i)
                                    for (item in map) {
                                        if (item.getKey()=="date_release")
                                        {
                                            val format = SimpleDateFormat("dd MMMM yyyy").format(Date((item.getValue().toLong() * 1000).toLong()))
                                            println(item.getKey() + " - " + format)
                                        }
                                        else {
                                            println(item.getKey() + " - " + item.getValue())
                                        }
                                    }
                                    println("")
                                }

                            } else {
                                var s: MutableSet<String> = jedis.smembers(MakeDB.COLOR_SET)
                                var count: Int = 0
                                val jaro = JaroWinkler()
                                val lev = Levenshtein()
                                for (l in s) {
                                    if (lev.getSimilarity(l, find) < 3 || jaro.compare(l, find) > 0.9) {
                                        count += 1
                                        var s: MutableSet<String> = jedis.smembers(l)
                                        for (i in s) {
                                            var map: MutableMap<String, String> = HashMap<String, String>()
                                            map = jedis.hgetAll(MakeDB.ID_PREFIX + i)
                                            for (item in map) {
                                                if (item.getKey()=="date_release")
                                                {
                                                    val format = SimpleDateFormat("dd MMMM yyyy").format(Date((item.getValue().toLong() * 1000).toLong()))
                                                    println(item.getKey() + " - " + format)
                                                }
                                                else {
                                                    println(item.getKey() + " - " + item.getValue())
                                                }
                                            }
                                            println("")

                                        }
                                    }
                                }
                                if (count == 0) {
                                    println("No car with this color")
                                }
                            }
                        }

                    }
                }
            }

            "magic" -> {
                if (args.size() >= 2) {
                    var brand: String = args[1].toLowerCase()
                    var min: Double = 0.0
                    var max: Double = 99999999.0
                    if (args.size() >= 3) {
                        min = args[2].toDouble()
                        if (args.size() >= 4) {
                            max = args[3].toDouble()
                        }
                    }

                    var s: MutableSet<String> = jedis.smembers(MakeDB.BRAND_SET)

                    if (brand in s) {

                        var price: MutableSet<String> = jedis.zrangeByScore(MakeDB.CARSPRICE, min, max)
                        for (t in price) {

                            jedis.sadd("TEMP1", t);
                        }


                        var res: MutableSet<String> = jedis.sinter(brand, "TEMP1")

                        for (k in res) {
                            val map = jedis.hgetAll(MakeDB.ID_PREFIX + k)
                            for (item in map) {
                                if (item.getKey()=="date_release")
                                {
                                    val format = SimpleDateFormat("dd MMMM yyyy").format(Date((item.getValue().toLong() * 1000).toLong()))
                                    println(item.getKey() + " - " + format)
                                }
                                else {
                                    println(item.getKey() + " - " + item.getValue())
                                }
                            }
                            println("")
                        }
                    } else {
                        var count: Int = 0
                        for (i in s) {
                            if (EditDistance.distance(i, brand) < 4) {
                                var price: MutableSet<String> = jedis.zrangeByScore(MakeDB.CARSPRICE, min, max)
                                var b: MutableSet<String> = jedis.smembers(i)
                                price.retainAll(b)
                                for (k in price) {
                                    count += 1
                                    val map = jedis.hgetAll(MakeDB.ID_PREFIX + k)
                                    for (item in map) {
                                        if (item.getKey()=="date_release")
                                        {
                                            val format = SimpleDateFormat("dd MMMM yyyy").format(Date((item.getValue().toLong() * 1000).toLong()))
                                            println(item.getKey() + " - " + format)
                                        }
                                        else {
                                            println(item.getKey() + " - " + item.getValue())
                                        }
                                    }
                                    println("")
                                }
                            }
                        }
                        if (count == 0) {
                            print("Not Car found")
                        }
                    }
                }

            }
            else -> print("error command")
        }
}


