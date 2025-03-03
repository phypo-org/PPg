package org.phypo.PPg.PPgUtils;

//***********************************
public class PPgParam {

	//---------------------------------
	public static String GetString( String[] args, String p_prefix, String pDefault ){

		if( args == null )	return pDefault;

		int lSz = p_prefix.length();
			//			System.out.println( "");
			//		System.out.println( "GetString : " +  args.length + " : " + p_prefix  );

		for( int i=0; i<  args.length; ++i){
			String arg = args[i];

		//System.out.print( " | " + arg );

			if( arg.startsWith( p_prefix )){
				args[i] = "";
				return arg.substring( lSz );
			}
		}
		return pDefault;
	}
	//---------------------------------
	public static boolean ExistParam( String[] args, String p_prefix){

		if( args == null )	return false;

		int l = p_prefix.length();

		//				System.out.println( "");
		//				System.out.println( "\nExistParam : "  +  args.length +  " : " + p_prefix  );

		for( int i=0; i<  args.length; i++){

			String arg = args[i];

			//						System.out.print( " | " + arg );

			if( arg.startsWith( p_prefix )){
				args[i] = "";

				return true;
			}
		}
		return false;
	}
	//---------------------------------

	public static Integer GetInteger( String[] args, String p_prefix, Integer pDefault){

		if( args == null )	return pDefault;

		String lVar = GetString( args, p_prefix, null );
		//				System.out.println(" :::: Var:" + lVar+";" );

		if( lVar != null ){

			try{
				return Integer.decode( lVar ) ;
			}catch(NumberFormatException ex){
				System.err.println( "bad format commande:"+p_prefix);
				return pDefault;
			}
		}
		return pDefault;
	}
	//---------------------------------
	public static boolean GetBoolean( String[] args, String p_prefix, boolean pDefault ){

		if( args == null )	return pDefault;

		if( ExistParam(  args, p_prefix ))
			return true;

		return pDefault;
	}
	//---------------------------------
	public static int GetInt( String[] args, String p_prefix, int pDefault){

		if( args == null )	return pDefault;

		String lVar = GetString( args, p_prefix, null );
		//				System.out.println(" :::: Var:" + lVar+";" );

		if( lVar != null ){

			try{
				return Integer.parseInt( lVar);
			}catch(NumberFormatException ex){
				System.err.println( "Mauvais format pour commande "+p_prefix);
			}
		}
		return pDefault;
	}
	//---------------------------------
	public static double GetDouble( String[] args, String p_prefix, double pDefault){

		if( args == null )	return pDefault;

		String lVar = GetString( args, p_prefix, null );

		if( lVar != null ){

			try{
				return Double.parseDouble( lVar );
			}catch(NumberFormatException ex){
				System.err.println( "Mauvais format pour commande "+p_prefix);
			}
		}
		return pDefault;
	}
	//---------------------------------
	public static float GetFloat( String[] args, String p_prefix, float pDefault){

		if( args == null )	return pDefault;

		String lVar = GetString( args, p_prefix, null );


		if( lVar != null ){

			try{

				return Float.parseFloat( lVar );
			}catch(NumberFormatException ex){
				System.err.println( "Mauvais format pour commande "+p_prefix);
			}
		}
		return pDefault;
	}
}

//*************************************************
