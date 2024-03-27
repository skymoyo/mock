package work.skymoyo.mock.core.resource.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import work.skymoyo.mock.core.admin.model.MockConfigVO;
import work.skymoyo.mock.core.resource.entity.MockConfig;

import java.util.List;

/**
 * (Config)表数据库访问层
 *
 * @author skymoyo
 * @since 2022-07-23 09:21:07
 */

@Mapper
@Repository
public interface MockConfigDao {
    /**
     * 通过route查询单条数据
     *
     * @param route 路由
     * @return 实例对象
     */
    MockConfig queryByRoute(@Param("route") String route);


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MockConfig queryById(Long id);


    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MockConfig> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param config 实例对象
     * @return 对象列表
     */
    List<MockConfig> queryAll(MockConfig config);

    /**
     * 新增数据
     *
     * @param config 实例对象
     * @return 影响行数
     */
    int insert(MockConfig config);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Config> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MockConfig> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Config> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MockConfig> entities);

    /**
     * 修改数据
     *
     * @param config 实例对象
     * @return 影响行数
     */
    int update(MockConfig config);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);


    MockConfigVO selectVO(long id);
}

