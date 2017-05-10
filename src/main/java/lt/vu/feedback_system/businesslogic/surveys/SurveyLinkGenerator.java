package lt.vu.feedback_system.businesslogic.surveys;


import lt.vu.feedback_system.businesslogic.generate.MD5Generator;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

@Specializes
@ApplicationScoped
public class SurveyLinkGenerator extends MD5Generator {
    @Override
    public String hash(String data) {
        return super.hash(data).substring(0, 10);
    }
}
