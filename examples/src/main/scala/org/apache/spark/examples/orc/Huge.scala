package org.apache.spark.examples.orc

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Output
import org.apache.commons.codec.binary.Base64
import org.apache.hadoop.hive.ql.io.sarg.{PredicateLeaf, SearchArgument, SearchArgumentFactory}
import org.apache.spark.sql.SparkSession

/**
  * @author zyp
  */
object Huge {
  val path = "C:\\Users\\zyp\\ali\\spark\\examples\\src\\main\\resources\\giant.txt"
  val tableName = "simple"
  val cacheTableName = "default_"+tableName

//  def toKryo(sarg: SearchArgument): String = {
//    val out = new Output(4 * 1024, 10 * 1024 * 1024)
//    new Kryo().writeObject(out, sarg)
//    out.close()
//    Base64.encodeBase64String(out.toBytes)
//  }

  def main(args: Array[String]): Unit = {
//    val number = "2"
//    val sarg = SearchArgumentFactory.newBuilder.startAnd.lessThan("frequency", number.toLong).end.build.toKryo
//    println(sarg)
    val spark = SparkSession
      .builder()
      .master("local")
      .config("spark.sql.catalogImplementation","hive")
      .config("spark.sql.json.optimize",true)
//      .config("spark.sql.orc.filterPushdown",true)
//      .config("hive.optimize.index.filter",true)
//      .config("sarg.pushdown", sarg)
      .config("hive.io.file.readcolumn.names", "time,frequency")

//      .config("spark.sql.json.writeCache",true)
//      .config("spark.sql.codegen.wholeStage", false)
      .enableHiveSupport()
      .getOrCreate()


//
//    TestUtil.createTable(spark,path,tableName)
//    TestUtil.cacheJson(spark,cacheTableName,tableName)


//    val readCacheJsonTime  = TestUtil.readCacheJson(spark,cacheTableName,5)
//    println(s"readCacheJsonTime:$readCacheJsonTime")


//    val readJsonTime = TestUtil.readJson(spark,tableName,1)
//    println(s"readJsonTime:$readJsonTime")

////
//    val readColTime = TestUtil.readCol(spark,tableName,2)
spark.sql("select time,get_json_object(path,'$.id') as path_id,get_json_object(path,'$.url') as path_url,frequency from huge").explain()
//      .foreachPartition(iter => println(iter.size))

//    println(s"readColTime:$readColTime")

  }
}

