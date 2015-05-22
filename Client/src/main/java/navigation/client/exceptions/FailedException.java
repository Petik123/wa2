package navigation.client.exceptions;


import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Petikk on 16. 5. 2015.
 */
public class FailedException extends Exception implements IsSerializable {
    public FailedException(String message) {
        super(message);
    }


    public FailedException() {
        super();
    }
}
