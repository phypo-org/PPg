package com.phipo.GLib;

import org.w3c.dom.*;

import java.util.*;
import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.geom.*;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;

import javax.swing.*;


//*************************************************
// Contient les caracteristiques commune a chaque type d'entite

public class PrototypeWeapon extends PrototypeXmlLoader{
		
		public static final HashMap<String,PrototypeWeapon> sHashPrototypeWeapon = new HashMap<String,PrototypeWeapon>();
		
		public static PrototypeWeapon GetPrototypeWeapon( String pName ){
				return sHashPrototypeWeapon.get( pName );
		}

		public boolean isMobil() { return true; }

		String    cName;
		public String    getName() { return cName; }

		ImageIcon cImgImpact=null;
		ImageIcon getImgImpact() { return cImgImpact; }

		double    cWidth=0;
		double getWidth() { return cWidth;}

		double    cHeight=0;
		double getHeight() { return cHeight; }

		
		public double cRange=0;
		public double cFireTime=0; // temps entre chaque tir
		public double cArmTime=0; // temps avant le premier tir !
		public double cReArmTime=0; // temps entre chargeur ?
		public double cDamage=0;
		public double cEffectZone=0;

		public double cAttenuation=0;
		public double cWeight=0;

		World.MiddleType   cMiddleUse; // le milieu


		//------------------------------------------------		
		public PrototypeWeapon( String pName ){
				cName = pName;
		}		
		//------------------------------------------------
		public String toString() {
				return cName 
						+ " Width:" + cWidth
						+ " Height:" + cHeight
						+ " range:" + cRange
            + " damage:" + cDamage
						+ " time:" + cFireTime
						+ " effectZone:" + cEffectZone
            + " " + cMiddleUse.getName();
		}
		//------------------
		public String getMyTag() { return XmlLoader.TAG_XML_WEAPON; }
		public PrototypeXmlLoader findByName( String pName )    { return sHashPrototypeWeapon.get( pName );		}
		
		public PrototypeXmlLoader create( String pName)  { 
  			PrototypeWeapon lNew = new PrototypeWeapon( pName ); 
				sHashPrototypeWeapon.put( pName, lNew );
				return lNew;
		}
		
		public void doInherit( PrototypeXmlLoader pMother ){
				PrototypeWeapon lMother = (PrototypeWeapon)pMother;
				//				cImg = lMother.cImg;
				cWidth    = lMother.cWidth;
				cHeight   = lMother.cHeight;	 
				cFireTime = lMother.cFireTime;
				cArmTime  = lMother.cArmTime;
				cReArmTime  = lMother.cReArmTime;
				cRange    = lMother.cRange;
				cDamage   = lMother.cDamage;
				cEffectZone= lMother.cEffectZone ;
				// ... FAIRE LE RESTE 
		}
		//------------------
		public boolean loadXmlNode(  Node pMotherNode, Node pNode, XmlLoader pXmlLoader ){

				System.out.println( "\t\tloadXml for PrototypeWeapon " + cName);

				//------------
				String lFile = XmlLoader.GetAttributeVal( null, pNode, XmlLoader.TAG_XML_FILE );
				if( lFile != null){
						// Charger l'image ???
				}
				//------------
				 cHeight   = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_HEIGHT, cHeight  );
				 cWidth    = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_WIDTH,  cWidth );
				 cWeight   = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_WEIGHT, cWeight  );
				 cRange    = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_RANGE,  cRange  );
				 cDamage   = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_DAMAGE, cDamage );
				 cFireTime = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_FIRE_TIME,  cFireTime );
				 cFireTiem*=1000; // conversion en milliseconde
				 cArmTime  = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_ARM_TIME,   cArmTime );				 
				 cArmTime*=1000;
				 cReArmTime= XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_REARM_TIME, cReArmTime  );
				 cReArmTime*=1000
				 //				  = XmlLoader.GetAttributeDouble( null, pNode,  XmlLoader.TAG_XML_,   );
				//------------
				
				return true;
		}
		//------------------
		public boolean loadXmlSubNode(Node  lNode ) {
				System.out.println( "\t\tloadXmlSubNode for PrototypeWeapon " + cName);

				return true;
		}

		//------------------------------------------------
};
//*************************************************
