package work.skymoyo.test.resource.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import work.skymoyo.mock.common.model.MockDataBo;

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
     * 查询返回结果
     *
     * @param id
     * @return
     */
    MockDataBo queryResultById(Long id);
}

