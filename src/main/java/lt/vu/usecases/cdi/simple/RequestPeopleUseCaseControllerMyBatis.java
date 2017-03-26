package lt.vu.usecases.cdi.simple;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.dao.datamapper.UserMapper;
import lt.vu.dao.datamapper.UserSurveyMapper;
import lt.vu.dao.datamapper.SurveyMapper;
import lt.vu.entities.mybatis.User;
import lt.vu.entities.mybatis.UserSurvey;
import lt.vu.entities.mybatis.Survey;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model // tas pats kaip: @Named ir @RequestScoped
@Slf4j
public class RequestPeopleUseCaseControllerMyBatis {
    @Getter
    private User user = new User();
    @Getter
    private Survey survey = new Survey();

    @Inject
    private UserMapper userMapper;
    @Inject
    private SurveyMapper surveyMapper;
    @Inject
    private UserSurveyMapper userSurveyMapper;

    public List<User> getAllPeople() {
        List<User> people = userMapper.selectAll();
        for (User p: people
             ) {
            System.out.println("I am " + p.getFirstName());
            for (Survey s: p.getSurveyList()
                 ) {
                System.out.println("Also, I have survey: " + s.getName());
                // comme
            }
        }
        return people;
    }
    @Transactional
    public void createCourseStudent() {
        surveyMapper.insert(survey);
        userMapper.insert(user);
        UserSurvey userSurvey = new UserSurvey();
        userSurvey.setSurveyId(survey.getId());
        userSurvey.setUserId(user.getId());
        userSurveyMapper.insert(userSurvey);
        log.info("Maybe OK...");
    }

    @Transactional
    public void createUser() {
//        checkEmail(user.getEmail());
        userMapper.insert(user);
    }

    @Transactional
    public void removeUserByEmail(String email) {
        List<User> people = userMapper.selectAll();
        for (User user:
             people) {
            if (user.getEmail().equals(email)) {
                userMapper.deleteByPrimaryKey(user.getId());
                break;
            }
        }
    }

    private void checkEmail(String email) throws Exception {
        if (!email.contains("@")) {
            throw new Exception();
        }
    }
}
