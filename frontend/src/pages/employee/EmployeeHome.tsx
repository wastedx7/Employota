import { useAuth } from '@/contexts/AuthContext';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { User, Mail, Shield, Calendar } from 'lucide-react';

export const EmployeeHome = () => {
  const { user } = useAuth();

  const infoItems = [
    {
      icon: User,
      label: 'Username',
      value: user?.principal?.username || 'N/A',
    },
    {
      icon: Mail,
      label: 'Email',
      value: user?.principal?.username || 'N/A',
    },
    {
      icon: Shield,
      label: 'Role',
      value: user?.authorities?.includes('ROLE_EMPLOYEE') ? 'Employee' : 'User',
    },
    {
      icon: Calendar,
      label: 'Session Status',
      value: user?.authenticated ? 'Active' : 'Inactive',
    },
  ];

  return (
    <div className="space-y-8 animate-fade-in">
      {/* Welcome section */}
      <div className="text-center sm:text-left">
        <h2 className="text-2xl font-bold text-foreground mb-2">
          Welcome, {user?.principal?.username?.split('@')[0] || 'Employee'}!
        </h2>
        <p className="text-muted-foreground">
          This is your personal dashboard. Here you can view your account information.
        </p>
      </div>

      {/* Info cards */}
      <div className="grid gap-4 sm:grid-cols-2">
        {infoItems.map((item) => (
          <Card key={item.label} className="border-border hover:shadow-md transition-shadow">
            <CardHeader className="flex flex-row items-center gap-4 pb-2">
              <div className="p-2 rounded-lg bg-primary/10">
                <item.icon className="h-5 w-5 text-primary" />
              </div>
              <div>
                <CardTitle className="text-sm font-medium text-muted-foreground">
                  {item.label}
                </CardTitle>
              </div>
            </CardHeader>
            <CardContent>
              <p className="text-lg font-semibold text-foreground truncate">
                {item.value}
              </p>
            </CardContent>
          </Card>
        ))}
      </div>

      {/* Quick info card */}
      <Card className="border-border bg-primary/5">
        <CardContent className="pt-6">
          <div className="flex items-start gap-4">
            <div className="p-3 rounded-full bg-primary/10">
              <Shield className="h-6 w-6 text-primary" />
            </div>
            <div>
              <h3 className="font-semibold text-foreground mb-1">
                Your Account is Secure
              </h3>
              <p className="text-sm text-muted-foreground">
                Your session is active and secured. If you notice any suspicious activity,
                please contact your administrator immediately.
              </p>
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};
