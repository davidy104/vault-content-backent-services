package nz.co.yellow.vault.content.common.camel;

import org.apache.camel.Exchange;

public interface CamelRouteCallback {

	Object postProcess(Exchange exchange) throws Exception;
}
