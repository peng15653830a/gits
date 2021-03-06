package xyz.gits.boot.api.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import xyz.gits.boot.common.core.enums.CodeEnum;

import java.util.Arrays;

/**
 * 有效状态[0:有效;1:无效]
 *
 * @author dingmingyang
 * @date 2020/06/04/11:02
 */
@Slf4j
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status implements CodeEnum {
    /**
     * 有效
     */
    VALID("0", "有效"),
    /**
     * 无效
     */
    INVALID("1", "无效");
    @EnumValue
    private String code;
    private String message;

    Status() {
    }

    Status(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 反序列化时的初始化函数
     */
    @JsonCreator
    public static Status getItem(@JsonProperty("code") String code) {
        for (Status item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        log.warn("[Status] 枚举反序列化异常：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(Status.values()));
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link Status}
     * @author dingmingyang
     * @date 2020/6/5 11:36
     */
    public static Status fromString(String code) {
        for (Status b : Status.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        log.warn("[Status] 无此枚举：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(Status.values()));
    }
}
