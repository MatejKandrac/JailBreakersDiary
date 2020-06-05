package org.jailbreakers.obj;

public interface ConnectionEvent {

    void onConnect();
    void onConnectionError(String message);

}
