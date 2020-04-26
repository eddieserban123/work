package org.demo.kafka.trading;

import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Log4j2
public class TransactionInfo {

    String name;
    Integer amount;
    LocalDateTime time;

}
