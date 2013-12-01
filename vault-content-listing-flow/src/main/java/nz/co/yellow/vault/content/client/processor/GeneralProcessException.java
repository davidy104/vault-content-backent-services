package nz.co.yellow.vault.content.client.processor;

/**
 *
 * @author david
 *
 */
@SuppressWarnings("serial")
public class GeneralProcessException
    extends Exception {

  public GeneralProcessException() {
    super();
  }

  public GeneralProcessException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public GeneralProcessException(String message, Throwable cause) {
    super(message, cause);
  }

  public GeneralProcessException(Throwable cause) {
    super(cause);
  }

  public GeneralProcessException(String message) {
    super(message);
  }
}
