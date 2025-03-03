package org.phypo.D4K;


import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.JComponent;
import javax.swing.ImageIcon;

import org.phypo.PPg.PPgWin.*;

import org.phypo.PPg.PPgUtils.*;

/**
 ** Sous fenetre permettant de voir les application et les connexions 
 ** sous forme graphique.
 ** 
 */

//***********************************
final public class CanvasMachines extends PPgCanvas
    implements MouseMotionListener, MouseListener, ActionListener, KeyListener,
	       InterfaceCanvas {
	
    FrameCanvasCnx        cFrame;

    int cMemX, cMemY, cMemX2, cMemY2;
		
    final int NOTHING=0;
    final int DRAG_SELECTION=1;
    final int DRAG_RECT_SELECTION=2;
    int cState = NOTHING;

    static ImageIcon sBackGroundImageScaled = null;

    public void setFrame( FrameCanvasCnx iFrame ){ cFrame = iFrame;}
 
    //--------------------------
    // implementation de InterfaceCanvasAppli

    public JComponent  getComponent() { return this; }


    //--------------------------
    public CanvasMachines( FrameCanvasCnx pFrame) { 		
	super();

	setFocusable(true);
				
	cFrame = pFrame;

	addMouseListener( this );		
	addMouseMotionListener( this );		
	addKeyListener( this );		 // phipo 2008/01/28
    }
    //-------------------------- 
    public void paint( Graphics pG ){

	Graphics2D  lGraf = (Graphics2D) pG;
	


	// Pour des pb avec la l'image 
	if( SurvParam.GetGeneralScale() > 1.0)
	    lGraf.scale( SurvParam.GetGeneralScale(), SurvParam.GetGeneralScale() );

	// UTILISER LA TAILLE DU JVIEWPORT !!!! car pb sur l'image
	if( SurvParam.sBackGroundImage != null ){	
	    if( sBackGroundImageScaled == null
		|| (((getWidth() > sBackGroundImageScaled.getIconWidth() 
		      || getHeight() > sBackGroundImageScaled.getIconHeight())&&  SurvParam.GetGeneralScale() < 1.0)))
		{
		    float lRapportW = ((float)getWidth())   / ((float)SurvParam.sBackGroundImage.getIconWidth());
		    float lRapportH = ((float)getHeight()) / ((float)SurvParam.sBackGroundImage.getIconHeight());
										
		    float lRapport = lRapportH;
		    if( lRapportW > lRapportH )
			lRapport = lRapportW;
										
		    Image lTmpImg  = SurvParam.sBackGroundImage.getImage().getScaledInstance((int)(SurvParam.sBackGroundImage.getIconWidth()*lRapport),
											     (int)(SurvParam.sBackGroundImage.getIconHeight()*lRapport),
											     Image.SCALE_SMOOTH );
		    sBackGroundImageScaled = new ImageIcon( lTmpImg );
		}

	    sBackGroundImageScaled.paintIcon( PPgAppli.GetAppli(), lGraf, 0, 0 );	
	}		else {
	    if( PPgAppli.sGlobalBackground != null  ) {
		Color lMemColor = lGraf.getColor();
		lGraf.setColor( PPgAppli.sGlobalBackground  );
		lGraf.fillRect( 0, 0, 40000, 40000  );			 
		lGraf.setColor( lMemColor );
	    }
	}

	lGraf.scale( SurvParam.GetGeneralScale(), SurvParam.GetGeneralScale() );

	
	//						SurvParam.sBackGroundImage.paintIcon( PPAppli.GetAppli(), g, 0, 0 );			
			 

	super.paintComponent(lGraf);
						

	// On fait l affichage en deux fois 
	// pour que les connections soit dessous

	Enumeration lEnum = cFrame.getSpriteTable().elements();
	while( lEnum.hasMoreElements() ){						
	    Sprite lSprite = (Sprite)lEnum.nextElement();
	    if( lSprite.getType() == Sprite.sTypeCnx )
		lSprite.draw( lGraf );			
	}

	lEnum = cFrame.getSpriteTable().elements();
	while( lEnum.hasMoreElements() ){
	    Sprite lSprite = (Sprite)lEnum.nextElement();
	    if( lSprite.getType() == Sprite.sTypeAppli )
		lSprite.draw( lGraf);			
	}


	drawSelection( lGraf );

	if( cState == DRAG_RECT_SELECTION
	    && ( cMemX != cMemX2 && cMemY != cMemY2) ){

	    lGraf.setColor( SurvParam.sColorSelection );

	    int xmin = Math.min( cMemX,  cMemX2 );
	    int xmax = Math.max( cMemX,  cMemX2 );
	    int ymin = Math.min( cMemY,  cMemY2 );
	    int ymax = Math.max( cMemY,  cMemY2 );

	    lGraf.drawRect( xmin, ymin, xmax-xmin, ymax-ymin );
	}
    }																									
    //-------------------------- 
    void drawSelection(  Graphics2D pGraf ){
	//				lGraf.scale( SurvParam.GetGeneralScale(), SurvParam.GetGeneralScale() );

	pGraf.setColor( SurvParam.sColorSelection );
	for( Sprite lSel : cFrame.getSelection() ){
	    pGraf.drawRect( (int)lSel.getX()-5,(int)lSel.getY()-5, (int)lSel.getWidth()+10,(int)lSel.getHeight()+10);
	    pGraf.drawRect( (int)lSel.getX()-6,(int)lSel.getY()-6, (int)lSel.getWidth()+12,(int)lSel.getHeight()+12);
	    //						lGraf.drawRect( (int)lSel.getX()-7,(int)lSel.getY()-7, (int)lSel.getWidth()+14,(int)lSel.getHeight()+14);
	}
    }
    //-------------------------- 
    // Retrouve l'objet sur lequel on a clique
		 
    Sprite getClickedSprite(  MouseEvent p_e )		{	
				
	// On explore tout les sprite et on garde le dernier (pour avoir celui du dessus)
	Sprite lSelectSprite=null;
				
	Enumeration lEnum = cFrame.getDataAppli().getSpriteTable().elements();
	while( lEnum.hasMoreElements() ){						
	    Sprite lCurSprite = (Sprite)lEnum.nextElement();
	    //				for( Sprite lCurSprite : cVectSprite.values() ){
						
	    int lX =(int)( ((double)p_e.getX())/SurvParam.GetGeneralScale() );
	    int lY =(int)( ((double)p_e.getY())/SurvParam.GetGeneralScale() );

	    if( lCurSprite.contains( lX, lY ) ){								
		lSelectSprite = lCurSprite;
	    }						
	}
				
	return lSelectSprite;
    }

    //---------------------------------------------------
    public	void update( Graphics g ){
	//			System.out.println( "Update" );
    }
    //-------------------------- 
    public void mousePressed( MouseEvent pEv ) {

	int lCtrl  = InputEvent.CTRL_DOWN_MASK; 
	int lShift = InputEvent.SHIFT_DOWN_MASK;
	int lAlt   = InputEvent.ALT_DOWN_MASK;
	boolean lFlagCtrl  = false;
	boolean lFlagShift = false;
				
	if( (pEv.getModifiersEx() &  lCtrl) == lCtrl){
	    lFlagCtrl = true;
	    //					 						System.out.println( "CTRL" );
	}
	if( (pEv.getModifiersEx() & lShift) == lShift ){
	    lFlagShift = true;
	    //												System.out.println( "SHIFT" );
	}
	//				if( (pEv.getModifiersEx() & lAlt) == lAlt ){
	///						System.out.println( "ALT" );
	//				}
						

	int lX =(int)( ((double)pEv.getX())/SurvParam.GetGeneralScale() );
	int lY =(int)( ((double)pEv.getY())/SurvParam.GetGeneralScale() );



	cMemX = cMemX2 = lX;
	cMemY = cMemY2 = lY;


	Sprite lSprite=getClickedSprite(pEv);

				
	//================
	if( SwingUtilities.isLeftMouseButton( pEv ) == true && pEv.getClickCount() == 1){
												
	    if( lSprite!= null 
		&& lSprite.getType() == Sprite.sTypeAppli ){

		if( lFlagShift == false && lFlagCtrl == false ) {										
		    cFrame.getDataAppli().clearSelection();
		}										
								
		if( cFrame.getDataAppli().isInSelection( lSprite )) {
										
		    if( lFlagCtrl) {
			cFrame.getDataAppli().removeFromSelection( lSprite );
		    }
		}  else {
		    cFrame.getDataAppli().addSelection(lSprite);
		}
		cState = DRAG_SELECTION;
	    }
	    else {										
		cState = DRAG_RECT_SELECTION;				
	    }
	}
	else 
	    //==============
	    if( (SwingUtilities.isMiddleMouseButton( pEv ) == true 
		 || SwingUtilities.isLeftMouseButton( pEv ) == true )
		&& pEv.getClickCount() == 2 ) {						

		if( lSprite!= null){											
		    FrameProps lFrame = new FrameProps( lSprite );
		    PPgAppli.TheAppli.addChild(lFrame);	
		}
	    }
	    else
		//==============
		if( SwingUtilities.isRightMouseButton( pEv ) == true 
		    && pEv.getClickCount() == 1 ) {
						
		    if( lSprite != null){											
								
			JPopupMenu lPopmenu = new JPopupMenu();
			if( lSprite.initPopup( lPopmenu ) != null){					
										
			    lPopmenu.show( pEv.getComponent(),
					   pEv.getX(),
					   pEv.getY() );												
			}										
		    }					
		}
	//==============
				
	cFrame.forceRepaint();
    }
    //-------------------------- 
    public void mouseReleased( MouseEvent pEv ) {
				
	switch( cState ){
	case DRAG_SELECTION:
	    cState = NOTHING;				
	    cFrame.forceRepaint();
	    break;
						
	case DRAG_RECT_SELECTION:						

	    int xmin = Math.min( cMemX,  cMemX2 );
	    int xmax = Math.max( cMemX,  cMemX2 );
	    int ymin = Math.min( cMemY,  cMemY2 );
	    int ymax = Math.max( cMemY,  cMemY2 );

	    Rectangle lRect = new Rectangle( xmin, ymin, xmax-xmin, ymax-ymin );


	    int lCtrl  = InputEvent.CTRL_DOWN_MASK; 
	    int lShift = InputEvent.SHIFT_DOWN_MASK;
	    int lAlt   = InputEvent.ALT_DOWN_MASK;
	    boolean lFlagCtrl  = false;
	    boolean lFlagShift = false;
				
																						

	    if( (pEv.getModifiersEx() &  lCtrl) == lCtrl){
		lFlagCtrl = true;
		///						System.out.println( "CTRL" );
	    }
	    if( (pEv.getModifiersEx() & lShift) == lShift ){
		lFlagShift = true;
		///						System.out.println( "SHIFT" );
	    }


	    if( lFlagShift == false && lFlagCtrl == false ) {
		cFrame.getDataAppli().clearSelection();
	    }										

	    for( Sprite lSprite : cFrame.getDataAppli().getSpriteTable().values() ){
		if(	lSprite.getType() == Sprite.sTypeAppli
			&& lRect.contains( lSprite ) ){

		    if( cFrame.getDataAppli().isInSelection( lSprite ) ) {
			if( lFlagCtrl) 
			    cFrame.getDataAppli().removeFromSelection( lSprite );
		    } 																															
		    else 
			cFrame.getDataAppli().	addSelection(lSprite);	
		}
	    }		
						
	    cState = NOTHING;				
	    cFrame.forceRepaint();
	    break;
						
	default:;
	}

	cState = NOTHING;				
    }		
    //-------------------------- 
    public void mouseEntered( MouseEvent pEv ) {
    }		
    //-------------------------- 
    public void mouseExited( MouseEvent pEv ) {
    }
    //-------------------------- 
    public void actionPerformed( ActionEvent pEvv ){		
				
				
    }
    // -----------------------------
    public void mouseClicked( MouseEvent pEv ) {
    }
    //-------------------------------------
    public void mouseDragged( MouseEvent pEv ){
				
	if( SwingUtilities.isLeftMouseButton( pEv ) == true ){


	    int lX =(int)( ((double)pEv.getX())/SurvParam.GetGeneralScale() );
	    int lY =(int)( ((double)pEv.getY())/SurvParam.GetGeneralScale() );



	    if( cState == DRAG_RECT_SELECTION  ){


		cMemX2 = lX;
		cMemY2 = lY;		
								
		cFrame.forceRepaint();
	    }
	    else
		if( cState == DRAG_SELECTION 
		    && cFrame.getDataAppli().getSelection() != null ){
								
		    cFrame.getDataAppli().moveSelection( lX-cMemX, lY-cMemY );
								
		    cMemX = lX;
		    cMemY = lY;
		    cFrame.forceRepaint();
		}				
	}
    }
    //-------------------------------------
    public void mouseMoved( MouseEvent pEv ){
				
    }
    //-------------------------------------
    public Dimension getRealSize(){
				
				
	double l_max_x=0;
	double l_max_y=0;
				
	Enumeration lEnum = cFrame.getDataAppli().getSpriteTable().elements();
	while( lEnum.hasMoreElements() ){
	    Sprite l_sprite =(Sprite)lEnum.nextElement();
	    double l_x = l_sprite.getX()+l_sprite.getWidth();
	    double l_y = l_sprite.getY()+l_sprite.getHeight();
	    if( l_x > l_max_x ) l_max_x = l_x;
	    if( l_y > l_max_y ) l_max_y = l_y;
	}
				
	//				System.out.println( "max x:" + l_max_x + " max_y:" + l_max_y );
	Dimension l_dim = new Dimension( (int)l_max_x, (int)l_max_y );
	return l_dim;
    }
		
    public void repaintCanvas()		{
	repaint();
    }

    //------------------------------------------
    //------------------------------------------ 
    //------------------------------------------ 
    // Phio 2008/01/28

    public	void keyPressed( KeyEvent pEv ){

	int lKeyCode = pEv.getKeyCode();

	//				System.out.println( "KeyPressed:" + lKeyCode + " " +KeyEvent.getKeyText(lKeyCode) + " " + pEv.getKeyChar());
				
	switch( lKeyCode ){
	case KeyEvent.VK_DELETE :
	case KeyEvent.VK_BACK_SPACE :
	    cFrame.getDataAppli().deleteSelection();
	    actualize();
	    break;
	default: {
						
	    switch( pEv.getKeyChar() ) {
	    case '-' :
		SurvParam.SetGeneralScale( SurvParam.GetGeneralScale() * 0.9 );
		cFrame.forceRepaint();
		actualize();
		break;
								
	    case '+' :
		SurvParam.SetGeneralScale( SurvParam.GetGeneralScale() * 1.1);
		cFrame.forceRepaint();
		actualize();
		break;
								
	    case '=':
		SurvParam.SetGeneralScale( 1.0 );
		cFrame.forceRepaint();
		actualize();
		break;		
	    }
	}
						
	    //				case KeyEvent. :
	    //						break;
						
	}
    }
    //----------------------
    public	void keyReleased( KeyEvent e ){
	//				System.out.println( "KeyReleased" );
    }
    //----------------------
    public	void keyTyped( KeyEvent e ){
	//				System.out.println( "KeyTyped"  );
    }
}
//***********************************















