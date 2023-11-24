package work.skymoyo.mock.core.resource.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import work.skymoyo.mock.core.resource.entity.MockCondition;

import java.util.List;

/**
 * (MockCondition)表数据库访问层
 *
 * @author skymoyo
 * @since 2022-07-23 09:13:57
 */
@Mapper
@Repository
public interface MockConditionDao {


    List<MockCondition> queryByRuleCode(@Param("ruleCode") String ruleCode);


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return result
     */
    String queryById(@Param("id") Long id);


}

