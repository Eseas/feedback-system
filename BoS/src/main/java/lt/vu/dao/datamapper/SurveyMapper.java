package lt.vu.dao.datamapper;

import lt.vu.entities.mybatis.Survey;
import org.mybatis.cdi.Mapper;

import java.util.List;


@Mapper  // RANKOMIS. NEPAMIRSTI
public interface SurveyMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(Survey record);
    Survey selectByPrimaryKey(Integer id);
    List<Survey> selectAll();
    int updateByPrimaryKey(Survey record);
}