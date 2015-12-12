package service

import models.hkg.HistoryItem
import com.redis.RedisClient
import dao.RedisDAO
import org.json4s._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}

trait UserHistoryService {
  private implicit val formats = Serialization.formats(NoTypeHints)

  protected val UUID_KEY = "uuid"

  // move to config later
  private val HISTORY_SIZE = 10

  // move to config later
  // unit in second (2 days)
  private val HISTOR_TTL = 2 * 24 * 60 * 60

  private def getKey(uuid: String) = s"golden-cache:$uuid"

  def getHistory(uuid: String): List[HistoryItem] = {
    val key = getKey(uuid)
    RedisDAO.redisPool.withClient {
      redis => {
        if (redis.expire(key, HISTOR_TTL)) {
          // reverse so that is sorted from new to old
          getItems(redis, key).reverse
        } else {
          List[HistoryItem]()
        }
      }
    }
  }

  private def getItems(redis: RedisClient, key: String): List[HistoryItem] = {
    redis.lrange(key, 0, HISTORY_SIZE - 1) match {
      case None => List[HistoryItem]()
      case Some(xsOpt) =>
        xsOpt.flatten.map(json => read[HistoryItem](json))
    }
  }

  def saveHistory(uuid: String, item: HistoryItem): Boolean = {
    val key = getKey(uuid)
    RedisDAO.redisPool.withClient {
      redis => {
        getItems(redis, key).find(_ == item) match {
          case Some(_) =>
          case None =>
            redis.lpush(key, write(item))
            redis.ltrim(key, 0, HISTORY_SIZE - 1)
        }
        redis.expire(key, HISTOR_TTL)
      }
    }
  }
}
