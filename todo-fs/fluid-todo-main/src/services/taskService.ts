import axios from 'axios';
import type { Task, CreateTaskData, UpdateTaskData } from '@/types/task';

const API_BASE_URL = 'http://localhost:8080/api';

export const taskService = {
  async getAllTasks(): Promise<Task[]> {
    const response = await axios.get(`${API_BASE_URL}/tasks`);
    return response.data;
  },

  async getTaskById(id: number): Promise<Task> {
    const response = await axios.get(`${API_BASE_URL}/tasks/${id}`);
    return response.data;
  },

  async createTask(taskData: CreateTaskData): Promise<Task> {
    const response = await axios.post(`${API_BASE_URL}/tasks`, taskData);
    return response.data;
  },

  async updateTask(id: number, taskData: UpdateTaskData): Promise<Task> {
    const response = await axios.put(`${API_BASE_URL}/tasks/${id}`, taskData);
    return response.data;
  },

  async toggleTaskCompletion(id: number): Promise<Task> {
    const response = await axios.patch(`${API_BASE_URL}/tasks/${id}?completed=true`);
    return response.data;
  },

  async deleteTask(id: number): Promise<void> {
    await axios.delete(`${API_BASE_URL}/tasks/${id}`);
  },
};