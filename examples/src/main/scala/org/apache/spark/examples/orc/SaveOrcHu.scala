package org.apache.spark.examples.orc

import org.apache.spark.sql.{SaveMode, SparkSession}

object SaveORCHu {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local")
      .config("spark.sql.catalogImplementation","hive")
      .config("spark.sql.json.optimize",true)
      .config("spark.network.timeout", 3600)
      .config("spark.sql.codegen.wholeStage",false)
      .enableHiveSupport()
      .getOrCreate()
//        import spark.implicits._
//        val  df = spark.sparkContext.textFile("examples/src/main/resources/train-zhang.txt").map(x => {
//          val info = x.split(" ")
//          Log(info(0),info(1).toInt,info(2))
//        }).toDF()
//
//
//    //    spark.sql("drop table newLog")
//        df.write.format("hive").option("fileFormat","orc").saveAsTable("newLog")

    //    spark.sql("insert overwrite table log select * from log order by time")
    /**********************搞清楚RowGroup是怎么跳的***************************/
    //   spark.sql("select count(*) from log").show()
    //   val rdd =  spark.sql("select path,frequency from log where frequency >= 3").rdd
    //   println(rdd.toDebugString)
    //    rdd.collect()

    /*****************模拟从原表读数据并缓存**************************/
//        val log_path = spark.sql("select get_json_object(path,'$.age') as path_age,get_json_object(path,'$.name')as path_name from newLog")
//
//        log_path.write.format("hive").option("fileFormat","orc").saveAsTable("default_newLog")
    /*********************模拟读缓存(测试单独读path的reader可不可以用)*************************/
    //    val log = spark.read.orc("data/log_path")
    //    val log  = spark.sql("select path from newLog_path")
    ////    log.collect()
    ////    log.show(10)
    /**********************模拟读缓存，当语句中有path的时候，开启两个reader************************/

    val log = spark.sql("select get_json_object(path,'$.name')as path_name,get_json_object(path,'$.age') as path_age,frequency,time from newLog")
    log.explain(true)
    log.collect()
    log.show(10)
  }
}

//case class People(name:String,age:String)
