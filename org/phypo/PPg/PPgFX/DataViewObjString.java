package org.phypo.PPg.PPgFX;



public class DataViewObjString implements DataViewObj{
	String       cLabel;
	Object       cUserData = null;
	
	@Override
	public String toString(){
		return cLabel;
	}
	
	@Override
	public String getLabel() {
		return  cLabel;
	}
	
	public DataViewObjString( String iLabel ) {
		this( iLabel, null );
	}
	public DataViewObjString( String iLabel, Object iUserData ) {
		cLabel = iLabel;
		cUserData = iUserData;
	}

	public Object getUserData() { return cUserData; }

	@Override
	public String getObjName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFrom(DataViewObj iObj) {
		cLabel = iObj.getLabel() ;
		
	}
	
}
