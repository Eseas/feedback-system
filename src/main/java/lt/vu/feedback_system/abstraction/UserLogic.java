package lt.vu.feedback_system.abstraction;

import lt.vu.feedback_system.entities.PotentialUser;

public interface UserLogic {
    void createPotentialUser(PotentialUser potentialUser);
    void removePotentialUser(PotentialUser potentialUser);
    boolean isEmailFree(String email);
    boolean isEmailFormatValid(String email);
}
