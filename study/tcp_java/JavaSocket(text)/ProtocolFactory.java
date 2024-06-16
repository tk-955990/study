import java.net.*; // Socket¤ËÉ¬Í×

public interface ProtocolFactory {
  public Runnable createProtocol(Socket clntSock, Logger logger);
}
