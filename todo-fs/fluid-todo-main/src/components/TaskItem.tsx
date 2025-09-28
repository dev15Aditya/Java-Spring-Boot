import React, { useState } from 'react';
import { useSortable } from '@dnd-kit/sortable';
import { CSS } from '@dnd-kit/utilities';
import { Card, CardContent } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Textarea } from '@/components/ui/textarea';
import { Checkbox } from '@/components/ui/checkbox';
import { GripVertical, Edit, Trash2, Check, X, Calendar } from 'lucide-react';
import type { Task } from '@/types/task';

interface TaskItemProps {
  task: Task;
  onToggleComplete: (id: number) => void;
  onUpdateTask: (id: number, updates: Partial<Task>) => void;
  onDeleteTask: (id: number) => void;
  isDragging?: boolean;
}

export const TaskItem: React.FC<TaskItemProps> = ({
  task,
  onToggleComplete,
  onUpdateTask,
  onDeleteTask,
  isDragging = false,
}) => {
  const [isEditing, setIsEditing] = useState(false);
  const [editData, setEditData] = useState({
    title: task.title,
    description: task.description,
    dueDate: task.dueDate,
  });

  const {
    attributes,
    listeners,
    setNodeRef,
    transform,
    transition,
    isDragging: isSortableDragging,
  } = useSortable({ id: task.id });

  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
  };

  const handleSaveEdit = () => {
    onUpdateTask(task.id, editData);
    setIsEditing(false);
  };

  const handleCancelEdit = () => {
    setEditData({
      title: task.title,
      description: task.description,
      dueDate: task.dueDate,
    });
    setIsEditing(false);
  };

  const formatDate = (dateString: string) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { 
      month: 'short', 
      day: 'numeric', 
      year: 'numeric' 
    });
  };

  const isOverdue = (dateString: string) => {
    if (!dateString) return false;
    const dueDate = new Date(dateString);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    return dueDate < today && !task.completed;
  };

  return (
    <div
      ref={setNodeRef}
      style={style}
      className={`
        ${isSortableDragging || isDragging ? 'task-drag' : ''}
        animate-slide-up
      `}
    >
      <Card className={`
        gradient-card shadow-card hover:shadow-hover transition-smooth group
        ${task.completed ? 'opacity-75' : ''}
        ${isOverdue(task.dueDate) ? 'ring-2 ring-destructive/50' : ''}
      `}>
        <CardContent className="p-4">
          <div className="flex items-start gap-3">
            {/* Drag Handle */}
            <div
              {...attributes}
              {...listeners}
              className="flex-shrink-0 mt-1 p-1 rounded cursor-grab active:cursor-grabbing opacity-50 group-hover:opacity-100 transition-opacity"
            >
              <GripVertical className="w-4 h-4 text-muted-foreground" />
            </div>

            {/* Checkbox */}
            <div className="flex-shrink-0 mt-1">
              <Checkbox
                checked={task.completed}
                onCheckedChange={() => onToggleComplete(task.id)}
                className="data-[state=checked]:gradient-success"
              />
            </div>

            {/* Task Content */}
            <div className="flex-1 min-w-0">
              {isEditing ? (
                <div className="space-y-3">
                  <Input
                    value={editData.title}
                    onChange={(e) => setEditData(prev => ({ ...prev, title: e.target.value }))}
                    placeholder="Task title..."
                    className="font-medium"
                  />
                  <Textarea
                    value={editData.description}
                    onChange={(e) => setEditData(prev => ({ ...prev, description: e.target.value }))}
                    placeholder="Task description..."
                    rows={2}
                  />
                  <Input
                    type="date"
                    value={editData.dueDate}
                    onChange={(e) => setEditData(prev => ({ ...prev, dueDate: e.target.value }))}
                  />
                  <div className="flex gap-2">
                    <Button size="sm" onClick={handleSaveEdit}>
                      <Check className="w-3 h-3" />
                      Save
                    </Button>
                    <Button size="sm" variant="outline" onClick={handleCancelEdit}>
                      <X className="w-3 h-3" />
                      Cancel
                    </Button>
                  </div>
                </div>
              ) : (
                <>
                  <h3 className={`
                    font-medium text-sm leading-tight mb-2
                    ${task.completed ? 'line-through text-muted-foreground' : ''}
                  `}>
                    {task.title}
                  </h3>
                  
                  {task.description && (
                    <p className={`
                      text-sm text-muted-foreground mb-2 leading-relaxed
                      ${task.completed ? 'line-through' : ''}
                    `}>
                      {task.description}
                    </p>
                  )}
                  
                  {task.dueDate && (
                    <div className={`
                      flex items-center gap-1 text-xs
                      ${isOverdue(task.dueDate) ? 'text-destructive' : 'text-muted-foreground'}
                      ${task.completed ? 'line-through' : ''}
                    `}>
                      <Calendar className="w-3 h-3" />
                      {formatDate(task.dueDate)}
                      {isOverdue(task.dueDate) && (
                        <span className="text-destructive font-medium ml-1">Overdue</span>
                      )}
                    </div>
                  )}
                </>
              )}
            </div>

            {/* Action Buttons */}
            {!isEditing && (
              <div className="flex-shrink-0 flex gap-1 opacity-0 group-hover:opacity-100 transition-opacity">
                <Button
                  size="icon"
                  variant="ghost"
                  onClick={() => setIsEditing(true)}
                  className="h-8 w-8"
                >
                  <Edit className="w-3 h-3" />
                </Button>
                <Button
                  size="icon"
                  variant="ghost"
                  onClick={() => onDeleteTask(task.id)}
                  className="h-8 w-8 text-destructive hover:text-destructive"
                >
                  <Trash2 className="w-3 h-3" />
                </Button>
              </div>
            )}
          </div>
        </CardContent>
      </Card>
    </div>
  );
};