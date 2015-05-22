package navigation.client.exceptions;


import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Petikk on 16. 5. 2015.
 */
public class BadRequestException extends Exception implements IsSerializable {
    public BadRequestException(String message) {
        super(message);
    }


    public BadRequestException() {
        super();
    }
}
