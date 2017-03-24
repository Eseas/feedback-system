package lt.vu.usecases.cdi.simple;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.dao.datamapper.PersonMapper;
import lt.vu.dao.datamapper.PersonSurveyMapper;
import lt.vu.dao.datamapper.SurveyMapper;
import lt.vu.entities.mybatis.Person;
import lt.vu.entities.mybatis.PersonSurvey;
import lt.vu.entities.mybatis.Survey;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model // tas pats kaip: @Named ir @RequestScoped
@Slf4j
public class RequestPeopleUseCaseControllerMyBatis {
    @Getter
    private Person person = new Person();
    @Getter
    private Survey survey = new Survey();

    @Inject
    private PersonMapper personMapper;
    @Inject
    private SurveyMapper surveyMapper;
    @Inject
    private PersonSurveyMapper personSurveyMapper;

    public List<Person> getAllPeople() {
        List<Person> people = personMapper.selectAll();
        for (Person p: people
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
        personMapper.insert(person);
        PersonSurvey personSurvey = new PersonSurvey();
        personSurvey.setSurveyId(survey.getId());
        personSurvey.setPersonId(person.getId());
        personSurveyMapper.insert(personSurvey);
        log.info("Maybe OK...");
    }

    @Transactional
    public void createPerson() {
//        checkEmail(person.getEmail());
        personMapper.insert(person);
    }

    @Transactional
    public void removePersonByEmail(String email) {
        List<Person> people = personMapper.selectAll();
        for (Person person:
             people) {
            if (person.getEmail().equals(email)) {
                personMapper.deleteByPrimaryKey(person.getId());
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
