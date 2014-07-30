package it.enea.xlab.tebes.external.jolie.gjs;

import java.util.List;
import java.util.LinkedList;
import jolie.runtime.Value;
import jolie.runtime.ByteArray;

public class GenerateServerWSResponse {
private String _wsdlEndpoint;

public GenerateServerWSResponse(Value v){

if (v.hasChildren("wsdlEndpoint")){
_wsdlEndpoint= v.getFirstChild("wsdlEndpoint").strValue();
}
}
public GenerateServerWSResponse(){

}
public String getWsdlEndpoint(){

return _wsdlEndpoint;
}
public void setWsdlEndpoint(String value ){

_wsdlEndpoint = value;
}
public Value getValue(){
Value vReturn = Value.create();
if((_wsdlEndpoint!=null)){
vReturn.getNewChild("wsdlEndpoint").setValue(_wsdlEndpoint);
}
return vReturn ;
}
}
