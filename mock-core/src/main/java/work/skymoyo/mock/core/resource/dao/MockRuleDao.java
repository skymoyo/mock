package work.skymoyo.mock.core.resource.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import work.skymoyo.mock.common.model.MockDataBo;
import work.skymoyo.mock.core.resource.entity.MockRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * mock配置表(MockRule)表数据库访问层
 *
 * @author skymoyo
 * @since 2022-07-25 20:19:32
 */
@Mapper
@Repository
public interface MockRuleDao {


    /**
     * 通过mockCode查询数据
     *
     * @param mockCode mock编码
     * @return 实例对象
     */
    List<MockRule> queryByMockCode(@Param("mockCode") String mockCode);


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MockRule queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MockRule> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param mockRule 实例对象
     * @return 对象列表
     */
    List<MockRule> queryAll(MockRule mockRule);

    /**
     * 新增数据
     *
     * @param mockRule 实例对象
     * @return 影响行数
     */
    int insert(MockRule mockRule);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MockRule> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MockRule> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MockRule> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MockRule> entities);

    /**
     * 修改数据
     *
     * @param mockRule 实例对象
     * @return 影响行数
     */
    int update(MockRule mockRule);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 查询返回结果
     *
     * @param id
     * @return
     */
    MockDataBo queryResultById(Long id);
}

