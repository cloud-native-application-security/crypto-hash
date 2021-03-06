package com.example.payments;

import com.example.util.CryptoUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Component;

@Component
public class PaymentService {

  public void processRefunds(Path refundsFile) {
    try {
      byte[] refunds = Files.readAllBytes(refundsFile);
      String actualHash = CryptoUtils.sha256(refunds);

      String shaFilename = refundsFile.getFileName() + ".sha256";
      Path hashFile = refundsFile.resolveSibling(shaFilename);
      String exceptedHash = Files.readString(hashFile);

      if (!actualHash.equals(exceptedHash)) {
        throw new CorruptRefundFileException();
      }

      System.out.println("Issuing Refund to");
      System.out.println(Files.readString(refundsFile));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
