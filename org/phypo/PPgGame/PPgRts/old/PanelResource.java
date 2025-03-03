package org.phypo.PPg.PPgRts;



import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.Robot;


import java.util.*;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgUtils.*;


//***********************************
public class PanelResource extends PanelBox {


		GamerHuman        cGamer = null;

		//------------------------------------------ 
		public PanelResource( GamerHuman pGamer, 
												 int pX, int pY, int pWidth, int pHeight, boolean pVertical){
				
				super( pGamer, pX, pY, pWidth, pHeight);
				
				setRender( new RenderResource( pGamer, this, pVertical ) );
		}
}
//***********************************
