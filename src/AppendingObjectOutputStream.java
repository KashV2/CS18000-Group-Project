import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

// Custom ObjectOutputStream that skips the header for appending
public class AppendingObjectOutputStream extends ObjectOutputStream implements Interface.AppendingObjectOutputStreamInterface {
    public AppendingObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        // Do nothing, skip writing the header
    }
}
