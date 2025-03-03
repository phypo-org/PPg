package com.phipo.GLib;

import org.w3c.dom.*;

import java.util.*;
import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;

import javax.swing.*;



//*************************************************

class AnimAll {

		String cName;
		String getName() { return cName; }

		AnimAction [] cVectAction = new AnimAction[ActionEntity.ActionType.Size()];


		//------------------------------------------------
		AnimAll(){
				for( int i=0; i< ActionEntity.ActionType.Size();i++)
						cVectAction[i] = null;
		}		
		//------------------------------------------------
		public void draw( int pMX, int pMY, double pMagnify, Graphics pGraf, ActionEntity.ActionType pType, double pAngle, double pTime ){
				AnimAction lAnimAction  = cVectAction[ pType.getIndex()]; 
				if( lAnimAction != null )
						lAnimAction.draw( pMX, pMY, pMagnify, pGraf, pAngle, pTime );
		}
		//------------------------------------------------
		AnimActionSeq findOrCreateSeq( ActionEntity.ActionType pType){

				AnimActionSeq lAnimAction = (AnimActionSeq)cVectAction[ pType.getIndex() ]; 

				if( lAnimAction == null ){
					cVectAction[ pType.getIndex()] = lAnimAction = new AnimActionSeq();																						
				}
				
				return lAnimAction;
		}				
		//------------------------------------------------
		AnimActionMap findOrCreateMap( ActionEntity.ActionType pType){

				AnimActionMap lAnimAction = (AnimActionMap)cVectAction[ pType.getIndex() ]; 

				if( lAnimAction == null ){
					cVectAction[ pType.getIndex()] = lAnimAction = new AnimActionMap();																						
				}
				
				return lAnimAction;
		}				
		//-------
		void debug() {
				System.out.println( "ANIM All Name:" + cName );
				for( int i=0; i< ActionEntity.ActionType.Size();i++){
						if( cVectAction[i] != null ){
								System.out.print( "" + ActionEntity.ActionType.Get(i) + " / " );
								cVectAction[i].debug();
						}
				}
		}
		//------------------------------------------------
		//-----------------  STATIC  ---------------------
		//------------------------------------------------
		
		static HashMap<String,AnimAll> sVectAnimAll = new HashMap<String,AnimAll>();
		static AnimAll FindByName( String pStr ){ return sVectAnimAll.get( pStr ); }
		
		static public int cHeight = 32;
		static public int cWidth  = 32;
		//------------------------------------------------
		static public boolean LoadXmlGen( Node pNode, XmlLoader pXmlLoader ){				

				return  pXmlLoader.processNode( pNode  ); // faire les sous noeund !!!
		}
		//------------------------------------------------
		static public boolean LoadXml( Node pMotherNode, Node pNode, XmlLoader pXmlLoader ){
				
				AnimAll lAnimAll = new AnimAll();
				lAnimAll.cName = XmlLoader.GetAttributeVal( null, pNode, XmlLoader.TAG_XML_NAME );

				String lGenPath = XmlLoader.GetAttributeVal( pMotherNode, pNode, XmlLoader.TAG_XML_GENPATH );


			 
				ArrayList<Node> lSubNodeArray = XmlLoader.GetSubNode( pNode );
				if( lSubNodeArray != null )
						for( Node lSubNode:lSubNodeArray){
								
								// --------------------- Animation ---------------------
								if( lSubNode.getNodeName().equals( XmlLoader.TAG_XML_ANIMSEQ)) {
										
										String lActionStr  = XmlLoader.GetAttributeString( null, lSubNode, XmlLoader.TAG_XML_ACTION, "Walk");

										ActionEntity.ActionType lActionType = ActionEntity.ActionType.FindByName( lActionStr );

										int lNbDirection = XmlLoader.GetAttributeInt( pMotherNode, pNode, XmlLoader.TAG_XML_ORIENTATION, 1);
										System.out.println( "ANIM AnimAll.loadXml ActionType=" + lActionStr 
																				+ " Type=" +  lActionType + " " +lActionType.getIndex()+ " directions=" + lNbDirection  );

										AnimActionSeq lAnimActionSeq = lAnimAll.findOrCreateSeq( lActionType );
										lAnimActionSeq.setNbOrientation( lNbDirection );


										if( lAnimActionSeq.loadXml( pNode, lSubNode, pXmlLoader, lGenPath  ) == false ){
												System.err.println( "*** Error AnimAll seq.LoadXml lAnimAction.loadXml failed for " + lAnimAll.cName );
												return false;
										}
								}
								else	if( lSubNode.getNodeName().equals( XmlLoader.TAG_XML_ANIMACTION)) {
										
										String lActionStr  = XmlLoader.GetAttributeString( null, lSubNode, XmlLoader.TAG_XML_ACTION, "Walk");
										ActionEntity.ActionType lActionType = ActionEntity.ActionType.FindByName( lActionStr );

										System.out.println( "ANIM AnimAll map.loadXml ActionType=" + lActionStr 
																				+ " Type=" +  lActionType + " " +lActionType.getIndex() );

										AnimActionMap lAnimActionMap = lAnimAll.findOrCreateMap( lActionType );


										if( lAnimActionMap.loadXml( pNode, lSubNode, pXmlLoader, lGenPath   ) == false ){
												System.err.println( "*** Error Animage.LoadXml lAnimAction.loadXml failed for " + lAnimAll.cName );
												return false;
										}
								}
						}			 
				

				sVectAnimAll.put( lAnimAll.cName, lAnimAll );

				System.out.println( "******************************************");
				lAnimAll.debug();
				return true;
		}
};
