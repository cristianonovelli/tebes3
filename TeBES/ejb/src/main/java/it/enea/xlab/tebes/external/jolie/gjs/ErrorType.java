package it.enea.xlab.tebes.external.jolie.gjs;


import jolie.runtime.Value;

public class ErrorType {
	private String _errorType;
	private String _msg;

	public ErrorType(Value v){

		if (v.hasChildren("errorType")){
			_errorType= v.getFirstChild("errorType").strValue();
		}
		if (v.hasChildren("msg")){
			_msg= v.getFirstChild("msg").strValue();
		}
	}
	public ErrorType(){

	}
	public String getErrorType(){

		return _errorType;
	}
	public void setErrorType(String value ){

		_errorType = value;
	}
	public String getMsg(){

		return _msg;
	}
	public void setMsg(String value ){

		_msg = value;
	}
	public Value getValue(){
		Value vReturn = Value.create();
		if((_errorType!=null)){
			vReturn.getNewChild("errorType").setValue(_errorType);
		}
		if((_msg!=null)){
			vReturn.getNewChild("msg").setValue(_msg);
		}
		return vReturn ;
	}
}
