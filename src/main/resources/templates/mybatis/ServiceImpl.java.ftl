package ${packageName}.service${path}.impl;

import ${packageName}.entity${path}.${modelName};
import ${packageName}.mapper${path}.${mapperName};
import ${packageName}.service${path}.${serviceName};
<#if slf4jFlag>
import lombok.extern.slf4j.Slf4j;
</#if>
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


<#if slf4jFlag>
@Slf4j
</#if>
@Service
public class ${serviceImplName} implements ${serviceName} {

    @Autowired
    ${mapperName} ${mapperName?uncap_first};

    @Override
    public ${modelName} get${modelName}By${primaryColumn.propertyName?cap_first}(${primaryColumn.propertyType} ${primaryColumn.propertyName?uncap_first}) {
        return ${mapperName?uncap_first}.getBy${primaryColumn.propertyName?cap_first}(${primaryColumn.propertyName?uncap_first});
    }

    @Override
    public List<${modelName}> getAll${modelName}s() {
        return ${mapperName?uncap_first}.getAll${modelName}s();
    }

    @Override
    public List<${modelName}> getAll${modelName}sBySearch(${modelName} ${modelName?uncap_first}) {
        return ${mapperName?uncap_first}.getAllBySearch(${modelName?uncap_first});
    }

    @Override
    public List<${modelName}> getAllBySearchMap(Map<String, Object> searchMap) {
        return ${mapperName?uncap_first}.getAllBySearchMap(searchMap);
    }

    @Override
    public int insert${modelName}(${modelName} ${modelName?uncap_first}) {
        // TODO 主键生成 补全其他信息(操作人、操作时间等)
        return ${mapperName?uncap_first}.insert(${modelName?uncap_first});
    }

    @Override
    public int update${modelName}(${modelName} ${modelName?uncap_first}) {
        // 判断主键是否为空，为空或者查不到对应数据 返回-1
        <#if primaryColumn.propertyType=="String">
        if (StringUtils.isEmpty(${modelName?uncap_first}.get${primaryColumn.propertyName?cap_first}())) {
        </#if>
        <#if primaryColumn.propertyType=="Integer">
        if (${modelName?uncap_first}.get${primaryColumn.propertyName?cap_first}() == null
            || ${modelName?uncap_first}.get${primaryColumn.propertyName?cap_first}() == 0) {
        </#if>
            return -1;
        }
        ${modelName} old${modelName} = ${mapperName?uncap_first}.getBy${primaryColumn.propertyName?cap_first}(${modelName?uncap_first}.get${primaryColumn.propertyName?cap_first}());
        if (old${modelName} == null) {
            return -1;
        }
        // TODO 补全其他信息(操作人、修改时间等)
        return ${mapperName?uncap_first}.update(${modelName?uncap_first});
    }

    @Override
    public int delete${modelName}By${primaryColumn.propertyName?cap_first}(${primaryColumn.propertyType} ${primaryColumn.propertyName?uncap_first}) {
        return ${mapperName?uncap_first}.deleteBy${primaryColumn.propertyName?cap_first}(${primaryColumn.propertyName?uncap_first});
    }

<#if batchFlag>
    @Override
    public int insertBatch${modelName}s(List<${modelName}> list) {
        // TODO 主键生成 补全其他信息(操作人、操作时间等)
        return ${mapperName?uncap_first}.insertBatch(list);
    }

    @Override
    public int updateBatch${modelName}s(List<${modelName}> list) {
        return ${mapperName?uncap_first}.updateBatch(list);
    }

    @Override
    public int deleteBatch${modelName}s(List<${primaryColumn.propertyType}> list) {
        return ${mapperName?uncap_first}.deleteBatchBy${primaryColumn.propertyName?cap_first}s(list);
    }

</#if>

}