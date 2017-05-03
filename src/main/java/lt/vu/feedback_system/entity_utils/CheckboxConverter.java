package lt.vu.feedback_system.entity_utils;

import lt.vu.feedback_system.dao.CheckboxDAO;
import lt.vu.feedback_system.dao.SelectedCheckboxDAO;
import lt.vu.feedback_system.entities.answers.SelectedCheckbox;
import lt.vu.feedback_system.entities.questions.Checkbox;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by kazim on 2017-05-02.
 */
@Named
@ApplicationScoped
public class CheckboxConverter implements Converter{
    @Inject
    private CheckboxDAO checkboxDAO;
    @Inject
    private SelectedCheckboxDAO selectedCheckboxDAO;
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        SelectedCheckbox selectedCheckbox = new SelectedCheckbox();
        selectedCheckbox.setCheckbox(checkboxDAO.getCheckboxById(Integer.parseInt(s)));
        return selectedCheckbox;

    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
//        return ((Checkbox) o).getId().toString();
//        return null;
        return ((SelectedCheckbox) o).getCheckbox().getId().toString();

//        String returnString = String.valueOf(((Checkbox) o).getId()).toString();
//        return returnString;
    }

//    @Override
//    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
////        SelectedCheckbox selectedCheckbox = new SelectedCheckbox();
////        ATKREIPTI DEMESI I ID TUREJIMA
//        return selectedCheckboxDAO.getSelectedCheckboxById(Integer.parseInt(s));
//    }
//
//    @Override
//    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
////        return ((Checkbox) o).getId().toString();
////        return null;
////        PASIMOVIAU ANT ID... DAR KARTA
//        String returnString = String.valueOf(((SelectedCheckbox) o).getId()).toString();
//        return returnString;
//    }
}
