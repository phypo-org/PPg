package org.phypo.PPgTestGame3d;

import org.phypo.PPgGame3d.*;


//*************************************************
class FactoryActor{

		static double sBeginPosX = 0;
		static double sBeginPosY = 0;
		static double sBeginPosZ = 0;
		static double sSpeed = -15;


		 public static Primitiv3d sPrimitiv = 	new Primitiv3d();

		public enum ActorType{
				ActorWeaponType(1),
						ActorAsteroideType(2),
						ActorExplosionType(3),
						ActorShipType(4),
			    	ActorBonusType(5),
						ActorGamerType(6);
				public final int cCode;
				ActorType( int pCode) {cCode=pCode;}
				};



		//------------------------------------------------
		public static ActorMobil MakeAsteroidAA( Float3 pLoc, EnumFaction pFaction, int lComplexity, float lSize,	Transform3d PTransf, Aspect3d pAspect ){

				System.out.println( "FactoryActor.MakeAsteroid " + lSize);

				Primitiv3d.SubParamObject3d lParam01 = Primitiv3d.GetSubParamObject3d ( lComplexity, lSize,  false, Primitiv3d.SubNormalizeType.NORMALIZE_INC_SUB );

				/*
				Object3d lObjOdron = ((Primitiv3d.SubParamObject3d)Primitiv3d.Odron( lParam01 )).getObject3d();
				Leaf3d lLeaf1 = new Leaf3d( lObjOdron, PTransf, pAspect );

				ActorMobil lActor = new ActorMobil( pLoc, pFaction , lLeaf1);	

				*/

				Object3d  lO1  = Primitiv3d.Pyramid4( 0,0,0, lSize,        lSize,       lParam01).getObject3d();
				Object3d  lO2 =  Primitiv3d.Pyramid4( 0,0,0, lSize*1.01f,  lSize*1.01f, lParam01).getObject3d();

				Aspect3d lAspect1 = new Aspect3d( new Color4( 0.3f, 0.4f, 0.5f, 1f ), Aspect3d.DrawStyle.FILL );
				Aspect3d lAspect2 = new Aspect3d( new Color4( 0.2, 0.3, 0, 0.5 ), Aspect3d.DrawStyle.LINE );
				
				ModelBase pO1 = new CompilObj( lO1);
			 	ModelBase pO2 = new CompilObj( lO2);

				Transform3d lTransf = new Transform3d( null, new Float3(), null );
				Node3d lNode  = new Node3d( );
				Leaf3d lLeaf1 = new Leaf3d( lO1, lTransf, lAspect1, true );
				lNode.addChild( lLeaf1 );
				Leaf3d lLeaf2 = new Leaf3d( lO2, lTransf, lAspect2, true );								
				lNode.addChild( lLeaf2 );
				ActorMobil lActor = new ActorMobil(  pLoc, pFaction,	lNode);
				

				lActor.setBoundingSphere( lSize );
				return lActor;
		}
		/*
				lParam01.cHoleFacet = 4;
				lParam01.cHoleDepth = 2;

				lParam01.cDepthGrowFactor = 0.5f;
				lParam02.cDepthGrowFactor = 0.5f;

				switch( lType ){
						case 	Pyramid3: break;
						case 	Pyramid4: break;
						case 	Cube: break;
						case 	Parallelepiped: break;
						case 	Tetrahedron: break;
						case 	Octahedron: break;
						case 	Icosahedron: break;
						case 	Dodecahedron: break;
						case 	Cylinder: break;
						case 	Cone: break;
						case 	Disk: break;
						case PartialDisk: break;
						case Sphere: break;
				}
		*/
};
