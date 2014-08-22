package it.enea.xlab.tebes.external.jolie.gjs;

import jolie.runtime.Value;

public class GetStatusResponse {
	private String _status;

	public GetStatusResponse(Value v){

		if (v.hasChildren("status")){
			_status= v.getFirstChild("status").strValue();
		}
	}
	public GetStatusResponse(){

	}
	public String getStatus(){

		return _status;
	}
	public void setStatus(String value ){

		_status = value;
	}
	public Value getValue(){
		Value vReturn = Value.create();
		if((_status!=null)){
			vReturn.getNewChild("status").setValue(_status);
		}
		return vReturn ;
	}
}
