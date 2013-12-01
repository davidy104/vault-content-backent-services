package nz.co.yellow.vault.content.ds;

/**
 * specific exception for procedure calling errors
 *
 * @author david
 *
 */
@SuppressWarnings("serial")
public class MturkFieldsCreateUptException
    extends Exception {

  public MturkFieldsCreateUptException() {
    super();
  }

  public MturkFieldsCreateUptException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public MturkFieldsCreateUptException(String message, Throwable cause) {
    super(message, cause);
  }

  public MturkFieldsCreateUptException(String message) {
    super(message);
  }

  public MturkFieldsCreateUptException(Throwable cause) {
    super(cause);
  }

}
