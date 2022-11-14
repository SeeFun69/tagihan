package com.rasyid.tagihan.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagihanListrikDto implements Serializable {

	private static final long serialVersionUID = -9203641612966948638L;

	@NotNull(message = "Jumlah Alat cannot be null")
	private Double jlmAlat;
	
	@NotNull(message = "Data Alat cannot be null")
	private Double dayaAlat;
	
	@NotNull(message = "Lama Pakai cannot be null")
	private Double lamaPakai;
}
