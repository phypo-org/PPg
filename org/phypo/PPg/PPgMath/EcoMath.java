package org.phypo.PPg.PPgMath;

//*************************************************
// Dans les jeux video, on n'a pas toujours besoin
// d'une precision extraordinaire ! Masi on veut
// aller vite, plutot que calculer sinus et cosinus
// on va utiliser une table pour les calculer
//*************************************************

public class EcoMath {

		public static boolean sEco = false;

		static int sEcosize=360;
		static double sEcoDiv;


		static double []sEcoCos=null;
		static double []sEcoSin=null;

		static final double PI2 = Math.PI*2.0;

		public static void SetEco( boolean pEco ) {

				sEco = pEco;
				if(  sEcoCos == null ){

						sEcoCos = new double[sEcosize];
						sEcoSin = new double[sEcosize];

						sEcoDiv = PI2/sEcosize;

						for(int i = 0; i< sEcosize; i++ ){

								double lAngle = sEcoDiv*i;

								sEcoSin[i] = Math.sin( lAngle );
								sEcoCos[i] = Math.cos( lAngle );

								//			System.out.println( "i:" + i +" Angle:" +lAngle + " =" +sEcoSin[i] +" " + sEcoCos[i] );
						}
						for( int i=0;i < sEcosize; i++ )
								{
										int l = i % sEcosize ;
										//				System.out.print( "i:" +i + " l:" + l +" = " );
										//				System.out.println(		sEcoSin[l] );
								}
						sEcoDiv = 1.0/sEcoDiv;

				}
		}
		//------------------------------------------------
		public static double GetSin( double pAngleRadian ){

				pAngleRadian %= Math.PI;

				boolean lNegatif = false;
				if( pAngleRadian < 0 ) {
						pAngleRadian += PI2;
				}

				int lPos =(int)(( pAngleRadian*sEcoDiv));

				//			System.out.println( "pAngleRadian:" + pAngleRadian + " " +(int)( pAngleRadian*sEcoDiv) + " -> " +lPos );

				return sEcoSin[ lPos ];

		}


		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public static double GetCos( double pAngleRadian ){

				pAngleRadian %= Math.PI;
				if( pAngleRadian < 0 )
						pAngleRadian  += PI2;

				int lPos = (int)(pAngleRadian*sEcoDiv);
				//				System.out.println( "pAngleRadian:" + pAngleRadian + " " +(int)( pAngleRadian*sEcoDiv) + " -> " +lPos );

				return sEcoCos[ lPos ];
		}
		//------------------------------------------------
		public static double GetSinDeg( double pAngleDegre ){

				int lPos = (int)(pAngleDegre % sEcosize) ;

				return sEcoSin[ lPos ];
		}
		//------------------------------------------------
		public static double GetCosDeg( double pAngleDegre){

				int lPos = (int)(pAngleDegre % sEcosize) ;

				return sEcoCos[ lPos ];
		}
}

//*************************************************

