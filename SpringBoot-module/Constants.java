package bjja.sys.platform.customs.utils.constant;

import cn.pigicutils.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hzero.core.base.BaseConstants;

import java.util.Date;

/**
 * 通用常量类
 *
 * @author 潘顾昌
 * @date 2019/12/17 13:45
 */
public interface Constants extends BaseConstants {
    /**
     * 拣货状态
     */
    @AllArgsConstructor
    @Getter
    enum SHIP_PICK_STATUS {
        /**
         * 未拣货
         */
        A("10", "未拣货");
        private String key;
        private String value;
    }

    /**
     * 货物状态
     */
    @AllArgsConstructor
    @Getter
    enum CARGO_STATUS {
        /**
         * 虚拟
         */
        A("01", "未入库"),
        /**
         * 正式
         */
        B("02", "已入库"),
        C("03", "已预留");
        private String key;
        private String value;
    }

    /**
     * 基础异常编码
     */
    interface ErrorCode {
        /**
         * 数据校验不通过
         */
        String DATA_INVALID = "error.data_invalid";

        /**
         * 虚拟
         */
        String IS_VITRAL = "error.is_vitral";
        /**
         * 状态出错
         */
        String STATUS_ERROR = "error.cargo_status";
        /**
         * 数量非法
         */
        String QTY_INVALID = "error.qty_invalid";
        /**
         * 虚拟转换失败
         */
        String VITRAL_FAILED = "error.vitral_failed";
        /**
         * 资源不存在
         */
        String NOT_FOUND = "error.not_found";
        /**
         * 程序出现错误，请联系管理员
         */
        String ERROR = "error.error";
        /**
         * 网络异常，请稍后重试
         */
        String ERROR_NET = "error.network";
        /**
         * 记录不存在或版本不一致
         */
        String OPTIMISTIC_LOCK = "error.optimistic_lock";
        /**
         * 数据已存在，请不要重复提交
         */
        String DATA_EXISTS = "error.data_exists";
        /**
         * 数据不存在
         */
        String DATA_NOT_EXISTS = "error.data_not_exists";
        /**
         * 资源禁止访问
         */
        String FORBIDDEN = "error.forbidden";
        /**
         * 数据库异常：编码重复
         */
        String ERROR_CODE_REPEAT = "error.code_repeat";
        /**
         * 数据库异常：编号重复
         */
        String ERROR_NUMBER_REPEAT = "error.number_repeat";
        /**
         * SQL执行异常
         */
        String ERROR_SQL_EXCEPTION = "error.sql_exception";
        /**
         * 请登录后再进行操作！
         */
        String NOT_LOGIN = "error.not_login";
        /**
         * 不能为空
         */
        String NOT_NULL = "error.not_null";
    }

    /**
     * 类型
     */
    @AllArgsConstructor
    @Getter
    enum CONTAINER_SPLICE {
        /**
         * 虚拟
         */
        A("01", "集装箱"),
        /**
         * 正式
         */
        B("02", "托盘"),
        C("03", "托片");
        private String key;
        private String value;
    }

    @AllArgsConstructor
    @Getter
    enum CHECK_TASK_STATUS {
        A("01", "新增"),
        D("04", "已生成盘点明细"),
        B("02", "盘点中"),
        C("03", "盘点完成");
        private String key;
        private String value;
    }

    @AllArgsConstructor
    @Getter
    enum CHECK_INFO_STATUS {
        A("01", "新增"),
        B("02", "已盘点确认");
        private String key;
        private String value;
    }

    /**
     * 库存事务类型
     */
    @AllArgsConstructor
    @Getter
    enum BOUND_TRANSACTION_TYPE {
        /**
         * 虚拟
         */
        A("01", "入库单收货"),
        /**
         * 正式
         */
        B("02", "出库释放"),
        /**
         * 计划外入库
         */
        C("03", "计划外入库"),
        /**
         * 计划外出库
         */
        D("04", "计划外出库"),
        /**
         * 虚拟出库
         */
        E("05", "虚拟出库");
        private String key;
        private String value;
    }

    /**
     * 审批信息
     */
    @AllArgsConstructor
    @Getter
    enum APPROVE_STATUS {
        /**
         * 审批
         */
        A("Approved", "审批"),
        /**
         * 拒绝
         */
        B("Rejected", "拒绝");
        private String key;
        private String value;
    }

    @AllArgsConstructor
    @Getter
    enum OUTBOUND_LOGTIC_STATUS {
        A("01", "出库"),
        B("02", "在途"),
        C("03", "抵达"),
        D("04", "签收"),
        E("05", "签收审核");
        private String key;
        private String value;
    }

    @AllArgsConstructor
    @Getter
    enum OUTBOUND_ORDER_STATUS {
        A("01", "新建"),
        B("02", "已出库"),
        C("03", "出库在途"),
        D("04", "已签收"),
        E("05", "已关闭"),
        F("06", "已审核"),
        G("07", "可出库"),
        H("08", "已拒绝");
        private String key;
        private String value;
    }

    @Getter
    enum LOGISTICS_PLAN {
        A("10", "已生成报关任务"),
        B("20", "已保存"),
        C("30", "已作废"),
        D("40", "已结单"),
        E("50", "已确认订舱"),
        F("60", "配仓中"),
        G("70", "待作废");

        private String key;
        private String value;

        LOGISTICS_PLAN(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    @Getter
    enum InboundType {
        PROTAX("01", "保税"),
        NOPROTAX("02", "保税");

        private String value;
        private String name;

        InboundType(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public static String getMeaning(String value) {
            for (InboundType inboundOrder : InboundType.values()) {
                if (inboundOrder.getValue().equals(value)) {
                    return inboundOrder.getName();
                }
            }
            return null;
        }
    }

    @Getter
    enum INBOUND_SALE_STATES {
        INBOUND("01", "在库"),
        REMAIN("02", "预留"),
        RELEASE("03", "释放");

        private String value;
        private String name;

        INBOUND_SALE_STATES(String value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    /**
     * 文件常量类
     */
    interface FileUtils {
        /**
         * 导入桶名称
         */
        String IMPORT_BUCKET = "import-task";

        /**
         * 当前时间
         */
        String NOW = DateUtil.format(new Date(), "yyyyMMdd");
    }

    interface EmailConfig {

        /**
         * 邮箱账户编码
         */
        String SERVERCODE = "PIGIC";

        /**
         * 语言
         */
        String LANG = "zh_CN";
    }

    interface Device {

        /**
         * 邮箱账户编码
         */
        String UNIT_LOV_NAME = "HCTM.TIME_UNIT";
    }

    @Getter
    enum ImportBill {
        /**
         * 草稿
         */
        DRAFT("DRAFT", "草稿"),
        /**
         * 已提交
         */
        SUBMITTED("SUBMITTED", "已提交"),
        /**
         * 已退回
         */
        REJECTED("REJECTED", "已退回"),
        /**
         * 审批通过
         */
        APPROVED("APPROVED", "审批通过"),
        /**
         * 申报成功
         */
        SUCCEED("SUCCEED", "申报成功"),
        /**
         * 已作废
         */
        INVALID("INVALID", "已作废"),
        /**
         * 暂存成功
         */
        SAVE_SUCCESSED("SAVE_SUCCESSED", "暂存成功"),
        /**
         * 暂存失败
         */
        SAVE_FAILED("SAVE_FAILED", "暂存失败"),
        /**
         * 申报失败
         */
        FAILED("FAILED", "申报失败");

        private String value;
        private String meaning;

        ImportBill(String value, String meaning) {
            this.value = value;
            this.meaning = meaning;
        }
    }

    @Getter
    enum TaskStatus {
        /**
         * 草稿
         */
        DRAFT("DRAFT", "草稿"),
        /**
         * 新增
         */
        NEW("NEW", "新增"),
        /**
         * 已指派
         */
        APPOINTED("APPOINTED", "已指派"),
        /**
         * 已生成报关单
         */
        QUOTED("QUOTED", "已生成报关单"),
        /**
         * 申报成功
         */
        SUCCEED("SUCCEED", "申报成功"),
        /**
         * 申报失败
         */
        FAILED("FAILED", "申报失败"),
        /**
         * 已作废
         */
        INVALID("INVALID", "已作废");

        private String value;
        private String meaning;

        TaskStatus(String value, String meaning) {
            this.value = value;
            this.meaning = meaning;
        }
    }

    @Getter
    enum InboundOrderStatus {

        /**
         * 在途
         */
        INTRANSIT("INTRANSIT", "在途"),

        /**
         * 已入库
         */
        WAREHOUSING("WAREHOUSING", "已入库"),

        /**
         * 已作废
         */
        INVALID("INVALID", "已作废");

        private String value;
        private String meaning;

        InboundOrderStatus(String value, String meaning) {
            this.value = value;
            this.meaning = meaning;
        }
    }

}
