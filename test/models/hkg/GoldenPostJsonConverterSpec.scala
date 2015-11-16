package models.hkg

import base.HKGCacheSpecBase
import org.joda.time.DateTime
import play.api.libs.json.Json

class GoldenPostJsonConverterSpec extends HKGCacheSpecBase with GoldenPostJsonConverter {

  "an valid reply" should "parse" in {
    val jsonStr = """{"Reply_ID":229018821,"Author_Name":"知錯難改","Author_Gender":"M","Author_ID":361152,"Message_Date":"/Date(1444479910797)/","Message_Body":"<img src=\"/faces/frown.gif\" alt=\":-(\" border=\"0\" />","isBlock":"N"}"""
    val json = Json.parse(jsonStr)
    val expected = Reply(229018821,"知錯難改","M",361152,Some(new DateTime(1444479910797L)),"""<img src="/faces/frown.gif" alt=":-(" border="0" />""", isBlock = false)
    info(expected.toString)
    json.validate[Reply].get shouldBe expected
  }

  "an valid post" should "parse" in {
    val jsonStr = """{"success":true,"error_msg":"","Message_ID":6081591,"Message_Title":"人越大 就越難得到個種簡單平淡既開心","Message_Status":"A","Message_Date":"\/Date(1444479657000)\/","Last_Reply_Date":"\/Date(1444498814277)\/","Total_Replies":22,"Rating_Good":6,"Rating_Bad":0,"Rating":6,"Total_Pages":1,"Current_Pages":2,"messages":[]}"""
    val json = Json.parse(jsonStr)
    val expected = Post(6081591,"人越大 就越難得到個種簡單平淡既開心",Some(new DateTime(1444479657000L)),Some(new DateTime(1444498814277L)),22,6,0,6,1,2,List())
    info(expected.toString)
    json.validate[Post].get shouldBe expected
  }

  "mixing Post & Reply" should "also parse" in {
    val jsonStr = """{"success":true,"error_msg":"","Message_ID":6081591,"Message_Title":"人越大 就越難得到個種簡單平淡既開心","Message_Status":"A","Message_Date":"\/Date(1444479657000)\/","Last_Reply_Date":"\/Date(1444498814277)\/","Total_Replies":22,"Rating_Good":6,"Rating_Bad":0,"Rating":6,"Total_Pages":1,"Current_Pages":2,"messages":[{"Reply_ID":229018821,"Author_Name":"知錯難改","Author_Gender":"M","Author_ID":361152,"Message_Date":"/Date(1444479910797)/","Message_Body":"<img src=\"/faces/frown.gif\" alt=\":-(\" border=\"0\" />","isBlock":"N"}]}"""
    val json = Json.parse(jsonStr)
    val expected = Post(6081591,"人越大 就越難得到個種簡單平淡既開心",Some(new DateTime(1444479657000L)),Some(new DateTime(1444498814277L)),22,6,0,6,1,2,List(Reply(229018821,"知錯難改","M",361152,Some(new DateTime(1444479910797L)),"""<img src="/faces/frown.gif" alt=":-(" border="0" />""", isBlock = false)))
    info(expected.toString)
    json.validate[Post].get shouldBe expected
  }

  "parse a topic list" should "return correctly" in {
    val jsonStr = """{"success":true,"error_msg":"","topic_list":[{"Message_ID":6081760,"Message_Title":"又到秋天 一人n首係秋天聽既歌","Author_ID":511000,"Author_Name":"你根本冇女友","Last_Reply_Date":"\/Date(1444580919410)\/","Total_Replies":52,"Message_Status":"A","Message_Body":"","Rating":1},{"Message_ID":6082916,"Message_Title":"[一齊鳩up] 單身左幾耐? (15)","Author_ID":396943,"Author_Name":"白寶","Last_Reply_Date":"\/Date(1444580917760)\/","Total_Replies":697,"Message_Status":"A","Message_Body":"","Rating":1},{"Message_ID":6082545,"Message_Title":"熱烈地彈琴熱烈地唱(唱歌whatsapp group)","Author_ID":500939,"Author_Name":"陳小姐貴姓","Last_Reply_Date":"\/Date(1444580916467)\/","Total_Replies":8,"Message_Status":"A","Message_Body":"","Rating":1},{"Message_ID":6083178,"Message_Title":"《十月》我後悔萬聖節去左哈囉喂 (2)","Author_ID":517538,"Author_Name":"筆言筆語","Last_Reply_Date":"\/Date(1444580915583)\/","Total_Replies":40,"Message_Status":"A","Message_Body":"","Rating":15},{"Message_ID":6083321,"Message_Title":"扑野要考牌 , 大家覺得點?","Author_ID":313451,"Author_Name":"挽歌","Last_Reply_Date":"\/Date(1444580911420)\/","Total_Replies":0,"Message_Status":"A","Message_Body":"","Rating":0},{"Message_ID":6083315,"Message_Title":"聽日要番工 依家冇野做但又唔想訓","Author_ID":232956,"Author_Name":"夢見你夢遺","Last_Reply_Date":"\/Date(1444580910513)\/","Total_Replies":6,"Message_Status":"A","Message_Body":"","Rating":0},{"Message_ID":6083319,"Message_Title":"[屯門撚]覺唔覺屯門公路唔塞車個時d景色都幾撚靚","Author_ID":510243,"Author_Name":"新生活_","Last_Reply_Date":"\/Date(1444580910130)\/","Total_Replies":1,"Message_Status":"A","Message_Body":"","Rating":0},{"Message_ID":6083288,"Message_Title":"如果迪基亞無偷冬甩，佢而家幾多歲？","Author_ID":319934,"Author_Name":"挽歌之聲","Last_Reply_Date":"\/Date(1444580908557)\/","Total_Replies":12,"Message_Status":"A","Message_Body":"","Rating":0},{"Message_ID":6082497,"Message_Title":"有冇巴絲打會開Levi\u0027s團購？","Author_ID":449398,"Author_Name":"BakarySako","Last_Reply_Date":"\/Date(1444580906043)\/","Total_Replies":5,"Message_Status":"A","Message_Body":"","Rating":0},{"Message_ID":6083197,"Message_Title":"好似聽日咁既氣溫正常要著咩衫","Author_ID":460016,"Author_Name":"玉海尋","Last_Reply_Date":"\/Date(1444580902463)\/","Total_Replies":22,"Message_Status":"A","Message_Body":"","Rating":0},{"Message_ID":6083104,"Message_Title":"林豬豪最新髮型 驚震球壇","Author_ID":511349,"Author_Name":"蓋世寶丟老母","Last_Reply_Date":"\/Date(1444580899130)\/","Total_Replies":42,"Message_Status":"A","Message_Body":"","Rating":2},{"Message_ID":6082728,"Message_Title":"[認真]想送對鞋比人但唔知佢著咩碼","Author_ID":459639,"Author_Name":"內卜","Last_Reply_Date":"\/Date(1444580898883)\/","Total_Replies":58,"Message_Status":"A","Message_Body":"","Rating":-1},{"Message_ID":6076993,"Message_Title":"小圈子利高普討論#5","Author_ID":506871,"Author_Name":"他首","Last_Reply_Date":"\/Date(1444580897787)\/","Total_Replies":639,"Message_Status":"A","Message_Body":"","Rating":-4},{"Message_ID":6082079,"Message_Title":"[10月新番]Gundam新作 高達鐵血的孤兒 第2話","Author_ID":495715,"Author_Name":"惡魔曉美焰","Last_Reply_Date":"\/Date(1444580896557)\/","Total_Replies":212,"Message_Status":"A","Message_Body":"","Rating":3},{"Message_ID":6077041,"Message_Title":"[鋼琴〕有有葵涌荃灣巴絲教琴？","Author_ID":516354,"Author_Name":"stainessss","Last_Reply_Date":"\/Date(1444580888060)\/","Total_Replies":2,"Message_Status":"A","Message_Body":"","Rating":0},{"Message_ID":6081077,"Message_Title":"[Gay]自由討論區(112)X奶哥哥d蕭功真係唔多得","Author_ID":448438,"Author_Name":"小腸","Last_Reply_Date":"\/Date(1444580887060)\/","Total_Replies":738,"Message_Status":"A","Message_Body":"","Rating":-3},{"Message_ID":6082552,"Message_Title":"[DC影視綜合] We\u0027re Jason Todd(2)","Author_ID":289882,"Author_Name":"ArkhamKnight","Last_Reply_Date":"\/Date(1444580886267)\/","Total_Replies":46,"Message_Status":"A","Message_Body":"","Rating":7},{"Message_ID":6083255,"Message_Title":"【支爆】深圳名工廠福昌電子倒閉 四千人失業","Author_ID":243939,"Author_Name":"Scouser","Last_Reply_Date":"\/Date(1444580884413)\/","Total_Replies":13,"Message_Status":"A","Message_Body":"","Rating":1}]}"""
    val json = Json.parse(jsonStr)
    val result = json.validate[Topics].get
    result.topicList should have size 18
    info(result.toString)
  }
}