package models.hkg

import play.api.libs.json.Json

trait HistoryItemJsonConverter {
  implicit val historyItemReads = Json.reads[HistoryItem]
  implicit val historyItemWrites = Json.writes[HistoryItem]
  implicit val historyItemFormat = Json.format[HistoryItem]
}