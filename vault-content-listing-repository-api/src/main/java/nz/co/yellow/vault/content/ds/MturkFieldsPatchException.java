package nz.co.yellow.vault.content.ds;

/**
 * Specific exception for Patch error, used for Exception handle
 *
 * @see MturkListPatchExceptionProcessor
 *
 * @author david
 *
 */
@SuppressWarnings("serial")
public class MturkFieldsPatchException
    extends Exception {

  public MturkFieldsPatchException() {
    super();
  }

  public MturkFieldsPatchException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public MturkFieldsPatchException(String message, Throwable cause) {
    super(message, cause);
  }

  public MturkFieldsPatchException(String message) {
    super(message);
  }

  public MturkFieldsPatchException(Throwable cause) {
    super(cause);
  }

}
