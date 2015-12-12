package dao

import com.redis.RedisClientPool
import com.typesafe.config.ConfigFactory

object RedisDAO {
  private val config = ConfigFactory.load
  config.checkValid(ConfigFactory.defaultReference)

  // init some Redis
  private val redisHost = config.getString("golden.redis.host")
  private val redisPort = config.getInt("golden.redis.port")
  val redisPool: RedisClientPool = new RedisClientPool(redisHost, redisPort)
}