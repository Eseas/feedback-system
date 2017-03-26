package lt.vu.dao.datamapper;

import lt.vu.entities.mybatis.Person;
import org.mybatis.cdi.Mapper;

import java.util.List;

@Mapper // rankomis
public interface PersonMapper {

    int deleteByPrimaryKey(Integer id);
    int insert(Person record);
    Person selectByPrimaryKey(Integer id);
    List<Person> selectAll();
    int updateByPrimaryKey(Person record);
}