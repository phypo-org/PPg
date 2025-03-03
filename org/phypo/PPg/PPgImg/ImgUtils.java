package org.phypo.PPg.PPgImg;



import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ImageObserver;
import java.awt.image.LookupOp;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.awt.image.RescaleOp;
import java.awt.image.ShortLookupTable;
import java.io.File;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;


//*************************************************
public class ImgUtils{


    //------------------------------------------------
    public static BufferedImage GetSameBufferImage( BufferedImage pBufIn ){

        return new BufferedImage( pBufIn.getWidth(),
                                  pBufIn.getHeight(),
                                  pBufIn.getType()); // TYPE_INT_ARGB_PRE
    }
    //------------------------------------------------
    public static BufferedImage GetSameBufferImage( BufferedImage pBufIn, int pW, int pH ){

        return new BufferedImage( pW,
                                  pH,
                                  pBufIn.getType()); // TYPE_INT_ARGB_PRE
    }
    //------------------------------------------------
    public  static BufferedImage CloneImage( BufferedImage pBufIn ){
        BufferedImage lBufOut = GetSameBufferImage( pBufIn );

        Graphics2D lG = lBufOut.createGraphics();

        lG.setComposite( AlphaComposite.Src );
        lG.drawImage( pBufIn, 0, 0,  null);
        lG.dispose();
        return lBufOut;
    }
    //------------------------------------------------
    public  static BufferedImage Merge( BufferedImage pBufIn1, Image pBufIn2){
        BufferedImage lBufOut = GetSameBufferImage( pBufIn1 );

        Graphics2D lG = lBufOut.createGraphics();

        lG.drawImage( pBufIn1, 0, 0,  null);
        lG.drawImage( pBufIn2, 0, 0,  null);
        lG.dispose();

        return lBufOut;
    }
    //------------------------------------------------
    public  static BufferedImage CopyToImage( Image pBufIn,  BufferedImage pBufOut){
        Graphics2D lG = pBufOut.createGraphics();

        lG.setComposite( AlphaComposite.Src );
        lG.drawImage( pBufIn, 0, 0,  null);
        lG.dispose();

        return pBufOut;
    }
    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------
    public static BufferedImage Convert2GrayScale( BufferedImage pBufIn ){

        BufferedImage lBufOut = GetSameBufferImage( pBufIn );

        ColorSpace lColSpace = ColorSpace.getInstance(ColorSpace.CS_GRAY);

        ColorConvertOp lTheOp = new ColorConvertOp( lColSpace, null);

        return lTheOp.filter( pBufIn, lBufOut );
    }
    //------------------------------------------------
    public static BufferedImage InvertColor( BufferedImage pBufIn, boolean pR, boolean pG, boolean pB, boolean pA ){

        BufferedImage lBufOut = GetSameBufferImage( pBufIn );

        short [] lInvTab = new short[256];
        short [] lNormTab = new short[256];

        for( int i=0; i< 256; i++ ){
            lInvTab[i]  = (short)((255 - i));
            lNormTab[i] = (short)i;
        }

        //					short[][] lTab4 = new short[][]{lNormTab, lInvTab,lInvTab,lInvTab};
        short[][] lTab4 = new short[][]{ (pR?lInvTab:lNormTab),
            (pG?lInvTab:lNormTab),
            (pB?lInvTab:lNormTab),
            (pA?lInvTab:lNormTab) };

        ShortLookupTable lLook = new ShortLookupTable( 0, lTab4 );
        BufferedImageOp lTheOp = new LookupOp( lLook, null);

        return lTheOp.filter( pBufIn, null );

    }
    //------------------------------------------------
    public static BufferedImage Posterize( BufferedImage pBufIn, int pFact, int pOffset ){

        //		pFact = 255 - pFact;

        BufferedImage lBufOut = GetSameBufferImage( pBufIn );

        short [] lPosTab = new short[256];
        short [] lNormTab = new short[256];

        for( int i=0; i< 256; i++ ){

            //					System.out.println( "i:" +i +" i/pfact=" +(i/pFact) + " 255/pFact=" + (255/pFact)
            //															+ "(i/pFact)*(255/pFact)=" +(i/pFact)*(255/pFact)  + "   offset:"  +pOffset );
            //
            //			lPosTab[i]  = (short) ((i/pFact)*(255/pFact)+pOffset);
            int lVal = (((i/pFact)*pFact)+pOffset);

            if( lVal > 255 ){
                lVal = 255;
            }
            lPosTab[i] = (short)lVal;
            lNormTab[i] = (short)i;
        }

        //					short[][] lTab4 = new short[][]{lNormTab, lInvTab,lInvTab,lInvTab};
        short[][] lTab4 = new short[][]{ lPosTab, lPosTab, lPosTab, lNormTab}; // RGBA ????

        ShortLookupTable lLook = new ShortLookupTable( 0, lTab4 );
        BufferedImageOp lTheOp = new LookupOp( lLook, null);

        return lTheOp.filter( pBufIn, null );

    }
    //------------------------------------------------
    public static BufferedImage PosterizeRGB( BufferedImage pBufIn, int [] pFact, int [] pOffset  ){

        //		pFact = 255 - pFact;

        BufferedImage lBufOut = GetSameBufferImage( pBufIn );

        short [][] lPosTab = new short[3][256];
        short [] lNormTab = new short[256];

        for( int i=0; i< 256; i++ ){
            for( int c=0; c<3; c++){

                //					System.out.println( "i:" +i +" i/pfact=" +(i/pFact) + " 255/pFact=" + (255/pFact)
                //															+ "(i/pFact)*(255/pFact)=" +(i/pFact)*(255/pFact)  + "   offset:"  +pOffset );
                //
                //			lPosTab[i]  = (short) ((i/pFact)*(255/pFact)+pOffset);
                int lVal = (((i/pFact[c])*pFact[c])+pOffset[c]);

                if( lVal > 255 ){
                    lVal = 255;
                }
                lPosTab[c][i] = (short)lVal;
            }
            lNormTab[i] = (short)i;
        }
        //					short[][] lTab4 = new short[][]{lNormTab, lInvTab,lInvTab,lInvTab};
        short[][] lTab4 = new short[][]{ lPosTab[0], lPosTab[1], lPosTab[2], lNormTab}; // RGBA ????

        ShortLookupTable lLook = new ShortLookupTable( 0, lTab4 );
        BufferedImageOp lTheOp = new LookupOp( lLook, null);

        return lTheOp.filter( pBufIn, null );                
    }
    //------------------------------------------------
    //------------------------------------------------
    public static BufferedImage FilterRGB2BW( BufferedImage pBufIn, int [] pSub, int pSeuil[]  ){
            
        BufferedImage lBufOut = GetSameBufferImage( pBufIn );

        /*

          final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
          final int width = image.getWidth();
          final int height = image.getHeight();
          final boolean hasAlphaChannel = image.getAlphaRaster() != null;
            
          int[][] result = new int[height][width];
          if (hasAlphaChannel) {
          final int pixelLength = 4;
                
          for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
          int argb = 0;
          argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
          argb += ((int) pixels[pixel + 1] & 0xff); // blue
          argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
          argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
          result[row][col] = argb;
          col++;
          if (col == width) {
          col = 0;
          row++;
          }
          }
          } else {
          final int pixelLength = 3;
          for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
          int argb = 0;
          argb += -16777216; // 255 alpha
          argb += ((int) pixels[pixel] & 0xff); // blue
          argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
          argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
          result[row][col] = argb;
          col++;
          if (col == width) {
          col = 0;
          row++;
          }
          }
          }
        */

            
        //		pFact = 255 - pFact;
           
        int w = pBufIn.getWidth();
        int h = pBufIn.getHeight();
        System.out.println( "\tw:"+w +"\th:"+h);

        Graphics2D lGraf = lBufOut.createGraphics();
       for( int y=0; y<h; y++ ){                      
            for( int x=0; x<w; x++){
                int lPix = pBufIn.getRGB( x, y );
                
                int b = lPix & 0xFF;
                int g = (lPix >> 8)  & 0xFF;
                int r = (lPix >> 16)  & 0xFF;
                //           System.out.print( "\t"+lPix+"->" + "r:" +r + " g:" +g + " b:" +b );
                lGraf.setColor( Color.red ); 
                  
                int lRes = 255;

                int r1 = r+pSub[0];
                if( r1 >255  ) r1 = 255;

                int g1 = g+ pSub[1];
                if( g1 >255 ) g1 = 255;

                int b1 = b+ pSub[2];
                if( b1 > 255 ) b1 = 255;

                if( r1 > pSeuil[0] ) r1 = 255;
                else r1 = 0;
                
                if( g1 > pSeuil[1] ) g1 = 255;
                else g1 = 0;
                
                if( b1 > pSeuil[2] ) b1 = 255;
                else b1 = 0;
                               
                
                lGraf.setColor( new Color( r1, g1, b1 ));
                
                lGraf.drawLine( x,y, x,y);                      
            }
        }
            
  
        
        return lBufOut;
    }
    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------
    public static BufferedImage RescaleOp( BufferedImage pBufIn, float[] pScale, float[] pOffset ){

        BufferedImage lBufOut = GetSameBufferImage( pBufIn );

        RescaleOp filterObj = new RescaleOp( pScale, pOffset, null );

        return filterObj.filter(  pBufIn, lBufOut );
    }
    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------
    public static BufferedImage FlipHor( BufferedImage pBufIn ){

        BufferedImage lBufOut = GetSameBufferImage( pBufIn );

        AffineTransform lTransform = AffineTransform.getScaleInstance(-1, 1);
        lTransform.translate( -pBufIn.getWidth(), 0);

        AffineTransformOp lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR );
        if( lOp.filter( pBufIn, lBufOut ) != null ) {
            return lBufOut;
        }
        return null;
    }
    //------------------------------------------------
    public static BufferedImage FlipVer( BufferedImage pBufIn ){

        BufferedImage lBufOut = GetSameBufferImage( pBufIn );


        AffineTransform lTransform = AffineTransform.getScaleInstance(1, -1);
        lTransform.translate( 0, -pBufIn.getHeight());

        AffineTransformOp lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR );
        if( lOp.filter( pBufIn, lBufOut ) != null ) {
            return lBufOut;
        }
        return null;
    }
    //------------------------------------------------
    public static BufferedImage RotQuadrant( BufferedImage pBufIn, int pQuad ){


        AffineTransform lTransform =
            AffineTransform.getQuadrantRotateInstance( pQuad, pBufIn.getWidth()/2, pBufIn.getHeight()/2 );

        AffineTransformOp lOp  = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_BILINEAR);

        Rectangle         lRect   = lOp.getBounds2D( pBufIn ).getBounds();


        System.out.println( " x=" + lRect.getX() + " y=" + lRect.getY() + " w=" + lRect.width + " h=" + lRect.height );


        BufferedImage     lBufOut = new BufferedImage( lRect.width, lRect.height,
                                                       pBufIn.getType());

        // Pour recadrer dans la nlle image
        switch ( pQuad & 3) {

        case 0 :  return pBufIn ;
        case 1 :
            lTransform.translate( lRect.getX(), -lRect.getY() );
            lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_BILINEAR);
            break;

        case 2 : break;

        case 3:
            lTransform.translate( -lRect.getX(), lRect.getY() );
            lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_BILINEAR);
            break;
        }


        lOp.createCompatibleDestImage( pBufIn, null );

        return lOp.filter( pBufIn, lBufOut );
    }

    //------------------------------------------------
    public static BufferedImage Rot( BufferedImage pBufIn, double pAngle ){

        AffineTransform lTransform = AffineTransform.getRotateInstance( pAngle );

        AffineTransformOp lOp  = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_BILINEAR);

        Rectangle         lRect   = lOp.getBounds2D( pBufIn ).getBounds();

        System.out.println( " x=" + lRect.getX() + " y=" + lRect.getY() + " w=" + lRect.width + " h=" + lRect.height );


        BufferedImage     lBufOut = new BufferedImage( lRect.width, lRect.height,
                                                       pBufIn.getType());

        int lX=lRect.x;
        int lY=lRect.y;

        //				if( lX < 0 ) lX = -lX;
        //				if( lY > 0 ) lY = -lY;

        lTransform.translate( -lX, -lY );
        lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_BILINEAR);


        lOp.createCompatibleDestImage( pBufIn, null );
        return lOp.filter( pBufIn, lBufOut );
    }
    //------------------------------------------------
    // Creer une image miroir par rapport a un axe vertical

    static public ImageIcon CreateFlipHorImage( ImageIcon pSrc ){



        try{
            BufferedImage lBufIn = new BufferedImage( pSrc.getIconWidth(), pSrc.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE); // TYPE_INT_ARGB_PRE
            BufferedImage lBufOut = new BufferedImage( pSrc.getIconWidth(), pSrc.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE); // TYPE_INT_ARGB_PRE

            Graphics2D g = lBufIn.createGraphics();
            g.setComposite( AlphaComposite.Src );
            //				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            //													 RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR ); // [NEAREST_NEIGHBOR BILINEAR BICUBIC]
            g.drawImage( pSrc.getImage(), 0, 0,  null);
            g.dispose();


            AffineTransform lTransform = AffineTransform.getScaleInstance(-1,1);
            lTransform.translate( -pSrc.getIconWidth(), 0);
            AffineTransformOp lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR );


            if(  lOp.filter( lBufIn, lBufOut ) != null ) {
                ImageIcon lImage = new ImageIcon(	lBufOut );
                return lImage;
            }
        }
        catch(Exception e ){
            System.err.println( "Error for CreateFlipHorImage");
            e.printStackTrace();
        }
        return null;
    }
    //-----------------------------------------------------------------
    static public ImageIcon CreateFlipScaleImage( ImageIcon pSrc, double pScaleX, double pScaleY ){

        try{
            BufferedImage lBufIn = new BufferedImage( pSrc.getIconWidth(), pSrc.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE); // TYPE_INT_ARGB_PRE
            BufferedImage lBufOut = new BufferedImage( pSrc.getIconWidth(), pSrc.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE); // TYPE_INT_ARGB_PRE

            Graphics2D g = lBufIn.createGraphics();
            g.setComposite( AlphaComposite.Src );
            //				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            //													 RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR ); // [NEAREST_NEIGHBOR BILINEAR BICUBIC]
            g.drawImage( pSrc.getImage(), 0, 0,  null);
            g.dispose();


            AffineTransform lTransform = AffineTransform.getScaleInstance(pScaleX, pScaleY);
            lTransform.translate( -pSrc.getIconWidth(), 0);
            AffineTransformOp lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR );


            if(  lOp.filter( lBufIn, lBufOut ) != null ) {
                ImageIcon lImage = new ImageIcon(	lBufOut );
                return lImage;
            }
        }
        catch(Exception e ){
            System.err.println( "CreateFlipScaleImage");
            e.printStackTrace();
        }
        return null;
    }
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------
    // Subtitutions de couleurs ...
    final static public int [] GetPixels(ImageIcon pSrc ) {

        int w = pSrc.getIconWidth();
        int h = pSrc.getIconHeight();

        int [] lPixels = new int [w*h];
        PixelGrabber pg = new PixelGrabber( pSrc.getImage(), 0, 0, w, h, lPixels, 0, w);
        try{
            pg.grabPixels();
        } catch( InterruptedException e){
            System.err.println( "GetPixels exception: " + e );
            return null;
        }

        if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
            System.err.println("GetPixels image fetch aborted or errored");
            return null;
        }
        return lPixels;
    }
    //-----------------------------------------------------------------
    final static public ImageIcon GetImage(int w, int h, int lPixels[]) {
        MemoryImageSource lMis = new MemoryImageSource( w, h, lPixels, 0, w );
        lMis.setAnimated( false );

        return new ImageIcon( Toolkit.getDefaultToolkit().createImage( lMis ));
    }
    //-----------------------------------------------------------------
    public interface ModifyPixelsMap{
        public void modify( int w, int h, int [] lPixels);
    }
    //-----------------------------------------------------------------
    abstract public class PixelsChange implements ModifyPixelsMap {

        abstract public int modifyPixel( int x, int y, int pixel );

        @Override
        public void modify( int w, int h, int [] lPixels) {
            for( int y=0; y<h; y++ ){
                int yw = y*w;
                for( int x=0; x<w; x++){
                    int lPixel = lPixels[x+yw];
                    lPixels[x+yw] = modifyPixel( x, y, lPixel );
                }
            }
        }
    }
    //-----------------------------------------------------------------
    // Pour la creation de sprite on a partir d'image sans transparence
    // On part du principe que la couleur de fond est homogéne et que les coins sont vides

    static public ImageIcon ForceTransparencie( ImageIcon pSrc ){

	//	System.out.println(" ============ FORCE_TRANSPARENCIE ======  "  );

        int [] lPixels = GetPixels( pSrc);
        if( lPixels == null ) return null;

        int w = pSrc.getIconWidth();
        int h = pSrc.getIconHeight();

        // On par du principe qu'en general les coins du sprite sont vides,
        // on va donc essayer de determiner la valeur transparente en prenant comme
        // valeur l'un d'entre eux ( en les testant deux par deux).

        int lTransparent =  lPixels[0];
        /*
          if( lPixels[0] == lPixels[w*h-1] ){
          lTransparent = lPixels[0];
          }
          else 	if( lPixels[0] == lPixels[w-1] ){
          lTransparent = lPixels[0];
          }
          else	if( lPixels[w-1] == lPixels[w*h-1] ){
          lTransparent = lPixels[w-1];
          } 	else return null;
        */

        //				System.out.printf( "lTransparent:%1$x", lTransparent );
        //				System.out.println();

        for( int y=0; y<h; y++ ){
            int yw = y*w;
            //						System.out.print( "\t"+y+"\t");
            for( int x=0; x<w; x++){

                int lPixel = lPixels[x+yw];
                //								System.out.printf( "%1$x ", lPixel );
                if( lPixel == lTransparent ) {
                    //										System.out.print( "-");
                    lPixels[x+y*w] =0; // Tout a zero notamment alpha
                }
                //								if( x == y )
                //										lPixels[x+y*w] =    0xffffff;
            }
            //								System.out.println();
        }

        return GetImage( w, h, lPixels);
    }
    //-----------------------------------------------------------------
    // Changes des couleurs par d'autres
    // pTarget and pDest must have same size

    final static public ImageIcon ChangeColors( ImageIcon pSrc, int pTargets[], int pDests[]){
        if( pTargets.length != pDests.length )
            return null;

        int [] lPixels = GetPixels( pSrc);
        if( lPixels == null ) return null;

        int w = pSrc.getIconWidth();
        int h = pSrc.getIconHeight();

        for( int y=0; y<h; y++ ){
            int yw = y*w;
            for( int x=0; x<w; x++){
                int lPixel = lPixels[x+y*w];
                for( int i =0; i< pTargets.length; i++ ) {
                    if( lPixel == pTargets[i] ) {
                        lPixels[x+yw] = pDests[i];
                        break;
                    }
                }
            } // xxx
        } // yyyyy
        return GetImage( w, h, lPixels);
    }
    //-----------------------------------------------------------------
    final static public  void ChangeBiColor(  int w, int h, int [] lPixels, int pTarget1, int pDest1, int pDest2){

        for( int y=0; y<h; y++ ){
            int yw = y*w;
            for( int x=0; x<w; x++){
                int lPixel = lPixels[x+yw];

                if( lPixel == 0) // on garde les transparents
                    continue;

                if( lPixel == pTarget1 ) {
                    lPixels[x+yw] = pDest1;
                }
                else { // les autres
                    lPixels[x+yw] = pDest2;
                }
            } // xxx
        } // yyyyy
    }
    //-----------------------------------------------------------------
    // Passe en bicolore en gardant la transparence
    static public ImageIcon ChangeBiColor( ImageIcon pSrc, int pTarget1, int pDest1, int pDest2){

        int [] lPixels = GetPixels( pSrc);
        if( lPixels == null ) return null;

        int w = pSrc.getIconWidth();
        int h = pSrc.getIconHeight();

        ChangeBiColor( w, h, lPixels, pTarget1, pDest1, pDest2 );

        return GetImage( w, h, lPixels);
    }
    //-----------------------------------------------------------------
    // Passe en bicolore en gardant la transparence
    static public ImageIcon ChangeImage( ImageIcon pSrc, ModifyPixelsMap iModify){

        int [] lPixels = GetPixels( pSrc);
        if( lPixels == null ) return null;

        int w = pSrc.getIconWidth();
        int h = pSrc.getIconHeight();

        iModify.modify( w, h, lPixels);

        return GetImage( w, h, lPixels);
    }
    //-----------------------------------------------------------------
    // Passe en bicolore en gardant la transparence
    static public ImageIcon ChangePixels( ImageIcon pSrc, PixelsChange iModify){

        int [] lPixels = GetPixels( pSrc);
        if( lPixels == null ) return null;

        int w = pSrc.getIconWidth();
        int h = pSrc.getIconHeight();

        iModify.modify( w, h, lPixels);

        return GetImage( w, h, lPixels);
    }
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------

    //------------------------------------------------
    // Creation d'une image par rotation d'une autre

    static public ImageIcon CreateRotateImage( ImageIcon pSrc, double pAngle ){

        try{
            BufferedImage lBufIn = new BufferedImage( pSrc.getIconWidth(), pSrc.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE); // TYPE_INT_ARGB_PRE
            BufferedImage lBufOut = new BufferedImage( pSrc.getIconWidth(), pSrc.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE); // TYPE_INT_ARGB_PRE

            Graphics2D g = lBufIn.createGraphics();
            g.setComposite( AlphaComposite.Src );
            //				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            //													 RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR ); // [NEAREST_NEIGHBOR BILINEAR BICUBIC]
            g.drawImage( pSrc.getImage(), 0, 0,  null);
            g.dispose();


            AffineTransform lTransform = AffineTransform.getRotateInstance( pAngle, pSrc.getIconWidth()/2.0, pSrc.getIconHeight()/2.0);
            AffineTransformOp lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR );

            if(  lOp.filter( lBufIn, lBufOut ) != null ) {
                ImageIcon lImage = new ImageIcon(	lBufOut );
                return lImage;
            }
        }
        catch(Exception e ){
            System.err.println( "Error for CreateFlipHorImage");
            e.printStackTrace();
        }
        return null;
    }
    //------------------------------------------------
    static public boolean WriteImage( BufferedImage pImage, File pFile, String pFormat ){

        Iterator lIterWriters = ImageIO.getImageWritersByFormatName(pFormat); // ou png
        ImageWriter lWriter = (ImageWriter)lIterWriters.next();


        try {

            ImageOutputStream llIOs = ImageIO.createImageOutputStream(pFile);
            lWriter.setOutput(llIOs);


            //    Finally, the image may be written to the output stream:

            lWriter.write(pImage);
        }
        catch(Exception e){
            System.err.println( e );
            return false;
        }
        return true;
    }
    //------------------------------------------------
    static public BufferedImage GetBufferedImage( ImageIcon pSrc ){
        BufferedImage lBuf = new BufferedImage( pSrc.getIconWidth(), pSrc.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE); // TYPE_INT_ARGB_PRE
        Graphics2D g = lBuf.createGraphics();
        g.setComposite( AlphaComposite.Src );
        g.drawImage( pSrc.getImage(), 0, 0,  null);
        g.dispose();
        return lBuf;
    }
    //------------------------------------------------
    static public boolean TransformBmp( File pFile, String pFormat ){

        if( pFile.getName().endsWith( ".bmp" )){

            File lNewName = new File(  pFile.getParent(), pFile.getName().substring( 0, pFile.getName().length()-4)+"."+pFormat);

            Image lImg = Bmp.Loadbitmap( pFile );
            if( lImg != null ){

                ImageIcon lIconImg = new ImageIcon( lImg );

                BufferedImage lBuf = GetBufferedImage( lIconImg );
                if( lBuf != null ){
                    return WriteImage( lBuf, lNewName, pFormat );
                }
                else
                    System.err.println( "convertion failed");
            }
            else
                System.err.println( "bmp read failed");
        }
        else
            System.err.println( "Name not finish by .bmp");
        return false;
    }
    //------------------------------------------------
    static public ImageIcon LoadImageFromFile( File pName, double pWidth, double pHeight, boolean pForceTransparencie, double pScale  ){

        ImageIcon lImageIcon=null;

        File lFile = new File( pName.toString() );
        if( !lFile.canRead() )
            return null;

        ImageIcon lTmpImage = null;
        if( pName.getName().endsWith( ".bmp" ) ){
            Image lImg = Bmp.Loadbitmap(pName );
            if( lImg == null )
                return null;

            lTmpImage = new ImageIcon( lImg );
        }
        else
            lTmpImage = new ImageIcon( pName.getPath() );

        //				if( pForceTransparencie ){
        //						lTmpImage = 	ForceTransparencie(lTmpImage );
        //				}

        //		System.out.println( "================== Width:" + pWidth + " pHeight:" +  pHeight + " pScale" + pScale );

        if( pWidth >0 && pHeight >0 ){
            lImageIcon = new ImageIcon( lTmpImage.getImage().getScaledInstance( (int)(pWidth*pScale),
                                                                                (int)(pHeight*pScale),
                                                                                Image.SCALE_SMOOTH ));

        }
        else
            if( pScale != 1 ){
                lImageIcon = new ImageIcon( lTmpImage.getImage().getScaledInstance( (int)(lTmpImage.getIconWidth()*pScale),
                                                                                    (int)(lTmpImage.getIconHeight()*pScale),
                                                                                    Image.SCALE_SMOOTH ));

            }
            else
                lImageIcon = 	lTmpImage;

        return lImageIcon;
    }
}
//*************************************************
