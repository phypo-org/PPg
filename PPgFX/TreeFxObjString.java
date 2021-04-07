package org.phypo.PPg.PPgFX;



public class TreeFxObjString<KEY> implements TreeFXObjInterface<KEY>{
	String cLabel;
	
	@Override
	public String toString(){
		return cLabel;
	}
	
	public TreeFxObjString( String iLabel ) {
		cLabel = iLabel;
	}

	@Override
	public String getObjName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KEY getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
