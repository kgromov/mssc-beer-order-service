package guru.sfg.brewery.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateBeerOrderRequest implements Serializable {
    private static final long serialVersionUID = 3147755645006185461L;

    private BeerOrderDto beerOrderDto;
}
