package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_project_tl.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_project_tl") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        createTable(tableName: "hiot_project_tl", remarks: "项目信息语言表") {
            column(name: "PROJECT_ID", type: "bigint",  remarks: "表ID，主键")  {constraints(nullable:"false")}  
            column(name: "LANG", type: "varchar(" + 20 * weight + ")",  remarks: "语言")  {constraints(nullable:"false")}  
            column(name: "PROJECT_NAME", type: "varchar(" + 64 * weight + ")",  remarks: "项目名称")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "varchar(" + 255 * weight + ")",  remarks: "说明")   

        }

        addUniqueConstraint(columnNames:"PROJECT_ID,LANG",tableName:"hiot_project_tl",constraintName: "hiot_project_tl_u1")
    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-20_hiot_project_tl_fix"){
        sql {
            "INSERT INTO hiot_project_tl ( " +
                    "  project_id, " +
                    "  lang, " +
                    "  project_name, " +
                    "  description " +
                    ") SELECT " +
                    "  p.PROJECT_ID, " +
                    "  'zh_CN', " +
                    "  p.PROJECT_NAME, " +
                    "  p.DESCRIPTION " +
                    "FROM " +
                    "  hiot_project p " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_project_tl pt " +
                    "  WHERE " +
                    "    p.PROJECT_ID = pt.PROJECT_ID " +
                    ") " +
                    "UNION " +
                    "  SELECT " +
                    "  p.PROJECT_ID, " +
                    "  'ja_JP', " +
                    "  p.PROJECT_NAME, " +
                    "  p.DESCRIPTION " +
                    "FROM " +
                    "  hiot_project p " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_project_tl pt " +
                    "  WHERE " +
                    "    p.PROJECT_ID = pt.PROJECT_ID " +
                    ") " +
                    "  UNION " +
                    "    SELECT " +
                    "  p.PROJECT_ID, " +
                    "  'en_US', " +
                    "  p.PROJECT_NAME, " +
                    "  p.DESCRIPTION " +
                    "FROM " +
                    "  hiot_project p " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_project_tl pt " +
                    "  WHERE " +
                    "    p.PROJECT_ID = pt.PROJECT_ID " +
                    ");"
        }

    }
}