package Events;

import java.io.File;
import java.util.EventObject;

public class OnFileChangeEvent extends EventObject {
    private File file;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public OnFileChangeEvent(Object source, File file) {
        super(source);
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
