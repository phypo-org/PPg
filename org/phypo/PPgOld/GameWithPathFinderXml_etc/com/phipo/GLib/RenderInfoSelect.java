package com.phipo.GLib;

import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

 

//*************************************************
// Gete l'affichage 2D du monde modelise

public class RenderInfoSelect extends  RenderBase{

		Font cPolice = new Font( "Monospaced", Font.PLAIN, 14 );
		int cX = 0;
		int cY = 0;
		int cHeightFont;


	
		//------------------------------------------------

		public RenderInfoSelect(GamerHuman pGamer){
				super( pGamer );
		}
		//---------------------
		public void paint() {

				if( World.sFurtifMode == false )
						cGC.setColor( Color.black );
				else
						cGC.setColor( Color.white );
						

				cGC.fillRect( 0, 0, (int)cSize.getWidth(),   (int)cSize.getHeight() );

				cGC.setFont(cPolice);
				cHeightFont = cGC.getFontMetrics().getMaxAscent() + cGC.getFontMetrics().getMaxDescent() +2;
				cX = 2;
				cY = cHeightFont/2;
				cGC.setColor( Color.green );

				Point2D.Double lSizeView = cGamer.getSizeView();

				if( cGamer.getSelection().size() > 0 ){
						if( cGamer.getSelection().size() == 1 )
								drawEntityInfo( cGamer.getSelection().get(0));
						else
								drawSelectInfo( cGamer.getSelection() );
				}
				else if( cGamer.getEnemySelect() != null )
						drawEntityInfo( cGamer.getEnemySelect() );
				else
						if( cGamer.getCaseSelect() != null )
								drawCaseInfo( cGamer.getCaseSelect() );
				
		}
		//------------------------------------------------
		void drawHealthBar( Entity pEntity, int pX, int pY, int pWidth, int pHeight ){

				float lSante = (float)(pEntity.cLife/pEntity.getPrototype().cLife);
				if( lSante <= 0.2 )
						cGC.setColor( Color.red );
				else
						if( lSante <= 0.5 )
								cGC.setColor( Color.orange );
						else
								if( lSante <= 0.8 )
										cGC.setColor( Color.yellow );
								else
										cGC.setColor( Color.green );
				
				
				cGC.drawRect( pX, pY, pWidth, pHeight );
				cGC.fillRect(  pX, pY, (int)(pWidth*lSante), pHeight );
		}
		//------------------------------------------------
		void drawEntityInfo( Entity pEntity) {

				PrototypeUnit lProtoUnit = pEntity.getPrototype();
				
				if( World.sFurtifMode == false ){
						pEntity.getIcon().paintIcon( null, cGC, cX, cY);	
						drawHealthBar( pEntity, cX, cY-6, pEntity.getIcon().getIconWidth(), 5 );
				}

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
				cY += cHeightFont;
				cGC.drawString( "Conmstuct Work:"+ pEntity.getWorkingConstruct()
												+ " In:" + pEntity.getInConstruct(),
												cX, cY );				

				cY += cHeightFont;
				cGC.drawString( "Construct:" + (int)lProtoUnit.cCostWork 
												+" SelSit:" + lProtoUnit.cSelectProductSite 
												+ " mob:" +lProtoUnit.isMobil()
												+ " Const:"+pEntity.cInConstruct,
												cX, cY );	


				// En fonction de l'action courante afficher une icone en relation !!!
				cY += cHeightFont;
				cGC.drawString( pEntity.getStringCurrentMissionInfo(),
												cX, cY );	

				
				
		}
		//---------------------
		void drawSelectInfo( java.util.List<Entity> pList ) {
				
				// FAIRE COMME SPELLFORCE si trop d'unite mettre un nombre sur les unites
				// si vraiment trop passer en smallicon !!!
			 
				// Ajouter la deselection des unites en cliquant sur un des boutons
				
						int lNbMaxIconX = (int)(cSize.getWidth()/(World.sSizeSmallIcon+1));
						int lNbMaxIconY = (int)(cSize.getHeight()/(World.sSizeSmallIcon+1+4));
						
						int cY = 1+4;
						for( int y=0; y<lNbMaxIconY; y++ ){
								int cX = 1;
								for( int x=0; x<lNbMaxIconX; x++ ){
										int k = y*lNbMaxIconX+x;
										
										if( k >= cGamer.getSelection().size() ){
												return;
										}
										Entity lEntity = cGamer.getSelection().get(k);
										
										if( World.sFurtifMode == false )
												lEntity.getPrototype().getSmallIcon().paintIcon( null, cGC, cX, cY);	
										else
												cGC.drawRect( cX, cY, World.sSizeSmallIcon, World.sSizeSmallIcon  );

										drawHealthBar( lEntity, cX, cY-4, lEntity.getPrototype().getSmallIcon().getIconWidth(), 3 );
										
										cX += World.sSizeSmallIcon+1+4;
								}
								cY += World.sSizeSmallIcon+1;
						}
					
						cGC.drawString( "" + cGamer.getSelection().size(),	cX, cY );
		}
		//---------------------
		void drawCaseInfo( PathCase pCase ){
				
				PrototypeCase lProtoCase = pCase.getProto();
				
				
				cGC.drawString( lProtoCase.getName(),
												cX, cY );
				
				cY += cHeightFont;

				cGC.drawString( "Position:" + cGamer.getLastPoint(),
												cX, cY );
				
				cY += cHeightFont;
				cGC.drawString( "Middle:"
												+ pCase.getMiddleMeterX() 
												+ ","
												+ pCase.getMiddleMeterY(),
												cX, cY );				

				cY += cHeightFont;
				cGC.drawString( "Vision:" + lProtoCase.cVisionBonus,
												cX, cY );	

				cY += cHeightFont;
				cGC.drawString( "Fight:" + lProtoCase.cFightBonus,
												cX, cY );	
		}
}
//*************************************************
