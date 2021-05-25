package org.phypo.PPg.PPgWin;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;


import java.awt.geom.*;


import org.phypo.PPg.PPgUtils.*;


//*************************************************
// Il faudrait plusieurs style de fleches 
// et des pouvoir avoir deux extremitees

public class Arrow extends Rectangle2D.Double{
				
    double cArrowSize  = 15;
    double cArrowAngle = 20;
    double cArrowPos   = 0.700;
    double cArrowSlide = 10;

		

    double cSrcX   = 0;
    double cSrcY   = 0; 
    double cDestX  = 0;
    double cDestY  = 0;

		
    int cTx[] = new int[5];
    int cTy[] = new int[5];		
		
    double cPente = 0;
    double cDt    = 0;  // taille de la fleche en repere parametrique (entre 0 et 1 )
    double cDx    = 0;  // cette taille convertie en pixel
    double cDy    = 0;
		
    public Point2D.Double getPointOfArrow()     { return new Point2D.Double( cTx[0], cTy[0] ); }
    public Point2D.Double get1SideOfArrow()     { return new Point2D.Double( cTx[1], cTy[1] ); }
    public Point2D.Double getBaseOfArrow()      { return new Point2D.Double( cTx[2], cTy[2] ); }
    public Point2D.Double get2SideOfArrow()     { return new Point2D.Double( cTx[3], cTy[3] ); }



    public Point2D.Double getSizePointOfArrow() { return new Point2D.Double( cDx, cDy ); }




    public static double sArrowSize=15;
    public static double sArrowAngle=20;
    public static double sArrowPos=0.700;
    public static double sArrowSlide=10;
    static  public double GetDefaultArrowSlide() { return sArrowSlide; }

    //------------------------------------------------
    public static void InitDefault( PPgIniFile pIni, String pStrSection  ){
	sArrowSize  = pIni.getdouble(  pStrSection, "ArrowSize",  sArrowSize);
	sArrowAngle = pIni.getdouble(  pStrSection, "ArrowAngle", sArrowAngle);
	sArrowPos   = pIni.getdouble(  pStrSection, "ArrowPos",   sArrowPos);
	sArrowSlide = pIni.getdouble(  pStrSection, "ArrowSlide", sArrowSlide);
    }		
    //------------------------------------------------
    public Arrow(  double pSrcX, double pSrcY, double pDestX, double pDestY ){
	resetFromDefault();
	setArrowExtremity( pSrcX, pSrcY, pDestX, pDestY );
	computeArrow();
    }
    //------------------------------------------------
    public Arrow(  double pSrcX, double pSrcY, double pDestX, double pDestY, 
		   double pArrowSize, double pArrowAngle, double pArrowPos, double pArrowSlide ){

	resetFromDefault();
	setArrowExtremity( pSrcX, pSrcY, pDestX, pDestY );
	setArrowParameters(pArrowSize,pArrowAngle,pArrowPos,pArrowSlide);
	computeArrow();
    }
    //------------------------------------------------
    public void setArrowExtremity(  double pSrcX, double pSrcY, double pDestX, double pDestY ){
	cSrcX  = pSrcX;
	cSrcY  = pSrcY;
	cDestX = pDestX;
	cDestY = pDestY;				
    }
    //------------------------------------------------
    public void setArrowParameters( double pArrowSize, double pArrowAngle, double pArrowPos, double pArrowSlide ){
	cArrowSize  = pArrowSize;
	cArrowAngle = pArrowAngle;
	cArrowPos   = pArrowPos;
	cArrowSlide = pArrowSlide;
    }
    //------------------------------------------------
    public void resetFromDefault() {
	cArrowSize  = sArrowSize;
	cArrowAngle = sArrowAngle;
	cArrowPos   = sArrowPos;
	cArrowSlide = sArrowSlide;		 				
    }
    //------------------------------------------------
    public void computeArrow(){

	// On calcule les valeur de la droite en parametriques
	double bx = cSrcX;
	double ax = cDestX-cSrcX;
				
	double by = cSrcY;
	double ay = cDestY-cSrcY;
				
	// positionnement a pos_arrow pour la pointe de la fleche 
	double xf = ax * cArrowPos + bx;
	double yf = ay * cArrowPos + by;
				
				
	this.setRect(  xf-cArrowSize, 
		       yf-cArrowSize, 
		       cArrowSize*2, 
		       cArrowSize*2 );
				
	//======= ========= ========= = ======  

						 


	// On veut faire un fleche de n pixel, il faut d'abord calculer le deplacement
	// dx sur la droite parametrique, on va calculer la distance entre les deux points
	// qui vaut 1 par definition, et etablir le decalage pour les n pixel

	// On va caluler la pente de la courbe, via le cosinus et le sinus
	double dx = cDestX - cSrcX;
	double dy = cDestY - cSrcY;
	double dist = Math.sqrt(dx*dx + dy*dy);
	if( dist <1 && dist > -1 )
	    return;

	//				System.out.println( "========================================================" );
	//				System.out.println( "p0=( "+cSrcX+", "+cSrcY+" ) p1=( "+ cDestX + ", " + cSrcY +" )");
	//				System.out.println( "dx= " +dx +"\t dy= " +dy + " dist= " +dist  );
	//				System.out.println( "cos= " + dx/dist  + "\tsin= " +dy/dist    + "   => " + Math.sqrt(((dx/dist)*(dx/dist))+((dy/dist)*(dy/dist))));
	cPente = Math.acos(dx/dist);  // la pente en degre (inverse car origine des y inverse sur les ecrans)
	if( dy < 0 )
	    cPente = 2*Math.PI-cPente;

	//				System.out.println( "cPente = "+ (cPente/Math.PI)*180);
						
	double cDt = (cArrowSize*0.7) / dist; // decalage parametrique sur la droite pour n pixel

	// calcul de distance en x et y pour ce point ( l'arriere de la fleche)
	cDx = ax*cDt;
	cDy = ay*cDt;

	// Pour trouver le centre de la fleche
	///					pG.drawLine( (int)(xf-cDx/2), (int)(yf-cDy/2), (int)(xf+50-cDx/2), (int)(yf-cDy/2) );

				
	double lAngle = (cArrowAngle/180.0)*Math.PI;
				
	// On calcule les cotes de la fleche

	cTx[4] = cTx[0] = (int)xf;

	cTy[4] = cTy[0] = (int)yf;
	cTx[1] = (int)(xf-Math.cos( cPente+lAngle)*cArrowSize);
	cTy[1] = (int)(yf-Math.sin( cPente+lAngle)*cArrowSize);
	cTx[2] = (int)(xf-cDx);
	cTy[2] = (int)(yf-cDy);

	cTx[3] = (int)(xf - Math.cos( cPente-lAngle)*cArrowSize);
	cTy[3] = (int)(yf - Math.sin( cPente-lAngle)*cArrowSize);
    }
    //------------------------------------------------

    public void draw( Graphics pG, Color pColorArrow, Color pColorContour, float pSz ){				
								
	Graphics2D lG2d =  (Graphics2D) pG;
	//		Graphics lG2d =  (Graphics) pG;
	Object lMemRenderHint= 	lG2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
	lG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
	// Ligne entre les deux points
				
	if( pSz > 1.0f ){
	    lG2d.setStroke(new BasicStroke( pSz ));
	    lG2d.setColor( pColorContour );
						
	    lG2d.drawLine( (int)cSrcX, (int)cSrcY, (int)cDestX, (int)cDestY );
	    pSz = pSz -2.0f;
	    if( pSz < 1.0f )
		pSz = 1.0f;
	    lG2d.setStroke(new BasicStroke( pSz ));
	}
				
	lG2d.setColor( pColorArrow );
	lG2d.drawLine( (int)cSrcX, (int)cSrcY, (int)cDestX, (int)cDestY );

	lG2d.fillPolygon( cTx, cTy, 4 );
				
	if( pColorArrow != pColorContour ){
						
	    // Pour faire plus beau
	    lG2d.setColor( pColorContour );
	    lG2d.drawPolyline( cTx, cTy, 5 );
	}
	lG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,lMemRenderHint);
    }				
}

//*************************************************
