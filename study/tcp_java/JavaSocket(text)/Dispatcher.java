import java.net.*; // ServerSocket��ɬ��

public interface Dispatcher {
  public void startDispatching(ServerSocket servSock, Logger logger,
                               ProtocolFactory protoFactory);
}
