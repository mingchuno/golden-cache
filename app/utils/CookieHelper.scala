package utils

import java.util.UUID

import play.api.mvc.Cookie

trait CookieHelper {
  protected val UUID_KEY = "HKG_CACHE_UUID"

  def genCookie(uuid: String = UUID.randomUUID().toString) = Cookie(name = UUID_KEY, value = uuid, maxAge = Some(2592000))
}
