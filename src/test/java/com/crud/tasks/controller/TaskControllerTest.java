package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private DbService service;


    @Test
    void testGetTask() throws Exception {
        //Given
        Task task = new Task(1L, "Test Task", "testing");
        TaskDto taskDto = new TaskDto(1L, "Test Task", "testing");
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(service.getTask(1L)).thenReturn(Optional.of(task));

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/task/getTask?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test Task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("testing")));
    }

    @Test
    void testGetTasks() throws Exception{
        //Given
        Task task1 = new Task(1L, "Test Task 1", "testing 1");
        Task task2 = new Task(2L, "Test Task 2", "testing 2");
        TaskDto taskDto1 = new TaskDto(1L, "Test Task 1", "testing 1");
        TaskDto taskDto2 = new TaskDto(2L, "Test Task 2", "testing 2");
        List<Task> taskList = Arrays.asList(task1, task2);
        List<TaskDto> taskDtoList = Arrays.asList(taskDto1, taskDto2);

        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);
        when(service.getAllTasks()).thenReturn(taskList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Test Task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content", Matchers.is("testing 2")));
    }

    @Test
    void testDeleteTask() throws Exception{
        //Given
        Task task = new Task(1L, "Test Task", "testing");
        TaskDto taskDto = new TaskDto(1L, "Test Task", "testing");
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(service.getTask(1L)).thenReturn(Optional.of(task));
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/task/deleteTask?taskId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTask() throws Exception {

        //Given
        Task task = new Task(1L, "Test Task", "testing");
        TaskDto taskDto = new TaskDto(1L, "Test Task", "testing");
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(taskDto);
        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);
        when(service.saveTask(ArgumentMatchers.any(Task.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/task/updateTask?taskId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsContent))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test Task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("testing")));
    }

    @Test
    public void createTaskTest() throws Exception {

        //Given
        Task task = new Task(1L, "Test Task", "testing");
        TaskDto taskDto = new TaskDto(1L, "Task 1", "testing");
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String jsContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsContent))
                .andExpect(status().isOk());
    }
}