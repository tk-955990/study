import java.net.*; // Socket‚É•K—v

public class EchoProtocolFactory implements ProtocolFactory {
  public Runnable createProtocol(Socket clntSock, Logger logger) {
	return new EchoProtocol(clntSock, logger);
  }
}
