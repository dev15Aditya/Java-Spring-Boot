export interface Task {
  id: number;
  title: string;
  description: string;
  dueDate: string;
  completed: boolean;
}

export interface CreateTaskData {
  title: string;
  description: string;
  dueDate: string;
}

export interface UpdateTaskData {
  title?: string;
  description?: string;
  dueDate?: string;
  completed?: boolean;
}