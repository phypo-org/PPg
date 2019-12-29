package org.phypo.PPg.PPgUtils;


import java.util.*;
import java.io.*;


//***********************************
public interface SimpleNotifierObserver{
    public void notify( SimpleNotifierSubject iObj );
    public <PARAM> void notify( SimpleNotifierSubject iObj, PARAM iParam  );
}
//***********************************
