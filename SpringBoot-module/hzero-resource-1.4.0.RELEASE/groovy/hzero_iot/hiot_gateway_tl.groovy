package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_gateway_tl.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_gateway_tl") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        createTable(tableName: "hiot_gateway_tl", remarks: "网关语言表") {
            column(name: "GATEWAY_ID", type: "bigint",  remarks: "表ID，主键")  {constraints(nullable:"false")}  
            column(name: "LANG", type: "varchar(" + 20 * weight + ")",  remarks: "语言")  {constraints(nullable:"false")}  
            column(name: "GATEWAY_NAME", type: "varchar(" + 45 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "varchar(" + 100 * weight + ")",  remarks: "说明")   

        }

        addUniqueConstraint(columnNames:"GATEWAY_ID,LANG",tableName:"hiot_gateway_tl",constraintName: "hiot_gateway_tl_u1")
    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-20_hiot_gateway_tl_fix"){
        sql {
            "INSERT INTO hiot_gateway_tl (  " +
                    "  gateway_id,  " +
                    "  lang,  " +
                    "  gateway_name,  " +
                    "  description  " +
                    ") SELECT  " +
                    "  g.GATEWAY_ID, " +
                    "  'zh_CN', " +
                    "  g.GATEWAY_NAME, " +
                    "  g.description " +
                    "FROM " +
                    "  hiot_gateway g " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_gateway_tl gt " +
                    "  WHERE " +
                    "    g.GATEWAY_ID = gt.GATEWAY_ID " +
                    ") " +
                    "UNION " +
                    "  SELECT " +
                    "    g.GATEWAY_ID, " +
                    "    'ja_JP', " +
                    "    g.GATEWAY_NAME, " +
                    "    g.description " +
                    "  FROM " +
                    "    hiot_gateway g " +
                    "  WHERE " +
                    "    1 = 1 " +
                    "  AND NOT EXISTS ( " +
                    "    SELECT " +
                    "      1 " +
                    "    FROM " +
                    "      hiot_gateway_tl gt " +
                    "    WHERE " +
                    "      g.GATEWAY_ID = gt.GATEWAY_ID " +
                    "  ) " +
                    "  UNION " +
                    "    SELECT " +
                    "      g.GATEWAY_ID, " +
                    "      'en_US', " +
                    "      g.GATEWAY_NAME, " +
                    "      g.description " +
                    "    FROM " +
                    "      hiot_gateway g " +
                    "    WHERE " +
                    "      1 = 1 " +
                    "    AND NOT EXISTS ( " +
                    "      SELECT " +
                    "        1 " +
                    "      FROM " +
                    "        hiot_gateway_tl gt " +
                    "      WHERE " +
                    "        g.GATEWAY_ID = gt.GATEWAY_ID " +
                    "    );"
        }

    }
}