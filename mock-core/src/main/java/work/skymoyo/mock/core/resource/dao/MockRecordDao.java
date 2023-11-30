package work.skymoyo.mock.core.resource.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import work.skymoyo.mock.core.resource.entity.MockRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * mock记录(MockRecord)表数据库访问层
 *
 * @author makejava
 * @since 2023-11-30 23:24:43
 */
@Mapper
@Repository
public interface MockRecordDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MockRecord queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MockRecord> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param mockRecord 实例对象
     * @return 对象列表
     */
    List<MockRecord> queryAll(MockRecord mockRecord);

    /**
     * 新增数据
     *
     * @param mockRecord 实例对象
     * @return 影响行数
     */
    int insert(MockRecord mockRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MockRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MockRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MockRecord> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MockRecord> entities);

    /**
     * 修改数据
     *
     * @param mockRecord 实例对象
     * @return 影响行数
     */
    int update(MockRecord mockRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

