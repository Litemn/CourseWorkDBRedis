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
    val param = arrayOf("model","engine","bodytype","daterelease","mileage", "pts","color", "price", "photoid", "finalprice")


    if (args.size() > 0)

        when (args[0].toLowerCase()) {
            "new","edit" -> {

                val it: Iterator<String> = args.iterator()
                var ID = jedis.get("MAXID")
                it.next()
                var map:MutableMap<String,String> = HashMap<String, String>()
                if(it.next().toLowerCase() == "id"){
                   ID = it.next()

                    if(ID !is String) {
                      map =  jedis.hgetAll("car:id:" + ID)
                    }
                }else {
                    jedis.incr("MAXID")
                }


                while (it.hasNext()) {
                    var value : String = ""
                    val p = it.next().toLowerCase().replace("_","")
                    when (p) {
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
                            val pattern: Regex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$".toRegex()
                            value = it.next()
                            if (value.matches(pattern)) {
                                map.put("daterelease", it.next())
                            } else {
                                println("Wrong date release")
                            }
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
                            println("param "+p+" wrong")
                            it.next()
                        }


                    }


                }

                map.put("id_car",ID)
                jedis.hmset("car:id:" +ID, map)
                println("changes blabla id is "+ ID)

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

            "x" -> {
                if(args.size()>=4) {
                    val map = HashMap<String, String>()
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

