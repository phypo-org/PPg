package com.phipo.GLib;

import org.w3c.dom.*;

import java.util.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import javax.swing.*;


import java.awt.event.*;

import javax.swing.SwingUtilities;
import java.util.concurrent.ConcurrentLinkedQueue;

//***********************************
public class GamerHuman extends Gamer {
		
		public GamerHuman( String pName, int pGamerId, int pGroupId ){
				super( pName, pGamerId, pGroupId );
		}

		boolean isHuman() { return true; }
		boolean isReadyToDraw() { return cDisplayGamer.isReadyToDraw(); }

		Point2D.Double cPosView = new Point2D.Double( 0, 0 );
		public Point2D.Double getViewPoint() { return cPosView;}
		public void           setViewPoint( Point2D.Double pPos) { cPosView = pPos; }


		ConcurrentLinkedQueue<GamerMessage>	cQueue = new ConcurrentLinkedQueue<GamerMessage>();
		public void sendMessage( GamerMessage pMsg ) { cQueue.add( pMsg); }


		// ATTENTION A REMPLACER PEUT ETRE PAR POINT DANS FRAME
		Point2D.Double cLastPoint = new Point2D.Double(0,0);
		public Point2D.Double getLastPoint() { return cLastPoint; }


		// Remplit par le Render2D
		Point2D.Double cSizeView = new Point2D.Double(0,0);
		Point2D.Double getSizeView() { return cSizeView; }
    public void setSizeView( Point2D.Double pSizeView ) { cSizeView.setLocation( pSizeView ); }


		String		cSelectionPositionAction = null;
		ImageIcon	cSelectionPositionIcon   = null;
		public ImageIcon getSelectionPositionIcon() { return cSelectionPositionIcon;} // pour le Render
		
		boolean   cSelectionPositionFlag = false;
		void      setSelectionPositionFlag( boolean pFlag){ cSelectionPositionFlag=pFlag; }

		CommandIcon cCurrentCommandIcon = null;
		ArrayList<CommandIcon> cCurrentVectCommandIcon = null;
		public void setCurrentVectCommandIcon( ArrayList<CommandIcon> pVect ) { cCurrentVectCommandIcon = pVect;}
		ArrayList<CommandIcon> getCurrentVectCommandIcon()   { return  cCurrentVectCommandIcon; }
		
		
		Entity cCurrentEntity=null;
		PrototypeUnit cCurrentPrototype=null;
		
		JPopupMenu cPopMenu;
		AWTEvent cEv;
		
		
		InterfaceDisplayGamer  cDisplayGamer; // ce qui gere l'interface graphique
		public void   setDisplayRender( InterfaceDisplayGamer pDisplayGamer ) {	
				cDisplayGamer = pDisplayGamer;
		}


		//--------- Selection ---------
		//		List<Entity> cSelection = Collections.synchronizedList(new ArrayList<Entity>());		
		ArrayList<Entity> cSelection = new ArrayList<Entity>();		
		ArrayList<Entity> getSelection() { return cSelection; }

		Entity       cEnemySelect = null;
		Entity       getEnemySelect() { return cEnemySelect; }


		PathCase         cCaseSelect = null;
		PathCase         getCaseSelect() { return cCaseSelect; }

		boolean addToSelection( Entity pEntity){
				boolean lOk = pEntity.setSelect( true );
				if( lOk )
						cSelection.add( pEntity );

				return lOk;
		}
		void removeToSelection(  Entity pEntity){
				cSelection.remove( pEntity );
				pEntity.setSelect( false );
		}
		void clearSelection(){
				for( Entity lEntity:cSelection )
						lEntity.setSelect( false );
				cSelection.clear(); 

				cCurrentCommandIcon     = null;
				cCurrentVectCommandIcon = null;
				cSelectionPositionIcon  = null;
				cSelectionPositionFlag  = false;
		}
		//-----------------------------

		//--------------------------------------
		void execOrder() {
						
				while( cQueue.isEmpty() == false ){
						GamerMessage lMsg = cQueue.poll();

						System.out.println( "GamerHuman.execOrder " );

						switch( lMsg.cOrder ){
						case CURSOR_AT:
								System.out.println("CURSOR_AT");
								cLastPoint = new Point2D.Double( lMsg.cRect.getX(), lMsg.cRect.getY());
								break;
						case SELECT_AT:
								System.out.println("SELECT_AT");
								selectAt( new Point2D.Double( lMsg.cRect.getX(), lMsg.cRect.getY()), lMsg.cCtrl, lMsg.cShift);
								break;
					  case SELECT_RECT:
								System.out.println("SELECT_RECT");
								selectRect( lMsg.cRect, lMsg.cCtrl, lMsg.cShift);
								break;
						case ORDER_AT:
								System.out.println("ORDER_AT");
								/*
								if( cSelectionPositionAction != null ){
										// POSITON D'UNE CONSTRUCTION VIA MENU
										if(cSelectionPositionFlag==true){
												System.out.println( "ORDER_AT -> Action : " + cSelectionPositionAction + cLastPoint+ " Entity:" +  cCurrentEntity );
												actionEvent( cSelectionPositionAction, cLastPoint );
												cCurrentEntity = null;
												cCurrentPrototype= null;
												cSelectionPositionAction = null;
												cSelectionPositionIcon   = null;												
										}
								}
								else
								*/
								if( cCurrentCommandIcon != null && cSelection.size() != 0 ){
										// POSITON D'UNE CONSTRUCTION VIA ORDER
										cSelection.get(0).getPrototype().actionCommand( this, cSelection.get(0), cCurrentCommandIcon, cLastPoint);
										cCurrentPrototype= null;
										cCurrentCommandIcon = null;
										cSelectionPositionAction = null;
										cSelectionPositionIcon   = null;												
								}
								else
										// ORDRE A LA SELECTION
										orderAt( new Point2D.Double( lMsg.cRect.getX(), lMsg.cRect.getY()), lMsg.cCtrl, lMsg.cShift);										
								break;

						case ORDER_ENTITY:
								System.out.println("ORDER_ENTITY");
								actionCommand( lMsg.cEv );
								break;
						case ACTION_EVENT:
								System.out.println("ACTION_EVENT");
								System.out.print( "execOrder ACTION_EVENT" );
								actionEvent( ((ActionEvent)lMsg.cEv).getActionCommand(), null);
								break;

						case CANCEL:								
								System.out.print( "execOrder CANCEL" );

								cCurrentEntity = null;
								cCurrentPrototype= null;
								cCurrentCommandIcon = null;
								cSelectionPositionAction = null;
								cSelectionPositionIcon   = null;								
								break;
						}

						//						redrawSelectInfo(); // FAIRE PLUS SELECTIF !
						//						redrawOrder();      // FAIRE PLUS SELECTIF !
				}
		}
		//--------------------------------------
		void actionCommand( AWTEvent pEv ){
				if( cCurrentVectCommandIcon != null && cSelection.size() > 0 ){

						MouseEvent lEv = (MouseEvent) pEv;

						for( CommandIcon lCommand: cCurrentVectCommandIcon ){
								if( lCommand.contains( lEv.getX()-lCommand.getPanel().getX(), lEv.getY()-lCommand.getPanel().getY() )){						
										
										for( Entity lEntity: cSelection ){
												lEntity.getPrototype().actionCommand( this, lEntity, lCommand, null );
												break;
										}
										return;
								}
						}
				}
		}
		//--------------------------------------
		void actionEvent( String pAction, Point2D.Double pPosition ){

				if( cCurrentEntity != null ){
						cCurrentEntity.getPrototype().actionEvent( this, cCurrentEntity, pAction, pPosition );
						//////////						cCurrentEntity = null;
				}
		}
		//--------------------------------------
		// envoyer des messages plutot qu'appeller des fonctions
		// thread differends

		// renvoie true s'il faut redessiner

		void selectAt( Point2D.Double pPoint, boolean pCtrl, boolean pShift ){

				System.out.println( "Select for "+ getGamerId() + " " + getGroupId() 
														+ "  At:" + pPoint + " " +pCtrl + " " + pShift);

				cEnemySelect = null;
				cCaseSelect  = null;

				Entity lEntityFound = World.GetPathCarte().searchEntityAtPosition( pPoint );

				if( lEntityFound == null ){
						System.out.println( "Select not found" );

						if( pCtrl == false && pShift == false ){
								clearSelection();
								cCaseSelect = World.GetPathCarte().getMeter( pPoint.getX(), pPoint.getY() );
						}
						return ;
				}

				System.out.println( "Select found" + lEntityFound );
						
				if( isMine( lEntityFound ) == false ){
						System.out.println( "Not mine :" + lEntityFound.getGamerId() );

						echoInfoOn( lEntityFound );
						return ;
				}

				int lIndex = cSelection.indexOf( lEntityFound );
				System.out.println( "index:"+ lIndex );

				if( lIndex != -1 ){
						if( pCtrl){
								removeToSelection( lEntityFound );
								return ;
						}
						else 
						if( pShift )
								return;

						clearSelection();
						addToSelection( lEntityFound );
						return ;
				}
				
				if( pShift == false && pCtrl == false ){
						clearSelection();						
				}
				addToSelection( lEntityFound );

				return;						
		}
		//--------------------------------------
		void selectRect( Rectangle2D.Double pRect, boolean pCtrl, boolean pShift ){

				cEnemySelect = null;
				cCaseSelect  = null;

				if( pCtrl == false && pShift == false ){
						clearSelection();
				}

				
				
				ArrayList<Entity> lListEntity = World.GetPathCarte().searchEntityInRect( pRect);
				if( lListEntity == null ){
						System.out.println( "selectRect NULL" );
						return ;
				}

						System.out.println( "selectRect " + lListEntity.size() );

				for( Entity lEntity: lListEntity ){						
						if( isMine( lEntity ) == false )
								continue;
						
						int lIndex = cSelection.indexOf( lEntity );						
						if( lIndex != -1 ){
								if( pCtrl){
										removeToSelection( lEntity );
										continue ;
								}
						}
						else
								addToSelection( lEntity );
				}
				
				return;						
		}
		//--------------------------------------
		
		void orderAt( Point2D.Double pPoint, boolean pCtrl, boolean pShift ){
				
				cEnemySelect = null;
				cCaseSelect  = null;

				System.out.println( "orderAt "+ getGamerId() + " " + getGroupId() 
														+ "  At:" + pPoint + " " +pCtrl + " " + pShift);
				
				if( getSelection().isEmpty() ) {
						System.out.println( "Empty" );
						return;
				}
				

				Entity lEntityFound = World.GetPathCarte().searchEntityAtPosition( pPoint );
				for( Entity lEntity : cSelection ){
						
						if( lEntityFound == null ) {
								System.out.println( "Ordre de marche !" );
								lEntity.sendOrderTerrain(pPoint,Entity.ModeAddMission.REMPLACE_ALL );						
						}
						else {
								if( isEnemy(lEntityFound) ){
										lEntity.sendOrderAttackEnemy( pPoint, lEntityFound );						
										
						}
								else if( isFriend(lEntityFound) ){
										lEntity.sendOrderHelpFriend( pPoint, lEntityFound );						
								}
								else { 
										// Objet neutre ?
										lEntity.sendOrderUsing( pPoint, lEntityFound );								
								}
						}
				}
		}
		//--------------------------------------
		//--------------------------------------
		//--------------------------------------
		void echoInfoOn( Entity pEntity ){

				cEnemySelect = pEntity;
				// Declenche l'affichage ???
		}
		//--------------------------------------
		void display(){
				
				//				System.out.println( "GamerHuman.display" );

				cDisplayGamer.drawBuffer();
								
				// On appele l'interface graphique
				SwingUtilities.invokeLater( new Runnable(){
								public void run(){	
										cDisplayGamer.displayBuffer(); // demande/permet l'affichage 
								}								
						});				
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		void beginSelectionPosition( Entity pEntity, String pAction, PrototypeUnit pProtoUnit, ImageIcon pIcone ){

				System.out.println( ">>>>>>>>> beginSelectionPosition for :" + pAction );
												
				cCurrentEntity = pEntity;
				cCurrentPrototype= pProtoUnit;
										
				cSelectionPositionAction = pAction;
				cSelectionPositionIcon   = pIcone;

				// On appele l'interface graphique
				SwingUtilities.invokeLater( new Runnable(){
								public void run(){	
										cDisplayGamer.beginSelectionPosition(); 
								}								
						});
				
		}
		//------------------------------------------------
		void beginSelectionPosition( Entity pEntity, CommandIcon pCommand, PrototypeUnit pProtoUnit, ImageIcon pIcone ){

				System.out.println( ">>>>>>>>> beginSelectionPosition for :" + pCommand );
												
				cCurrentEntity = pEntity;
				cCurrentPrototype= pProtoUnit;
				cCurrentCommandIcon = pCommand;
										
				cSelectionPositionIcon   = pIcone;

				// On appele l'interface graphique
				SwingUtilities.invokeLater( new Runnable(){
								public void run(){	
										cDisplayGamer.beginSelectionPosition(); 
								}								
						});
		}
}

		
//*************************************************
