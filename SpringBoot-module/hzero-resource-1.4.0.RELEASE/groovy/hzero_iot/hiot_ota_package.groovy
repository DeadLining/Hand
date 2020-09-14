package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_ota_package.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_ota_package") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_ota_package_s', startValue:"1")
        }
        createTable(tableName: "hiot_ota_package", remarks: "OTA升级包") {
            column(name: "PACKAGE_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "THING_MODEL_ID", type: "bigint",  remarks: "设备模板主键")  {constraints(nullable:"false")}  
            column(name: "ENABLED", type: "tinyint",   defaultValue:"1",   remarks: "是否启用。1启用，0未启用")  {constraints(nullable:"false")}  
            column(name: "TYPE", type: "varchar(" + 20 * weight + ")",   defaultValue:"WHOLE",   remarks: "区分整包和差分包，WHOLE|DIFF")   
            column(name: "VERSION", type: "varchar(" + 20 * weight + ")",  remarks: "版本")  {constraints(nullable:"false")}  
            column(name: "CATEGORY", type: "varchar(" + 16 * weight + ")",  remarks: "类别，区分设备和网关")   
            column(name: "ATTACHMENT_UUID", type: "varchar(" + 255 * weight + ")",  remarks: "附件UUID，通过调用附件服务获取下载链接")   
            column(name: "ATTACHMENT_URL", type: "varchar(" + 255 * weight + ")",  remarks: "附件存储URL")   
            column(name: "FILE_NAME", type: "varchar(" + 255 * weight + ")",  remarks: "文件名称")   
            column(name: "FILE_KEY", type: "varchar(" + 255 * weight + ")",  remarks: "fileKey，用于获取下载链接")   
            column(name: "FILE_SIZE", type: "double",  remarks: "固件包文件大小， 单位为 MB")   
            column(name: "SIGN_METHOD", type: "varchar(" + 16 * weight + ")",  remarks: "签名算法，取自值集[HIOT.OTA_SIGN_METHOD]")   
            column(name: "SIGN_VALUE", type: "varchar(" + 512 * weight + ")",  remarks: "签名算法后的值")   
            column(name: "DESCRIPTION", type: "varchar(" + 255 * weight + ")",  remarks: "说明")   
            column(name: "ORGANIZATION_ID", type: "bigint",  remarks: "所属组织ID")   
            column(name: "TENANT_ID", type: "bigint",   defaultValue:"0",   remarks: "租户id")   
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"TENANT_ID,THING_MODEL_ID,VERSION",tableName:"hiot_ota_package",constraintName: "hiot_ota_package_u1")
    }
}