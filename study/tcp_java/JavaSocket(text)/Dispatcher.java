import java.net.*; // ServerSocket¤ËÉ¬Í×

public interface Dispatcher {
  public void startDispatching(ServerSocket servSock, Logger logger,
                               ProtocolFactory protoFactory);
}
