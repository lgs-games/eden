import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;
import java.util.Arrays;

public class TestIO {

    // https://socketio.github.io/socket.io-client-java/installation.html
    public static void main(String[] args) {
        URI uri = URI.create("http://localhost:3000");
        IO.Options options = IO.Options.builder()
                .setForceNew(false)
                .build();

        Socket socket = IO.socket(uri, options);
        socket.on("connect", (e) -> {
            System.out.println("socket "+socket.id()+" "+Arrays.toString(e));

            socket.emit("whoami", new Ack() {
                @Override
                public void call(Object... args) {
                    System.out.println(Arrays.toString(args));
                }
            });
        });

        socket.connect();
    }

}
