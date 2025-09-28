import React, { useState, useEffect } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { AddTaskForm } from './AddTaskForm';
import { TaskList } from './TaskList';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { CheckCircle, Clock, ListTodo, Target } from 'lucide-react';
import { taskService } from '@/services/taskService';
import { useToast } from '@/hooks/use-toast';
import type { Task, CreateTaskData } from '@/types/task';

export const TodoApp: React.FC = () => {
  // const [localTasks, setLocalTasks] = useState<Task[]>([]);
  const queryClient = useQueryClient();
  const { toast } = useToast();

  // Fetch all tasks
  const { data: localTasks = [], isLoading, error } = useQuery({
    queryKey: ['tasks'],
    queryFn: taskService.getAllTasks,
  });

  // Update local tasks when server data changes
  // useEffect(() => {
  //   setLocalTasks(tasks);
  // }, [tasks]);

  // Add task mutation
  const addTaskMutation = useMutation({
    mutationFn: taskService.createTask,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['tasks'] });
      toast({
        title: 'Task added',
        description: 'Your new task has been created successfully.',
      });
    },
    onError: () => {
      toast({
        title: 'Error',
        description: 'Failed to add task. Please try again.',
        variant: 'destructive',
      });
    },
  });

  // Toggle complete mutation
  const toggleCompleteMutation = useMutation({
    mutationFn: taskService.toggleTaskCompletion,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['tasks'] });
      toast({
        title: 'Task updated',
        description: 'Task completion status has been updated.',
      });
    },
    onError: () => {
      toast({
        title: 'Error',
        description: 'Failed to update task. Please try again.',
        variant: 'destructive',
      });
    },
  });

  // Update task mutation
  const updateTaskMutation = useMutation({
    mutationFn: ({ id, updates }: { id: number; updates: Partial<Task> }) =>
      taskService.updateTask(id, updates),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['tasks'] });
      toast({
        title: 'Task updated',
        description: 'Your task has been updated successfully.',
      });
    },
    onError: () => {
      toast({
        title: 'Error',
        description: 'Failed to update task. Please try again.',
        variant: 'destructive',
      });
    },
  });

  // Delete task mutation
  const deleteTaskMutation = useMutation({
    mutationFn: taskService.deleteTask,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['tasks'] });
      toast({
        title: 'Task deleted',
        description: 'Your task has been deleted successfully.',
      });
    },
    onError: () => {
      toast({
        title: 'Error',
        description: 'Failed to delete task. Please try again.',
        variant: 'destructive',
      });
    },
  });

  const handleAddTask = (taskData: CreateTaskData) => {
    addTaskMutation.mutate(taskData);
  };

  const handleToggleComplete = (id: number) => {
    toggleCompleteMutation.mutate(id);
  };

  const handleUpdateTask = (id: number, updates: Partial<Task>) => {
    updateTaskMutation.mutate({ id, updates });
  };

  const handleDeleteTask = (id: number) => {
    deleteTaskMutation.mutate(id);
  };

  const handleTasksReorder = (reorderedTasks: Task[]) => {
    // setLocalTasks(reorderedTasks);
    // Note: Since the API doesn't have ordering, we only update local state
    // In a real app, you'd want to persist the order to the backend
  };

  // Filter tasks
  const completedTasks = localTasks.filter(task => task.completed);
  const pendingTasks = localTasks.filter(task => !task.completed);
  const overdueTasks = pendingTasks.filter(task => {
    if (!task.dueDate) return false;
    const dueDate = new Date(task.dueDate);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    return dueDate < today;
  });

  if (error) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <Card className="gradient-card shadow-card">
          <CardContent className="p-8 text-center">
            <div className="text-destructive text-lg font-semibold mb-2">
              Connection Error
            </div>
            <div className="text-muted-foreground mb-4">
              Unable to connect to the backend at localhost:8080
            </div>
            <Button onClick={() => window.location.reload()}>
              Try Again
            </Button>
          </CardContent>
        </Card>
      </div>
    );
  }

  return (
    <div className="min-h-screen py-8 px-4">
      <div className="max-w-4xl mx-auto">
        {/* Header */}
        <header className="text-center mb-8">
          <h1 className="text-4xl font-bold gradient-primary bg-clip-text text-transparent mb-2">
            TodoMaster
          </h1>
          <p className="text-muted-foreground text-lg">
            Organize your life with beautiful drag & drop tasks
          </p>
        </header>

        {/* Stats Cards */}
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
          <Card className="gradient-card shadow-card">
            <CardContent className="p-4 text-center">
              <ListTodo className="w-8 h-8 mx-auto mb-2 text-primary" />
              <div className="text-2xl font-bold">{localTasks.length}</div>
              <div className="text-sm text-muted-foreground">Total Tasks</div>
            </CardContent>
          </Card>
          
          <Card className="gradient-card shadow-card">
            <CardContent className="p-4 text-center">
              <Clock className="w-8 h-8 mx-auto mb-2 text-accent" />
              <div className="text-2xl font-bold">{pendingTasks.length}</div>
              <div className="text-sm text-muted-foreground">Pending</div>
            </CardContent>
          </Card>
          
          <Card className="gradient-card shadow-card">
            <CardContent className="p-4 text-center">
              <CheckCircle className="w-8 h-8 mx-auto mb-2 text-success" />
              <div className="text-2xl font-bold">{completedTasks.length}</div>
              <div className="text-sm text-muted-foreground">Completed</div>
            </CardContent>
          </Card>
          
          <Card className="gradient-card shadow-card">
            <CardContent className="p-4 text-center">
              <Target className="w-8 h-8 mx-auto mb-2 text-destructive" />
              <div className="text-2xl font-bold">{overdueTasks.length}</div>
              <div className="text-sm text-muted-foreground">Overdue</div>
            </CardContent>
          </Card>
        </div>

        {/* Add Task Form */}
        <div className="mb-8">
          <AddTaskForm
            onAddTask={handleAddTask}
            isLoading={addTaskMutation.isPending}
          />
        </div>

        {/* Task Lists */}
        <Tabs defaultValue="all" className="w-full">
          <TabsList className="grid w-full grid-cols-4">
            <TabsTrigger value="all" className="flex items-center gap-2">
              <ListTodo className="w-4 h-4" />
              All
              <Badge variant="secondary">{localTasks.length}</Badge>
            </TabsTrigger>
            <TabsTrigger value="pending" className="flex items-center gap-2">
              <Clock className="w-4 h-4" />
              Pending
              <Badge variant="secondary">{pendingTasks.length}</Badge>
            </TabsTrigger>
            <TabsTrigger value="completed" className="flex items-center gap-2">
              <CheckCircle className="w-4 h-4" />
              Completed
              <Badge variant="secondary">{completedTasks.length}</Badge>
            </TabsTrigger>
            <TabsTrigger value="overdue" className="flex items-center gap-2">
              <Target className="w-4 h-4" />
              Overdue
              <Badge variant="destructive">{overdueTasks.length}</Badge>
            </TabsTrigger>
          </TabsList>

          <TabsContent value="all" className="mt-6">
            {isLoading ? (
              <div className="text-center py-8">
                <div className="text-muted-foreground">Loading tasks...</div>
              </div>
            ) : (
              <TaskList
                tasks={localTasks}
                onTasksReorder={handleTasksReorder}
                onToggleComplete={handleToggleComplete}
                onUpdateTask={handleUpdateTask}
                onDeleteTask={handleDeleteTask}
              />
            )}
          </TabsContent>

          <TabsContent value="pending" className="mt-6">
            <TaskList
              tasks={pendingTasks}
              onTasksReorder={handleTasksReorder}
              onToggleComplete={handleToggleComplete}
              onUpdateTask={handleUpdateTask}
              onDeleteTask={handleDeleteTask}
            />
          </TabsContent>

          <TabsContent value="completed" className="mt-6">
            <TaskList
              tasks={completedTasks}
              onTasksReorder={handleTasksReorder}
              onToggleComplete={handleToggleComplete}
              onUpdateTask={handleUpdateTask}
              onDeleteTask={handleDeleteTask}
            />
          </TabsContent>

          <TabsContent value="overdue" className="mt-6">
            <TaskList
              tasks={overdueTasks}
              onTasksReorder={handleTasksReorder}
              onToggleComplete={handleToggleComplete}
              onUpdateTask={handleUpdateTask}
              onDeleteTask={handleDeleteTask}
            />
          </TabsContent>
        </Tabs>
      </div>
    </div>
  );
};