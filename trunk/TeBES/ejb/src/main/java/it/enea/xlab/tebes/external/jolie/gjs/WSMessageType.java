package it.enea.xlab.tebes.external.jolie.gjs;

import java.util.LinkedList;
import java.util.List;

import jolie.runtime.Value;

public class WSMessageType {
	public class parameter {
		private Object _value;
		private String _key;

		public parameter(Value v){

			if (v.hasChildren("value")){
				if(v.getFirstChild("value").isDouble()){
					_value = v.getFirstChild("value").doubleValue();
				}else if(v.getFirstChild("value").isString()){
					_value = v.getFirstChild("value").strValue();
				}else if(v.getFirstChild("value").isInt()){
					_value = v.getFirstChild("value").intValue();
				}
			}
			if (v.hasChildren("key")){
				_key= v.getFirstChild("key").strValue();
			}
		}
		public parameter(){

		}
		public Object get__Value(){

			return _value;
		}
		public void set__Value(Object value ){

			_value = value;
		}
		public String getKey(){

			return _key;
		}
		public void setKey(String value ){

			_key = value;
		}
		public Value getValue(){
			Value vReturn = Value.create();
			if((_value!=null)){
				if(_value instanceof Integer){
					vReturn.getNewChild("value").setValue(((Integer)(_value)).intValue());
				}else if(_value instanceof Double){
					vReturn.getNewChild("value").setValue(((Double)(_value)).doubleValue());
				}else if(_value instanceof String){
					vReturn.getNewChild("value").setValue((String)(_value));
				}}
			if((_key!=null)){
				vReturn.getNewChild("key").setValue(_key);
			}
			return vReturn ;
		}
	}
	private List<parameter> _parameter;

	public WSMessageType(Value v){

		_parameter= new LinkedList<parameter>();
		if (v.hasChildren("parameter")){
			for(int counterparameter=0;counterparameter<v.getChildren("parameter").size();counterparameter++){
				parameter supportparameter=new parameter(v.getChildren("parameter").get(counterparameter));
				_parameter.add(supportparameter);
			}
		}
	}
	public WSMessageType(){

		_parameter= new LinkedList<parameter>();
	}
	public parameter getParameterValue(int index){

		return _parameter.get(index);
	}
	public int getParameterSize(){

		return _parameter.size();
	}
	public void addParameterValue(parameter value ){

		_parameter.add(value);
	}
	public void removeParameterValue( int index ){
		_parameter.remove(index);
	}
	public Value getValue(){
		Value vReturn = Value.create();
		if(_parameter!=null){
			for(int counterparameter=0;counterparameter<_parameter.size();counterparameter++){
				vReturn.getNewChild("parameter").deepCopy(_parameter.get(counterparameter).getValue());
			}
		}
		return vReturn ;
	}
}
