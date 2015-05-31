import redis.clients.jedis.Jedis
import java.util.HashMap

/**
 * Created by litemn on 30.05.15.
 */

fun main(args: Array<String>) {
    val jedis = Jedis("localhost")
    if (args.size() > 0) {
        when (args[0]) {
            "new" -> {
                if (args.size() != 21)
                    error("Wrond number of param")
                val it: Iterator<String> = args.iterator()
                it.next()
                val map = HashMap<String, String>()
                while (it.hasNext()) {
                    when (it.next()) {
                        "id_model" -> {
                            map.put("id_model", it.next())
                        }
                        "id_engine" -> {
                            map.put("id_engine", it.next())
                        }
                        "id_body_type" -> {
                            map.put("id_body_type", it.next())
                        }
                        "date_release" -> {
                            map.put("date_release", it.next())
                        }
                        "mileage" -> {
                            map.put("mileage", it.next())
                        }
                        "pts" -> {
                            map.put("pts", it.next())
                        }
                        "color" -> {
                            map.put("color", it.next())
                        }
                        "price" -> {
                            map.put("price", it.next())
                        }
                        "photo_id" -> {
                            map.put("photo_id", it.next())
                        }
                        "final_price" -> {
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
                println("R you sure?(yes/no)")
                when(readLine()){
                    "yes" ->{

                       if(jedis.del("car:id:"+args[1])==1L){
                           println("Deleted")
                       }else{
                           println("Try again")
                       }
                    }
                    else -> println("Canceled")
                }

            }

            else -> print("error")
        }
    }

}