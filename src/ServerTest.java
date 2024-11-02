import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    private Method createServerSocketMethod;
    private Method createClientMethod;
    private Method closeServerSocketMethod;

    @BeforeEach
    void setUp() throws Exception {
        // Access private methods in the Server class using reflection
        createServerSocketMethod = Server.class.getDeclaredMethod("createServerSocket");
        createServerSocketMethod.setAccessible(true);

        createClientMethod = Server.class.getDeclaredMethod("createClient", ServerSocket.class);
        createClientMethod.setAccessible(true);

        closeServerSocketMethod = Server.class.getDeclaredMethod("closeServerSocket", ServerSocket.class);
        closeServerSocketMethod.setAccessible(true);
    }

    @Test
    void testCreateServerSocket_Success() {
        try {
            ServerSocket serverSocket = (ServerSocket) createServerSocketMethod.invoke(null);
            assertNotNull(serverSocket, "Server socket should be created successfully.");
            if (serverSocket != null) {
                serverSocket.close(); // Clean up after test
            }
        } catch (Exception e) {
            fail("Exception should not have been thrown during server socket creation: " + e.getMessage());
        }
    }

    @Test
    void testCreateClient_Success() {
        try {
            ServerSocket stubServerSocket = new StubServerSocket();
            Socket clientSocket = (Socket) createClientMethod.invoke(null, stubServerSocket);
            assertNotNull(clientSocket, "Client socket should be created successfully.");
            if (clientSocket != null) {
                clientSocket.close(); // Clean up after test
            }
        } catch (Exception e) {
            fail("Exception should not have been thrown during client socket creation: " + e.getMessage());
        }
    }

    @Test
    void testCloseServerSocket_Success() {
        try {
            ServerSocket stubServerSocket = new StubServerSocket();
            closeServerSocketMethod.invoke(null, stubServerSocket);

            // Check if the stub server socket is closed
            assertTrue(stubServerSocket.isClosed(), "Server socket should be closed after invoking closeServerSocket.");
        } catch (Exception e) {
            fail("Exception should not have been thrown during server socket closure: " + e.getMessage());
        }
    }

    // Inner class to simulate a ServerSocket for testing
    private static class StubServerSocket extends ServerSocket {
        private boolean isClosed = false;

        public StubServerSocket() throws IOException {
            super(); // Call the parent constructor
        }

        @Override
        public Socket accept() {
            return new Socket(); // Return a dummy socket for testing purposes
        }

        @Override
        public synchronized void close() {
            isClosed = true;
        }

        @Override
        public boolean isClosed() {
            return isClosed;
        }
    }
}
