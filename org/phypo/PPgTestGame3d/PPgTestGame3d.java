package org.phypo.PPgTestGame3d;

import com.jogamp.opengl.*;

import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.newt.event.*;
import com.jogamp.opengl.util.awt.TextRenderer;

import com.jogamp.opengl.glu.gl2.*;

import com.jogamp.nativewindow.util.Point;

import java.nio.*;
import java.util.*;
import java.awt.Font;
import java.text.DecimalFormat;

import org.phypo.PPg.PPgUtils.*;
import org.phypo.PPg.PPgMath.*;

import org.phypo.PPgGame3d.*;

//*************************************************

public class PPgTestGame3d  extends World3d {

    static boolean sDebugDrawGrids = false;



    static public PPgTestGame3d sTheTestGame;

    PPgRandom cRand = new PPgRandom(666);
	
    FactoryLevel cFactory;

    static FloatBuffer sFBuf = ByteBuffer.allocateDirect(256).asFloatBuffer();

    static float sPosition[] =       { 1.0f, 1.0f, 0.0f, 0.0f }; //  a l'infini
		


    TextRenderer cTextRender;
    //------------------------------------------------
    public  PPgTestGame3d(  DimFloat3 pSz, Engine pEngine ){
        super( pSz, pEngine );
				
        setFlagCollision( (byte)( EnumFaction.Blue.getCode() + EnumFaction.Green.getCode() + EnumFaction.Red.getCode()),  false );
				
        //				getGLWindow().addKeyListener( this );
        //				getGLWindow().addMouseListener( this );

				
    }
    //------------------------------------------------
    @Override
    public void callEngineInit(GL2 pGl ){

        System.out.println( "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT PPgG3d.callEngineInit TTTTTTTTTTTTTTTTT" );


        // FAIRE DES LUMIERE QUI DERIVE DE MODELBASE !!!!



        pGl.glClearColor( 0.20f, 0.20f, 0.2f, 0.0f );

	
        pGl.glEnable( GL2.GL_LIGHTING);
        pGl.glEnable( GL2.GL_LIGHT0);
        //				pGl.glEnable( GL2.GL_LIGHT1);

        //=============== Add camera =============
        {
            cKamera=  new Kamera3d( null, new Transform3d( new Float3(),  new Float3(),  new Float3( 2f, 2f, 2f)),
                                    -0.5, 0.5, -0.5, 0.5, -0.5, 0.5 );

						
            float w = World3d.sTheWorld.getWidth()*0.8f;
            float h = World3d.sTheWorld.getHeight()*0.8f;
            float d = World3d.sTheWorld.getDepth()*0.8f;
            cKamera.setOrtho( null, -h, h, -h, h, -h, h );

						
            //				cKamera.resetOrtho(null);

            /*
              cKamera.getTransf().move( new Float3( 0f,0f,0f ) );				
              cKamera.getTransf().rotate( new Float3( 0f,0f,0f ) );
              cKamera.getTransf().scale( new Float3( 2f, 1f, 1f ) );
            */

            //				-800, 800, -600, 600, -1, -1 );
            //				-800, 800, -600, 600, -500, 500 );
						
						
            //				cKamera=  new Kamera3d( null, new Transform3d( null, null, null),
            //																-800, 800, -600, 600, -1, -1 );
						
            //			cKamera.getTransf().rotate( new Float3( -45f, -10f, 0f ) );


            World3d.sTheWorld.setKamera(  new ActorLocation( 0.5f, 0.5f, -0.5f, EnumFaction.Neutral, cKamera ));

            World3d.sTheWorld.getUserControl().initFromKamera( cKamera );
            //			((HumanControl)World3d.sTheWorld.getUserControl()).makeActorGamer( false, 4f, 2,  Primitiv3d.SubNormalizeType.NORMALIZE );
            //		((HumanControl)World3d.sTheWorld.getUserControl()).makeActorGamer( false, -3f, 1,  Primitiv3d.SubNormalizeType.NORMALIZE_HALF_INIT );
            //	((HumanControl)World3d.sTheWorld.getUserControl()).makeActorGamer( true, 1f, 3,  Primitiv3d.SubNormalizeType.NORMALIZE_ONLY_INIT);

            //		cKam.getTransf().rotate( new Float3( 30f, 30f, 0f ) );
            //		cKam.getTransf().scale( new Float3( 0.1f, 0.1f, 0.1f ) );
            //
            //cKam.getTransf().move( new Float3( 0f, 0f, -10f ) );
        }

        cTextRender = new TextRenderer(new Font("SansSerif", Font.BOLD, 36));
        cTextRender.setColor( 0f, 1.0f, 0f, 0.5f );
        //	Demo(1);


        FactoryLevel.LoadGlobalTextures( pGl );
        ShipMaker.Init(  pGl );

    }
    //------------------------------------------------
    static float sGreyAmbient[]  =   { 0.5f,  0.5f, 0.5f, 1.0f };
    static float sWhiteDiffuse[]  =  { 1.0f,  1.0f, 1.0f, 1.0f };

    @Override
    public void callEngineDrawBegin(GL2 pGl){		

        //				System.out.println( "PPgG3d callEngineRenderGL.callEngineRenderGL" );
				
        ByteBuffer lTemp = ByteBuffer.allocateDirect(16);
        lTemp.order(ByteOrder.nativeOrder());
        /*
          pGl.glLightfv( GL2.GL_LIGHT0, GL2.GL_AMBIENT, sGreyAmbient, 0);
          pGl.glLightfv( GL2.GL_LIGHT0, GL2.GL_DIFFUSE, sWhiteDiffuse, 0);								
          pGl.glLightfv( GL2.GL_LIGHT0, GL2.GL_POSITION, sPosition, 0 );

          //				sFBuf.put( sModelAmbient, 0, 4 );
          //	pGl.glLightModel( GL2.GL_LIGHT_MODEL_AMBIENT, sFBuf);

				
          float yellowAmbientDiffuse[] = {1.0f, 1.0f, 0f, 1.0f};	
          float sPosition2[] = {-2.0f, 2.0f, -5.0f, 0.0f}; // a l'inifni

          pGl.glLightfv( GL2.GL_LIGHT1, GL2.GL_AMBIENT, yellowAmbientDiffuse, 0);
          pGl.glLightfv( GL2.GL_LIGHT1, GL2.GL_DIFFUSE,  yellowAmbientDiffuse, 0);
          pGl.glLightfv( GL2.GL_LIGHT1, GL2.GL_POSITION,  sPosition2, 0 );
        */
        Float4  ambient = new Float4( 0.1, 0.1, 0.1, 1.0 );				
        //		Float4  ambient = new Float4( 1.0, 1.0, 1.0, 1.0 );
        //	Float4  diffuse = new Float4( 0.9, 0.9, 0.9, 1.0 );
        Float4  diffuse = new Float4(  0.8, 0.8, 0.8, 1);
        //	Float4  lmodel_ambient = new Float4( 0.5, 0.5, 0.5, 0.5);

        // Float4 position = new Float4( -500.0, 500.0, 0.0, 0.0);
        Float4 position = new Float4( -200.0, 2000.0, 0.0, 0.0);
				
        pGl.glLightfv( GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient.get(), 0 );
        pGl.glLightfv( GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse.get(), 0 );
        pGl.glLightfv( GL2.GL_LIGHT0, GL2.GL_POSITION, position.get(), 0 );


        Float4  lmodel_ambient = new Float4( 0.5, 0.5, 0.5, 0.5);
        pGl.glLightModelfv( GL2.GL_LIGHT_MODEL_AMBIENT, lmodel_ambient.get(), 0);



        //				Primitiv3d.Green( pGl );

        //				Primitiv3d.DrawGridXY(  pGl, getWidth()/2, getHeight()/2, 3 );
				
        //		Primitiv3d.DrawRepere(  pGl, 10f, true );	

				
        pGl.glDisable(GL2.GL_LIGHTING);
        pGl.glColor3f( 0.0f, 0.0f, 0.9f );

        Primitiv3d lPrim = 	new Primitiv3d();
        Primitiv3d.SubParamDrawing lParam01 = lPrim.GetSubParamDrawing(  0, 1.0f, false, Primitiv3d.SubNormalizeType.NORMALIZE_NONE, GLUgl2.GLU_SILHOUETTE );


        //		Primitiv3d.Parallelepiped( getWidth(), getHeight(), getDepth(), lParam01 );

        pGl.glEnable(GL2.GL_LIGHTING);


        if( sDebugDrawGrids == true ){
            //	Primitiv3d.Green( pGl );
            Primitiv3d.DrawGridXZ(  pGl, getWidth()/2, getDepth()/2, 3 );
            Primitiv3d.DrawGridXY(  pGl, getWidth()/2, getHeight()/2, 3 );
            Primitiv3d.DrawGridYZ(  pGl, getHeight()/2, getDepth()/2, 3 );
						
            Primitiv3d.DrawRepere(  pGl, 10f, true );	
        }
				
    }
    //------------------------------------------------

    @Override
    public void worldCallEndSceneDraw(GL2 pGl) {
				
        System.out.println( "PPJ3d.worldCallEndSceneDraw" 
                            + " Actors:" + World3d.sTheWorld.getActorLocation().size()
                            + " CompilOBj:" + CompilObj.sNbCompilObjectInUse );

									
        if( DefaultUserControl3d.sTrace != null ){
            cTextRender.beginRendering(  Engine.sTheGlDrawable.getSurfaceWidth(), Engine.sTheGlDrawable.getSurfaceHeight());
            cTextRender.draw( DefaultUserControl3d.sTrace, 1, 30);
            cTextRender.endRendering();				
        }

        if( SpritePilot.sTrace != null ){
            cTextRender.beginRendering( Engine.sTheGlDrawable.getSurfaceWidth(), Engine.sTheGlDrawable.getSurfaceHeight());	
            cTextRender.draw( SpritePilot.sTrace, 1, 30);
            cTextRender.endRendering();					
        }
    }
    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------

    public static void main( String [] args ) {
 
        //				int lW      = PPgParam.GetInteger( args, "-w", 1280 );
        //				int lH      = PPgParam.GetInteger( args, "-h", 1024);
        int lW      = PPgParam.GetInteger( args, "-w", 1280);
        int lH      = PPgParam.GetInteger( args, "-h", 1024);
				
        String lStrFullScreen = PPgParam.GetString( args, "-S", null );
        String lSizeStr = lStrFullScreen;

        String lStrWindown = PPgParam.GetString( args, "-W", null );
        if( lSizeStr == null )
            lSizeStr = lStrWindown;



        if( lSizeStr != null ){

            if( lSizeStr.compareTo( "640x480" ) ==0 || lSizeStr.compareTo("1") ==0 ){
                lW = 640;
                lH = 480;
            }	else	if( lSizeStr.compareTo( "800x600") ==0 || lSizeStr.compareTo("2")==0 ){
                lW = 800;
                lH = 600;
            }	else	if( lSizeStr.compareTo( "1280x1024") ==0 || lSizeStr.compareTo("3")==0 ){
                lW = 1280;
                lH = 1024;
            }	else	if( lSizeStr.compareTo( "1920x1080") ==0 || lSizeStr.compareTo("4")==0 ){
                lW = 1920;
                lH = 1080;
            }	else	if( lSizeStr.compareTo( "1920x1200") ==0 || lSizeStr.compareTo("(5")==0 ){
                lW = 1920;
                lH = 1200;
            }
        }



        int lFps     = PPgParam.GetInteger( args, "-F", 30);

        World3d.sDebugFps = PPgParam.GetBoolean( args, "-f", false);



        int lBeginLevel  = PPgParam.GetInteger( args, "-L", 0 );

        boolean lSmooth = PPgParam.GetBoolean( args, "-s", false);

        //				PPgGame3d.sDebugDrawGrids = PPgParam.GetBoolean( args, "-g", false);

        //PB sur la taille de World et le rebond
        //	PB sur la vitesse : tous a gauche !

        Engine    lEngine = new Engine( "Test JOGL",  lW, lH,  lStrFullScreen != null, lSmooth, true  );
        sTheTestGame = new PPgTestGame3d( new DimFloat3( 100, 100, 100 ), lEngine );

        sTheTestGame.cFactory = new FactoryLevel( 0 );
        sTheTestGame.cFactory.setBeginLevel( lBeginLevel );

        World3d.Get().addActor( new SkyDomeStar( 1000 ));

        sTheTestGame.addVirtualActor( FactoryLevel.sTheFactoryLevel.getCurrentLevel() );				

        HumanControl lControl =  new HumanControl( sTheTestGame ); 
        sTheTestGame.setUserControl( lControl );
        SpritePilot.Make( lControl );


        lEngine.start( lFps );
    }
}
