package it.enea.xlab.tebes.external.jolie.gjs;

import java.util.List;
import java.util.LinkedList;
import jolie.runtime.Value;
import jolie.runtime.ByteArray;

public class GenerateClientWSRequest {
private String _port;
private String _operation;
private WSMessageType _requestMessage;
private String _messageProfile;
private String _wsdl;
private String _idService;
private String _path;
private String _filename;

public GenerateClientWSRequest(Value v){

if (v.hasChildren("port")){
_port= v.getFirstChild("port").strValue();
}
if (v.hasChildren("operation")){
_operation= v.getFirstChild("operation").strValue();
}
if (v.hasChildren("requestMessage")){
_requestMessage = new WSMessageType( v.getFirstChild("requestMessage"));
}
if (v.hasChildren("messageProfile")){
_messageProfile= v.getFirstChild("messageProfile").strValue();
}
if (v.hasChildren("wsdl")){
_wsdl= v.getFirstChild("wsdl").strValue();
}
if (v.hasChildren("idService")){
_idService= v.getFirstChild("idService").strValue();
}
if (v.hasChildren("path")){
_path= v.getFirstChild("path").strValue();
}
if (v.hasChildren("filename")){
_filename= v.getFirstChild("filename").strValue();
}
}
public GenerateClientWSRequest(){

}
public String getPort(){

return _port;
}
public void setPort(String value ){

_port = value;
}
public String getOperation(){

return _operation;
}
public void setOperation(String value ){

_operation = value;
}
public WSMessageType getRequestMessage(){

return _requestMessage;
}
public void setRequestMessage(WSMessageType value ){

_requestMessage = value;
}
public String getMessageProfile(){

return _messageProfile;
}
public void setMessageProfile(String value ){

_messageProfile = value;
}
public String getWsdl(){

return _wsdl;
}
public void setWsdl(String value ){

_wsdl = value;
}
public String getIdService(){

return _idService;
}
public void setIdService(String value ){

_idService = value;
}
public String getPath(){

return _path;
}
public void setPath(String value ){

_path = value;
}
public String getFilename(){

return _filename;
}
public void setFilename(String value ){

_filename = value;
}
public Value getValue(){
Value vReturn = Value.create();
if((_port!=null)){
vReturn.getNewChild("port").setValue(_port);
}
if((_operation!=null)){
vReturn.getNewChild("operation").setValue(_operation);
}
if((_requestMessage!=null)){
vReturn.getNewChild("requestMessage").deepCopy(_requestMessage.getValue());
}
if((_messageProfile!=null)){
vReturn.getNewChild("messageProfile").setValue(_messageProfile);
}
if((_wsdl!=null)){
vReturn.getNewChild("wsdl").setValue(_wsdl);
}
if((_idService!=null)){
vReturn.getNewChild("idService").setValue(_idService);
}
if((_path!=null)){
vReturn.getNewChild("path").setValue(_path);
}
if((_filename!=null)){
vReturn.getNewChild("filename").setValue(_filename);
}
return vReturn ;
}
}
