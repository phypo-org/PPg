package org.phypo.PPg.PPgUtils;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

//***********************************
public class PPgTrace {
	public enum Type{ Void, Unknown, Debug,Verbose,Info,Busy,Warn,Error,Critic,Fatal};

		
		private static int sVerbose=0;
		private static int sDebug=0;
		
		static SimpleDateFormat sDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
		
		public static void SetVerbose( int iVerbose ) {
			sVerbose = iVerbose;
		}
		public static void SetDebug( int iDebug ) {
			sDebug = iDebug;
			if( sDebug > 0 ) {
				PPgLog.UseDbg();
			}				
		}
		public static int Debug() {
			return sDebug;	
		}
		public static int Verbose() {
			return sVerbose;	
		}
		public static boolean UseFile(String iFilename) {
			return PPgLog.UseFile(iFilename);	
		}
	
		
		public static PrintStream GetOut() { return PPgLog.Out; }
		public static PrintStream GetErr() { return PPgLog.Err; }

		// Attention ce n'est sans doute pas trés efficace
		// faire une autre fonction (faire le calcul nous meme ?)
		
		static public String CurrentDatetime() {
			Date date = new Date(System.currentTimeMillis());
			return sDateFormatter.format(date);
		}
		//-----------------------------
		
		public static void Trace( int pVerbose, String pToTrace ){
			if( sVerbose >= pVerbose  ) {
				PPgLog.Out.println( pToTrace );
			}					
		}
		public static void Trace( String pToTrace ){
				PPgLog.Out.println( pToTrace );		
		}
		//-----------------------------
		public static void TraceNl( int pVerbose,String pToTrace ){
			if( sVerbose >= pVerbose  ) {
				PPgLog.Out.print( pToTrace );
			}
		}		
		//-----------------------------
		public static void TraceNl( String pToTrace ){
			
			PPgLog.Out.print( pToTrace );
		}		
		//-----------------------------
		public static void InfoNl( String pToTrace ){
			PPgLog.Out.print( CurrentDatetime() + " --- : " + pToTrace );
		}		
		//-----------------------------
		public static void Info( String pToTrace ){
			PPgLog.Out.println( CurrentDatetime()+" --- : " + pToTrace );
		}		
		//-----------------------------
		public static void BusyNl( String pToTrace ){
			PPgLog.Out.print( CurrentDatetime() + " ... Busy : " + pToTrace );
		}		
		//-----------------------------
		public static void Busy( String pToTrace ){
			PPgLog.Out.println( CurrentDatetime()+" ... Busy : " + pToTrace );
		}		
		//-----------------------------
		//-----------------------------
		//-----------------------------
		public static void WarnNl( String pToTrace ){
			PPgLog.Err.print( CurrentDatetime()+" +++ Warning : " + pToTrace );
		}		
		//-----------------------------
		public static void Warn( String pToTrace ){
			PPgLog.Err.println( CurrentDatetime()+" +++ Warning : " + pToTrace );
		}		
		//-----------------------------
		public static void ErrNl( String pToTrace ){
			PPgLog.Err.print( CurrentDatetime()+" *** Error : " + pToTrace );
		}		
		//-----------------------------
		public static void Err( String pToTrace ){
			PPgLog.Err.println( CurrentDatetime()+" *** Error : " + pToTrace );
		}		
		//-----------------------------
		public static void CriticNl( String pToTrace ){
			PPgLog.Err.print( CurrentDatetime()+" *** Critical Error : " + pToTrace );
		}		
		//-----------------------------
		public static void Critic( String pToTrace ){
			PPgLog.Err.println( CurrentDatetime()+" *** Critical Error : " + pToTrace );
		}		
		//-----------------------------
		public static void FatalNl( String pToTrace ){
			PPgLog.Err.print( CurrentDatetime()+" *** Fatal Error : " + pToTrace );
		}		
		//-----------------------------
		public static void Fatal( String pToTrace ){
			PPgLog.Err.println( CurrentDatetime()+" Fatal Error : " + pToTrace );
		}		
		//-----------------------------
		public static void EndErrLn( String pToTrace ){
			PPgLog.Err.println( pToTrace );
		}		
		//-----------------------------
		//-----------------------------
		//-----------------------------
		public static void DbgNl( String pToTrace ){
			if( sDebug > 0 )
				PPgLog.Dbg.print( CurrentDatetime()+" @@@ Debug : " + pToTrace );
		}		
		public static void Dbg( String pToTrace ){
			if( sDebug >0 )
				PPgLog.Dbg.println( CurrentDatetime()+" @@@ Debug : " + pToTrace );
		}		
		//-----------------------------
		public static void Dbg2Nl( String pToTrace ){
			if( sDebug > 1 )
				PPgLog.Dbg.print( CurrentDatetime()+" @@@ Debug : " + pToTrace );
		}		
		public static void Dbg2( String pToTrace ){
			if( sDebug >1 )
				PPgLog.Dbg.println( CurrentDatetime()+" @@@ Debug : " + pToTrace );
		}		
		//-----------------------------
		public static void Dbg3Nl( String pToTrace ){
			if( sDebug > 2 )
				PPgLog.Dbg.print( CurrentDatetime()+" @@@ Debug : " + pToTrace );
		}		
		public static void Dbg3( String pToTrace ){
			if( sDebug >2 )
				PPgLog.Dbg.println( CurrentDatetime()+" @@@ Debug : " + pToTrace );
		}		
		//-----------------------------
		public static void Dbg4( String pToTrace ){
			if( sDebug >3 )
				PPgLog.Dbg.println( CurrentDatetime()+" @@@ Debug : " + pToTrace );
		}		
		public static void Dbg4Nl( String pToTrace ){
			if( sDebug >3 )
				PPgLog.Dbg.print( CurrentDatetime()+" @@@ Debug : " + pToTrace );
		}		
		//-----------------------------
		public static void Dbg5( String pToTrace ){
			if( sDebug >4 )
				PPgLog.Dbg.println( CurrentDatetime()+" @@@ Debug : " + pToTrace );
		}		
		public static void Dbg5Nl( String pToTrace ){
			if( sDebug >4 )
				PPgLog.Dbg.print( CurrentDatetime()+" @@@ Debug : " + pToTrace );
		}		
		//-----------------------------
		public static void DbgNl( int pLevel, String pToTrace ){
			if( sDebug >= pLevel )
				PPgLog.Dbg.print( CurrentDatetime()+" @@@ Debug : " + pToTrace );
		}		
		//-----------------------------
		public static void Dbg( int pLevel, String pToTrace ){
			if( sDebug >= pLevel )
				PPgLog.Dbg.println( CurrentDatetime()+" @@@ Debug : " + pToTrace );
		}		
	};
//***********************************
