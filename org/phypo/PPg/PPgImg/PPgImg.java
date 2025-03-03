package org.phypo.PPg.PPgImg;


import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;

import org.phypo.PPg.PPgUtils.PPgIniFile;


//*************************************************
public class PPgImg  implements  PPgImgBase {

		protected Image cImg = null;
		protected int cSemiWidth;
		protected int cSemiHeight;

		public PPgImg( String pStr ){
				cImg = new ImageIcon( pStr ).getImage();

				cSemiWidth  = cImg.getWidth(null)/2;
				cSemiHeight = cImg.getHeight(null)/2;
		}

		public PPgImg( Image pImg ){
				cImg = pImg;
				cSemiWidth  = cImg.getWidth(null)/2;
				cSemiHeight = cImg.getHeight(null)/2;
		}


		//=====================================
		@Override
		public int getWidth()  { return cImg.getWidth(null);  }
		@Override
		public int getHeight() { return cImg.getHeight(null); }

		@Override
		public int getAnimNbState()      { return 1; }
		@Override
		public int getAnimSizeSequence() { return 1; }
		@Override
		public double getAnimDuration()  { return 0.0; }

		@Override
		public void draw( Graphics2D pG, int pX, int pY, int pState, double pTimeSeq ) {
				pG.drawImage( cImg, pX-cSemiWidth, pY-cSemiHeight,  null);

				//				pG.setColor( Color.green );
				//				pG.fillOval( (int)(pX-1),  (int)(pY-1),	 2,2);


		}

	//------------------------------------------------
		public static Image ReadImg( PPgIniFile pIni, String pSection, String pName, HashMap<String, Image> pImgChache  ){
				String lName=null;

				if( pImgChache != null ){
						lName =  pSection+'.'+pName ;

						Image lImage = pImgChache.get(lName);
						if( lImage != null )
								return lImage;
				}

				Image lImage = PPgIniFile.ReadIcon( pIni,  pSection,  pName, null ).getImage();
				if(  pImgChache != null ){
						pImgChache.put( lName, lImage );
				}
				return lImage;
		}
	//------------------------------------------------

		public static PPgImgBase ReadAnim( PPgIniFile pIni, String pSection, String pName, HashMap<String, Image> pImgChache  ){


				String lStrAnim = pIni.get(  pSection, pName, null );
				if( lStrAnim == null )
						return null;

				int lIndex = lStrAnim.indexOf( ':' );
				String lStrImg = lStrAnim.substring(lIndex+1);
				String lStrParamAnim = lStrAnim.substring( 0, lIndex );



				System.out.println( "readAnim :" + lStrAnim + " ->"+lStrImg +"<->" + lStrParamAnim );

				Image lImg = ReadImg( pIni, pSection, lStrImg, pImgChache );
				if( lImg == null ){
						System.out.println( "readAnim img is  null" );
						return null;
				}

				String []lStrParam = lStrParamAnim.split( "," );

				System.out.println( "Split param :" +lStrParam.length );
				for (int i=0; i < lStrParam.length; i++)
						System.out.println( "\tSplit param :" + i + "="+ lStrParam[i] );

				if( lStrParam[0].equals( "ALL" ) ){

						//						System.out.println( "ALL" );

						int lW = Integer.parseInt( lStrParam[1] );
						int lH = Integer.parseInt( lStrParam[2] );
						int lNb = Integer.parseInt( lStrParam[3] );
						int lTime = Integer.parseInt( lStrParam[4] );

						System.out.println( "Param " + lStrParam[0] +  " W:" + lW + " H:" + lH + " Nb:" + lNb + " Time:" + lTime);

						return  new PPgAnimLinear( lImg, lW,lH, lNb, lTime );
				} else {
						System.out.println(	"Type image unknown : " +  lStrParam[0] );
			}


				return null;
		}

}
//*************************************************
