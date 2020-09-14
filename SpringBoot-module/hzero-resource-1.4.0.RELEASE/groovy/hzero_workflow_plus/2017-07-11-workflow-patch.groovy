package script.db

databaseChangeLog(logicalFilePath:"patch.groovy"){
    def weight = 1
    if (helper.isSqlServer()) {
        weight = 2
    } else if (helper.isOracle()) {
        weight = 3
    }
    changeSet(author: "qixiangyu", id: "20170808-act_hi_identitylink") {
        addColumn(tableName: "act_hi_identitylink") {
            column(name: "read_flag_", type: "tinyint", remarks: "读取状态", defaultValue: "0")
        }
    }

    changeSet(author: "qingsheng.chen", id: "20190611-act_re_model") {
        addColumn(tableName: "act_re_model") {
            column(name: "document_id_", type: "bigint", remarks: "单据ID")
        }
    }

    changeSet(author: "peng.yu01@hand-china.com", id: "2020-03-17-act_hi_procinst") {
        addColumn(tableName: "act_hi_procinst") {
            column(name: "proc_description", type: "varchar(" + 600 * weight + ")", remarks: "流程描述") {
                constraints(nullable: true)
            }
        }
    }
}