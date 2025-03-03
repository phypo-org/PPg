package com.phipo.GLib;


import org.w3c.dom.*;


import java.util.*;
import java.io.*;
import java.util.*;
import java.lang.*;

//*************************************************
abstract public class PrototypeXmlLoader{


		PrototypeXmlLoader(){
		}
		//------------------------------------------------

		abstract public String  getName();
		abstract public PrototypeXmlLoader findByName( String pName );
		abstract public PrototypeXmlLoader create( String pName);
		abstract public void doInherit(PrototypeXmlLoader pMother );
		abstract public boolean loadXmlNode( Node pMotherNode,  Node pNode, XmlLoader pXmlLoader );
		abstract public String getMyTag(); 
		abstract public boolean isMobil();
	

		//------------------------------------------------
		public boolean loadXmlBase( Node pMotherNode, Node pNode, XmlLoader pXmlLoader ){
				
				System.out.println( "****************** ProtoypeXmlLoader.LoadXml for " + getMyTag() );
								
				String lName = XmlLoader.GetAttributeVal( null, pNode, XmlLoader.TAG_XML_NAME );
				if( lName == null ){
						System.err.println( "PrototypeCase LoadXml  "
																+ XmlLoader.TAG_XML_NAME  );
				}
				else {
						// On recupere un nouveau prototype
						
						PrototypeXmlLoader lProtoBase = findByName( lName ); 
						if( lProtoBase == null ) {
								lProtoBase = create( lName );
						}					

						if( lProtoBase.inherit(pNode) == false )
								return false;
								
						if( lProtoBase.loadXmlNode( pMotherNode, pNode, pXmlLoader ) == false )
								return false;
				}
				return true;
		}
		//		else {
		//				if( lProtoBase == null ){
		//						System.err.println( "ProtoypeXmlLoader no Protobase define"  );
		//				}
		//				else
		//						if(	lProtoBase.loadXmlSubNode( lNode ) == false )
		//								return false;
		//				System.out.println( "****************** PrototypeCase.LoadXml End for " + getMyTag());
		//				return true;
		//		}
		//------------------
		boolean inherit( Node pNode ){
								
				//------------
				String lInheritFrom = XmlLoader.GetAttributeVal( null, pNode, XmlLoader.TAG_XML_INHERIT_FROM );
				if( lInheritFrom != null){
						PrototypeXmlLoader lMother = findByName( lInheritFrom );
						if( lMother == null )
								System.err.println("XXXXXXXXXXXXXXXX Error inherit fail for " + getName() + " from " + lInheritFrom );
						else {
								System.out.println("\t\tInherit " +getName() + " from " + lMother.getName() );
								
								// Tout n'est pas herite !
								doInherit( lMother ); 
						}
				}
				
				return true;
		}
}
//*************************************************
