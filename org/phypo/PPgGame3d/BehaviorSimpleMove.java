package org.phypo.PPgGame3d;



//*************************************************

public class  BehaviorSimpleMove implements Behavior{
		
		public enum MoveType{
				LimitToWorld,
						MoebiusWorld,
						BounceOnLimit,
						DeleteOutOfWorld
						};

		MoveType cMoveType;

		public BehaviorSimpleMove( MoveType pMoveType ){
				cMoveType = pMoveType;
		}
		public void exec( ActorBase pActor ){
				switch( cMoveType ){
				case LimitToWorld:
						World3d.sTheWorld.limitToWorld( ((ActorLocation)pActor).cLocation );
						break;

				case MoebiusWorld:
						World3d.sTheWorld.moebiusWorld( ((ActorLocation)pActor).cLocation );
						break;

				case BounceOnLimit:
						World3d.sTheWorld.bounceOnWorldLimit( ((ActorMobil)pActor).cLocation, ((ActorMobil)pActor).cSpeed );
						break;

				case DeleteOutOfWorld:

						if( World3d.sTheWorld.outOfWorld(  ((ActorMobil)pActor).cLocation )){
								//	System.out.println( ">>>>>>>>>cWorldBox.outOfWorld:" + World3d.sTheWorld.cWorldBox + " location:"+ ((ActorMobil)pActor).cLocation );

								pActor.setDeleted();
						}
						break;
				}
		}
}
//*************************************************
