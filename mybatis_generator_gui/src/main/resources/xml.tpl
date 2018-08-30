<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${ps}">
    <sql id="s_name">
        `${table}`
    </sql>

    <sql id="s_col">
        <#list columnList as col>
        `${col.db_name}`<#if col_has_next>,</#if>
        </#list>
    </sql>

    <sql id="s_prop">
    <#list columnList as col>
        ${r"#{"}${col.fmt_name}}<#if col_has_next>,</#if>
    </#list>
    </sql>

    <sql id="insert_clause">
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list columnList as col>
            <if test="bean.${col.fmt_name}!=null">
                `${col.db_name}`,
            </if>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list columnList as col>
            <if test="bean.${col.fmt_name}!=null">
                ${r"#{"}bean.${col.fmt_name}},
            </if>
            </#list>
        </trim>
    </sql>

    <sql id="set_clause">
        <set>
            <#list columnList as col>
            <if test="bean.${col.fmt_name}!=null">
                `${col.db_name}`=${r"#{"}bean.${col.fmt_name}},
            </if>
            </#list>
        </set>
    </sql>

    <sql id="where_clause">
        <where>
            <if test="example!=null">
                <#list columnList as col>
                <if test="example.${col.fmt_name}!=null">
                    AND `${col.db_name}`=${r"#{"}example.${col.fmt_name}}
                </if>
                </#list>
            </if>
        </where>
    </sql>

    <sql id="page">
        <if test="page!=null">
            limit ${r"#{"}page.start},${r"#{"}page.pageSize}
        </if>
    </sql>

    <resultMap type="${clazz}" id="s_map">
    <#list columnList as col>
    <#if col.pk>
        <id property="${col.fmt_name}" column="${col.db_name}" />
    <#else>
        <result property="${col.fmt_name}" column="${col.db_name}"/>
    </#if>
    </#list>
    </resultMap>

    <insert id="insert" parameterType="${clazz}" useGeneratedKeys="true" keyProperty="bean.${pk.fmt_name}">
        insert into
        <include refid="s_name"/>
        <include refid="insert_clause"/>
    </insert>

    <delete id="deleteByExample" parameterType="${clazz}">
        delete from
        <include refid="s_name"/>
        <include refid="where_clause"/>
    </delete>

    <delete id="deleteByPk" parameterType="${clazz}">
        delete from
        <include refid="s_name"/>
        where
        `${pk.db_name}`=${r"#{"}bean.${pk.fmt_name}}
    </delete>

    <update id="updateByExample" parameterType="${clazz}">
        update
        <include refid="s_name"/>
        <include refid="set_clause"/>
        <include refid="where_clause"/>
    </update>

    <update id="updateByPk" parameterType="${clazz}">
        update
        <include refid="s_name"/>
        <include refid="set_clause"/>
        where
       `${pk.db_name}`=${r"#{"}bean.${pk.fmt_name}}
    </update>

    <select id="selectOneByExample" parameterType="${clazz}" resultMap="s_map">
        select
        <include refid="s_col"/>
        from
        <include refid="s_name"/>
        <include refid="where_clause"/>
    </select>

    <select id="selectListByExample" resultMap="s_map">
        select
        <include refid="s_col"/>
        from
        <include refid="s_name"/>
        <include refid="where_clause"/>
        <include refid="page"/>
    </select>

    <select id="countByExample" resultType="long">
        select count(*)
        from
        <include refid="s_name"/>
        <include refid="where_clause"/>
    </select>

    <!-- 下方为连表公共代码区 -->
    <sql id="j_name">
        `${table}` AS ${table}
    </sql>

    <sql id="j_col">
        <#list columnList as col>
        ${table}.`${col.db_name}` AS ${table}_${col.db_name}<#if col_has_next>,</#if>
        </#list>
    </sql>

    <sql id="j_where_clause">
        <where>
            <if test="${obj}!=null">
                <#list columnList as col>
                <if test="${obj}.${col.fmt_name}!=null">
                    AND ${table}.`${col.db_name}`=${r"#{"}${obj}.${col.fmt_name}}
                </if>
                </#list>
            </if>
        </where>
    </sql>

    <resultMap type="${clazz}" id="j_map">
        <#list columnList as col>
        <#if col.pk>
        <id property="${col.fmt_name}" column="${table}_${col.db_name}" />
        <#else>
        <result property="${col.fmt_name}" column="${table}_${col.db_name}"/>
        </#if>
        </#list>
    </resultMap>

    <!-- 连表容器参考
    一对一容器
    <association property="" javaType="">
        <result property="" column="" />
    </association>
    一对多容器
    <collection property="" ofType="">
        <result property="" column="" />
    </collection>
    -->
    <!-- 下方为自定义代码区 -->

</mapper>