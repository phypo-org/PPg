package org.phypo.PPg.PPgUtils;

//********************************
public class Tuple {

	public static Tuple Instance= new Tuple();
	//=============================
	public class One<T1> {	
		protected T1 cT1 = null;
		public One( T1 iT1)    { cT1 = iT1; }		
		public One()           {; }		

		public T1 get1()            { return cT1;}
		public void set1( T1 iT1 ) { cT1 = iT1; }
	};	
	//=============================
	public class Two<T1,T2> {

		protected T1 cT1 = null;
		protected T2 cT2 = null;

		public Two( T1 iT1, T2 iT2)    { cT1 = iT1; cT2 = iT2; }		
		public Two()           {; }		

		public T1 get1()            { return cT1;}
		public void set1( T1 iT )   { cT1 = iT; }


		public T2 get2()            { return cT2;}
		public void set2( T2 iT )   { cT2 = iT; }
	}
	//=============================
	public class Three<T1,T2,T3> {

		protected T1 cT1 = null;
		protected T2 cT2 = null;
		protected T3 cT3 = null;

		public Three( T1 iT1, T2 iT2, T3 iT3)    { cT1 = iT1; cT2 = iT2; cT3=iT3; }		
		public Three()           {; }		

		public T1 get1()            { return cT1;}
		public void set1( T1 iT )   { cT1 = iT; }

		public T2 get2()            { return cT2;}
		public void set2( T2 iT )   { cT2 = iT; }

		public T3 get3()            { return cT3;}
		public void set3( T3 iT )   { cT3 = iT; }
	}	
	//=============================
	public class Four<T1,T2,T3,T4> {

		protected T1 cT1 = null;
		protected T2 cT2 = null;
		protected T3 cT3 = null;
		protected T4 cT4 = null;

		public Four( T1 iT1, T2 iT2, T3 iT3, T4 iT4 )    { cT1 = iT1; cT2 = iT2; cT3=iT3; cT4=iT4; }		
		public Four()           {; }		

		public T1 get1()            { return cT1;}
		public void set1( T1 iT )   { cT1 = iT; }

		public T2 get2()            { return cT2;}
		public void set2( T2 iT )   { cT2 = iT; }

		public T3 get3()            { return cT3;}
		public void set3( T3 iT )   { cT3 = iT; }
		
		public T4 get4()            { return cT4;}
		public void set4( T4 iT )   { cT4 = iT; }
	}	
}
//********************************

