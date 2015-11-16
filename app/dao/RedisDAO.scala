package dao

import com.redis.RedisClientPool

object RedisDAO {
  val clients = new RedisClientPool("localhost", 6379)
}