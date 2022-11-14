package com.rasyid.tagihan.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagihanListrik implements Serializable {

	private static final long serialVersionUID = -860418733404429527L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull(message = "ID cannot be null")
	private Long id;
	
	@NotNull(message = "Jumlah Alat cannot be null")
	private Double jlmAlat;
	
	@NotNull(message = "Data Alat cannot be null")
	private Double dayaAlat;
	
	@NotNull(message = "Lama Pakai cannot be null")
	private Double lamaPakai;
}
