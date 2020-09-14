package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_alert_model_tl.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_alert_model_tl") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        createTable(tableName: "hiot_alert_model_tl", remarks: "告警模板语言表") {
            column(name: "ALERT_MODEL_ID", type: "bigint",  remarks: "表ID，主键")  {constraints(nullable:"false")}  
            column(name: "LANG", type: "varchar(" + 20 * weight + ")",  remarks: "语言")  {constraints(nullable:"false")}
            column(name: "ALERT_MODEL_NAME", type: "varchar(" + 45 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "varchar(" + 255 * weight + ")",  remarks: "规则说明")

        }

        addUniqueConstraint(columnNames:"ALERT_MODEL_ID,LANG",tableName:"hiot_alert_model_tl",constraintName: "hiot_alert_model_tl_u1")
    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-20_hiot_alert_model_tl_fix"){
        sql {
            "INSERT INTO hiot_alert_model_tl (  " +
                    "  alert_model_id,  " +
                    "  lang,  " +
                    "  alert_model_name,  " +
                    "  description  " +
                    ") SELECT  " +
                    "  am.ALERT_MODEL_ID,  " +
                    "  'zh_CN',  " +
                    "  am.ALERT_MODEL_NAME,  " +
                    "  am.description  " +
                    "FROM  " +
                    "  hiot_alert_model am  " +
                    "WHERE  " +
                    "  1 = 1  " +
                    "AND NOT EXISTS (  " +
                    "  SELECT  " +
                    "    1  " +
                    "  FROM " +
                    "    hiot_alert_model_tl amt " +
                    "  WHERE " +
                    "    amt.ALERT_MODEL_ID = am.ALERT_MODEL_ID " +
                    ") " +
                    "UNION " +
                    "  SELECT " +
                    "  am.ALERT_MODEL_ID, " +
                    "  'ja_JP', " +
                    "  am.ALERT_MODEL_NAME, " +
                    "  am.description " +
                    "FROM " +
                    "  hiot_alert_model am " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_alert_model_tl amt " +
                    "  WHERE " +
                    "    amt.ALERT_MODEL_ID = am.ALERT_MODEL_ID " +
                    ") " +
                    "  UNION " +
                    "    SELECT " +
                    "  am.ALERT_MODEL_ID, " +
                    "  'en_US', " +
                    "  am.ALERT_MODEL_NAME, " +
                    "  am.description " +
                    "FROM " +
                    "  hiot_alert_model am " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_alert_model_tl amt " +
                    "  WHERE " +
                    "    amt.ALERT_MODEL_ID = am.ALERT_MODEL_ID " +
                    ");"
        }

    }
}