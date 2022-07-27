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

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MockCondition> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param mockCondition 实例对象
     * @return 对象列表
     */
    List<MockCondition> queryAll(MockCondition mockCondition);

    /**
     * 新增数据
     *
     * @param mockCondition 实例对象
     * @return 影响行数
     */
    int insert(MockCondition mockCondition);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MockCondition> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MockCondition> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MockCondition> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MockCondition> entities);

    /**
     * 修改数据
     *
     * @param mockCondition 实例对象
     * @return 影响行数
     */
    int update(MockCondition mockCondition);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);


}

