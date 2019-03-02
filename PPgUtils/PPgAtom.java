package org.phypo.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

//-----------------------------------------------------------------------
// Les PPAtom devrait etre remplacer DQP par du XML et un lib genre xeres
//-----------------------------------------------------------------------

//*************************************************

@SuppressWarnings("serial")
public class PPAtom extends DefaultMutableTreeNode{

		// Pour Java 5  REMPLACER LES DATATYPE PAR UN ENUM !

		char cDataType='\0';
		Object cData= null;

		public PPAtom( String pName ){
				super( pName );
		}
		// ---------------
		public String getName() {
				return (String) getUserObject(); 
		}
		// ---------------
		public Object getData() {
				return cData;
		}
		// ---------------
		public PPAtom getAtomRoot() {
				return (PPAtom)super.getRoot();
		}
		// ---------------
		public PPAtom getAtomParent() {
				return (PPAtom)super.getParent();
		}
		// ---------------
		/* pour jdk 1.5 remettre 
		public PPAtom getRoot() {
				return (PPAtom)super.getRoot();
		}
		// ---------------
		public PPAtom getParent() {
				return (PPAtom)super.getParent();
		}
		*/
		// ---------------
		public char getDataType() {
				return cDataType;
		}
		// ------------------------------
		public PPAtom findChild( String pStr) {

				Enumeration lEnum = children();
				while( lEnum.hasMoreElements() ) {
						PPAtom lAtom = (PPAtom)lEnum.nextElement();
						if( pStr.compareTo( ((String)lAtom.getUserObject()) ) == 0 ){
								return lAtom;
						}
				}
		
				return null;
		}
		// ------------------------------
		public PPAtom findChild( String pStr, int pMaxDepth) {
				
				ArrayList<TreeNode> lPile= new ArrayList<TreeNode>(); // pour la deRecursion
				lPile.add( this );

				// on compte la profondeur par rapport au node courant
				pMaxDepth += this.getDepth();

				PPAtom lTmp = null;
				while( lPile.size() != 0 ) {
						lTmp = (PPAtom)lPile.remove(0);
						
						if( pStr.compareTo( ((String)lTmp.getUserObject()) ) == 0 )
								return lTmp;								
						
						for( int i=0; i< lTmp.getChildCount(); i++ ){

								if( i == 0 ) { // Il suffit de faire le test sur le premier
										PPAtom lChild  = (PPAtom)lTmp.getChildAt( i );
										if( lTmp.getDepth() > pMaxDepth )
												break;						
								}								
								lPile.add( lTmp.getChildAt( i ) );
						}								
				}															
				return null;
		}
		// ---------------
		public PPAtom findChildDebug( String pStr, int pMaxDepth) {

				System.out.println( "*********** PPAtom.findChildDebug" );

				ArrayList<TreeNode> lPile= new ArrayList<TreeNode>(); // pour la deRecursion
				lPile.add( this );

				// on compte la profondeur par rapport au node courant
				pMaxDepth += this.getDepth();

				PPAtom lTmp = null;
				while( lPile.size() != 0 ) {
						lTmp = (PPAtom)lPile.remove(0);
						
						System.out.println( ">>>" + ((String)lTmp.getUserObject()) + " : "+ pMaxDepth );

						if( pStr.compareTo( ((String)lTmp.getUserObject()) ) == 0 ){
								System.out.println( "*********** PPAtom.findChildDebug fin" );
								return lTmp;								
						}
						
						for( int i=0; i< lTmp.getChildCount(); i++ ){

								if( i == 0 ) { // Il suffit de faire le test sur le premier
										PPAtom lChild  = (PPAtom)lTmp.getChildAt( i );
										if( lTmp.getDepth() > pMaxDepth )
												break;						
								}								
								lPile.add( lTmp.getChildAt( i ) );
						}								
				}															
				System.out.println( "*********** PPAtom.findChildDebug fin null" );
				return null;
		}
		// ---------------
		public String getProp(){
				PPAtom lTmp = (PPAtom)getChildAt( 0 );

				if( lTmp != null  )
						return lTmp.getName();

				return null;								
		}
		// ---------------
		public String getProp( String pKey ){
				PPAtom lTmp = findChild( pKey );

				if( lTmp != null && lTmp.getChildCount() > 0 )
						return ((PPAtom)lTmp.getChildAt( 0 )).getName();

				return null;								
		}
		// --------------
		public String findProp( String pKey ){

				PPAtom lTmp = findChild( pKey, 9999 );
				if( lTmp != null && lTmp.getChildCount() > 0 )
						return ((PPAtom)lTmp.getChildAt( 0 )).getName();

				return null;								
		}
		// --------------
		public void print( PrintStream pOs ) {
				
				final String sTab   = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"; 
				
				String lCurTab = sTab.substring( sTab.length() - getLevel()) ;
				
				pOs.print( lCurTab + getName() );
				
				if( cData != null ){
						
						pOs.print( " [" + cDataType + ' ' );
						switch( cDataType ){
						case 'c': pOs.print( ((Character)cData)); break;
						case 's': pOs.print( ((Short)cData)); break;
						case 'l': pOs.print( ((Long) cData)); break;
						case 'd': pOs.print( ((Double)cData)); break;
						case 'S': pOs.print( ((String)cData)); break;
						}
				}
				
				pOs.println();

				if( getChildCount() != 0 ) {
						pOs.println( lCurTab + "{" );
						for( int i=0; i < getChildCount(); i++ ){
								((PPAtom)getChildAt( i )).print( pOs );
						}
						pOs.println( lCurTab + "}" );
				}
						
		}

		// ---------------------------------------------
		// ---------------------------------------------
		// ---------------------------------------------
		static public PPAtom  ReadFromFile(  String pFileName ){		
				File lFile= new File( pFileName );
				try {
						if( lFile != null ) {
								FileReader lFread = new FileReader(lFile  );
								return Read(lFread);	
						}
				}
				catch( Exception e){
						System.err.println("catch " + e + " in PPAtom.ReadFromFile read file " );
						e.printStackTrace();

				}
				return null;
		}
		// ------------------------------
		static public PPAtom  Read( Reader lFread ){		

				PPAtom lRoot = new PPAtom( "ROOT" );

				try {						
						String lSbuf;
						BufferedReader lBufread = new BufferedReader(lFread);
						
						

						//		enum Type{ VOID='v', CHAR='c', SHORT='s', LONG='l', DOUBLE='d', STRING='S' };
					
						while( (lSbuf=lBufread.readLine()) != null) {								
								try{
										if(  lSbuf.length() == 0 || lSbuf.charAt(0) == '#'  || lSbuf.charAt(0) == '\n'
												 || lSbuf.trim().length() == 0  )
												continue;

										
										// On repart de zero a chaque ligne
										PPAtom lCurrent = lRoot;


										StringTokenizer lTok = new StringTokenizer( lSbuf, "/");
										while( lTok.hasMoreTokens() ){
												String lStr = lTok.nextToken("/]");

												char   lDataType='\0';
												String lDataStr=null;
												Object lDataObj=null;

												int lIndexData = lStr.indexOf( '[' );
												if( lIndexData != -1 ) {
														lDataType  = lStr.charAt(lIndexData+1);
														lDataStr = lStr.substring(lIndexData+3);

														switch( lDataType ){
														case 'c': lDataObj = new Character(lDataStr.charAt(0) ); break;
														case 's': lDataObj = new Short( lDataStr ); break;
														case 'l': lDataObj = new Long( lDataStr ); break;
														case 'd': lDataObj = new Double( lDataStr ); break;
														case 'S': lDataObj = lDataStr; break;
														}

														lStr = lStr.substring( 0, lIndexData);
														////														System.out.println( "Str : <" + lStr + "> Data : " + lDataType + " <" +lDataStr +">" );
												}

											 
												PPAtom lAtom = lCurrent.findChild( lStr );
												if( lAtom == null ){
														lCurrent.add( (lAtom = new PPAtom( lStr )));
														if( lDataObj != null ){
																lAtom.cDataType = lDataType;
																lAtom.cData     = lDataObj;
														}
												}
												lCurrent = lAtom;														
										}
								}
								catch( Exception e){
										System.err.println("catch " + e + " in PPAtom.Read <"+lSbuf+">" );
										e.printStackTrace();

								}
						}
						lFread.close();						
				}
				catch( Exception e){
						System.err.println("catch " + e + " in PPATom.Read read file " );
						e.printStackTrace();
						return null;
				}
				return lRoot;
		}
		// ---------------
		static public PPAtom  ReadTreeFromFile(  String pFileName ){		
				File lFile= new File( pFileName );
				try {
						if( lFile != null ) {
								FileReader lFread = new FileReader(lFile  );
								return ReadTree(lFread);	
						}
				}
				catch( Exception e){
						System.err.println("catch " + e + " in PPAtom.ReadTreeFromFile read file " );
						e.printStackTrace();
				}
				return null;
		}
		// ------------------------------
		static public PPAtom  ReadTree( Reader lFread ){		

				PPAtom lRoot    = null;
				PPAtom lCurrent = null;
				PPAtom lNewAtom = null;

				try {						
						String lSbuf;
						BufferedReader lBufread = new BufferedReader(lFread);
						
						int lNumline = 0;

						//		enum Type{ VOID='v', CHAR='c', SHORT='s', LONG='l', DOUBLE='d', STRING='S' };
					
						while(  (lSbuf=lBufread.readLine()) != null) {								
								try{
										lNumline++;
										//										System.out.println( lNumline + ">>>" + lSbuf );
										
										if(  lSbuf.length() == 0 || lSbuf.charAt(0) == '#'  || lSbuf.charAt(0) == '\n'
												 || lSbuf.trim().length() == 0  )
												continue;								 

										StringTokenizer lTok = new StringTokenizer( lSbuf, "[" );
										if( lTok.hasMoreTokens() ){
												String lStr = lTok.nextToken("[").trim();
												//	System.out.println( "-> <" + lStr + ">" );
												
												if( lStr.charAt(0) == '}' ) {														
														lCurrent = (PPAtom)lCurrent.getParent(); // On remonte d'un niveau
														continue;  
												}
												else
														if( lStr.charAt(0) == '{' )
																{
																		lCurrent = lNewAtom;
																		continue;
																}		
																				
												char   lDataType='\0';
												String lDataStr=null;
												Object lDataObj=null;

												if( lTok.hasMoreTokens()  ){

														// Lecture des data s'il y en a
														String lStrData = lTok.nextToken("]");
														
														//														System.out.println( "Str:" +lStr + " lStrData:" + lStrData );
														
														int lIndexData = lStrData.indexOf( '[' );
														if( lIndexData != -1 ) {
																lDataType  = lStrData.charAt(lIndexData+1);
																lDataStr = lStrData.substring(lIndexData+3);
																
																switch( lDataType ){
																case 'c': lDataObj = new Character(lDataStr.charAt(0) ); break;
																case 's': lDataObj = new Short( lDataStr ); break;
																case 'l': lDataObj = new Long( lDataStr ); break;
																case 'd': lDataObj = new Double( lDataStr ); break;
																case 'S': lDataObj = lDataStr; break;
																}
																
																//														lStr = lStr.substring( 0, lIndexData);
																////														System.out.println( "Str : <" + lStr + "> Data : " + lDataType + " <" +lDataStr +">" );
														}
												}

												
												////												System.out.println( "Tok:" + lStr );
												
												//												if( pRoot == null ){
												//														pRoot = lCurrent = new PPAtom( lStr );	
												//												}
												//												else {
												
												lNewAtom = new PPAtom( lStr );
												if( lCurrent == null ){
														lRoot = lCurrent = lNewAtom;														
												}
												else
														lCurrent.add( lNewAtom );
												
												if( lDataObj != null ){
														lNewAtom.cDataType = lDataType;
														lNewAtom.cData     = lDataObj;
												}
										}
								}
								catch( Exception e){
										System.err.println("catch " + e + " in PPIniFile.exec <"+lSbuf+">" );
										e.printStackTrace();
								}
						}
						lFread.close();						
				}
				catch( Exception e){
						System.err.println("catch " + e + " in PPIniFile.exec read file " );
						e.printStackTrace();

						return null;
				}				

				return lRoot;
		}
};
//*************************************************
