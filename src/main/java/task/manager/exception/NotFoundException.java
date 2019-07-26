package task.manager.exception;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

  private static final String WALLET_MESSAGE = "Wallet not found with id: ";
  private static final String MERCHANT_MESSAGE = "Merchant not found with id: ";
  private static final String PAYMENT_INSTRUMENT_MESSAGE = "Payment Instrument {0} not found for wallet id: {1}";

  private final Map<String, String> context;

  public NotFoundException(String message) {
    super(message);
    this.context = ImmutableMap.of("message", message);
  }
}
