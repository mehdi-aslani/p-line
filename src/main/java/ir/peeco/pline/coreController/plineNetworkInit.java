package ir.peeco.pline.coreController;

import org.asteriskjava.fastagi.AgiServerThread;
import org.asteriskjava.fastagi.DefaultAgiServer;
import org.asteriskjava.live.DefaultAsteriskServer;
import org.asteriskjava.manager.ManagerConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class plineNetworkInit {

  private ManagerConnection managerConnection;
  private DefaultAsteriskServer asteriskServer;

  @Value("${pline.host}")
  private String plineHost;

  @Value("${pline.port}")
  private int plinePort;

  @Value("${pline.username}")
  private String plineUsername;

  @Value("${pline.password}")
  private String plinePassword;

  @Value("${pline.timeout}")
  private long plineTimeout;

  @Value("${pline.listenPort}")
  private int listenPort;

  @Value("${pline.poolSize}")
  private int poolSize;

  private DefaultAgiServer agiServer;

  private static AgiServerThread agiServerThread;

  @Bean
  public CommandLineRunner ExtensionCallHandeler() {
    return args -> {
      // ManagerConnectionFactory factory = new ManagerConnectionFactory(host, port,
      // username, password);
      // managerConnection = factory.createManagerConnection();
      // asteriskServer = new DefaultAsteriskServer(managerConnection);
      // asteriskServer.initialize();

      ExtensionCall call = new ExtensionCall();
      agiServer = new DefaultAgiServer(call);
      agiServer.setPoolSize(listenPort);
      agiServer.setPort(poolSize);
      agiServerThread = new AgiServerThread(agiServer);
      agiServerThread.startup();
    };
  }

}
