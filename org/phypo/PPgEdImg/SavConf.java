package org.phypo.PPgEdImg;


import java.util.*;
import java.awt.event.*;
import java.awt.Point;
import java.awt.geom.Point2D;


import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import java.io.*;
import java.util.*;

import org.w3c.dom.*;

import org.phypo.PPg.PPgUtils.*;



//*************************************************
class SavConf implements PPgInterfaceXmlNodeHandler{

		File cFileConf = new File("Conf.autosav");

		static SavConf sSavConf = new SavConf();

	//------------------------------------------------
		static public void AutoSav(){
				
				sSavConf.saveXml( sSavConf.cFileConf );
		}
		//------------------------------------------------		
		static void Load(){

				PPgXml lXml = new PPgXml();

				lXml.processFile( sSavConf.cFileConf, sSavConf );
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------

		public final static String TAG_XML_DIALOG_LAYERS   =  "DialogLayers";
		public final static String TAG_XML_DIALOG_COLORS   =  "DialogColors";
		public final static String TAG_XML_DIALOG_HISTO    =  "DialogHisto";



		 boolean saveXml( File pFile ) {
				
				System.out.println( "saveXml " + pFile.getPath() );

				PPgXml lXml = new PPgXml();
				Document lDoc = lXml.getNewDoc();
				
				//===================
				Element lEltConf = lDoc.createElement( PPgXmlTag.TAG_XML_CONFIG );
				lDoc.appendChild( lEltConf );
				

				//===================

				if( DialogLayers.sTheDialog != null) {
						Element lEltDiagLayers = lDoc.createElement( TAG_XML_DIALOG_LAYERS );
						lEltConf.appendChild( lEltDiagLayers );
						Point lPt = DialogLayers.sTheDialog.getLocation();
						lEltDiagLayers.setAttribute( PPgXmlTag.TAG_XML_POSITION, "" + lPt.x +","+lPt.y );
				}

				//===================
				if( DialogColor.sTheDialog != null ){

						Element lEltDiagColors = lDoc.createElement( TAG_XML_DIALOG_COLORS );
						lEltConf.appendChild( lEltDiagColors );
						Point lPt = DialogColor.sTheDialog.getLocation();
						lEltDiagColors.setAttribute( PPgXmlTag.TAG_XML_POSITION, "" + lPt.x +","+lPt.y );
				}
			 
				//===================
				if( DialogHisto.sTheDialog != null ){
						Element lEltDiagHisto = lDoc.createElement( TAG_XML_DIALOG_HISTO );
						lEltConf.appendChild( lEltDiagHisto );
						Point lPt = DialogHisto.sTheDialog.getLocation();
						lEltDiagHisto.setAttribute( PPgXmlTag.TAG_XML_POSITION, "" + lPt.x +","+lPt.y );
				}

				//===================

				return lXml.writeDocToFile( lDoc, pFile );
		}
		//------------------------------------------------
		 public boolean processNode( PPgXml pXml, Node pMotherNode, Node pNode, PPgInterfaceXmlNodeHandler pHandler) {
				
				if( pNode.getNodeName().equals( PPgXmlTag.TAG_XML_CONFIG) ) {

				//===================
				} else if( pNode.getNodeName().equals( TAG_XML_DIALOG_LAYERS ) ) {

						Point2D.Double lPosition = 	PPgXml.GetAttributePointDouble( null, pNode, PPgXmlTag.TAG_XML_POSITION, new Point2D.Double() );

						DialogLayers.sTheDialog.setLocation( (int)lPosition.x, (int)lPosition.y );
				//===================
				} else if( pNode.getNodeName().equals( TAG_XML_DIALOG_COLORS ) ) {

						Point2D.Double lPosition = 	PPgXml.GetAttributePointDouble( null, pNode, PPgXmlTag.TAG_XML_POSITION, new Point2D.Double() );

						DialogColor.sTheDialog.setLocation( (int)lPosition.x, (int)lPosition.y );
				//===================
				} else if( pNode.getNodeName().equals( TAG_XML_DIALOG_HISTO ) ) {

						Point2D.Double lPosition = 	PPgXml.GetAttributePointDouble( null, pNode, PPgXmlTag.TAG_XML_POSITION, new Point2D.Double() );

						DialogHisto.sTheDialog.setLocation( (int)lPosition.x, (int)lPosition.y );
				//===================
				}

				return pXml.processNodeChilds( pNode, pHandler );// on pourrait aussi appeler PPgXml.processNode( pXml, pNode, pHandler ); plus couteux	
		}
}

//*************************************************
