package it.enea.xlab.tebes.external.jolie.gjs;

import java.util.List;
import java.util.LinkedList;
import jolie.runtime.Value;
import jolie.runtime.ByteArray;

public class GetStatusResponse {
public class report {
private List<ErrorType> _error;

public report(Value v){

_error= new LinkedList<ErrorType>();
if (v.hasChildren("error")){
for(int countererror=0;countererror<v.getChildren("error").size();countererror++){
ErrorType supporterror = new ErrorType(v.getChildren("error").get(countererror));
_error.add(supporterror);
}
}
}
public report(){

_error= new LinkedList<ErrorType>();
}
public ErrorType getErrorValue(int index){

return _error.get(index);
}
public int getErrorSize(){

return _error.size();
}
public void addErrorValue(ErrorType value ){

_error.add(value);
}
public void removeErrorValue( int index ){
_error.remove(index);
}
public Value getValue(){
Value vReturn = Value.create();
if(_error!=null){
for(int countererror=0;countererror<_error.size();countererror++){
vReturn.getNewChild("error").deepCopy(_error.get(countererror).getValue());
}
}
return vReturn ;
}
}
private String _status;
private report _report;

public GetStatusResponse(Value v){

if (v.hasChildren("status")){
_status= v.getFirstChild("status").strValue();
}
if (v.hasChildren("report")){
_report = new report( v.getFirstChild("report"));
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
public report getReport(){

return _report;
}
public void setReport(report value ){

_report = value;
}
public Value getValue(){
Value vReturn = Value.create();
if((_status!=null)){
vReturn.getNewChild("status").setValue(_status);
}
if((_report!=null)){
vReturn.getNewChild("report").deepCopy(_report.getValue());
}
return vReturn ;
}
}
