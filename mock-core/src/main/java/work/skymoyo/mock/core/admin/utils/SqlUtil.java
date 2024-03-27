package work.skymoyo.mock.core.admin.utils;

import org.springframework.util.StringUtils;

/**
 * sql操作工具类
 *
 * @author ruoyi
 */
public class SqlUtil {
    /**
     * 定义常用的 sql关键字
     */
    public static String SQL_REGEX = "and |extractvalue|updatexml|exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |or |+|user()";

    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * 限制orderBy最大长度
     */
    private static final int ORDER_BY_MAX_LENGTH = 500;

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value) {
        if (StringUtils.hasLength(value) && !isValidOrderBySql(value)) {
            throw new RuntimeException("参数不符合规范，不能进行查询");
        }
        if (value.length() > ORDER_BY_MAX_LENGTH) {
            throw new RuntimeException("参数已超过最大限制，不能进行查询");
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }


}
