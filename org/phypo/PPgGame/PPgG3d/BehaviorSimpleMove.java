package org.phypo.PPgGame.PPgG3d;



//*************************************************

public class  BehaviorSimpleMove implements Behavior{
		
		public enum MoveType{
				LimitToWorld,
						MoebiusWorld,
						BounceOnLimit
						};

		MoveType cMoveType;

		public BehaviorSimpleMove( MoveType pMoveType ){
				cMoveType = pMoveType;
		}
		public void exec( ActorBase pActor ){
				switch( cMoveType ){
				case LimitToWorld:
						World.sTheWorld.limitToWorld( ((ActorLocation)pActor).cLocation );
						break;
				case MoebiusWorld:
						World.sTheWorld.moebiusWorld( ((ActorLocation)pActor).cLocation );
						break;
				case BounceOnLimit:
						World.sTheWorld.bounceOnWorldLimit( ((ActorMobil)pActor).cLocation, ((ActorMobil)pActor).cSpeed );
						break;
				}
		}
}
//*************************************************
