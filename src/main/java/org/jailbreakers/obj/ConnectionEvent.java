package org.jailbreakers.obj;

public interface ConnectionEvent {

    public void onConnect();
    public void onConnectionError(String message);

}
