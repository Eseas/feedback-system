package lt.vu.entities.mybatis;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

//    rankomis:
    private List<Survey> surveyList;
}