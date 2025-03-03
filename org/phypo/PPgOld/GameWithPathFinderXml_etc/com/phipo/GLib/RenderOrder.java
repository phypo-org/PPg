package com.phipo.GLib;

import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;
import java.awt.geom.*;

 

//*************************************************
// Gete l'affichage 2D du monde modelise

public class RenderOrder extends RenderBase{

		Font cPolice = new Font( "Monospaced", Font.PLAIN, 14 );
		int cX = 0;
		int cY = 0;
		int cHeightFont;
	

		//------------------------------------------------

		public RenderOrder(GamerHuman pGamer){
				super( pGamer );
		}
		//---------------------
		public void paint(  ) 
		{
				if( World.sFurtifMode == false )
						cGC.setColor( Color.black );
				else
						cGC.setColor( Color.white );

				cGC.fillRect( 0, 0, (int)cSize.getWidth(),   (int)cSize.getHeight() );

				cGC.setColor( Color.blue );
				cGC.drawRect( 0, 0, (int)cSize.getWidth(),   (int)cSize.getHeight() );

				cGC.setFont(cPolice);
				cHeightFont = cGC.getFontMetrics().getMaxAscent() + cGC.getFontMetrics().getMaxDescent() +2;
				cX = 2;
				cY = cHeightFont/2;
				cGC.setColor( Color.green );

				cGamer.setCurrentVectCommandIcon( null );

				if( cGamer.getSelection().size() > 0 ){
						if( cGamer.getSelection().size() == 1 )
								drawEntityOrder( cGamer.getSelection().get(0) );
						else
								drawSelectOrder( cGamer.getSelection());
				}
				else if( cGamer.getEnemySelect() != null )
						drawEnemyOrder( cGamer.getEnemySelect());
				else
						if( cGamer.getCaseSelect() != null )
								drawCaseOrder( cGamer.getCaseSelect() );

				////				cGC.setColor( Color.black );
		}
		//---------------------
		void drawProductUnit(){
		}

		//---------------------
		void drawEntityOrder( Entity pEntity ) {

				ArrayList<CommandIcon> lVectCommandIcon = pEntity.getCommandIcon( cGamer, cMyPanel );
				cGamer.setCurrentVectCommandIcon( lVectCommandIcon );
				

				if( lVectCommandIcon == null ){
						PrototypeUnit lProtoUnit = pEntity.getPrototype();
						
						if( World.sFurtifMode == false )
								pEntity.getIcon().paintIcon( null, cGC, cX, cY);	
						else
								 cGC.drawRect( cX, cY, pEntity.getIcon().getIconWidth(), pEntity.getIcon().getIconHeight());	

						cX += 10+pEntity.getIcon().getIconWidth();
						

						cGC.drawString( lProtoUnit.getName() + " "
														+ pEntity.getGamerId()+":"
														+ pEntity.getGroupId(), 
														cX, cY);
						
						cY += cHeightFont;
						
						cGC.drawString( "Life:" + (int)pEntity.cLife +"/" + (int)lProtoUnit.cLife,
														cX, cY );
						
						cY += cHeightFont;
						cGC.drawString( "Mana:" + (int)pEntity.cMana +"/" + (int)lProtoUnit.cMana,
														cX, cY );	
						return;
				}
								
				
				int lNbMaxIconX = (int)(cSize.getWidth()/(World.sSizeSmallIcon+1));
				int lNbMaxIconY = (int)(cSize.getHeight()/(World.sSizeSmallIcon+1));

				int lMaxIcon = lNbMaxIconX*lNbMaxIconY;

				//				System.out.println( "lMaxIcon:" + lMaxIcon + " Size:" + lVectCommandIcon.size() );

				cGC.setColor( Color.white );
				int cY = 1;
				for( int y=0; y<lNbMaxIconY; y++ ){
						int cX = 1;
						for( int x=0; x<lNbMaxIconX; x++ ){
								int k = y*lNbMaxIconX+x;

								if( k >= lVectCommandIcon.size() ){
										return;
								}

								CommandIcon lCommandIcon = lVectCommandIcon.get( k );
								
								if( lCommandIcon.getIcon() == null ){
										cGC.drawRect(  cX, cY, World.sSizeSmallIcon, World.sSizeSmallIcon );
								}
								else
										lCommandIcon.getIcon().paintIcon( null, cGC, cX, cY);	

								lCommandIcon.setRect( cX, cY, World.sSizeSmallIcon, World.sSizeSmallIcon );

								cGC.drawString( ""+k , cX, cY );

								cX += World.sSizeSmallIcon+1;
											 
						}					
						cY += World.sSizeSmallIcon+1;
				}

		}
		//---------------------
		void drawEnemyOrder( Entity pEntity ) {

				PrototypeUnit lProtoUnit = pEntity.getPrototype();

				if( World.sFurtifMode == false ){
				}					 			

				//		drawBasicIcon( Entity pEntity );
				//		if( lProtoUnit.
		}
		//---------------------
		void drawSelectOrder( java.util.List<Entity> pList ) {
		}
		//---------------------
		void drawCaseOrder( PathCase pCase){
		}

}
//*************************************************
