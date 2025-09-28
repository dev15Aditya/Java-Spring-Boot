import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Textarea } from '@/components/ui/textarea';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Label } from '@/components/ui/label';
import { Plus, X } from 'lucide-react';
import type { CreateTaskData } from '@/types/task';

interface AddTaskFormProps {
  onAddTask: (taskData: CreateTaskData) => void;
  isLoading?: boolean;
}

export const AddTaskForm: React.FC<AddTaskFormProps> = ({ onAddTask, isLoading = false }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [formData, setFormData] = useState<CreateTaskData>({
    title: '',
    description: '',
    dueDate: '',
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (formData.title.trim()) {
      onAddTask(formData);
      setFormData({ title: '', description: '', dueDate: '' });
      setIsOpen(false);
    }
  };

  const handleInputChange = (field: keyof CreateTaskData, value: string) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  if (!isOpen) {
    return (
      <Card className="gradient-card shadow-card hover:shadow-hover transition-smooth">
        <CardContent className="p-6">
          <Button
            onClick={() => setIsOpen(true)}
            variant="add"
            className="w-full"
            size="lg"
          >
            <Plus className="w-5 h-5" />
            Add New Task
          </Button>
        </CardContent>
      </Card>
    );
  }

  return (
    <Card className="gradient-card shadow-card animate-scale-in">
      <CardHeader className="flex flex-row items-center justify-between">
        <CardTitle className="text-lg font-semibold">Add New Task</CardTitle>
        <Button
          onClick={() => setIsOpen(false)}
          variant="ghost"
          size="icon"
        >
          <X className="w-4 h-4" />
        </Button>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="title">Title</Label>
            <Input
              id="title"
              value={formData.title}
              onChange={(e) => handleInputChange('title', e.target.value)}
              placeholder="Enter task title..."
              required
            />
          </div>
          
          <div className="space-y-2">
            <Label htmlFor="description">Description</Label>
            <Textarea
              id="description"
              value={formData.description}
              onChange={(e) => handleInputChange('description', e.target.value)}
              placeholder="Enter task description..."
              rows={3}
            />
          </div>
          
          <div className="space-y-2">
            <Label htmlFor="dueDate">Due Date</Label>
            <Input
              id="dueDate"
              type="date"
              value={formData.dueDate}
              onChange={(e) => handleInputChange('dueDate', e.target.value)}
            />
          </div>
          
          <div className="flex gap-3 pt-2">
            <Button type="submit" disabled={isLoading} className="flex-1">
              {isLoading ? 'Adding...' : 'Add Task'}
            </Button>
            <Button 
              type="button" 
              variant="outline" 
              onClick={() => setIsOpen(false)}
            >
              Cancel
            </Button>
          </div>
        </form>
      </CardContent>
    </Card>
  );
};