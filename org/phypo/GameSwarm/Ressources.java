
package org.phypo.GameSwarm;



import java.awt.geom.*;
import java.awt.*;
import java.awt.geom.Point2D.*;
import java.awt.event.*;

import org.phypo.PPg.PPgUtils.*;

import org.phypo.PPg.PPgImg.*;

import org.phypo.PPgGame.PPgGame.*;
import org.phypo.PPgGame.PPgSFX.*;
import org.phypo.PPg.PPgMath.*;



//*************************************************

public class Ressources{


    //		public static PPgImgIcon sSingleShipImg    = null;
    public static PPgImg sMyShipImg    = null;
    public static PPgImg sSingleShipImg    = null;

    public static PPgImg sContainerImg     = null;
    public static PPgImg sAsteroidImg11    = null;
    public static PPgImg sAsteroidImg12    = null;
    public static PPgImg sAsteroidImg2     = null;
    public static PPgImg sAsteroidBigImg     = null;


    public static PPgImg sShipLaserImg     = null;
    public static PPgImg sShipMissileImg   = null;
    public static PPgImg sShipFieldImg     = null;

    public static PPgImg sMyShipLaserImg   = null;
    public static PPgImg sMyShipMissileImg = null;
    public static PPgImg sMyShipFieldImg   = null;


    public static PPgImg sShipBigImg1     = null;
    public static PPgImg sShipBigImg2     = null;
    public static PPgImg sShipBigImg3     = null;
    public static PPgImg sShipBigImg4     = null;
    public static PPgImg sShipBigImg5     = null;

    public static PPgImg sShipMiddleImg1     = null;
    public static PPgImg sShipMiddleImg2    = null;
    public static PPgImg sShipMiddleImg3    = null;


		

    public static void Load() {

        sShipLaserImg      = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                              "SHIP_LASER", null ).getImage() );
        sShipMissileImg    = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                              "SHIP_MISSILE", null ).getImage() );
        sShipFieldImg      = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                              "SHIP_FIELD", null ).getImage() );
        sMyShipLaserImg    = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                              "MYSHIP_LASER", null ).getImage() );
        sMyShipMissileImg  = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                              "MYSHIP_MISSILE", null ).getImage() );
        sMyShipFieldImg    = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                              "MYSHIP_FIELD", null ).getImage() );
        ////		
        sMyShipImg     = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                              "MYSHIP", null ).getImage() );
        
        sSingleShipImg     = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                              "SINGLESHIP", null ).getImage() );

        sAsteroidBigImg    = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                              "ASTEROID_BIG", null ).getImage() );

        sShipBigImg1    = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                           "BIGSHIP1", null ).getImage() );
        sShipBigImg2    = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                           "BIGSHIP2", null ).getImage() );
        sShipBigImg3    = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                           "BIGSHIP3", null ).getImage() );
        sShipBigImg4    = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                           "BIGSHIP4", null ).getImage() );
        sShipBigImg5    = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                           "BIGSHIP5", null ).getImage() );

        sShipMiddleImg1    = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                              "MIDDLESHIP1", null ).getImage() );
        sShipMiddleImg2    = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                              "MIDDLESHIP2", null ).getImage() );
        sShipMiddleImg3    = new PPgImg( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                              "MIDDLESHIP3", null ).getImage() );


        //sSingleShipImg     = new PPgImgIcon( World.sTheIniFile.get( "LEVEL1",  "SHIP1" ));


        sContainerImg      = new PPgAnimImg( PPgIniFile.ReadIcon( World.sTheIniFile,    "LEVEL1",
                                                                  "CONTAINER", null ).getImage(),
                                             9, 4, 0.5 );
        
        sAsteroidImg11     = new PPgAnimLinear( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",                                                                     "ASTEROID11", null ).getImage(),                                                
                                                8, 8, 60, 2 );
        
        sAsteroidImg12     = new PPgAnimLinear( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                                     "ASTEROID12", null ).getImage(),
                                                8, 8, 60, 1.5 );

        sAsteroidImg2      = new PPgAnimLinear( PPgIniFile.ReadIcon( World.sTheIniFile, "LEVEL1",
                                                                     "ASTEROID2", null ).getImage(),
                                                21, 7, 143, 5 );



    }

};

//*************************************************
