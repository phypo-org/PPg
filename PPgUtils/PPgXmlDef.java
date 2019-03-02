package org.phypo.Util;




import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

//import org.xml.sax.SAXException;  
//import org.xml.sax.SAXParseException;  
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



//*************************************************

public class XmlDef{

// commandes
	public static final String XML_LOGIN        ="LOGIN";
	public static final String XML_NOTIF        ="NOTIF";
	public static final String XML_COMMANDES    ="CMD";
	public static final String XML_DATA         ="DATA";
	public static final String XML_ERROR        ="ERROR";
	public static final String XML_PING         ="PING";


	public static final String XML_SUBSCRIBE    ="SUBSCRIBE";
	public static final String XML_UNSUBSCRIBE  ="UNSUBSCRIBE";
	public static final String XML_GET          ="GET";
	public static final String XML_GETRATE      ="GETRATE";
	public static final String XML_CLOSE_SESSION="CLOSE";


// attributs
	public static final String XML_SESSION      ="Session";
	public static final String XML_RIC          ="Ric";
	public static final String XML_SRC          ="Src";
	public static final String XML_SNAPSHOT     ="Snapshot";
	public static final String XML_RIC_FIELD    ="Field";
	public static final String XML_CLIENTID     ="ClientId";

	public static final String XML_SUBJECT      ="Subject";
	public static final String XML_INFO         ="Info";


// Ors commandes
	public static final String XML_ORS_INSERT="OrsInsert";
	public static final String XML_ORS_UPDATE="OrsUpdate";
	public static final String XML_ORS_DELETE="OrsDelete";


// Ors Attribute
	public static final String XML_ORS_CLASSID       ="ClassId";
	public static final String XML_ORS_OBJECT_ID     ="ObjectId";
	public static final String XML_ORS_OBJECT_VERSION="Version";
	public static final String XML_ORS_TOKEN_STAMP   ="TokenStamp";
	public static final String XML_ORS_SYNCHRO_STAMP ="SynchroStamp";
	public static final String XML_ORS_DOC           ="Doc";
	public static final String XML_ORS_SYSTEM_TABLE  = "SystemTable";
	public static final String XML_ORS_OBJECT_TYPE_NAME="ObjectTypeName";
	public static final String XML_ORS_SQL           ="Sql";
	public static final String XML_ORS_SQL_BASENAME  = "SqlBaseName";
	public static final String XML_ORS_SQL_TABLENAME = "SqlTableName";
	public static final String XML_ORS_SQL_SELECT    = "SqlSelect";
	public static final String XML_ORS_CLASS_CORB_CODE="CorbCode";


	public static final String XML_ORS_FIELD        ="Field";
	public static final String XML_ORS_FIELD_NAME   ="Name";
	public static final String XML_ORS_FIELD_ID     ="FieldId";
	public static final String XML_ORS_FIELD_ISNULL ="IsNull";
	public static final String XML_ORS_FIELD_DEFAULT_VAL="Val";
	public static final String XML_ORS_FIELD_DEFAULT_CODE="Code";
	public static final String XML_ORS_FIELD_DEFAULT_ENUM="Enum";
	public static final String XML_ORS_FIELD_DEFAULT="Default";
	public static final String XML_ORS_FIELD_COMPUTE="Compute";
	public static final String XML_ORS_ELEMENT      ="Element";
	public static final String XML_ORS_FIELD_NBELM  ="NbElm";
	public static final String XML_ORS_FIELD_VAL    ="Val";
	public static final String XML_ORS_FIELD_INDICE ="Ind";
	public static final String XML_ORS_FIELD_LIB    ="Lib";
	public static final String XML_ORS_FIELD_WRITEABLE ="Writeable";

	public static final String XML_ORS_FIELD_TYPE    ="FieldType";
	public static final String XML_ORS_DATA_TYPE    ="DataType";

	public static final String XML_ORS_FIELD_CORB_CODE="CorbCode";
	public static final String XML_ORS_FIELD_SQL ="Sql";

	public static final String XML_ORS_FIELD_SQL_NAME ="SqlName";
	public static final String XML_ORS_FIELD_SQL_AUDIT="SqlAudit";
	public static final String XML_ORS_FIELD_SQL_NOUPDATE="SqlNoUpdate";
	public static final String XML_ORS_FIELD_VAL_NULL ="ValNull";
	public static final String XML_ORS_FIELD_DOMAIN   ="Domain";
	public static final String XML_ORS_FIELD_DOMAIN_ELEMENT   ="Element";
	public static final String XML_ORS_FIELD_DOMAIN_CODE  ="Code";
	public static final String XML_ORS_FIELD_DOMAIN_VAL   ="Val";
	public static final String XML_ORS_FIELD_DOMAIN_ENUM  ="Enum";

// Currencie
	public static final String XML_CURRENCIE     ="Currencie";
	public static final String XML_CURRENCIE_RATE="Rate";
	public static final String XML_CURRENCIES_CONVERTINCUR="ConvertInCur";
	public static final String XML_CURRENCIES_CURTOCONVERT="CurToConvert";

// Real Time 
	public static final String XML_RT               ="RealTime";
	public static final String XML_RT_UP            ="RealTimeUp";
	public static final String XML_RT_STALE         ="RealTimeStale";
	public static final String XML_RT_CLOSED        ="RealTimeClosed";
	public static final String XML_RT_SRC           ="Src";
	public static final String XML_RT_ITEM          ="Item";

	public static final String XML_RT_FIELD         ="Field";
	public static final String XML_RT_FIELD_ID      ="Id";
	public static final String XML_RT_FIELD_NAME    ="Name";
	public static final String XML_RT_FIELD_VAL     ="Val";


	public static final String XML_GET_PERF          ="GETPERF";
	public static final String XML_PERF              ="Perf";
	
	public static final String XML_PERF_FIELD        ="Field";
	public static final String XML_PERF_OBJECT_ID    ="ObjectId";
	public static final String XML_PERF_SECURITY_ID  ="SecurityId";
	public static final String XML_PERF_PERF_RECORD  ="PerfRecord";
	public static final String XML_PERF_DATE         ="Date";
	public static final String XML_PERF_EXECQUANTITY ="ExecQuantity";
	public static final String XML_PERF_EXECPRICE    ="ExecPrice";
	public static final String XML_PERF_VWAP         ="Vwap";
	public static final String XML_PERF_VOLUME       ="Volume";
	public static final String XML_PERF_OPTIMIZE     ="Optimize";
	public static final String XML_PERF_INTERVAL     ="Interval";

	//*************************************************
	

	public static final String XML_ROOT_PROJECTS    ="RootProject";
	public static final String XML_PROJECT          ="Project";
	public static final String XML_PROJECT_NAME     ="Name";
	public static final String XML_PROJECT_TARGET_SYSTEM="TargetSystem";
	public static final String XML_CLASSES          ="Classes";
	public static final String XML_CLASS            ="Class";
	public static final String XML_CLASS_NAME       ="Name";
	public static final String XML_BASE_CLASS       ="BaseClass";

	public static final String XML_GRP_ENUMS        ="GrpEnums";
	public static final String XML_ENUM             ="Enum";
	public static final String XML_ENUM_ELEMENT     ="Element";
	public static final String XML_ENUM_NAME        ="Name";
	public static final String XML_ENUM_VAL         ="Val";


	public static final String XML_FIELD_ISQUERY    ="IsQuery";
	//	public static final String XML_ ="";
	public static final String XML_AUTO_INIT        ="AutoInit";
	public static final String XML_OBJECT_DATA      ="ObjectData";
	public static final String XML_OBJECT_REF       ="ObjectRef";
	public static final String XML_FIELD_SIZE       ="FieldSize";
	public static final String XML_FIELD_PRINT_SIZE ="PrintSize";
	public static final String XML_FIELD_PRINT_FORMAT ="PrintFormat";
	public static final String XML_FIELD_PRECISION  ="Precision";

		
		//------------------------------------------------
		public static String GetAttributeVal( Node pNode, String pName ){
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
				return null;
		}
		//------------------------------------------------
		public static String GetAttributeString( Node pNode, String pName, String pDefault ){
				
				String pVal = GetAttributeVal( pNode, pName );
				if( pVal != null ){
						return pVal;
				}
				return pDefault;
		}

		//------------------------------------------------
		public static ArrayList<Node> ToArrayList( NodeList pNodeList ){

				ArrayList<Node> lNodeArray= null;
				if( pNodeList != null ){
						for (int i=0; i< pNodeList.getLength(); i++) {
								
								Node lNode = pNodeList.item(i);
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
				}
				return lNodeArray;
		}
		//------------------------------------------------
		public  static ArrayList<Node> GetSubNodes( Node pNode ){
				
				return ToArrayList( pNode.getChildNodes());				
		}		
		//------------------------------------------------
		public  static ArrayList<Node> GetSubNodes( Node pNode, String pQuery ){


				///				System.out.println( "XmlDef.GetSubNodes : " +pQuery );
				
				try {
						XPath lXpath = XPathFactory.newInstance().newXPath();
						return ToArrayList( (NodeList) lXpath.evaluate( pQuery, pNode, XPathConstants.NODESET));
				}
				catch( XPathExpressionException ex ){
						// Error generated by the parser
						System.err.println("XmlDef.GetSubNodes Fatal Error : pQuery : " +ex );
						ex.printStackTrace();						
						return null;
				}
		}
		//------------------------------------------------
		public  static ArrayList<Node> GetNodes(  InputSource pInputSource, String pQuery  ){
				try {
						XPath lXpath = XPathFactory.newInstance().newXPath();
						return ToArrayList( (NodeList) lXpath.evaluate( pQuery, pInputSource, XPathConstants.NODESET));
				}
				catch( XPathExpressionException ex ){
						// Error generated by the parser
						System.err.println("XmlDef.GetNodes Fatal Error : pQuery : " +ex );
						ex.printStackTrace();						
						return null;
				}
		}
		//------------------------------------------------
		public  static Node GetNode(  InputSource pInputSource, String pQuery  ){
				try {
						XPath lXpath = XPathFactory.newInstance().newXPath();
						return  (Node) lXpath.evaluate( pQuery, pInputSource, XPathConstants.NODE);
				}
				catch( XPathExpressionException ex ){
						// Error generated by the parser
						System.err.println("XmlDef.GetNode Fatal Error : pQuery : " +ex );
						ex.printStackTrace();						
						return null;
				}
		}
}

//*************************************************
