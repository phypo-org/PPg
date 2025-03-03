package org.phypo.PPg.PPgUtils;




//*************************************************
public class PPgRef<T>{

		public T cRef = null;

		public PPgRef( T pRef)    { cRef = pRef; }
		public PPgRef()           { }
		public T get()            { return cRef;}
		public void set( T pRef ) { cRef = pRef; }

}
//*************************************************
