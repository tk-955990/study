import java.net.*; // Socket��ɬ��

public interface ProtocolFactory {
  public Runnable createProtocol(Socket clntSock, Logger logger);
}
