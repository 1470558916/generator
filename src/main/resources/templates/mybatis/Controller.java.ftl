package ${packageName}.controller;

import ${packageName}.entity${path}.${modelName};
import ${packageName}.service${path}.${serviceName};
<#if swaggerFlag>
import io.swagger.annotations.*;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ${functionName}Controller
 *
 * @author:
 * @date: ${createDate}
 */
<#if slf4jFlag>
@Slf4j
</#if>
<#if swaggerFlag>
@Api(value = "${modelName}管理", tags = {"${modelName}管理"})
</#if>
@RestController
@RequestMapping("/${modelName?uncap_first}")
public class ${controllerName} {

    @Autowired
    ${serviceName} ${serviceName?uncap_first};

    /**
    * 获取${functionName}详情
    */
    <#if swaggerFlag>
    @ApiOperation(value = "获取${functionName}详情")
    </#if>
    @GetMapping("/{${primaryColumn.propertyName?uncap_first}}")
    public ${modelName} get${modelName}(@PathVariable ${primaryColumn.propertyType} ${primaryColumn.propertyName?uncap_first}) {
        return ${serviceName?uncap_first}.get${modelName}By${primaryColumn.propertyName?cap_first}(${primaryColumn.propertyName?uncap_first});
    }

    /**
     * 查询${functionName}列表
     */
    <#if swaggerFlag>
    @ApiOperation(value = "查询${functionName}列表")
    </#if>
    @PostMapping("/list")
    public List<${modelName}> getAll${modelName}sBySearch(@RequestBody ${modelName} ${modelName?uncap_first}) {
        return ${serviceName?uncap_first}.getAll${modelName}sBySearch(${modelName?uncap_first});
    }

    /**
     * 新增${functionName}
     */
    <#if swaggerFlag>
    @ApiOperation(value = "新增${functionName}")
    </#if>
    @PostMapping("/add")
    public int add${modelName}(@RequestBody ${modelName} ${modelName?uncap_first}) {
        return ${serviceName?uncap_first}.insert${modelName}(${modelName?uncap_first});
    }

    /**
     * 修改${functionName}
     */
    <#if swaggerFlag>
    @ApiOperation(value = "修改${functionName}")
    </#if>
    @PutMapping("/edit")
    public int update${modelName}(@RequestBody ${modelName} ${modelName?uncap_first}) {
        return ${serviceName?uncap_first}.update${modelName}(${modelName?uncap_first});
    }

    /**
     * 删除${functionName}
     */
    <#if swaggerFlag>
    @ApiOperation(value = "删除${functionName}")
    </#if>
    @DeleteMapping("/{${primaryColumn.propertyName?uncap_first}}")
    public int delete${modelName}(@PathVariable ${primaryColumn.propertyType} ${primaryColumn.propertyName?uncap_first}) {
        return ${serviceName?uncap_first}.delete${modelName}By${primaryColumn.propertyName?cap_first}(${primaryColumn.propertyName?uncap_first});
    }

}