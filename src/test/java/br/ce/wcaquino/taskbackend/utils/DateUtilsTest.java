package br.ce.wcaquino.taskbackend.utils;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {
	
	@Test
	public void deveRetornarTrueParaDatasFuturas() {
		// Cenario
		LocalDate date = LocalDate.of(2030, 01, 01);
		
		// Ação
		boolean result = DateUtils.isEqualOrFutureDate(date);
		
		// Verificação
		Assert.assertTrue(result);
	}
	
	@Test
	public void deveRetornarFalseParaDatasPassadas() {
		// Cenario
		LocalDate date = LocalDate.of(2015, 01, 01);
		
		// Ação
		boolean result = DateUtils.isEqualOrFutureDate(date);
		
		// Verificação
		Assert.assertFalse(result);
	}
	
	@Test
	public void deveRetornarTrueParaDataAtual() {
		// Cenario
		LocalDate date = LocalDate.now();
		
		// Ação
		boolean result = DateUtils.isEqualOrFutureDate(date);
		
		// Verificação
		Assert.assertTrue(result);
	}
}
