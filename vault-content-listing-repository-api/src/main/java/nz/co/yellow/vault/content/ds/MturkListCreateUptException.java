package nz.co.yellow.vault.content.ds;

/**
 * specific exception for procedure calling errors
 *
 * @author david
 *
 */
@SuppressWarnings("serial")
public class MturkListCreateUptException
    extends Exception {

  public MturkListCreateUptException() {
    super();
  }

  public MturkListCreateUptException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public MturkListCreateUptException(String message, Throwable cause) {
    super(message, cause);
  }

  public MturkListCreateUptException(String message) {
    super(message);
  }

  public MturkListCreateUptException(Throwable cause) {
    super(cause);
  }

}
