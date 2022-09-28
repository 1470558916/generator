package ${packageName}.service${path};

import ${packageName}.entity${path}.${modelName};
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ${functionName}Service业务处理
 *
 * @author:
 * @date: ${createDate}
 */
public interface ${serviceName} {

    /**
     * 获取${functionName}详情
     *
     * @param ${primaryColumn.propertyName?uncap_first} 主键
     * @return ${modelName} ${functionName}详情
     */
    public ${modelName} get${modelName}By${primaryColumn.propertyName?cap_first}(${primaryColumn.propertyType} ${primaryColumn.propertyName?uncap_first});

    /**
     * 查询${functionName}列表
     *
     * @return List<${modelName}> ${functionName}列表
     */
    public List<${modelName}> getAll${modelName}s();

    /**
     * 查询${functionName}列表
     *
     * @param ${modelName?uncap_first} ${functionName}参数
     * @return List<${modelName}> ${functionName}列表
     */
    public List<${modelName}> getAll${modelName}sBySearch(${modelName} ${modelName?uncap_first});

    /**
     * 查询${functionName}列表
     *
     * @param searchMap ${functionName}参数
     * @return List<${modelName}>  ${functionName}列表
     */
    List<${modelName}> getAllBySearchMap(Map<String, Object> searchMap);

    /**
     * 新增${functionName}
     *
     * @param ${modelName?uncap_first} ${functionName}
     * @return int 新增成功返回 1,失败返回 0
     */
    public int insert${modelName}(${modelName} ${modelName?uncap_first});

    /**
     * 修改${functionName}
     *
     * @param ${modelName?uncap_first} ${functionName}
     * @return 修改成功返回 1,失败返回 0,未找到数据返回 -1
     */
    public int update${modelName}(${modelName} ${modelName?uncap_first});

    /**
     * 删除${functionName}
     *
     * @param ${primaryColumn.propertyName?uncap_first} 主键
     * @return 删除成功返回 1,失败返回 0
     */
    public int delete${modelName}By${primaryColumn.propertyName?cap_first}(${primaryColumn.propertyType} ${primaryColumn.propertyName?uncap_first});

<#if batchFlag>
    /**
     * 批量新增${functionName}
     *
     * @param list ${functionName}集合
     * @return 新增成功数量
     */
    public int insertBatch${modelName}s(List<${modelName}> list);

    /**
     * 批量修改${functionName}
     *
     * @param list ${functionName}集合
     * @return 修改成功数量
     */
    public int updateBatch${modelName}s(List<${modelName}> list);

    /**
     * 批量删除${functionName}
     *
     * @param list 主键集合
     * @return 删除成功数量
     */
    public int deleteBatch${modelName}s(List<${primaryColumn.propertyType}> list);

</#if>
}