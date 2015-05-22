package navigation.client.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Petikk on 21. 5. 2015.
 */

public class CannotBeModifiedException extends Exception implements IsSerializable {
    public CannotBeModifiedException(String message) {
        super(message);
    }

    public CannotBeModifiedException() {
        super();
    }
}