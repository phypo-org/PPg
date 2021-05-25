package org.phypo.PPg.PPgUtils;


import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;




//***********************************
public class PPgToken {

	String c_forend=" \n\t";
	String c_toeat=" \t\n";

	char   c_back='\0';
	char   c_sep;
	Reader c_reader;
	int    c_numline;
	boolean c_eatsep=true;

	//-----------------------------
	public char getSep()     {	return c_sep; }
	public int  numLine() { return c_numline; }
	//-----------------------------
	public PPgToken( Reader l_reader){
		c_reader = l_reader;
	}
	//-----------------------------
	public	PPgToken( Reader l_reader, String p_toeat, String p_forend ){
		c_reader = l_reader;
		c_forend = p_forend;
		c_toeat  = p_toeat;
	} 
	//-----------------------------
	public	PPgToken( String pStr, String p_toeat, String p_forend ){
		c_reader = new StringReader( pStr );
		c_forend = p_forend;
		c_toeat  = p_toeat;
	} 
	//-----------------------------
	public	PPgToken( String pStr ){
		c_reader = new StringReader( pStr );
	} 
	//------------
	public	int read(){
		int c='\0';
		try {

			if( c_back != '\0' )
			{
				c = (int)c_back;
				c_back = '\0';
			}
			else
				if( c_reader.ready() && (c=c_reader.read())!=-1)
				{
					//						System.out.println("PPgToken read :" +(char)c);
					return c;
				}
				else 
					return -1;
		}
		catch( IOException e )
		{
			System.out.println( "IO Error: " +e.getMessage() );
		}
		//		System.out.println("PPgToken read -:" +(char)c);
		return c;
	}
	//------------
	public	void putback( int c ){
		//		System.out.println( "putback:" + (char) c );
		c_back = (char)c;
	}
	//------------
	public	void eat(String p_toeat) {

		//		System.out.println("PPgToken eat");

		int ic;

		while( (ic=read()) != -1)
		{
			char c = (char)ic;
			if( c == '\n' )
				c_numline++;

			if( p_toeat.indexOf( c ) == -1 )
			{
				putback( c );
				break;
			}
		}
	}
	//------------
	public	StringBuilder nextToken(){
		return nextToken( c_toeat, c_forend );
	}
	//------------
	public	StringBuilder nextToken( String p_toeat, String p_forend ){		
		int rc;
		char c;
		c_sep ='\0';
		StringBuilder l_strbuf = new StringBuilder( 1000 );

		eat( p_toeat );

		while( (rc=read()) != -1 )
		{
			c = (char)rc;
			if( p_forend.indexOf( c )!=-1)
			{
				c_sep = c;
				if( c_eatsep == false)		putback( c );
				break;
			}

			if( c == '\n' )
				c_numline++;

			l_strbuf.append(  c );				
		}

		if( l_strbuf.length()  == 0 && rc == -1 )
			return null;

		//		System.out.println( "nextToken->" + l_strbuf );
		return l_strbuf;
	}
	//------------
	public	String nextTokenString( String p_toeat, String p_forend ){		
		StringBuilder lTmp =  nextToken(p_toeat, p_forend );
		if( lTmp == null) return "";
		return lTmp.toString();
	}
	//------------
	public	String nextTokenStringTrim( String p_toeat, String p_forend ){		
		StringBuilder lTmp =  nextToken(p_toeat, p_forend );
		if( lTmp == null) return "";
		return lTmp.toString().trim();
	}
	//------------
	public	String nextTokenString( ){		
		StringBuilder lTmp =  nextToken(c_toeat, c_forend);
		if( lTmp == null) return "";
		return lTmp.toString();
	}
	//------------
	public	String nextTokenStringTrim( ){		
		StringBuilder lTmp =  nextToken(c_toeat, c_forend );
		if( lTmp == null) return "";
		return lTmp.toString().trim();
	}
	//------------
	public List<String> getTrimStrings() {
		List<String> lList = new ArrayList<>();
		String lStr;
		while( (lStr = nextTokenStringTrim()).length() > 0) {
			lList.add(lStr);
		}
		return lList;
	}
	//------------
	public	StringBuilder getParagraph( String p_toeat, char p_begin, char p_end, boolean p_depsep ){

		eat( p_toeat );

		int l_deep=0;
		if( p_depsep )
			l_deep++;

		StringBuilder l_strbuf = new StringBuilder( 1000 );

		int rc;
		while( (rc=read()) != -1 ){
			if( p_begin == (char)(rc) )
			{
				l_deep++;
				if( l_deep == 1 ) continue;
			}
			else if( p_end == (char)(rc) )
				l_deep--;

			if( l_deep <= 0)
				break;

			l_strbuf.append( (char)rc );				
		}

		if( l_strbuf.length()  == 0)
			return null;

		//		System.out.println( "Paragraph=" + l_strbuf );
		return l_strbuf;
	}				
}
//***********************************
