package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {
	
	@Mock
	private TaskRepo taskRepo;
	
	@InjectMocks
	private TaskController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
//		controller = new TaskController();
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricai() {
		// Cenario
		Task todo = new Task();
		todo.setDueDate(LocalDate.now());
		
		
		try {
			// Ação
			controller.save(todo);
			Assert.fail();
		} catch (ValidationException e) {			
			// Verificao
			Assert.assertEquals("Fill the task description", e.getMessage());
		}		
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		// Cenario
		Task todo = new Task();
		todo.setTask("Teste de task");
		
		
		try {
			// Ação
			controller.save(todo);
			Assert.fail();
		} catch (ValidationException e) {			
			// Verificao
			Assert.assertEquals("Fill the due date", e.getMessage());
		}	
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		// Cenario
		Task todo = new Task();
		todo.setTask("Teste de task");
		todo.setDueDate(LocalDate.of(2023, 02, 02));		
		
		try {
			// Ação
			controller.save(todo);
			Assert.fail();
		} catch (ValidationException e) {			
			// Verificao
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}	
	}
	
	@Test
	public void deveSalvarTarefaComSUcesso() throws ValidationException {
		
		// Cenario
		Task todo = new Task();
		todo.setTask("Teste de task");
		todo.setDueDate(LocalDate.now());
		
		// Ação
		controller.save(todo);
		
		// Verificao
		Mockito.verify(taskRepo).save(todo);
	}
}
