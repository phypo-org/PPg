package org.phypo.PPg.PPgFX;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageFxHelper {
	//-------------------------------------
	public static Image ChangeColor( Image iImg, Color iTarget, Color iRemplace ) {
		return ChangeBiColor( iImg, iTarget, iRemplace, null );
	}		
	//-------------------------------------
	public static Image ChangeBiColor( Image iImg, Color iTarget, Color iRemplace, Color iAltern ) {

		int lWidth  = (int)iImg.getWidth(); 
		int lHeight = (int)iImg.getHeight(); 

		//	Log.Dbg(  "ChangeBiColor w:"+ lWidth + " h:" + lHeight);

		WritableImage lWImage = new WritableImage(lWidth, lHeight); 
		PixelWriter lWriter = lWImage.getPixelWriter();           

		PixelReader lReader = iImg.getPixelReader(); 

		for(int y = 0; y < lHeight; y++) { 
			for(int x = 0; x < lWidth; x++) { 
				Color lColor = lReader.getColor(x, y); 
				if( lColor.equals( iTarget) ) {
					lWriter.setColor(x, y, iRemplace );
				}
				else
					if( iAltern != null ) {
					lWriter.setColor(x, y, iAltern );
					}
			}
		}
		return lWImage;		
	}	
	//-------------------------------------
	public static Image ChangeBiColor( Image iImg, Color iTarget, Color iRemplace, Color iTarget2, Color iRemplace2) {
		return ChangeBiColor( iImg, iTarget, iRemplace, iTarget2, iRemplace2, null );
	}	
	public static Image ChangeBiColor( Image iImg, Color iTarget, Color iRemplace, Color iTarget2, Color iRemplace2, Color iAltern) {

		int lWidth  = (int)iImg.getWidth(); 
		int lHeight = (int)iImg.getHeight(); 

		//	Log.Dbg(  "ChangeBiColor w:"+ lWidth + " h:" + lHeight);

		WritableImage lWImage = new WritableImage(lWidth, lHeight); 
		PixelWriter lWriter = lWImage.getPixelWriter();           

		PixelReader lReader = iImg.getPixelReader(); 

		for(int y = 0; y < lHeight; y++) { 
			for(int x = 0; x < lWidth; x++) { 
				Color lColor = lReader.getColor(x, y); 
				if( lColor.equals( iTarget) ){				
					lWriter.setColor(x, y, iRemplace );
				}
				else 
					if( lColor.equals( iTarget2) ) {
						lWriter.setColor(x, y, iRemplace2 );
				}
				else {
						if( iAltern != null )
						lWriter.setColor(x, y, iAltern ); 
					}
				}
		}
		return lWImage;		
	}	



}
