package ${packageName}.entity${path};

import com.fasterxml.jackson.annotation.JsonFormat;
<#if swaggerFlag>
import io.swagger.annotations.*;
</#if>
<#if lombokFlag>
import lombok.Data;
</#if>
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.lang.*;

/**
 * ${functionName}实体
 *
 * @author:
 * @date: ${createDate}
 */
<#if swaggerFlag>
@ApiModel(value = "${modelName}", description = "${tableComment}")
</#if>
<#if lombokFlag>
@Data
</#if>
public class ${modelName} {

<#if columns??>
<#list columns as column>
    /**
     * ${column.remark}
     */
    <#if swaggerFlag>
    @ApiModelProperty(value = "${column.remark}")
    </#if>
    <#if column.propertyType='Date' || column.propertyType='Timestamp'>
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    </#if>
    private ${column.propertyType} ${column.propertyName?uncap_first};

</#list>
</#if>
<#-- get/set 方法  -->
<#if !lombokFlag>
<#if columns??>
<#list columns as column>
    public ${column.propertyType} get${column.propertyName}(){
        return ${column.propertyName?uncap_first};
    }
    public void set${column.propertyName}(${column.propertyType} ${column.propertyName?uncap_first}){
        this.${column.propertyName?uncap_first}=${column.propertyName?uncap_first};
    }
</#list>
</#if>
</#if>
}