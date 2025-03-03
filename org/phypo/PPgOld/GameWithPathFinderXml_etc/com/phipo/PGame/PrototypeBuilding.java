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

public class PrototypeBuilding extends PrototypeUnit{


		public 		PrototypeBuilding(String pName ){
				super( pName );
		}
		public String getMyTag() { return XmlLoader.TAG_XML_BUILD_PROTO; }

		public boolean isMobil() { return false;}

		public PrototypeXmlLoader create( String pName)  { 
				PrototypeUnit lNew = new PrototypeBuilding( pName ); 
				sHashPrototypeUnit.put( pName, lNew );
				return lNew;
		}

};

//*************************************************
