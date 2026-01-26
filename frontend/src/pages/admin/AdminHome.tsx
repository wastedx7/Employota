import { useEffect, useState } from 'react';
import { useAuth } from '@/contexts/AuthContext';
import { adminApi, Employee } from '@/lib/api';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Users, UserCheck, Clock, TrendingUp } from 'lucide-react';

export const AdminHome = () => {
  const { user } = useAuth();
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchEmployees = async () => {
      try {
        const response = await adminApi.getEmployees();
        setEmployees(response.data);
      } catch (error) {
        console.error('Failed to fetch employees:', error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchEmployees();
  }, []);

  const stats = [
    {
      title: 'Total Employees',
      value: employees.length,
      icon: Users,
      color: 'bg-primary/10 text-primary',
    },
    {
      title: 'Active This Month',
      value: employees.filter(e => {
        const createdAt = new Date(e.createdAt);
        const now = new Date();
        return createdAt.getMonth() === now.getMonth() && 
               createdAt.getFullYear() === now.getFullYear();
      }).length,
      icon: UserCheck,
      color: 'bg-success/10 text-success',
    },
    {
      title: 'Admins',
      value: employees.filter(e => e.role === 'ADMIN').length,
      icon: TrendingUp,
      color: 'bg-warning/10 text-warning',
    },
    {
      title: 'Recent Activity',
      value: employees.filter(e => {
        const updatedAt = new Date(e.updatedAt);
        const weekAgo = new Date();
        weekAgo.setDate(weekAgo.getDate() - 7);
        return updatedAt > weekAgo;
      }).length,
      icon: Clock,
      color: 'bg-accent/10 text-accent',
    },
  ];

  return (
    <div className="space-y-8 animate-fade-in">
      {/* Welcome section */}
      <div>
        <h2 className="text-2xl font-bold text-foreground mb-2">
          Welcome back, {user?.principal?.username?.split('@')[0] || 'Admin'}!
        </h2>
        <p className="text-muted-foreground">
          Here's what's happening with your team today.
        </p>
      </div>

      {/* Stats grid */}
      <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-4">
        {stats.map((stat) => (
          <Card key={stat.title} className="border-border hover:shadow-md transition-shadow">
            <CardHeader className="flex flex-row items-center justify-between pb-2">
              <CardTitle className="text-sm font-medium text-muted-foreground">
                {stat.title}
              </CardTitle>
              <div className={`p-2 rounded-lg ${stat.color}`}>
                <stat.icon className="h-4 w-4" />
              </div>
            </CardHeader>
            <CardContent>
              <div className="text-3xl font-bold text-foreground">
                {isLoading ? '...' : stat.value}
              </div>
            </CardContent>
          </Card>
        ))}
      </div>

      {/* Recent employees */}
      <Card className="border-border">
        <CardHeader>
          <CardTitle className="text-lg font-semibold">Recent Employees</CardTitle>
        </CardHeader>
        <CardContent>
          {isLoading ? (
            <div className="text-muted-foreground">Loading...</div>
          ) : employees.length === 0 ? (
            <div className="text-muted-foreground">No employees found.</div>
          ) : (
            <div className="space-y-4">
              {employees.slice(0, 5).map((employee) => (
                <div 
                  key={employee.id} 
                  className="flex items-center justify-between py-2 border-b border-border last:border-0"
                >
                  <div className="flex items-center gap-3">
                    <div className="w-10 h-10 rounded-full bg-primary/10 flex items-center justify-center">
                      <span className="text-primary font-medium text-sm">
                        {employee?.firstName?.[0]}{employee?.lastName?.[0]}
                      </span>
                    </div>
                    <div>
                      <p className="font-medium text-foreground">
                        {employee.firstName} {employee.lastName}
                      </p>
                      <p className="text-sm text-muted-foreground">{employee.email}</p>
                    </div>
                  </div>
                  <span className={`px-2 py-1 rounded-md text-xs font-medium ${
                    employee.role === 'ADMIN' 
                      ? 'bg-primary/10 text-primary' 
                      : 'bg-success/10 text-success'
                  }`}>
                    {employee.role}
                  </span>
                </div>
              ))}
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  );
};
