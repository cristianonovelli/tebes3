package it.enea.xlab.tebes.external.jolie.gjs;

import java.util.List;
import java.util.LinkedList;
import jolie.runtime.Value;
import jolie.runtime.ByteArray;

public class StopRequest {

    private String _idService;

    public StopRequest(Value v) {

        if (v.hasChildren("idService")) {
            _idService = v.getFirstChild("idService").strValue();
        }
    }

    public StopRequest() {
    }

    public String getIdService() {

        return _idService;
    }

    public void setIdService(String value) {

        _idService = value;
    }

    public Value getValue() {
        Value vReturn = Value.create();
        if ((_idService != null)) {
            vReturn.getNewChild("idService").setValue(_idService);
        }
        return vReturn;
    }
}
