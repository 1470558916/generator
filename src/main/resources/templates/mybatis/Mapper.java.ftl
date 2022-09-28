package ${packageName}.mapper${path};

import ${packageName}.entity${path}.${modelName};

import java.util.List;
import java.util.Map;

/**
 * ${functionName}Mapper接口
 *
 * @author:
 * @date: ${createDate}
 */
public interface ${mapperName} {

    /**
     * 查询${functionName}列表
     *
     * @return ${modelName} ${functionName}列表
     */
    List<${modelName}> getAll${modelName}s();

    /**
     * 查询${functionName}列表
     *
     * @param ${modelName?uncap_first} ${functionName}参数
     * @return List<${modelName}>  ${functionName}列表
     */
    List<${modelName}> getAllBySearch(${modelName} ${modelName?uncap_first});

    /**
     * 查询${functionName}列表
     *
     * @param searchMap ${functionName}参数
     * @return List<${modelName}>  ${functionName}列表
     */
    List<${modelName}> getAllBySearchMap(Map<String, Object> searchMap);

    /**
     * 获取${functionName}详情
     *
     * @param ${primaryColumn.propertyName?uncap_first} 主键
     * @return ${modelName} ${functionName}
     */
    ${modelName} getBy${primaryColumn.propertyName?cap_first}(${primaryColumn.propertyType} ${primaryColumn.propertyName?uncap_first});

    /**
     * 新增${functionName}
     *
     * @param ${modelName?uncap_first}
     * @return int 新增成功返回 1,失败返回 0
     */
    int insert(${modelName} ${modelName?uncap_first});

    /**
     * 新增${functionName}(给有值的字段赋值)
     *
     * @param ${modelName?uncap_first}
     * @return int 新增成功返回 1,失败返回 0
     */
    int insertSelective(${modelName} ${modelName?uncap_first});

    /**
     * 修改${functionName}
     *
     * @param ${modelName?uncap_first}
     * @return int 修改成功返回 1,失败返回 0
     */
    int update(${modelName} ${modelName?uncap_first});

    /**
     * 修改${functionName}
     *
     * @param ${modelName?uncap_first}
     * @return 修改成功返回 1,失败返回 0
     */
    int updateSelective(${modelName} ${modelName?uncap_first});

    /**
     * 删除${functionName}
     *
     * @param ${primaryColumn.propertyName?uncap_first} 主键
     * @return 删除成功返回 1,失败返回 0
     */
    int deleteBy${primaryColumn.propertyName?cap_first}(${primaryColumn.propertyType} ${primaryColumn.propertyName?uncap_first});

<#if batchFlag>
    /**
     * 批量新增${functionName}
     *
     * @param list ${functionName}集合
     * @return 新增成功数量
     */
    int insertBatch(List<${modelName}> list);

    /**
     * 批量修改${functionName}
     *
     * @param list ${functionName}集合
     * @return 修改成功数量
     */
    int updateBatchSelective(List<${modelName}> list);

    /**
     * 批量修改${functionName}
     *
     * @param list ${functionName}集合
     * @return 修改成功数量
     */
    int updateBatch(List<${modelName}> list);

    /**
     * 批量删除${functionName}
     *
     * @param list 主键集合
     * @return 删除成功数量
     */
    int deleteBatchBy${primaryColumn.propertyName?cap_first}s(List<${primaryColumn.propertyType}> list);

</#if>
}