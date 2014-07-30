package it.enea.xlab.tebes.external.jolie.gjs;


import java.util.List;
import java.util.LinkedList;
import jolie.runtime.Value;
import jolie.runtime.ByteArray;

public class WSMessageType {

    public class parameter {

        private String _value;
        private String _key;

        public parameter(Value v) {

            if (v.hasChildren("value")) {
                _value = v.getFirstChild("value").strValue();
            }
            if (v.hasChildren("key")) {
                _key = v.getFirstChild("key").strValue();
            }
        }

        public parameter() {
        }

        public String get__Value() {

            return _value;
        }

        public void set__Value(String value) {

            _value = value;
        }

        public String getKey() {

            return _key;
        }

        public void setKey(String value) {

            _key = value;
        }

        public Value getValue() {
            Value vReturn = Value.create();
            if ((_value != null)) {
                vReturn.getNewChild("value").setValue(_value);
            }
            if ((_key != null)) {
                vReturn.getNewChild("key").setValue(_key);
            }
            return vReturn;
        }
    }
    private List<parameter> _parameter;

    public WSMessageType(Value v) {

        _parameter = new LinkedList<parameter>();
        if (v.hasChildren("parameter")) {
            for (int counterparameter = 0; counterparameter < v.getChildren("parameter").size(); counterparameter++) {
                parameter supportparameter = new parameter(v.getChildren("parameter").get(counterparameter));
                _parameter.add(supportparameter);
            }
        }
    }

    public WSMessageType() {

        _parameter = new LinkedList<parameter>();
    }

    public parameter getParameterValue(int index) {

        return _parameter.get(index);
    }

    public int getParameterSize() {

        return _parameter.size();
    }

    public void addParameterValue(parameter value) {

        _parameter.add(value);
    }

    public void removeParameterValue(int index) {
        _parameter.remove(index);
    }

    public Value getValue() {
        Value vReturn = Value.create();
        if (_parameter != null) {
            for (int counterparameter = 0; counterparameter < _parameter.size(); counterparameter++) {
                vReturn.getNewChild("parameter").deepCopy(_parameter.get(counterparameter).getValue());
            }
        }
        return vReturn;
    }
}
