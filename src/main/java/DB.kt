import com.sun.xml.internal.bind.v2.TODO
import com.sun.xml.internal.fastinfoset.util.StringArray
import org.apache.commons.validator.UrlValidator
import redis.clients.jedis.Jedis
import java.net.URL
import java.util.HashMap
import kotlin.text.Regex

/**
 * Created by litemn on 30.05.15.
 */

fun main(args: Array<String>) {
    val jedis = Jedis("localhost")
    val param = arrayOf("model","engine","bodyType","date_release","mileage", "pts","color", "price", "photo_id", "final_price")


    if (args.size() > 0)

        when (args[0]) {
            "new" -> {
                if (args.size() != 21)
                    error("Wrond number of param")
                val it: Iterator<String> = args.iterator()
                it.next()
                val map = HashMap<String, String>()
                while (it.hasNext()) {
                    var value : String = ""
                    when (it.next().toLowerCase().replace("_","")) {
                        "model" -> {
                            map.put("model", it.next())
                        }
                        "engine" -> {
                            map.put("engine", it.next())
                        }
                        "bodytype" -> {
                            map.put("bodyType", it.next())
                        }
                        "daterelease" -> {
                            map.put("date_release", it.next())
                        }
                        "mileage" -> {
                            map.put("mileage", it.next())
                        }
                        "pts" -> {
                            val pattern : Regex = "^[0-9]{2}[а-яА-Я]{2}[0-9]+$".toRegex()
                            value = it.next()
                            if(value.matches(pattern)) {
                                map.put("pts", value)
                            }else{
                                println("Wrong pts")
                            }
                        }
                        "color"  -> {
                            map.put("color", it.next())
                        }
                        "price" -> {
                            map.put("price", it.next())
                        }
                        "photoid" -> {

                            map.put("photo_id", it.next())
                        }
                        "finalprice" -> {
                            map.put("final_price", it.next())
                        }
                        else -> {
                            println("error")
                            error("Wrond Param")
                        }


                    }


                }


                jedis.incr("MAXID")
                val ID = jedis.get("MAXID")
                map.put("id_car",ID)
                jedis.hmset("car:id:" +ID, map)
                println("new id is "+ ID)

            }

            "del"->{

                if(args.size()==2) {
                    println("R you sure?(yes/no)")
                    val temp = readLine().toString()
                    if (temp.startsWith("y")) {
                        if (jedis.del("car:id:" + args[1]) == 1L) {
                            println("Deleted")
                        } else {
                            println("Not ok")
                        }

                    } else  println("Canceled")

                } else println("Wrong numbers of param ")

            }

            "edit" -> {
                if(args.size()>=4) {
                    val it : Iterator<String> = args.copyOfRange(2,args.size()).iterator()
                    while(it.hasNext()) {
                        val temp = it.next()
                        if(temp in param){
                          //  TODO edit params
                        } else {
                            error("Wrong")
                        }
                    }

                }

            }

            else -> print("error")
        }
    }

