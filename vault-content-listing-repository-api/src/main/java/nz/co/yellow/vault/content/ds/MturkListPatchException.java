package nz.co.yellow.vault.content.ds;

/**
 * Specific exception for Patch error, used for Exception handle
 *
 * @author david
 *
 */
@SuppressWarnings("serial")
public class MturkListPatchException
    extends Exception {

  public MturkListPatchException() {
    super();
  }

  public MturkListPatchException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public MturkListPatchException(String message, Throwable cause) {
    super(message, cause);
  }

  public MturkListPatchException(String message) {
    super(message);
  }

  public MturkListPatchException(Throwable cause) {
    super(cause);
  }

}
