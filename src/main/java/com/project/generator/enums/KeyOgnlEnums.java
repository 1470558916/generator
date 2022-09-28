package com.project.generator.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 关键字枚举类,用于校验是否有 关键字冲突
 * mybatis的OGNL ： bor（字符|）的英文 、xor  字符^的英文、and   字符&&、band 字符&
 * eq  字符== 、neq  字符！=、lt 字符<、gt  字符>、lte 字符<=、gte 字符>=、shl 字符 <<、shr 字符>>、ushr 字符>>>
 *
 * @program: generator
 * @description: 关键字枚举类,用于校验是否有 关键字冲突
 * @author: zhaoym
 * @create: 2022-08-03 15:13
 */
public enum KeyOgnlEnums {

    BOR("|"),
    XOR("^"),
    BAND("&"),
    EQ("=="),
    NEQ("!="),
    LT("<"),
    GT(">"),
    LTE("<="),
    GTE(">="),
    SHL("<<"),
    SHR(">>"),
    USHR(">>>"),
    AND("&&");

    @JsonValue
    @Getter
    private final String code;

    KeyOgnlEnums(String code) {
        this.code = code;
    }

    public static String getCode(){
        return null;
    }

}
