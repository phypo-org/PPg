package org.phypo.PPg.PPgUtils;

//***********************************
public interface SimpleNotifierObserver{
    public void notify( SimpleNotifierSubject iObj );
    public <PARAM> void notify( SimpleNotifierSubject iObj, PARAM iParam  );
}
//***********************************
