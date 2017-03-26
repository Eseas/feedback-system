package lt.vu.dao.datamapper;

import lt.vu.entities.mybatis.User;
import org.mybatis.cdi.Mapper;

import java.util.List;

@Mapper // rankomis
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);
    int insert(User record);
    User selectByPrimaryKey(Integer id);
    List<User> selectAll();
    int updateByPrimaryKey(User record);
}