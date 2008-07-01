package net.sf.regadb.ui.framework.forms.fields;

import net.sf.witty.wt.SignalListener;
import net.sf.witty.wt.WContainerWidget;
import net.sf.witty.wt.WEmptyEvent;
import net.sf.witty.wt.WInteractWidget;
import net.sf.witty.wt.WText;
import net.sf.witty.wt.WWidget;
import net.sf.witty.wt.i8n.WMessage;
import net.sf.witty.wt.validation.WValidator;
import net.sf.witty.wt.validation.WValidatorState;

public abstract class FormField extends WContainerWidget implements IFormField
{
    private WText _fieldView;
    private boolean _unique=false;
    
    public void initViewWidget()
    {
        _fieldView = new WText();
        addWidget(_fieldView);
    }
    
    public WInteractWidget getViewWidget()
    {
        return _fieldView;
    }
    
    public boolean isMandatory()
    {
        if(getFormWidget()==null)
        {
            return false;
        }
        else
        {
            WValidator validator = getFormWidget().validator();
            if(validator==null)
                return false;
            else
                return validator.isMandatory();
        }
    }
    
    public boolean validate()
    {
        boolean valid=true;

        if(getFormWidget().validator()!=null)
        {
            valid = getFormWidget().validator().validate(getFormText(), null) == WValidatorState.Valid;
        }
        
        if(isUnique()){
            valid = checkUniqueness();
        }
        return valid;
    }
    
    public void setValidator(WValidator validator)
    {
        getFormWidget().setValidator(validator);
    }
    

    public void setMandatory(boolean mandatory)
    {
        if(getFormWidget()!=null && getFormWidget().validator()==null)
        {
            getFormWidget().setValidator(new WValidator());
        }
        if(getFormWidget()!=null)
        {
            getFormWidget().validator().setMandatory(mandatory);
        }
    }
    
    public String text()
    {
        return getFormWidget()!=null?getFormText():getViewMessage().value();
    }
    
    public void setText(String text)
    {
        if(text==null)
        {
        text = "";    
        }
        
        if(getFormWidget()!=null)
            {
                setFormText(text);
            }
        else
            {
                setViewMessage(lt(text));
            }
    }
    
    protected void setViewMessage(WMessage message)
    {
    	_fieldView.setText(message);
    }
    
    protected WMessage getViewMessage()
    {
    	return _fieldView.text();
    }
    
    public WWidget getWidget()
    {
        return this;
    }
    
    public void setConfirmAction(SignalListener<WEmptyEvent> se) {
        if(getFormWidget()!=null) {
        getFormWidget().enterPressed.removeAllListeners();
        if(se!=null)
            getFormWidget().enterPressed.addListener(se);
        }
    }
    
    public boolean isUnique(){
        return _unique;
    }
    
    public void setUnique(boolean unique){
        _unique = unique;
    }
    
    public boolean checkUniqueness(){
        return false;
    }
}