package Events;

import javax.media.jai.PlanarImage;
import java.util.EventObject;

public class OnImageChangeEvent extends EventObject {
    private PlanarImage planarImage;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public OnImageChangeEvent(Object source, PlanarImage planarImage) {
        super(source);
        this.planarImage = planarImage;
    }

    public PlanarImage getPlanarImage() {
        return planarImage;
    }
}
