package org.phypo.D4K;

import java.awt.*;
import javax.swing.JComponent;

import org.phypo.PPg.PPgWin.*;
//**********************************************************

interface InterfaceCanvas{
		
    public void        repaintCanvas();
    public JComponent  getComponent();
    public void        setPreferredSize(Dimension preferredSize);
    public Dimension   getRealSize();    
    public void        setFrame( FrameCanvasCnx pFrameAppli );
}

//**********************************************************
