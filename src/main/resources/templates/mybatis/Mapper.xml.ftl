<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${packageName}.mapper${path}.${mapperName}">
    <resultMap id="BaseResultMap" type="${packageName}.entity${path}.${modelName}">
        <#list columns as column>
        <<#if column.primary??>id<#else>result</#if> column="${column.columnName}" property="${column.propertyName?uncap_first}" jdbcType="${column.jdbcType}"/>
        </#list>
    </resultMap>

    <sql id="Base_Column_List"><#rt>
        <#list columns as column><#if column_index%12 gt 0><#t>${column.columnName}<#else>
        ${column.columnName}</#if><#rt><#sep>,</#sep></#list>
    </sql>
    <select id="getBy${primaryColumn.propertyName?cap_first}" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${tableName}
        where
        <#if primaryColumn??>
        ${primaryColumn.columnName} = <#noparse>#{</#noparse>${primaryColumn.propertyName?uncap_first},jdbcType=${primaryColumn.jdbcType}}
        <#else>
        <#noparse>id=#{id,jdbcType=VARCHAR}</#noparse>
        </#if>
    </select>
    <select id="getAll${modelName}s" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${tableName}
    </select>
    <select id="getAllBySearch" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${tableName}
        <where>
            <#list columns as column>
            <#noparse><if test="</#noparse>${column.propertyName?uncap_first}<#noparse> != null</#noparse> <#if column.propertyType=="String"><#noparse>and</#noparse> ${column.propertyName?uncap_first} <#noparse>!= '' </#noparse></#if><#noparse>"></#noparse>
                and ${column.columnName} <#if column.propertyType=="String" && column.propertyName!=primaryColumn.columnName>like concat('%',<#noparse>#</#noparse>{${column.propertyName?uncap_first},jdbcType=${column.jdbcType}},'%')<#else>= <#noparse>#</#noparse>{${column.propertyName?uncap_first},jdbcType=${column.jdbcType}}</#if>
            </if>
            </#list>
        </where>
    </select>
    <select id="getAllBySearchMap" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${tableName}
        <where>
            <#list columns as column>
            <#noparse><if test="</#noparse>${column.propertyName?uncap_first}<#noparse> != null</#noparse> <#if column.propertyType=="String"><#noparse>and</#noparse> ${column.propertyName?uncap_first} <#noparse>!= '' </#noparse></#if><#noparse>"></#noparse>
                and ${column.columnName} <#if column.propertyType=="String" && column.propertyName!=primaryColumn.columnName>like concat('%',<#noparse>#</#noparse>{${column.propertyName?uncap_first},jdbcType=${column.jdbcType}},'%')<#else>= <#noparse>#</#noparse>{${column.propertyName?uncap_first},jdbcType=${column.jdbcType}}</#if>
            </if>
            </#list>
        </where>
    </select>
    <insert id="insert" parameterType="${packageName}.entity${path}.${modelName}">
        insert into ${tableName} (<#rt>
        <#list columns as column><#if column_index%12 gt 0><#t>${column.columnName}<#else>
            ${column.columnName}</#if><#rt><#sep>,</#sep></#list>
        ) values (<#rt>
        <#list columns as column><#if column_index%5 gt 0><#noparse>#</#noparse>{${column.propertyName?uncap_first},jdbcType=${column.jdbcType}}<#t><#else>
        <#noparse>#</#noparse>{${column.propertyName?uncap_first},jdbcType=${column.jdbcType}}</#if><#rt><#sep>,</#sep></#list>)
    </insert>
    <insert id="insertSelective" parameterType="${packageName}.entity${path}.${modelName}">
        insert into ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list columns as column>
            <#noparse><if test="</#noparse>${column.propertyName?uncap_first}<#noparse>!= null"></#noparse>${column.columnName},<#noparse></if></#noparse>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list columns as column>
            <#noparse><if test="</#noparse>${column.propertyName?uncap_first}<#noparse>!= null"></#noparse><#noparse>#</#noparse>{${column.propertyName?uncap_first},jdbcType=${column.jdbcType}},</if>
            </#list>
        </trim>
    </insert>
    <update id="update">
        update ${tableName}
        <set>
            <#list columns as column>
            <#if primaryColumn.columnName==column.propertyName>
            <#else>
            ${column.columnName} = <#noparse>#</#noparse>{${column.propertyName?uncap_first},jdbcType=${column.jdbcType}},
            </#if>
            </#list>
        </set>
        where ${primaryColumn.columnName} = <#noparse>#</#noparse>{${primaryColumn.propertyName?uncap_first}, jdbcType=${primaryColumn.jdbcType}}
    </update>
    <update id="updateSelective">
        update ${tableName}
        <set>
            <#list columns as column>
            <#if primaryColumn.columnName==column.propertyName>
            <#else>
            <#noparse><if test="</#noparse>${column.propertyName?uncap_first}<#noparse> != null"></#noparse>${column.columnName} = <#noparse>#</#noparse>{${column.propertyName?uncap_first},jdbcType=${column.jdbcType}},</if>
            </#if>
            </#list>
        </set>
        where ${primaryColumn.columnName} = <#noparse>#</#noparse>{${primaryColumn.propertyName?uncap_first}, jdbcType=${primaryColumn.jdbcType}}
    </update>
    <delete id="deleteBy${primaryColumn.propertyName?cap_first}">
        delete from  ${tableName}
        where ${primaryColumn.columnName} = <#noparse>#</#noparse>{${primaryColumn.propertyName?uncap_first},jdbcType=${primaryColumn.jdbcType}}
    </delete>

<#if batchFlag>
    <insert id="insertBatch" parameterType="java.util.List" >
        insert into ${tableName}(<#rt>
        <#list columns as column><#if column_index%12 gt 0><#t>${column.columnName}<#else>
            ${column.columnName}</#if><#rt><#sep>,</#sep></#list>
        ) values
        <foreach collection ="list" item="item" index= "index" separator =",">(
            <#list columns as column><#if column_index%5 gt 0><#noparse>#</#noparse>{item.${column.propertyName?uncap_first},jdbcType=${column.jdbcType}}<#t><#else>
            <#noparse>#</#noparse>{item.${column.propertyName?uncap_first},jdbcType=${column.jdbcType}}</#if><#rt><#sep>,</#sep></#list>)
        </foreach>
    </insert>
    <update id="updateBatchSelective" parameterType="java.util.List" >
        <foreach collection="list" separator=";" item="item">
            update ${tableName}
            <set>
                <#list columns as column>
                <#--如果是主键则跳过-->
                    <#if primaryColumn.columnName==column.propertyName>
                    <#else>
                <#noparse><if test="</#noparse>item.${column.propertyName?uncap_first}<#noparse> != null"></#noparse>${column.columnName} = <#noparse>#</#noparse>{item.${column.propertyName?uncap_first},jdbcType=${column.jdbcType}},</if>
                    </#if>
                </#list>
            </set>
            where ${primaryColumn.columnName} = <#noparse>#</#noparse>{item.${primaryColumn.propertyName?uncap_first},jdbcType=${primaryColumn.jdbcType}}
        </foreach>
    </update>
    <update id="updateBatch" parameterType="java.util.List" >
        <foreach collection="list" separator=";" index="index" item="item">
            update ${tableName}
            <set>
                <#list columns as column>
                <#--如果是主键则跳过-->
                <#if primaryColumn.columnName==column.propertyName>
                <#else>
                ${column.columnName} = <#noparse>#</#noparse>{item.${column.propertyName?uncap_first},jdbcType=${column.jdbcType}},
                </#if>
                </#list>
            </set>
            where ${primaryColumn.columnName} = <#noparse>#</#noparse>{item.${primaryColumn.propertyName?uncap_first},jdbcType=${primaryColumn.jdbcType}}
        </foreach>
    </update>
    <delete id="deleteBatchBy${primaryColumn.propertyName?cap_first}s" parameterType="java.util.List" >
        delete from ${tableName}
        <#noparse><if test="list != null and list.size > 0"></#noparse>
            where ${primaryColumn.columnName} in
            <foreach collection="list" item="item" separator="," open="(" close=")">
                <#noparse>#</#noparse>{item,jdbcType=${primaryColumn.jdbcType}}
            </foreach>
        </if>
    </delete>
</#if>
</mapper>