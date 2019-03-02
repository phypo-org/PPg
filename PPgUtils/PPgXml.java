package org.phypo.PPg.PPgUtils;


import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.FactoryConfigurationError;  
import javax.xml.parsers.ParserConfigurationException;
 
import org.xml.sax.SAXException;  
import org.xml.sax.SAXParseException;  



import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;




import java.io.File;
import java.io.IOException;

import org.w3c.dom.*;

import java.awt.geom.*;
import java.awt.*;


import java.io.*;
import java.util.*;
import java.lang.*;


//*************************************************

public class PPgXml implements PPgInterfaceXmlNodeHandler {

		
		//------------------------------------------------
		static public void Traceln( String pStr ){
				System.out.println( ">" + pStr );
		}
		//------------------------------------------------
		static public void Trace( String pStr ){
				System.out.print( pStr );
		}	
		//------------------------------------------------

		public static String GetAttributeVal( Node pMotherNode, Node pNode, String pName ){

				NamedNodeMap lAttributes = pNode.getAttributes();
				if( lAttributes != null ){
						Node lNode = lAttributes.getNamedItem( pName );
						if( lNode != null ){
								String lTmp =  lNode.getNodeValue();
								if( lTmp != null && lTmp.length() ==0 ) // on veut vraiment rien 
										return null;
								else
										return lTmp;
						}
				}
				// si le mothernode est renseigne on va voir si une valeur par default existe !
				if( pMotherNode != null ){
						lAttributes = pMotherNode.getAttributes();
						if( lAttributes != null ){
								Node lNode = lAttributes.getNamedItem( pName );
								if( lNode != null ){
										String lTmp =  lNode.getNodeValue();
										return lTmp;
								}
						}
				}
				return null;
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public static String GetAttributeString( Node pMotherNode, Node pNode, String pName, String pDefault ){
				
				String pVal = GetAttributeVal( pMotherNode, pNode, pName );
				if( pVal != null ){
						return pVal;
				}
				return pDefault;
		}
		//------------------------------------------------
		public static double GetAttributeDouble( Node pMotherNode, Node pNode, String pName, double pDefault ){
				
				String pVal = GetAttributeVal( pMotherNode, pNode, pName );
				if( pVal != null ){
						return Double.parseDouble( pVal );
				}
				return pDefault;
		}
		//------------------------------------------------
		public static int GetAttributeInt( Node pMotherNode, Node pNode, String pName, int pDefault ){
				
				String pVal = GetAttributeVal( pMotherNode, pNode, pName );
				if( pVal != null ){
						return Integer.parseInt( pVal );
				}
				return pDefault;
		}
		//------------------------------------------------
		public static boolean GetAttributeBoolean( Node pMotherNode, Node pNode, String pName, boolean pDefault ){
				
				String pVal = GetAttributeVal( pMotherNode, pNode, pName );
				if( pVal  != null  ){
						return Boolean.parseBoolean( pVal );
				}
				return pDefault;
		}
		//------------------------------------------------
		public static Point2D.Double GetAttributePointDouble( Node pMotherNode, Node pNode, String pName, Point2D.Double pPosition ){
								
				String lVal = GetAttributeVal( pMotherNode, pNode, pName );
				if( lVal  == null ){
						return pPosition;
				}
				
				try{
						StringTokenizer lTok = new StringTokenizer( lVal, "," );
						String lStrX =lTok.nextToken();
						String lStrY =lTok.nextToken();
						
						if( pPosition == null )
								pPosition = new Point2D.Double();

						pPosition.setLocation( Double.parseDouble( lStrX ), Double.parseDouble( lStrY ));
						return pPosition;
				}
				catch( Exception e ){
						System.err.println( "Error for GetAttributePointDouble for <" + pName +  ":" + lVal + ">" + e );
						e.printStackTrace();
				
				}				

				return null;
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------

		public static String GetAttributeName( Node pNode ){
				NamedNodeMap lAttributes = pNode.getAttributes();
				if( lAttributes != null ){
						Node lNode = lAttributes.getNamedItem( PPgXmlTag.TAG_XML_NAME  );
						if( lNode != null ){
								String lTmp =  lNode.getNodeValue();
								if( lTmp != null && lTmp.length() ==0 )
										return null;
								else
										return lTmp;
						}
				}
				return null;
		}
		//------------------------------------------------
		public static Point2D.Double GetAttributePosition( Node pMotherNode, Node pNode, Point2D.Double pPosition ){
								
				return GetAttributePointDouble( pMotherNode, pNode, PPgXmlTag.TAG_XML_POSITION, pPosition );
		}
		//------------------------------------------------
		static Color GetColor( String pStrColor ){
				
				Color lColor = null;

				if( pStrColor != null ){
						if( pStrColor.equals( "blue" ) )
								lColor = Color.blue;
						else if( pStrColor.equals( "yellow" ) )
								lColor = Color.yellow;
						else if( pStrColor.equals( "green" ) )
								lColor = Color.green;
						else if( pStrColor.equals( "gray" ) )
								lColor = Color.gray;
						else if( pStrColor.equals( "orange" ) )
								lColor = Color.orange;
						else if( pStrColor.equals( "black" ) )
								lColor = Color.black;
						else if( pStrColor.equals( "cyan" ) )
								lColor = Color.cyan;
						else if( pStrColor.equals( "darkGray" ) )
								lColor = Color.darkGray;
						else if( pStrColor.equals( "lightGray" ) )
								lColor = Color.lightGray;
						else if( pStrColor.equals( "pink" ) )
								lColor = Color.pink;
						else if( pStrColor.equals( "red" ) )
								lColor = Color.red;
						else if( pStrColor.equals( "white" ) )
								lColor = Color.white;						
						else if( pStrColor.equals( "magenta" ) )
								lColor = Color.magenta;						
				}
				return lColor;
		}
		//------------------------------------------------
		public static Color GetAttributeColor( Node pMotherNode, Node pNode, String lStrTag, Color pColor ){

				String lStrColor = PPgXml.GetAttributeVal( pMotherNode, pNode, lStrTag);
				Color lTmpColor =  GetColor( lStrColor );
				if( lTmpColor != null )
						return lTmpColor;
				return pColor;
		}


		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------

		
		public final HashMap<String, PPgInterfaceXmlNodeHandler> cHashPrototypeXmlLoader = new HashMap<String, PPgInterfaceXmlNodeHandler>();

		 void registerPrototype( String pKey, PPgInterfaceXmlNodeHandler pProto){
				cHashPrototypeXmlLoader.put( pKey, pProto );
		}


		//------------------------------------------------
		// La fonction a appeler pour lire un document
		// l'utilisateur peut fournir un Handler pour les noeuds a traiter 
		// ou fournir ou enregistre des handlers via registedHandler
		// dans ce cas c'est a lui de rappeler processNodeChilds sur le node courant
		// s'il y a lieu
		//------------------------------------------------

		public PPgXml(){
		}

		//------------------------------------------------
		public boolean processFile( File pFile,  PPgInterfaceXmlNodeHandler pNodeHandler ) {


				Traceln( "PPgXml.process  file : " + pFile.getPath() );
				try {
						DocumentBuilderFactory lFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder lBuilder = lFactory.newDocumentBuilder();

						Document lDocument = lBuilder.parse( pFile  );
						return processDoc( lDocument, pNodeHandler);
				
				} catch (ParserConfigurationException pce) {
            // Parser with specified options can't be built
            pce.printStackTrace();						
        } catch (SAXException sxe) {
						// Error generated during parsing)
						Exception  x = sxe;
						if (sxe.getException() != null)
								x = sxe.getException();
						x.printStackTrace();
						
        } catch (IOException ioe) {
						// I/O error
						ioe.printStackTrace();
        }
				
				return false;
		}
		//------------------------------------------------
		public Document getNewDoc(){

				try {
						DocumentBuilderFactory lFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder lBuilder = lFactory.newDocumentBuilder();
				
				return lBuilder.newDocument();
				}
				catch( ParserConfigurationException  pce) {
            // Parser with specified options can't be built
            pce.printStackTrace();						
        }

				return null;
		}
		//------------------------------------------------
		
		public boolean processDoc( Document lDocument, PPgInterfaceXmlNodeHandler pNodeHandler ) {
				
				Element lElem = lDocument.getDocumentElement();
				
				if( pNodeHandler != null )
						return pNodeHandler.processNode( this, null,  lElem, pNodeHandler );			 										
				else
						return processNode( this, null,  lElem, pNodeHandler );			 										
		}
		//------------------------------------------------
		// Traitement des noeuds d'un niveau par un objet 
		// implementant pNodeHandler

		public  boolean processNodeChilds(  Node pNode, PPgInterfaceXmlNodeHandler pNodeHandler ){
				
				NodeList lNodeList = pNode.getChildNodes();
				
				System.out.println( "PPgXml.processNode Node=" +  lNodeList.getLength() );
				
				for (int i=0; i< lNodeList.getLength(); i++) {
								
						Node lNode = lNodeList.item(i);
						int lTypeNode = lNode.getNodeType();
														
						switch( lTypeNode ){
								
						case Node.ENTITY_NODE:
						case Node.ELEMENT_NODE:
								
								Trace( ".");
								System.out.println( "PPgXml Type:" +lTypeNode + " Node Name=" + lNode.getNodeName() );
								if( pNodeHandler != null ) {
										if( pNodeHandler.processNode( this, pNode, lNode, pNodeHandler ) == false )
												return false;
								} else {
										return pNodeHandler.processNode( this, pNode, lNode, this );
								}
						default: ;
						}
				}
				return true;
		}
		//------------------------------------------------
		public boolean processNode(  PPgXml pXml,  Node pMotherNode, Node pNode, PPgInterfaceXmlNodeHandler pNodeHandler ){
				
				// On charche si un handler n'a pas ete enregistre pour le node courant !
				PPgInterfaceXmlNodeHandler lProtoLoader = cHashPrototypeXmlLoader.get( pNode.getNodeName() );
				if( lProtoLoader != null ){
						Traceln( "Loading PPgInterfaceXmlNodelLoader");
						return lProtoLoader.processNode( pXml, pMotherNode, pNode, pNodeHandler );
				}
				
				
				if( pNode.getNodeName().equals( PPgXmlTag.TAG_XML_LOAD ) ) {
						// Gestion des include
						String lFilename = GetAttributeVal( null, pNode,  PPgXmlTag.TAG_XML_FILE );
						if( lFilename != null ){
								System.out.println( "PPgXml: Load file:" +lFilename );
								processFile( new File( lFilename) , pNodeHandler );
								System.out.println( "PPgXml: Close file:" +lFilename );
								return true;
						}
						else {
								System.err.println( "PPgXml Error for " + PPgXmlTag.TAG_XML_LOAD + " " +PPgXmlTag.TAG_XML_FILE+ " is void");
								return false;
						}
				}						 

				if( pNode.getNodeName().equals( PPgXmlTag.TAG_XML_ROOT ) ) {
				}			
				else {
						Traceln( "processNode " + pNode.getNodeName() + " not match " );
				}

				return processNodeChilds( pNode, pNodeHandler ); // recursif 
    }
		//------------------------------------------------
		// Met tout les noeuds fils dans un tableau

		public  static ArrayList<Node> GetSubNode( Node pNode ){
				
				NodeList lNodeList = pNode.getChildNodes();
				ArrayList<Node> lNodeArray= null;

				for (int i=0; i< lNodeList.getLength(); i++) {
								
						Node lNode = lNodeList.item(i);
						int lTypeNode = lNode.getNodeType();
														
						switch( lTypeNode ){
								
						case Node.ENTITY_NODE:
						case Node.ELEMENT_NODE:
								if( lNodeArray == null )
										lNodeArray = new ArrayList<Node>();
								lNodeArray.add( lNode );
								break;

						default: ;
						}						
				}
				return lNodeArray;
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		//            Ecriture  
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		// exemple veanr de http://wwwdi.supelec.fr/jacquet/teaching/java/xml-dom Christophe Jacquet

		   public boolean writeDocToFile( Document pDoc, File pFile) {

        // on considère le document "doc" comme étant la source d'une
        // transformation XML
        Source lSource = new DOMSource(pDoc);
				
        // le résultat de cette transformation sera un flux d'écriture dans
        // un fichier
        Result lResultat = new StreamResult( pFile );

        
        // création du transformateur XML
        Transformer lTransfo = null;
        try {
            lTransfo = TransformerFactory.newInstance().newTransformer();

        } catch(TransformerConfigurationException e) {
            System.err.println("Impossible de créer un transformateur XML.");
						return false;
        }
         
        // configuration du transformateur
         
        // sortie en XML
        lTransfo.setOutputProperty(OutputKeys.METHOD, "xml");
         
        // inclut une déclaration XML (recommandé)
        lTransfo.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
         
        // codage des caractères : UTF-8. Ce pourrait être également ISO-8859-1
        lTransfo.setOutputProperty(OutputKeys.ENCODING, "utf-8");
         
        // idente le fichier XML
        lTransfo.setOutputProperty(OutputKeys.INDENT, "yes");
         
        try {
            lTransfo.transform( lSource, lResultat);
        } catch(TransformerException e) {
            System.err.println("La transformation a échoué : " + e);
						return false;
        }
				return true;
    }
     

};


//*************************************************
