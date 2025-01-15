package guru.sfg.brewery.model.events;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerDto implements Serializable {

	private static final long serialVersionUID = 7410835340114833163L;

	@Null
	private UUID id;

	@Null
	private Integer version;

	@Null
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXXZ", shape = JsonFormat.Shape.STRING)
	private OffsetDateTime createdDate;

	@Null
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXXZ", shape = JsonFormat.Shape.STRING)
	private OffsetDateTime lastModifiedDate;

	@NotBlank
	@Size(min = 3, max = 100)
	private String beerName;

	@NotNull
	private String beerStyle;

	@NotNull
	private String upc;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NotNull
	@Positive
	private BigDecimal price;

	private Integer quantityOnHand;
}
