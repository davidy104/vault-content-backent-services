package nz.co.yellow.vault.content.common;

public interface IntegrationProducer {

  Object produce(final Payload payload)
      throws Exception;
}
